package com.etc.orderms.service;

import com.etc.orderms.security.RsaKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.Base64;

/**
 * Provides RSA encryption and decryption operations.
 */
@Service
@RequiredArgsConstructor
public class RsaService {

    private final RsaKeyGenerator keyGenerator;

    /**
     *Encrypts sensitive data using the RSA public key.
     *
     * @param value plain text value
     * @return encrypted value encoded in Base64
     */
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

    /**
     * Decrypts RSA encrypted data using the private key.
     *
     * @param value encrypted Base64 value
     * @return decrypted plain text
     */
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
