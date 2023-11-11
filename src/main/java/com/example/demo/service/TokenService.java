package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.lib.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.time.OffsetDateTime;

@Service
public class TokenService {

    private final Algorithm encodeAlgorithm;
    private final Algorithm decodeAlgorithm;
    private final Algorithm hmacAlgorithm;

    public TokenService(@Value("${public-key}") Resource publicKeyFile,
                        @Value("${private-key}") Resource privateKeyFile) throws Exception {
        RSAPublicKey publicKey = TokenUtil.loadPublicKey(publicKeyFile::getInputStream);
        this.encodeAlgorithm = Algorithm.RSA256(publicKey, TokenUtil.loadPrivateKey(privateKeyFile::getInputStream));
        this.decodeAlgorithm = Algorithm.RSA256(publicKey);

        this.hmacAlgorithm = Algorithm.HMAC256("lkhapsjf[jtpqu959qy34jlroqhefa");
    }

    public String createToken() {
        return createTokeInternal(encodeAlgorithm);
    }

    public String createHmacToken() {
        return createTokeInternal(hmacAlgorithm);
    }

    private static String createTokeInternal(Algorithm algorithm) {
        OffsetDateTime issuedAt = OffsetDateTime.now();
        OffsetDateTime notBefore = issuedAt.plusNanos(1000);
        OffsetDateTime expiresAt = issuedAt.plusMinutes(1);

        String tokenIssuer = "your_name";
        String tokenSubject = "service_token";
        String userEmail = "hoge@huga.com";

        return JWT.create()
                .withIssuer(tokenIssuer)  //トークン発行者情報
                .withSubject(tokenSubject) //トークンの主体
                .withAudience(userEmail)    //トークンの利用者（メールアドレスを用いてトークンを一意にする）
                .withIssuedAt(issuedAt.toInstant())    //発行日時
                .withNotBefore(notBefore.toInstant())   //トークンの有効期間開始時間
                .withExpiresAt(expiresAt.toInstant())   //トークンの有効期間終了時間 今回はログアウト、セッションタイムアウトまで保持
                .withClaim("hoge", "huga")
                .sign(algorithm);           //アルゴリズム指定して、署名を行う

    }

    public DecodedJWT decodeToken(String token) throws JWTVerificationException {
        return verifyToken(token, decodeAlgorithm);
    }

    public DecodedJWT decodeHmacToken(String token) throws JWTVerificationException {
        return verifyToken(token, hmacAlgorithm);
    }

    private static DecodedJWT verifyToken(String token, Algorithm algorithm) throws JWTVerificationException {
        String tokenIssuer = "your_name";
        String tokenSubject = "service_token";

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(tokenIssuer)
                .withSubject(tokenSubject)
                .build();

        return verifier.verify(token);
    }
}
