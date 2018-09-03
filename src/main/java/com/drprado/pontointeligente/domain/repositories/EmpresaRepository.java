package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.domain.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    // Essa notation do transactional é para não criar transação
    // vc pode por em cima da classe ai tds os métodos seriam assim
    @Transactional(readOnly = true)
    // Existe uma convenção com o spring, findBy um Campo, ele cria
    // um filtro por aquele campo
    Empresa findByCnpj(String cnpj);

}
