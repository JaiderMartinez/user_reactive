package com.reactive.user.infrastructure.adapter.dao;

import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveMongoRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(final String email);

    Mono<UserEntity> findByName(final String name);

    Mono<Void> deleteByNameAndEmail(final String name, final String email);
}
