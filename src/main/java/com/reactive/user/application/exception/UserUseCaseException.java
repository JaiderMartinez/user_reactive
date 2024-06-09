package com.reactive.user.application.exception;

import com.reactive.user.domain.exception.CodeException;

public class UserUseCaseException extends CoreException {
    public UserUseCaseException(final CodeException codeException, Exception exception) {
        super(codeException, exception);
    }
}
