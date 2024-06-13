package com.reactive.user.infrastructure.adapter.dao;

import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveMongoRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);

    Mono<UserEntity> findByEmail(String email);

    Mono<Void> deleteByNameAndEmail(String name, String email);
}
