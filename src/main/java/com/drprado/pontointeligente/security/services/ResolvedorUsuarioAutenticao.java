package com.drprado.pontointeligente.security.services;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.FilterConnectionType;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilter;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilterField;
import com.drprado.pontointeligente.domain.builders.GenericFilterBuilder;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.querySpecifications.FuncionarioQuery;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResolvedorUsuarioAutenticao implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Essa classe deve procurar o usuário pelo login dele, mas não precisa testar a senha
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(username);

        if(!funcionario.isPresent())
            throw new UsernameNotFoundException("O usuário " + username + " não existe");

        return new UserSecurity(funcionario.get());
    }
}
