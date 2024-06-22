package com.reactive.user.infrastructure.entrypoint;

import com.reactive.user.application.command.CreateUserCommand;
import com.reactive.user.application.command.DeleteUserCommand;
import com.reactive.user.application.command.UpdateFieldUserCommand;
import com.reactive.user.application.query.GetAllUserQuery;
import com.reactive.user.infrastructure.dto.request.UserFieldUpdateRequestDto;
import com.reactive.user.infrastructure.dto.request.UserRequestDto;
import com.reactive.user.infrastructure.dto.response.UserCreatedResponseDto;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import com.reactive.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private static final String LOGGER_PREFIX = String.format("[%s] ", UserController.class.getSimpleName());
    private final GetAllUserQuery getAllUserQuery;
    private final CreateUserCommand createUserCommand;
    private final UpdateFieldUserCommand updateFieldUserCommand;
    private final DeleteUserCommand deleteUserCommand;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserResponseDto> getUsers() {
        return this.getAllUserQuery.execute()
                .doFirst(() -> log.info(LOGGER_PREFIX + "[getUsers] request"))
                .map(this.userMapper::toDto)
                .doOnNext(userResponseDto -> log.info(LOGGER_PREFIX + "[getUsers] response {}", userResponseDto));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<UserCreatedResponseDto> saveUser(@RequestBody final UserRequestDto userRequestDto) {
        return this.createUserCommand.execute(this.userMapper.toModel(userRequestDto))
                .doFirst(() -> log.info(LOGGER_PREFIX + "[saveUser] request {}", userRequestDto))
                .map(this.userMapper::toCreatedResponseDto)
                .doOnSuccess(userCreatedResponseDto ->
                        log.info(LOGGER_PREFIX + "[saveUser] response {}", userCreatedResponseDto));
    }

    @PatchMapping
    public Mono<Void> updateUserFieldsByEmail(@RequestBody final UserFieldUpdateRequestDto userFieldUpdateRequestDto) {
        return Mono.just(this.userMapper.toModel(userFieldUpdateRequestDto))
                .doOnNext(userModel -> log.info(LOGGER_PREFIX + "[updateUser] request {}", userFieldUpdateRequestDto))
                .flatMap(this.updateFieldUserCommand::execute)
                .doOnSuccess(voidFlow ->
                        log.info(LOGGER_PREFIX + "[updateUser] response void"));
    }

    @DeleteMapping
    public Mono<Void> deleteUser(@RequestParam(value = "name") final String nameUser,
                                 @RequestParam(value = "email") final String email) {
        return this.deleteUserCommand.execute(nameUser, email)
                .doFirst(() -> log.info(LOGGER_PREFIX + "[deleteUser] request {}, {}", nameUser, email))
                .doOnSuccess(voidFlow ->
                        log.info(LOGGER_PREFIX + "[deleteUser] response void"));
    }
}
