package com.reactive.user.infrastructure.entrypoint;

import com.reactive.user.BaseIT;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.request.UserFieldUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class UpdateFieldUserIntegrationTest extends BaseIT {

    @Test
    void test_updateUserFieldsByEmail_withRequestValid_shouldReturnStatusCodeOK() {
        //Given
        final UserEntity userEntity = new UserEntity(null, "john.doe@gmail.com",
                "John", "1000500009", "Cra 22L");
        this.reactiveMongoTemplate.save(userEntity).block();
        final UserFieldUpdateRequestDto userFieldUpdateRequestDto = UserFieldUpdateRequestDto.builder()
                .email("john.doe@gmail.com")
                .name("John Doe")
                .phone("0123456789")
                .address("Cra 12#")
                .build();
        // When
        final WebTestClient.ResponseSpec response = this.webTestClient.patch().uri(USER_PATH)
                .body(BodyInserters.fromValue(userFieldUpdateRequestDto))
                .exchange();
        //Then
        response.expectStatus().isOk();
    }
}
