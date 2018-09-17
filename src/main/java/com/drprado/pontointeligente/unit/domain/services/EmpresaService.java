package com.drprado.pontointeligente.unit.domain.services;

import com.drprado.pontointeligente.domain.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {
    Optional<Empresa> buscarPorCNPJ(String cnpj);

    void lancarErro();

    Empresa salvar(Empresa empresa);
}
