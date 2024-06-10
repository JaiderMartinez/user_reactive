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

    public Mono<User> saveUser(final User user) {
        return validateUser(user)
                .then(userService.saveUser(user));
    }

    private Mono<Void> validateUser(final User user) {
        return Mono.defer(() -> {
            validateName(user);
            validatePhone(user);
            validateEmail(user);
            return existEmail(user.email())
                    .flatMap(existsEmail -> {
                        if (Boolean.TRUE.equals(existsEmail)) {
                            log.error(LOGGER_PREFIX + "[validateEmail] Email already exists");
                            return Mono.error(
                                    new UserUseCaseException(CodeException.EMAIL_ALREADY_EXISTS,
                                            new RuntimeException()));
                        }
                        return Mono.empty();
                    });
        });
    }

    private void validateName(final User user) {
        if (!user.isValidName()) {
            log.error(LOGGER_PREFIX + "[validateName] name user invalid");
            throw new UserUseCaseException(CodeException.NAME_USER_INVALID, new RuntimeException());
        }
    }

    private void validateEmail(final User user) {
        if (!user.isValidEmail()) {
            log.error(LOGGER_PREFIX + "[validateEmail] email user invalid");
            throw new UserUseCaseException(CodeException.EMAIL_USER_INVALID, new RuntimeException());
        }
    }

    private void validatePhone(final User user) {
        if (!user.isValidPhone()) {
            log.error(LOGGER_PREFIX + "[validatePhone] phone user invalid");
            throw new UserUseCaseException(CodeException.PHONE_USER_INVALID, new RuntimeException());
        }
    }

    private Mono<Boolean> existEmail(final String email) {
        return userService.existEmail(email);
    }

    public Mono<Void> partialUpdate(final User user, final String nameUser) {
        return userService.getUserByName(nameUser)
                .switchIfEmpty(
                        Mono.error(() -> {
                            log.error(LOGGER_PREFIX + "[partialUpdate] No users found, code: {}",
                                    CodeException.USER_NOT_FOUND);
                            return new UserUseCaseException(CodeException.USER_NOT_FOUND, new RuntimeException());
                        })
                )
                .flatMap(userToUpdate -> {
                    User userNew = userToUpdate.toBuilder()
                            .email(user.email())
                            .phone(user.phone())
                            .address(user.address())
                            .build();
                    return userService.saveUser(userNew)
                            .then(Mono.empty());
                });
    }

    public Mono<Void> deleteUser(final String nameUser, final String email) {
        return userService.deleteUser(nameUser, email);
    }
}
