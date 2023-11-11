package com.example.demo.lib;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JWTUtil {

    private JWTUtil() {}

    public static RSAPublicKey loadPublicKLey(CheckedSupplier<InputStream> pemSupplier) throws Exception {
        try (InputStream in = pemSupplier.get();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             Stream<String> lines = reader.lines()) {

            String pem = lines.filter(line -> !line.matches("-----(BEGIN|END) PUBLIC KEY-----"))
                    .map(String::trim)
                    .collect(Collectors.joining());

            byte[] encoded = Base64.getDecoder().decode(pem);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) factory.generatePublic(keySpec);
        }
    }

    public static RSAPrivateKey loadPrivateKey(CheckedSupplier<InputStream> pemSupplier) throws Exception {
        try (InputStream in = pemSupplier.get();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             Stream<String> lines = reader.lines()) {

            String pem = lines.filter(line -> !line.matches("-----(BEGIN|END) RSA PRIVATE KEY-----"))
                    .map(String::trim)
                    .collect(Collectors.joining());

            byte[] encoded = Base64.getDecoder().decode(pem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA", new BouncyCastleProvider()).generatePrivate(keySpec);
        }
    }

    public static String createToken(Algorithm algorithm) {
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

    public static DecodedJWT verifyToken(String token, Algorithm algorithm) throws JWTVerificationException {
        String tokenIssuer = "your_name";
        String tokenSubject = "service_token";

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(tokenIssuer)
                .withSubject(tokenSubject)
                .build();

        return verifier.verify(token);
    }

    public static String decodeToken(String token, Algorithm algorithm) throws JWTVerificationException {
        DecodedJWT decodedJWT = verifyToken(token, algorithm);
        List<String> userMailList = decodedJWT.getAudience();

        return userMailList.get(0);
    }

    public interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}
