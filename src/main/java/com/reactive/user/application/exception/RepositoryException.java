package com.reactive.user.application.exception;

import com.reactive.user.domain.exception.CodeException;

public class RepositoryException extends CoreException {
    public RepositoryException(final CodeException codeException, final Exception exception) {
        super(codeException, exception);
    }
}
