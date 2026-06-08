package com.etc.orderms.security;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * Generates an RSA key pair used for encryption
 * and decryption of sensitive information.
 */
@Component
public class RsaKeyGenerator {

    private PublicKey publicKey;

    /**
     * Loads the RSA public key from the application resources.
     *
     * @throws Exception if the key cannot be loaded or parsed
     */
    @PostConstruct
    public void init() throws Exception {

        ClassPathResource resource =
                new ClassPathResource("keys/public.key");

        String key = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        key = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decodedKey =
                Base64.getDecoder().decode(key);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        this.publicKey =
                keyFactory.generatePublic(keySpec);

    }

    /**
     * Returns the RSA public key.
     *
     * @return public key
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }


}
