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
        log.info(LOGGER_PREFIX + "[getUsers] request");
        return this.userDao.findAll()
                .map(this.userMapper::toModel);
    }

    @Override
    public Mono<User> saveUser(final User user) {
        log.info(LOGGER_PREFIX + "[saveUser] request {}", user);
        return this.userDao.save(this.userMapper.toEntity(user))
                .doOnSuccess(userModel -> log.info(LOGGER_PREFIX + "[saveUser] response {}", userModel))
                .map(this.userMapper::toModel);
    }

    @Override
    public Mono<Boolean> existEmail(final String email) {
        log.info(LOGGER_PREFIX + "[existEmail] request {}", email);
        return this.userDao.existsByEmail(email)
                .doOnSuccess(existsEmail -> log.info(LOGGER_PREFIX + "[existEmail] response {}", existsEmail));
    }

    @Override
    public Mono<User> getUserByName(final String nameUser) {
        log.info(LOGGER_PREFIX + "[getUserByName] request {}", nameUser);
        return this.userDao.findByName(nameUser)
                .switchIfEmpty(
                        Mono.error(() -> {
                            log.error(LOGGER_PREFIX + "[getUserByName] No users found");
                            return new RepositoryException(CodeException.USER_NOT_FOUND, null);
                        })
                )
                .doOnSuccess(user -> log.info(LOGGER_PREFIX + "[getUserByName] response {}", user))
                .map(this.userMapper::toModel);
    }

    @Override
    public Mono<Void> deleteUser(final String nameUser, final String email) {
        log.info(LOGGER_PREFIX + "[deleteUser] request {}, {}", nameUser, email);
        return this.userDao.deleteByNameAndEmail(nameUser, email)
                .doOnSuccess(voidFlow -> log.info(LOGGER_PREFIX + "[deleteUser] response void"));
    }
}
