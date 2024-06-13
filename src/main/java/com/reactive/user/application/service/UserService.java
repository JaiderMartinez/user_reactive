package com.reactive.user.application.service;

import com.reactive.user.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getUsers();

    Mono<User> saveUser(User user);

    Mono<Boolean> existEmail(String email);

    Mono<User> getUserByEmail(String email);

    Mono<Void> deleteUserByNameAndEmail(String nameUser, String email);
}
