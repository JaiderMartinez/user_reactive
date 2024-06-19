package com.reactive.user.infrastructure.dto.request;

import lombok.Builder;

@Builder
public record UserRequestDto(
        String email,
        String name,
        String phone,
        String address
) {
}
