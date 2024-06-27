package com.reactive.user.infrastructure.entrypoint;

import com.reactive.user.BaseIT;
import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import com.reactive.user.infrastructure.dto.response.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

class DeleteUserIntegrationTest extends BaseIT {
    private static final String REQUEST_PARAM_NAME = "name";
    private static final String REQUEST_PARAM_EMAIL = "email";

    @Test
    void test_deleteUser_withRequestValid_shouldReturnAnStatusOK() {
        final String name = "John";
        final String email = "johndoe@gmail.com";
        final UserEntity userToDelete = new UserEntity(null, "john.doe@gmail.com",
                "John", "1000500009", "Cra 22L");
        this.reactiveMongoTemplate.save(userToDelete).block();

        final WebTestClient.ResponseSpec response = this.webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_PATH)
                        .queryParam(REQUEST_PARAM_NAME, name)
                        .queryParam(REQUEST_PARAM_EMAIL, email)
                        .build())
                .exchange();

        response.expectStatus().isOk();
    }

    @Test
    void test_deleteUser_withRequestInvalid_shouldReturnCodeExceptionINVALID_PARAMETERSAndStatusBadRequest() {
        final ErrorResponseDto errorResponseExpected = ErrorResponseDto.builder()
                .code("INVALID_PARAMETERS")
                .message(String.format("Invalid %s parameters", REQUEST_PARAM_NAME))
                .build();

        final WebTestClient.ResponseSpec response = this.webTestClient.delete()
                .uri(USER_PATH)
                .exchange();

        response.expectStatus()
                .isBadRequest()
                .expectBody(ErrorResponseDto.class)
                .isEqualTo(errorResponseExpected);
    }
}
