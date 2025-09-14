package co.com.crediya.model.report.exceptions.enums;

public enum ExceptionStatusCode {
    BAD_REQUEST("400-BD"),
    BAD_REQUEST_VALIDATE("400-VD"),
    CONFLICT("409"),
    FIELDS_BAD_REQUEST("400-BD-FIELDS"),
    INTERNAL_SERVER_ERROR("500-ISE"),
    OK("200-OK"),
    FORBIDDEN("403"),
    UNAUTHORIZED("401");

    private final String statusCode;

    ExceptionStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String status() {
        return statusCode;
    }
}
