package com.reactive.user.infrastructure.dto;

public record UserDto(
        String id,
        String email,
        String name,
        String phone,
        String address
) {
}
