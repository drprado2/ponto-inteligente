package com.drprado.pontointeligente.crosscutting.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    public PasswordUtils() {
    }

    /**
     * Gera um hash utilizando o BCrypt.
     *
     * @param senha
     * @return String
     */
    public static String gerarBCrypt(String senha) {
        if (senha == null) {
            return senha;
        }

        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(12);
        return bCryptEncoder.encode(senha);
    }

}
