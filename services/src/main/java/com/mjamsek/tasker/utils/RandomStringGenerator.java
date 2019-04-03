package com.mjamsek.tasker.utils;

import java.security.SecureRandom;

public class RandomStringGenerator {
    
    public static String generate(int keyLength) {
        String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(keyLength);
        for (int i = 0; i < keyLength; i++) {
            sb.append(symbols.charAt(random.nextInt(symbols.length())));
        }
        return sb.toString();
    }
}
