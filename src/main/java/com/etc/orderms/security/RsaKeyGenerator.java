package com.etc.orderms.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

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

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
