package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.domain.entities.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {
    Funcionario salvar(Funcionario funcionario);

    Optional<Funcionario> buscarPorCpf(String cpf);

    Optional<Funcionario> buscarPorEmail(String email);

    Optional<Funcionario> buscarPorId(Long id);

    List<Funcionario> buscaFiltrada();
}
