package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilters;
import com.drprado.pontointeligente.domain.dtos.funcionarios.FuncionarioDto;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {
    Funcionario salvar(Funcionario funcionario);

    Optional<Funcionario> buscarPorCpf(String cpf);

    Optional<Funcionario> buscarPorId(Long id);

    List<Funcionario> buscaFiltrada(GenericFilters filters);

    Page<Funcionario> buscaFiltrada(GenericFilters filters, Pageable pageable);

    Funcionario criarFuncionario(FuncionarioDto dto);
}
