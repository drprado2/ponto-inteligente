package com.drprado.pontointeligente.security.acessos;

import org.springframework.security.core.GrantedAuthority;

public enum Permissao implements GrantedAuthority {

    VIEWER("ROLE_VIEWER"),
    MANTENER("ROLE_MANTENER"),
    ADMIN("ROLE_ADMIN"),
    OWNER("ROLE_OWNER");

    private String status;

    Permissao(String status) {
        this.status = status;
    }

    @Override
    public String getAuthority() {
        return status;
    }
}
