package com.reactive.user.domain.exception;

public class CoreException extends RuntimeException {

    private final ErrorCode errorCode;

    public CoreException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
