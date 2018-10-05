package com.drprado.pontointeligente.security;

public class AuthenticationExpiredException extends Error {
    public AuthenticationExpiredException() {
        super("A autenticação expirou, por favor faça login novamente.");
    }
}
