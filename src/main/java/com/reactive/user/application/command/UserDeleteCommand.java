package com.reactive.user.application.command;

import com.reactive.user.application.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserDeleteCommand {
    private final UserUseCase userUseCase;

    public Mono<Void> execute(final String nameUser, final String email) {
        return userUseCase.deleteUser(nameUser, email);
    }
}
