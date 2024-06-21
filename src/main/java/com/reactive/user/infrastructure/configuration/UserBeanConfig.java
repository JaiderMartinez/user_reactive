package com.reactive.user.infrastructure.configuration;

import com.reactive.user.application.command.CreateUserCommand;
import com.reactive.user.application.command.DeleteUserCommand;
import com.reactive.user.application.command.UpdateFieldUserCommand;
import com.reactive.user.application.query.GetAllUserQuery;
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
    public GetAllUserQuery userQueryHandler(final UserUseCase userUseCase) {
        return new GetAllUserQuery(userUseCase);
    }

    @Bean
    public CreateUserCommand userCreatorCommand(final UserUseCase userUseCase) {
        return new CreateUserCommand(userUseCase);
    }

    @Bean
    public UpdateFieldUserCommand userPartialUpdateCommand(final UserUseCase userUseCase) {
        return new UpdateFieldUserCommand(userUseCase);
    }

    @Bean
    public DeleteUserCommand userDeleteCommand(final UserUseCase userUseCase) {
        return new DeleteUserCommand(userUseCase);
    }
}
