package com.reactive.user.application.service;

import com.reactive.user.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getUsers();

    Mono<User> saveUser(final User user);

    Mono<Boolean> existEmail(final String email);

    Mono<User> getUserByName(final String nameUser);

    Mono<Void> deleteUser(final String nameUser, final String email);
}
