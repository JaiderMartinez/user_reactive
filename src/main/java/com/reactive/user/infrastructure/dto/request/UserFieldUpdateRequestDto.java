package com.reactive.user.infrastructure.dto.request;

public record UserFieldUpdateRequestDto(
        String email,
        String name,
        String phone,
        String address
) {
}
