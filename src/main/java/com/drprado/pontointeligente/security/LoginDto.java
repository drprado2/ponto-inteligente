package com.drprado.pontointeligente.security;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LoginDto {
    private String username;
    private String password;

    @NotNull(message = "O usuário é de preenchimento obrigatório")
    @Length(min=4, message = "O usuário deve possuir no mínimo 4 caracteres")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "A senha é de preenchimento obrigatório")
    @Length(min=6, message = "A senha deve possuir no mínimo 6 caracteres")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
