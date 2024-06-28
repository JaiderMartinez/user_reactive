package com.reactive.user.infrastructure.mapper;

import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.request.UserFieldUpdateRequestDto;
import com.reactive.user.infrastructure.dto.request.UserRequestDto;
import com.reactive.user.infrastructure.dto.response.UserCreatedResponseDto;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserResponseDto toDto(User user);

    UserCreatedResponseDto toCreatedResponseDto(User user);

    User toModel(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    User toModel(UserRequestDto userRequestDto);

    @Mapping(target = "id", ignore = true)
    User toModel(UserFieldUpdateRequestDto userFieldUpdateRequestDto);

    UserEntity toEntity(User user);
}
