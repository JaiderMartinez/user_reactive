package com.reactive.user.application.exception;

import com.reactive.user.domain.exception.CoreException;
import com.reactive.user.domain.exception.ErrorCode;

public class NotFoundException extends CoreException {
    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
