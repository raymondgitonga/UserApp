package com.poolke.UserApp.shared;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(){
        var userId = generateRandomString(12);
        return userId;
    }

    private String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder();

        for ( int i = 0; i < length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public String encryptPassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public boolean checkPassword(String password){
        String bcryptHashString = encryptPassword(password);
        return BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified;

    }
}
