package com.reactive.user.infrastructure.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserResponseDto(
        String id,
        String email,
        String name,
        String phone,
        String address
) {
}
