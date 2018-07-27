package com.elpmid.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoNotFoundException extends APIException {

    public TodoNotFoundException(String code) {
        super(code);
    }
}
