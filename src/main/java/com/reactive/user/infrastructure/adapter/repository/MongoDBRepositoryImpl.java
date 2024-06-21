package com.reactive.user.infrastructure.adapter.repository;

import com.reactive.user.application.exception.RepositoryException;
import com.reactive.user.application.service.UserService;
import com.reactive.user.domain.exception.CodeException;
import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.dao.UserDao;
import com.reactive.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MongoDBRepositoryImpl implements UserService {
    private static final String LOGGER_PREFIX = String.format("[%s] ", MongoDBRepositoryImpl.class.getSimpleName());
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public Flux<User> getUsers() {
        return this.userDao.findAll()
                .doFirst(() -> log.info(LOGGER_PREFIX + "[getUsers] request"))
                .doOnNext(userEntityResponse -> log.info(LOGGER_PREFIX + "[getUsers] response {}", userEntityResponse))
                .map(this.userMapper::toModel);

    }

    @Override
    public Mono<User> saveUser(final User user) {
        return Mono.just(this.userMapper.toEntity(user))
                .doOnNext(userEntityRequest -> log.info(LOGGER_PREFIX + "[saveUser] request {}", userEntityRequest))
                .flatMap(this.userDao::save)
                .doOnSuccess(userEntityResponse -> log.info(LOGGER_PREFIX + "[saveUser] response {}", userEntityResponse))
                .map(this.userMapper::toModel);
    }

    @Override
    public Mono<Boolean> existEmail(final String email) {
        return Mono.just(email)
                .doOnNext(emailRequest -> log.info(LOGGER_PREFIX + "[existEmail] request {}", emailRequest))
                .flatMap(this.userDao::existsByEmail)
                .doOnSuccess(existsEmail -> log.info(LOGGER_PREFIX + "[existEmail] response {}", existsEmail));
    }

    @Override
    public Mono<User> getUserByEmail(final String email) {
        return Mono.just(email)
                .doOnNext(emailRequest -> log.info(LOGGER_PREFIX + "[getUserByEmail] request {}", emailRequest))
                .flatMap(this.userDao::findByEmail)
                .switchIfEmpty(
                        Mono.error(() -> {
                            log.error(LOGGER_PREFIX + "[getUserByEmail] No users found");
                            return new RepositoryException(CodeException.USER_NOT_FOUND, null);
                        })
                )
                .doOnSuccess(emailResponse -> log.info(LOGGER_PREFIX + "[getUserByEmail] response {}", emailResponse))
                .map(this.userMapper::toModel);
    }

    @Override
    public Mono<Void> deleteUserByNameAndEmail(final String nameUser, final String email) {
        return this.userDao.deleteByNameAndEmail(nameUser, email)
                .doFirst(() -> log.info(LOGGER_PREFIX + "[deleteUser] request {}, {}", nameUser, email))
                .doOnSuccess(voidFlow -> log.info(LOGGER_PREFIX + "[deleteUser] response void"));
    }
}
