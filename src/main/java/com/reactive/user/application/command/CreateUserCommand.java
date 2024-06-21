package com.reactive.user.application.command;

import com.reactive.user.application.usecase.UserUseCase;
import com.reactive.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserCommand {
    private final UserUseCase userUseCase;

    public Mono<User> execute(final User user) {
        return this.userUseCase.saveUser(user);
    }
}