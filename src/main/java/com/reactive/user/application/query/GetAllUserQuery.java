package com.reactive.user.application.query;

import com.reactive.user.application.usecase.UserUseCase;
import com.reactive.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllUserQuery {
    private final UserUseCase userUseCase;

    public Flux<User> execute() {
        return this.userUseCase.getUsers();
    }
}
