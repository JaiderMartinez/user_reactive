package com.reactive.user.infrastructure.dto.request;

public record UserPartialUpdateRequestDto(
        String email,
        String phone,
        String address
) {
}
