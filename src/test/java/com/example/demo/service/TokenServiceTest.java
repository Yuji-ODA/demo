package com.example.demo.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = TokenService.class, initializers = ConfigDataApplicationContextInitializer.class)
public class TokenServiceTest {

    @Autowired
    TokenService target;

    @Test
    void jwtRsaTest() {
        // given
        String given = target.createToken();

        // when
        DecodedJWT actual = target.decodeToken(given);

        // then
        assertThat(actual.getAudience()).containsExactlyElementsOf(List.of("hoge@huga.com"));
    }

    @Test
    void jwtHmacTest() {
        // given
        String given = target.createHmacToken();

        // when
        DecodedJWT actual = target.decodeHmacToken(given);

        // then
        assertThat(actual.getAudience()).containsExactlyElementsOf(List.of("hoge@huga.com"));
    }

}
