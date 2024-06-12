package com.reactive.user.application.exception;

import com.reactive.user.domain.exception.CodeException;
import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {
    private final CodeException codeException;

    protected CoreException(final CodeException codeException, final Exception exception, final String... fields) {
        super(getMessage(codeException, fields), exception);
        this.codeException = codeException;
    }

    private static String getMessage(final CodeException codeException, final String... fields) {
        return fields.length > 0
                ? String.format(codeException.getMessage(), (Object[]) fields)
                : codeException.getMessage();
    }
}
