package com.reactive.user.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record User(
        Long id,
        String email,
        String name,
        String phone,
        String address
) {
}
