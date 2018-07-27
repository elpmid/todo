package com.elpmid.todo.dto;

import java.util.List;

public class APIError {

    /**
     * An integer representing the HTTP status error httpStatusCode
     */
    int httpStatusCode;

    /**
     * A brief description of what the overall,
     * generalized error is. You will be able to explain
     * exactly what caused the overall error in the
     * errors array later.
     */
    String message;

    /**
     * An array containing all of the errors that
     * occurred. For example, if the form failed
     * because of missing data, you could list out
     * which fields are missing here with an error
     * message for each of them.
     */
    List<Error> errors;

    public APIError(int httpStatusCode, String message, List<Error> errors) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.errors = errors;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<Error> getErrors() {
        return errors;
    }


}
