package com.reactive.user.infrastructure.dto.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserRequestDto(
        String email,
        String name,
        String phone,
        String address
) {
}
