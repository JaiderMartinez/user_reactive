package com.reactive.user.infrastructure.mapper;

import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.request.UserFieldUpdateRequestDto;
import com.reactive.user.infrastructure.dto.request.UserRequestDto;
import com.reactive.user.infrastructure.dto.response.UserCreatedResponseDto;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto toDto(User user);

    UserCreatedResponseDto toCreatedResponseDto(User user);

    User toModel(UserEntity userEntity);

    User toModel(UserRequestDto userRequestDto);

    User toModel(UserFieldUpdateRequestDto userFieldUpdateRequestDto);

    UserEntity toEntity(User user);
}
