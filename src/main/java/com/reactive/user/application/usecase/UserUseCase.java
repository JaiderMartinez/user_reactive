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
        return this.userService.getUsers();
    }

    public Mono<User> saveUser(final User user) {
        return this.validateUserFields(user)
                .then(this.userService.saveUser(user));
    }

    private Mono<Void> validateUserFields(final User user) {
        this.validateName(user);
        this.validatePhone(user);
        this.validateEmail(user);
        return this.existEmail(user.email())
                .flatMap(existsEmail -> {
                    if (Boolean.TRUE.equals(existsEmail)) {
                        log.error(LOGGER_PREFIX + "[validateEmail] email already exists");
                        return Mono.error(
                                new UserUseCaseException(CodeException.EMAIL_ALREADY_EXISTS, null));
                    }
                    return Mono.empty();
                });
    }

    private void validateName(final User user) {
        if (!user.isValidName()) {
            log.error(LOGGER_PREFIX + "[validateName] name user invalid");
            throw new UserUseCaseException(CodeException.NAME_USER_INVALID, null);
        }
    }

    private void validateEmail(final User user) {
        if (!user.isValidEmail()) {
            log.error(LOGGER_PREFIX + "[validateEmail] email user invalid");
            throw new UserUseCaseException(CodeException.EMAIL_USER_INVALID, null);
        }
    }

    private void validatePhone(final User user) {
        if (!user.isValidPhone()) {
            log.error(LOGGER_PREFIX + "[validatePhone] phone user invalid");
            throw new UserUseCaseException(CodeException.PHONE_USER_INVALID, null);
        }
    }

    private Mono<Boolean> existEmail(final String email) {
        return this.userService.existEmail(email);
    }

    public Mono<Void> updateUserFields(final User userWithValueNews) {
        return this.userService.getUserByName(userWithValueNews.name())
                .flatMap(userToUpdate -> this.getObjectUserWithFieldsUpdate(userToUpdate, userWithValueNews))
                .flatMap(userNew -> this.userService.saveUser(userNew)
                        .then(Mono.empty()));
    }

    private Mono<User> getObjectUserWithFieldsUpdate(final User userToUpdate, final User userWithValueNews) {
        return Mono.just(
                userToUpdate.toBuilder()
                        .email(userWithValueNews.email())
                        .phone(userWithValueNews.phone())
                        .address(userWithValueNews.address())
                        .build()
        );
    }

    public Mono<Void> deleteUser(final String nameUser, final String email) {
        return this.userService.deleteUser(nameUser, email);
    }
}
