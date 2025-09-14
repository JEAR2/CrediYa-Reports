package co.com.crediya.model.report.exceptions;


import co.com.crediya.model.report.exceptions.enums.ExceptionStatusCode;

public class ReportForbiddenException extends ReportException {
    public ReportForbiddenException(String message) {
        super(ExceptionStatusCode.FORBIDDEN, message,403);
    }
}
