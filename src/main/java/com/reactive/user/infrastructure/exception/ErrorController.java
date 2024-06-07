package com.reactive.user.infrastructure.exception;

import com.reactive.user.domain.exception.CoreException;
import com.reactive.user.domain.exception.ErrorCode;
import com.reactive.user.infrastructure.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(CoreException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlerParkingException(CoreException e) {
        return genericHandleException(e.getErrorCode(), e);
    }

    public Mono<ResponseEntity<ErrorResponseDto>> genericHandleException(ErrorCode errorCode, Exception e) {
        return Mono.defer(() -> Mono.just(getErrorEntity(errorCode, e)));
    }

    private ResponseEntity<ErrorResponseDto> getErrorEntity(ErrorCode errorCode, Exception e) {
        HttpStatus status = HttpStatus.valueOf(errorCode.getCode());
        return ResponseEntity.status(status)
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())//TODO cambiar message
                        .build());
    }
}
