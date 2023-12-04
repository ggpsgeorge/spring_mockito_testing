package com.ggpsgeorge.spring_mockito_testing;

import java.util.Random;

public class MyUtils {
    public static String generatePassword(int passwordLen){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String alphanumeric = alphabet + alphabet.toLowerCase() + numbers;

        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();

        for(int i=0; i < passwordLen; i++ ){
            int index = random.nextInt(alphanumeric.length());
            stringBuilder.append(alphanumeric.charAt(index));
        }

        return stringBuilder.toString();

    }
}
