package co.com.crediya.model.report.exceptions;


import co.com.crediya.model.report.exceptions.enums.ExceptionStatusCode;

public class RequestUnauthorizedException extends RequestException {

    public RequestUnauthorizedException(String message) {
        super(ExceptionStatusCode.UNAUTHORIZED, message,401);
    }
}
