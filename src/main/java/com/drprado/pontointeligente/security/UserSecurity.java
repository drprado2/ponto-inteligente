package com.drprado.pontointeligente.security;

import com.drprado.pontointeligente.domain.entities.Funcionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserSecurity implements UserDetails {

    private String password;
    private String username;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Collection<GrantedAuthority> authorities;

    public UserSecurity(Funcionario funcionario) {
        password = funcionario.getSenha();
        username = funcionario.getEmail();
        isAccountNonExpired = true;
        isAccountNonLocked = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
        authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_VIEWER");
        authorities.add(() -> "ROLE_MANAGER");
        authorities.add(() -> "ROLE_MASTER");
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
