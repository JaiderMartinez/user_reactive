package com.reactive.user.application.exception;

import com.reactive.user.domain.exception.CodeException;
import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {
    private final CodeException codeException;

    public CoreException(final CodeException codeException, final Exception exception) {
        super(codeException.getMessage(), exception);
        this.codeException = codeException;
    }
}
