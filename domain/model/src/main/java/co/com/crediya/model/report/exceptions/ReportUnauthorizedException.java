package co.com.crediya.model.report.exceptions;


import co.com.crediya.model.report.exceptions.enums.ExceptionStatusCode;

public class ReportUnauthorizedException extends ReportException {

    public ReportUnauthorizedException(String message) {
        super(ExceptionStatusCode.UNAUTHORIZED, message,401);
    }
}
