package com.elpmid.todo.exception;

public class APIException extends RuntimeException {

    private String code;

    public APIException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
