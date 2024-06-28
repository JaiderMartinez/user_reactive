package com.reactive.user.application.exception;

import com.reactive.user.domain.exception.CodeException;

public class UserUseCaseException extends CoreException {
    public UserUseCaseException(final CodeException codeException, final Exception exception, final String... fields) {
        super(codeException, exception, fields);
    }
}
