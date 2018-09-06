package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCNPJ(String cnpj) {
        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
}
