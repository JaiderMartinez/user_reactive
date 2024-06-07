package com.reactive.user.application.usecase;

import com.reactive.user.application.exception.NotFoundException;
import com.reactive.user.application.service.UserService;
import com.reactive.user.domain.exception.ErrorCode;
import com.reactive.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {
    private final UserService userService;

    public Flux<User> getUsers() {
        return userService.getUsers()
                .switchIfEmpty(Mono
                        .error(new NotFoundException("", ErrorCode.USER_NOT_FOUND))
                );
    }
}
