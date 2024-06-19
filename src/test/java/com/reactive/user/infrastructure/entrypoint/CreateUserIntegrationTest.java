package com.reactive.user.infrastructure.entrypoint;

import com.reactive.user.BaseIT;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.request.UserRequestDto;
import com.reactive.user.infrastructure.dto.response.UserCreatedResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class CreateUserIntegrationTest extends BaseIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    void testSaveUser_withRequestValid_shouldReturnUserResponseDtoAndStatusCreated() {
        final UserRequestDto userRequestDto = UserRequestDto.builder()
                .email("john.doe@gmail.com")
                .name("John Doe")
                .phone("0123456789")
                .address("Cra 12#")
                .build();
        final Query getByEmailQuery = new Query();
        getByEmailQuery.addCriteria(Criteria.where("email").is(userRequestDto.email()));

        final UserCreatedResponseDto userCreatedResponseDto = this.webTestClient.post()
                .uri("/users")
                //.body(Mono.just(userRequestDto), UserRequestDto.class)
                .body(BodyInserters.fromValue(userRequestDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserCreatedResponseDto.class)
                .returnResult().getResponseBody();
        final UserEntity userEntity = this.reactiveMongoTemplate.find(getByEmailQuery, UserEntity.class).blockFirst();
        final UserCreatedResponseDto expected = new UserCreatedResponseDto(userEntity.getId(), userRequestDto.name());
        Assertions.assertEquals(expected, userCreatedResponseDto);
    }
}