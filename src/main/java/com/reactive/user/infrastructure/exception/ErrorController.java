package com.reactive.user.infrastructure.exception;

import com.reactive.user.application.exception.CoreException;
import com.reactive.user.domain.exception.CodeException;
import com.reactive.user.infrastructure.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
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
            Map.entry(CodeException.INVALID_PARAMETERS, HttpStatus.BAD_REQUEST),
            Map.entry(CodeException.USER_NOT_FOUND, HttpStatus.NOT_FOUND),
            Map.entry(CodeException.DB_INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR),
            Map.entry(CodeException.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    );

    @ExceptionHandler(CoreException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerCoreException(final CoreException coreException) {
        return Mono.just(coreException)
                .flatMap(coreExceptionS -> {
                    final CodeException codeException = coreException.getCodeException();
                    final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                            .code(codeException.name())
                            .message(coreExceptionS.getMessage())
                            .build();
                    final HttpStatus status = HTTP_STATUS_BY_CODE_EXCEPTION
                            .getOrDefault(codeException, HttpStatus.NOT_EXTENDED);
                    return Mono.just(new ResponseEntity<>(errorResponseDto, status));
                });
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerException(final Exception exception) {
        return Mono.just(CodeException.DB_INTERNAL)
                .doOnNext(codeException -> log
                        .error(LOGGER_PREFIX + "[handlerException] {}", exception.getMessage(), exception))
                .flatMap(codeException -> Mono.just(this.buildErrorResponseEntity(codeException)));
    }

    @ExceptionHandler(UncategorizedMongoDbException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerUncategorizedMongoDbException(
            final UncategorizedMongoDbException uncategorizedMongoDbException) {
        return Mono.just(CodeException.DB_INTERNAL)
                .flatMap(codeException -> Mono.just(this.buildErrorResponseEntity(codeException)));
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponseEntity(final CodeException codeException) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(codeException.name())
                .message(codeException.getMessage())
                .build();
        final HttpStatus status = HTTP_STATUS_BY_CODE_EXCEPTION
                .getOrDefault(codeException, HttpStatus.NOT_EXTENDED);
        return new ResponseEntity<>(errorResponseDto, status);
    }
}
