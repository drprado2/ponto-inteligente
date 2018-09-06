package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.domain.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {
    Optional<Empresa> buscarPorCNPJ(String cnpj);

    Empresa salvar(Empresa empresa);
}
