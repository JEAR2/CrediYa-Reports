package co.com.crediya.model.report.exceptions;

import co.com.crediya.model.report.exceptions.enums.ExceptionStatusCode;

public class ReportBadReportException extends ReportException {
    public ReportBadReportException(String message) {
        super(ExceptionStatusCode.BAD_REQUEST_VALIDATE, message,400);
    }
}
