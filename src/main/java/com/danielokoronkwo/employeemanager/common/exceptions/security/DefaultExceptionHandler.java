package com.danielokoronkwo.employeemanager.common.exceptions.security;

import com.danielokoronkwo.employeemanager.common.constants.MessageConstants;
import com.danielokoronkwo.employeemanager.common.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ErrorMessageDto> handleAuthenticationException(AuthenticationException ex) {

        String message = "Unauthorised";
        List<String> error = Collections.singletonList(message);
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, message, error,
                null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessageDto);
    }

}
