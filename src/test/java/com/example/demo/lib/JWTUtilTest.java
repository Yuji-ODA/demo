package com.example.demo.lib;

import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static com.example.demo.lib.JWTUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class JWTUtilTest {

    @Test
    void jwtHmacTest() {
        // given
        String secretKey = "lkhapsjf[jtpqu959qy34jlroqhefa";
        String given = createToken(Algorithm.HMAC256(secretKey));

        // when
        String actual = decodeToken(given, Algorithm.HMAC256(secretKey));

        // then
        assertThat(actual).isEqualTo("hoge@huga.com");
    }

    @Test
    void jwtRsaTest() throws Exception {

        // given
        Resource publicKeyResource = new ClassPathResource("jwt.pub.key");
        Resource privateKeyResource = new ClassPathResource("jwt.key.p1");
        RSAPublicKey publicKey = loadPublicKLey(publicKeyResource::getInputStream);
        RSAPrivateKey privateKey = loadPrivateKey(privateKeyResource::getInputStream);
        String given = createToken(Algorithm.RSA256(publicKey, privateKey));

        // when
        String actual = decodeToken(given, Algorithm.RSA256(publicKey));

        // then
        assertThat(actual).isEqualTo("hoge@huga.com");
    }
}
