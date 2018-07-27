package com.elpmid.todo.dto;

public class Error {

    /**
     * The name of the service, class or other identifier
     * for where the error occurred to better provide an
     * understanding of what the issue was
     */
    private String domain;

    /**
     * A unique identifier for the error, or the specific
     * type of error. For example, “InvalidParameter.”
     */
    private String reason;

    /**
     * A description of the error in a human-readable
     * format.
     */
    private String message;

    /**
     * The location of the error with interpretation
     * determined by the location type (In the case of a
     * parameter error, it might be the form field where
     * the error occurred, such as “firstName.”)
     */
    private String location;

    /**
     * Determines how the client should interpret the
     * locationType property (For example, if the locationType is
     * specific to a form field/ query parameter, you
     * would use “parameter” to describe the
     * locationType.)
     */
    private String locationType;

    public Error(String domain, String reason, String message, String location, String locationType) {
        this.domain = domain;
        this.reason = reason;
        this.message = message;
        this.location = location;
        this.locationType = locationType;
    }

    public String getDomain() {
        return domain;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationType() {
        return locationType;
    }
}
