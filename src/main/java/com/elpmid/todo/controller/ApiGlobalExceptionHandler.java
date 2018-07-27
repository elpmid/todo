package com.elpmid.todo.controller;

import com.elpmid.todo.dto.APIError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.elpmid.todo.dto.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.elpmid.todo.exception.APIException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<Error> errors = ex.getBindingResult().getFieldErrors().stream().map(
                fieldError -> new Error(
                        fieldError.getObjectName(),
                        "ValidationFailure",
                        fieldError.getDefaultMessage(),
                        fieldError.getField(),
                        "field")

        ).collect(Collectors.toList());

        String message = "One or more validation failures occurred. Check errors array for details.";

        APIError apiError = new APIError(
                status.value(),
                message,
                errors
        );
        return new ResponseEntity<>(apiError, status);
    }


    @ExceptionHandler(APIException.class)
    protected ResponseEntity<Object> handleAPIException(
            APIException ex) {

        ResponseStatus customStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = (customStatus == null) ? HttpStatus.BAD_REQUEST : customStatus.value();

        List<Error> errors = new ArrayList<>();
        errors.add(
                new Error(
                        ex.getClass().getSimpleName(),
                        "BusinessRuleFailure",
                        ex.getCode(),
                        null,
                        "resource"
                )
        );

        String message = "One or more business rules failed. Check errors array for details.";

        APIError apiError = new APIError(
                status.value(),
                message,
                errors
        );
        return new ResponseEntity<>(apiError, status);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(
            Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String message = "A System Error Occurred. Contact Support";

        APIError apiError = new APIError(
                status.value(),
                message,
                null
        );
        return new ResponseEntity<>(apiError, status);
    }

}
