package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecificationResolver;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilters;
import com.drprado.pontointeligente.domain.dtos.funcionarios.FuncionarioDto;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.querySpecifications.FuncionarioQuery;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

    @PersistenceContext
    EntityManager em;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioQuery funcionarioQuery;

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
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

    @Override
    public Funcionario criar(FuncionarioDto dto) {
        Funcionario funcionario = new Funcionario(dto.getEmpresaId(), dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCpf());
        funcionario = funcionarioRepository.saveAndFlush(funcionario);
        return funcionario;
    }

    @Override
    public Funcionario atualizar(FuncionarioDto dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.getId()).get();
        funcionario.setPerfil(dto.getPerfil());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setEmpresaId(dto.getEmpresaId());
        funcionario.setNome(dto.getNome());
        funcionario.setQtdHorasAlmoco(dto.getQtdHorasAlmoco());
        funcionario.setQtdHorasTrabalhoDia(dto.getQtdHorasTrabalhoDia());
        return funcionarioRepository.saveAndFlush(funcionario);
    }
}
