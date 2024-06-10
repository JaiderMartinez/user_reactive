package com.reactive.user.infrastructure.exception;

import com.reactive.user.application.exception.CoreException;
import com.reactive.user.domain.exception.CodeException;
import com.reactive.user.infrastructure.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErrorController {
    private static final String LOGGER_PREFIX = String.format("[%s] ", ErrorController.class.getSimpleName());
    private static final Map<CodeException, HttpStatus> HTTP_STATUS_BY_CODE_EXCEPTION = Map.ofEntries(
            Map.entry(CodeException.USERS_NOT_FOUND, HttpStatus.NO_CONTENT),
            Map.entry(CodeException.NAME_USER_INVALID, HttpStatus.BAD_REQUEST),
            Map.entry(CodeException.EMAIL_USER_INVALID, HttpStatus.BAD_REQUEST),
            Map.entry(CodeException.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT),
            Map.entry(CodeException.PHONE_USER_INVALID, HttpStatus.BAD_REQUEST),
            Map.entry(CodeException.USER_NOT_FOUND, HttpStatus.NOT_FOUND),
            Map.entry(CodeException.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    );

    @ExceptionHandler(CoreException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerCoreException(final CoreException coreException) {
        return genericHandleException(coreException.getCodeException());
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerException(final Exception exception) {
        log.error(LOGGER_PREFIX + "[handlerException] {}", exception.getMessage(), exception);
        return genericHandleException(CodeException.INTERNAL_SERVER_ERROR);
    }

    public Mono<ResponseEntity<ErrorResponseDto>> genericHandleException(CodeException codeException) {
        return Mono.defer(() -> Mono.just(buildErrorResponseEntity(codeException)));
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponseEntity(CodeException errorCode) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
        final HttpStatus status = HTTP_STATUS_BY_CODE_EXCEPTION
                .getOrDefault(errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(status)
                .body(errorResponseDto);
    }
}
