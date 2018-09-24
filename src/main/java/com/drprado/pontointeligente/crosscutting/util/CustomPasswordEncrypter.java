package com.drprado.pontointeligente.crosscutting.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncrypter implements PasswordEncoder {

    private static final String SALT = "1#@abc777889911";
    private static BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    public static String Encrypt(String password){
        return bCryptEncoder.encode(password + SALT);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptEncoder.encode(rawPassword + SALT);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptEncoder.encode(rawPassword + SALT) == encodedPassword;
    }
}
