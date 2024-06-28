package com.reactive.user.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record User(
        String id,
        String email,
        String name,
        String phone,
        String address
) {
    private static final String REGEX_CONTAINS_NUMBERS = ".*\\d.*";
    private static final String GMAIL_DOMAIN = "@gmail.com";
    private static final int PHONE_LENGTH = 10;

    public boolean isValidName() {
        return this.name != null && !this.name.matches(REGEX_CONTAINS_NUMBERS);
    }

    public boolean isValidEmail() {
        return this.email != null && this.email.endsWith(GMAIL_DOMAIN);
    }

    public boolean isValidPhone() {
        return this.phone != null && this.phone.length() == PHONE_LENGTH;
    }

    public User createUserWithUpdatedFields(final User userWithValueNews) {
        return this.toBuilder()
                .name(userWithValueNews.name())
                .phone(userWithValueNews.phone())
                .address(userWithValueNews.address())
                .build();
    }
}
