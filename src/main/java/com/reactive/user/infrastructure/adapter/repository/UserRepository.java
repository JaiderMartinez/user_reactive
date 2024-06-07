package com.reactive.user.infrastructure.adapter.repository;

import com.reactive.user.application.service.UserService;
import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.dao.UserDao;
import com.reactive.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public Flux<User> getUsers() {
        return userDao.findAll()
                .map(userMapper::toModel);
    }
}
