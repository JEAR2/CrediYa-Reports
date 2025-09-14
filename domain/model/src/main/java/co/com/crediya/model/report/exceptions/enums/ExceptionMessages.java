package co.com.crediya.model.report.exceptions.enums;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    REQUEST_ALREADY_APPROVED("The request has already been approved."),
    CREDENTIALS_NOT_FOUND("Credentials not found."),
    DO_NOT_ACCESS_RESOURCE("Doesn't have access to this resource."),
    UNAUTHORIZED_SENT_TOKEN_INVALID("Sent token is invalid."),
    EXPIRED_TOKEN("Expired token!");
    private String message;

    ExceptionMessages(String message) {
        this.message = message;
    }
}
