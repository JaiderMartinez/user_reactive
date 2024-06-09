package com.reactive.user.infrastructure.entrypoint;

import com.reactive.user.application.query.UserQueryHandler;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import com.reactive.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private static final String LOGGER_PREFIX = String.format("[%s] ", UserController.class.getSimpleName());
    private final UserQueryHandler userQueryHandler;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserResponseDto> getUser() {
        log.info(LOGGER_PREFIX + "[getUser] request");
        return userQueryHandler.execute()
                .map(userMapper::toDto);
    }
}
