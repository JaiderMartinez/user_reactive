package com.reactive.user.infrastructure.mapper;

import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.request.UserPartialUpdateRequestDto;
import com.reactive.user.infrastructure.dto.request.UserRequestDto;
import com.reactive.user.infrastructure.dto.response.UserCreatedResponseDto;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserResponseDto toDto(final User user);

    UserCreatedResponseDto toCreatedResponseDto(final User user);

    User toModel(final UserEntity userEntity);

    User toModel(final UserRequestDto userRequestDto);

    @Mapping(target = "name", source = "nameUser")
    User toModel(final UserPartialUpdateRequestDto userPartialUpdateRequestDto, final String nameUser);

    UserEntity toEntity(final User user);
}
