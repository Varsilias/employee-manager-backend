package com.danielokoronkwo.employeemanager.common.exceptions;

import com.danielokoronkwo.employeemanager.common.constants.MessageConstants;
import com.danielokoronkwo.employeemanager.common.dto.ErrorMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, errors.get(0), errors, null);

        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        // We create our own message because the generated error message is not helpful
        // to the client
        String message = "Invalid request body";
        List<String> error = Collections.singletonList(message);
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, message, error, null);

        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleResourceNotFoundException(ResourceNotFoundException ex,
            HttpServletRequest request) {
        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
            HttpServletRequest request) {
        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorMessageDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageDto> handleBadRequestException(BadRequestException ex,
            HttpServletRequest request) {
        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<ErrorMessageDto> handleUnauthorisedException(UnauthorisedException ex,
            HttpServletRequest request) {
        List<String> error = Collections.singletonList(ex.getMessage());
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, ex.getMessage(), error, null);
        return new ResponseEntity<>(errorMessageDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageDto> handleIllegalArgumentException(IllegalArgumentException ex,
            HttpServletRequest request) {
        logger.info("IllegalArgumentException Trace: {}", ex.getMessage());
        String message = "Something went wrong on our server, we are fixing it.";
        List<String> error = Collections.singletonList(message);
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(MessageConstants.ERROR, message, error, null);
        return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
