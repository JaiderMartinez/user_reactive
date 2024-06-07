package com.reactive.user.infrastructure.mapper;

import com.reactive.user.domain.model.User;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserResponseDto toDto(User user);

    User toModel(UserEntity userEntity);
}
