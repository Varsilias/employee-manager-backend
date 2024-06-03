package com.danielokoronkwo.employeemanager.v1.auth;

import com.danielokoronkwo.employeemanager.common.config.DatabaseConfig;
import com.danielokoronkwo.employeemanager.common.config.JwtConfig;
import com.danielokoronkwo.employeemanager.common.constants.MessageConstants;
import com.danielokoronkwo.employeemanager.common.dto.SuccessResponseDto;
import com.danielokoronkwo.employeemanager.v1.auth.dto.SignInRequestDto;
import com.danielokoronkwo.employeemanager.v1.auth.dto.SignUpRequestDto;
import com.danielokoronkwo.employeemanager.v1.auth.user.UserEntity;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService, DatabaseConfig databaseConfig, JwtConfig jwtConfig) {
        this.authService = authService;

    }

    @PostMapping("/auth/signup")
    public ResponseEntity<SuccessResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        logger.info(signUpRequestDto.toString());

        UserEntity userEntity = authService.signUpUser(signUpRequestDto);
        SuccessResponseDto successResponseDto = new SuccessResponseDto(MessageConstants.SUCCESS, "User registered successfully",null, userEntity);

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SuccessResponseDto> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        logger.info(signInRequestDto.toString());

        Map<String, Object> response = authService.signInUser(signInRequestDto);
        SuccessResponseDto successResponseDto = new SuccessResponseDto(MessageConstants.SUCCESS, "Login Successfully", null, response);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<SuccessResponseDto> me(Principal principal) {
        logger.info("Principal {}", principal.getName());
        UserEntity userProfile = authService.getUserProfile(principal.getName());

        SuccessResponseDto successResponseDto = new SuccessResponseDto(MessageConstants.SUCCESS, "Profile retrieved Successfully", null, userProfile);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}
