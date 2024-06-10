package com.reactive.user.infrastructure.adapter.repository;

import com.reactive.user.application.service.UserService;
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
        return userDao.findAll()
                .map(userMapper::toModel);
    }

    @Override
    public Mono<User> saveUser(final User user) {
        log.info(LOGGER_PREFIX + "[saveUser] request {}", user);
        return userDao.save(userMapper.toEntity(user))
                .map(userMapper::toModel)
                .doOnSuccess(userModel -> log.info(LOGGER_PREFIX + "[saveUser] response {}", userModel));
    }

    @Override
    public Mono<Boolean> existEmail(final String email) {
        log.info(LOGGER_PREFIX + "[existEmail] request {}", email);
        return userDao.existsByEmail(email)
                .doOnSuccess(existsEmail -> log.info(LOGGER_PREFIX + "[existEmail] response {}", existsEmail));
    }

    @Override
    public Mono<User> getUserByName(String nameUser) {
        log.info(LOGGER_PREFIX + "[getUserByName] request {}", nameUser);
        return userDao.findByName(nameUser)
                .map(userMapper::toModel)
                .doOnSuccess(user -> log.info(LOGGER_PREFIX + "[getUserByName] response {}", user));
    }

    @Override
    public Mono<Void> deleteUser(String nameUser, String email) {
        log.info(LOGGER_PREFIX + "[deleteUser] request {}, {}", nameUser, email);
        return userDao.deleteByNameAndEmail(nameUser, email)
                .doOnSuccess(voidFlow -> log.info(LOGGER_PREFIX + "[deleteUser] response void"));
    }
}
