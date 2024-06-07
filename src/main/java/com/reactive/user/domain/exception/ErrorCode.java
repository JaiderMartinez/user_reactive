package com.reactive.user.domain.exception;

public enum ErrorCode {
    USER_NOT_FOUND(204),
    ;

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
