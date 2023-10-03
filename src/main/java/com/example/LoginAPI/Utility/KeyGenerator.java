package com.example.LoginAPI.Utility;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGenerator {

    public static KeyPair generateRsaKey(){


        KeyPair keyPair;

        // configuring the bit size of my key
        // these are the settings that my jwt will use for security
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }catch(Exception e){
            throw new IllegalStateException();
        }

        return keyPair;

    }
}
