package com.reactive.user.domain.exception;

import lombok.Getter;

@Getter
public enum CodeException {
    USERS_NOT_FOUND("Users not found"),
    NAME_USER_INVALID("Incorrect user name"),
    EMAIL_USER_INVALID("Incorrect user email"),
    EMAIL_ALREADY_EXISTS("The email exists"),
    PHONE_USER_INVALID("The phone is invalid"),
    USER_NOT_FOUND("User not found"),
    INTERNAL_SERVER_ERROR("An error occurred"),
    ;

    private final String message;

    CodeException(String message) {
        this.message = message;
    }
}
