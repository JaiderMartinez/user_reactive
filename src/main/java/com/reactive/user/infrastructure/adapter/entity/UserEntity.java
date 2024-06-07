package com.reactive.user.infrastructure.adapter.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;
}
