package com.reactive.user.infrastructure.configuration;

import com.reactive.user.application.command.UserCreatorCommand;
import com.reactive.user.application.command.UserDeleteCommand;
import com.reactive.user.application.command.UserFieldUpdateCommand;
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

    @Bean
    public UserCreatorCommand userCreatorCommand(final UserUseCase userUseCase) {
        return new UserCreatorCommand(userUseCase);
    }

    @Bean
    public UserFieldUpdateCommand userPartialUpdateCommand(final UserUseCase userUseCase) {
        return new UserFieldUpdateCommand(userUseCase);
    }

    @Bean
    public UserDeleteCommand userDeleteCommand(final UserUseCase userUseCase) {
        return new UserDeleteCommand(userUseCase);
    }
}
