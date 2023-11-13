package com.example.demo.lib;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TokenUtil {

    private TokenUtil() {}

    public static RSAPublicKey loadPublicKey(CheckedSupplier<InputStream> pemSupplier) throws Exception {
        try (InputStream in = pemSupplier.get();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             Stream<String> lines = reader.lines()) {

            String pem = lines.filter(line -> !line.matches("-----(BEGIN|END) PUBLIC KEY-----"))
                    .collect(Collectors.joining());
            byte[] encoded = Base64.getDecoder().decode(pem);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }
    }

    public static RSAPrivateKey loadPrivateKey(CheckedSupplier<InputStream> pemSupplier) throws Exception {
        try (InputStream in = pemSupplier.get();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             Stream<String> lines = reader.lines()) {

            String pem = lines.filter(line -> !line.matches("-----(BEGIN|END)( RSA)? PRIVATE KEY-----"))
                    .collect(Collectors.joining());
            byte[] encoded = Base64.getDecoder().decode(pem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        }
    }

    public interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}
