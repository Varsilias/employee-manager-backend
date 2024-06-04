package com.danielokoronkwo.employeemanager.common.filters;

import com.danielokoronkwo.employeemanager.common.constants.MessageConstants;
import com.danielokoronkwo.employeemanager.common.dto.ErrorMessageDto;
import com.danielokoronkwo.employeemanager.common.exceptions.UnauthorisedException;
import com.danielokoronkwo.employeemanager.v1.auth.JwtService;
import com.danielokoronkwo.employeemanager.v1.auth.user.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get authorization header and validate
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String email = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                email = jwtService.getAccessTokenSubject(token);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtService.validateAccessToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken accessToken = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), null, userDetails.getAuthorities());
                    accessToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(accessToken);

                }
            }
        } catch (ExpiredJwtException ex) {
            String message = "Token Expired, you can request a new access token";
            List<String> error = Collections.singletonList(message);
            ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, message, error,
                    null);

            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(errorMessageDto));
            return;
        }

        filterChain.doFilter(request, response);

    }
}
