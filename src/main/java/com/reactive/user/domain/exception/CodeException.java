package com.reactive.user.domain.exception;

import lombok.Getter;

@Getter
public enum CodeException {
    USERS_NOT_FOUND("Users not found"),
    USER_NOT_FOUND("User not found"),
    INTERNAL_SERVER_ERROR("An error occurred"),
    INVALID_PARAMETERS("Invalid %s parameters"),
    DB_INTERNAL("Service DB failed");

    private final String message;

    CodeException(final String message) {
        this.message = message;
    }
}
