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
            Map.entry(CodeException.USERS_NOT_FOUND, HttpStatus.NO_CONTENT)
    );

    @ExceptionHandler(CoreException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerParkingException(final CoreException coreException) {
        return genericHandleException(coreException.getCodeException());
    }

    public Mono<ResponseEntity<ErrorResponseDto>> genericHandleException(CodeException codeException) {
        log.error(LOGGER_PREFIX + "[genericHandleException] {}", codeException.getMessage());
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
