package com.reactive.user.infrastructure.adapter.dao;

import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserDao extends ReactiveMongoRepository<UserEntity, Long> {
}
