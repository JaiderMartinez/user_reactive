package com.reactive.user.infrastructure.dto.response;


import lombok.Builder;

@Builder(toBuilder = true)
public record ErrorResponseDto(
        String message
) {
}
