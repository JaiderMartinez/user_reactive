package com.reactive.user.infrastructure.entrypoint;

import com.reactive.user.application.query.UserQueryHandler;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import com.reactive.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserQueryHandler userQueryHandler;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserResponseDto> getUser() {
        LOGGER.info("{} getUser request", UserController.class.getSimpleName());
        return userQueryHandler.execute()
                .map(userMapper::toDto);
    }
}
