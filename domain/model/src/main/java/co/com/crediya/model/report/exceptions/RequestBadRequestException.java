package co.com.crediya.model.report.exceptions;

import co.com.crediya.model.report.exceptions.enums.ExceptionStatusCode;

public class RequestBadRequestException extends RequestException {
    public RequestBadRequestException(String message) {
        super(ExceptionStatusCode.BAD_REQUEST_VALIDATE, message,400);
    }
}
