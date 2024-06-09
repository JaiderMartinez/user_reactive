package com.reactive.user.infrastructure.mapper;

import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserEntity userEntity);
}
