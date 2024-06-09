package com.reactive.user.domain.exception;

import lombok.Getter;

@Getter
public enum CodeException {
    USERS_NOT_FOUND("Users not found"),
    ;

    private final String message;

    CodeException(String message) {
        this.message = message;
    }
}
