package com.drprado.pontointeligente.unit.domain.services;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationResolver;
import com.drprado.pontointeligente.domain.dto.GenericFilters;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.querySpecifications.FuncionarioQuery;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioQuery funcionarioQuery;

    @Override
    public Funcionario salvar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        return Optional.ofNullable(funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        return Optional.ofNullable(funcionarioRepository.getOne(id));
    }

    @Override
    public List<Funcionario> buscaFiltrada(GenericFilters filters) {
        Specification<Funcionario> finalSpec = QuerySpecificationResolver.resolveFilters(funcionarioQuery, filters);
        finalSpec = finalSpec.and(QuerySpecificationResolver.resolveOrders(funcionarioQuery, filters));

        return funcionarioRepository.findAll(finalSpec);
    }

    @Override
    public Page<Funcionario> buscaFiltrada(GenericFilters filters, Pageable pageable) {
        Specification<Funcionario> finalSpec = QuerySpecificationResolver.resolveFilters(funcionarioQuery, filters);
        finalSpec = finalSpec.and(QuerySpecificationResolver.resolveOrders(funcionarioQuery, filters));

        return funcionarioRepository.findAll(finalSpec, pageable);
    }
}
