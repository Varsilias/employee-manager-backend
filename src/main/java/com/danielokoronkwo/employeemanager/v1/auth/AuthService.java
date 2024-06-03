package com.danielokoronkwo.employeemanager.v1.auth;

import com.danielokoronkwo.employeemanager.common.exceptions.BadRequestException;
import com.danielokoronkwo.employeemanager.common.exceptions.ResourceNotFoundException;
import com.danielokoronkwo.employeemanager.common.exceptions.UserAlreadyExistsException;
import com.danielokoronkwo.employeemanager.v1.auth.dto.SignInRequestDto;
import com.danielokoronkwo.employeemanager.v1.auth.dto.SignUpRequestDto;
import com.danielokoronkwo.employeemanager.v1.auth.user.UserEntity;
import com.danielokoronkwo.employeemanager.v1.auth.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getUserProfile(String email) {
        Optional<UserEntity> userEntity = userService.getUser(email);
        return  userEntity.orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
    }

    public Map<String, Object> signInUser(SignInRequestDto signInRequestDto) {
        Optional<UserEntity> user = userService.getUser(signInRequestDto.getEmail());

        logger.info("Signing In User: {}", signInRequestDto.getEmail());

        if (user.isEmpty()) {
            throw new BadRequestException("Invalid login credentials");
        }

        boolean isPasswordMatch = comparePassword(user.orElseThrow().getPassword(), signInRequestDto.getPassword());

        if (!isPasswordMatch) {
            throw new BadRequestException("Invalid login credentials");
        }

        String accessToken = jwtService.generateAccessToken(signInRequestDto.getEmail());
        String refreshToken = jwtService.generateRefreshToken(signInRequestDto.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("user", user);

        return response;
    }

    public UserEntity signUpUser(SignUpRequestDto signUpRequestDto) {
        boolean userExists = userService.getUser(signUpRequestDto.getEmail()).isPresent();

        logger.info("Signing In User: {}", signUpRequestDto.getEmail());


        if (userExists) {
            throw new UserAlreadyExistsException("User with email " + signUpRequestDto.getEmail() + " already exists");
        }
        UserEntity userEntity = new UserEntity();
        // TODO: hash password
        String passwordHash = generatePasswordHash(signUpRequestDto.getPassword());

        userEntity.setEmail(signUpRequestDto.getEmail());
        userEntity.setFirstName(signUpRequestDto.getFirstName());
        userEntity.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        userEntity.setLastName(signUpRequestDto.getLastName());
        userEntity.setPassword(passwordHash);

        return userService.createUser(userEntity);
    }

    private String generatePasswordHash(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean comparePassword(String hashedPassword, String password) throws IllegalArgumentException {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
