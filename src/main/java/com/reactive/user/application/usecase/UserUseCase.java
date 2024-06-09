package com.reactive.user.application.usecase;

import com.reactive.user.application.exception.UserUseCaseException;
import com.reactive.user.application.service.UserService;
import com.reactive.user.domain.exception.CodeException;
import com.reactive.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class UserUseCase {
    private static final String LOGGER_PREFIX = String.format("[%s] ", UserUseCase.class.getSimpleName());
    private final UserService userService;

    public Flux<User> getUsers() {
        return userService.getUsers()
                .switchIfEmpty(
                        Mono.error(() -> {
                            log.error(LOGGER_PREFIX + "[getUsers] No users found, code: {}",
                                    CodeException.USERS_NOT_FOUND);
                            return new UserUseCaseException(CodeException.USERS_NOT_FOUND, new RuntimeException());
                        })
                );
    }
}
