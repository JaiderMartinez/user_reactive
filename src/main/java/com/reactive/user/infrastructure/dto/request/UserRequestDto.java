package com.reactive.user.infrastructure.dto.request;

public record UserRequestDto(
        String email,
        String name,
        String phone,
        String address
) {
}
