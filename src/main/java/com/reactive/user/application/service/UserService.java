package com.reactive.user.application.service;

import com.reactive.user.domain.model.User;
import reactor.core.publisher.Flux;

public interface UserService {
    Flux<User> getUsers();
}
