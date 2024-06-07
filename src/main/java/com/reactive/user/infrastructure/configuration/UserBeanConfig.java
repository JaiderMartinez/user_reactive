package com.reactive.user.infrastructure.configuration;

import com.reactive.user.application.query.UserQueryHandler;
import com.reactive.user.application.service.UserService;
import com.reactive.user.application.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {
    @Bean
    public UserUseCase userUseCase(final UserService userService) {
        return new UserUseCase(userService);
    }

    @Bean
    public UserQueryHandler userQueryHandler(final UserUseCase userUseCase) {
        return new UserQueryHandler(userUseCase);
    }
}
