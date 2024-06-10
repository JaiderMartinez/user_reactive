package com.reactive.user.infrastructure.dto.response;

public record UserResponseDto(
        String id,
        String email,
        String name,
        String phone,
        String address
) {
}
