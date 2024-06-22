package com.reactive.user;

import com.reactive.user.infrastructure.adapter.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIT {
    protected static final String USER_PATH = "/users";
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected ReactiveMongoTemplate reactiveMongoTemplate;

    protected String findUserByEmail(final String email) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return this.reactiveMongoTemplate.findOne(query, UserEntity.class)
                .block().getId();
    }
}
