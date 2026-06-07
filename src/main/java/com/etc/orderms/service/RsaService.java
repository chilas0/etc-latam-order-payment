package com.etc.orderms.service;

import com.etc.orderms.security.RsaKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RsaService {

    private final RsaKeyGenerator keyGenerator;

    public String encrypt(String value) {
        try {

            Cipher cipher =
                    Cipher.getInstance("RSA");

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    keyGenerator.getPublicKey()
            );

            byte[] encrypted =
                    cipher.doFinal(value.getBytes());

            return Base64.getEncoder()
                    .encodeToString(encrypted);


        }catch (Exception ex) {
            throw new RuntimeException(
                    "Error encrypting data",
                    ex
            );
        }
    }

    public String decrypt(String value){
        try{

            Cipher cipher =
                    Cipher.getInstance("RSA");

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    keyGenerator.getPrivateKey()
            );

            byte[] decrypted =
                    cipher.doFinal(
                            Base64.getDecoder()
                                    .decode(value)
                    );

            return new String(decrypted);

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Error decrypting data",
                    ex
            );
        }
    }
}
