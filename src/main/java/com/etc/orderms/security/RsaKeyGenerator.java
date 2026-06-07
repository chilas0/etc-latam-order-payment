package com.etc.orderms.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Generates an RSA key pair used for encryption
 * and decryption of sensitive information.
 */
@Component
public class RsaKeyGenerator {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void init() throws Exception {

        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        KeyPair keyPair =
                generator.generateKeyPair();

        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    /**
     * Returns the RSA public key.
     *
     * @return public key
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Returns the RSA private key.
     *
     * @return private key
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
