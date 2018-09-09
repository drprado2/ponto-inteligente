package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.domain.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {

    Funcionario findByCpf(String cpf);

    Funcionario findByEmail(String email);

    // Esse OR é outra convenção, ele cria um  WHERE OR
    Funcionario findByCpfOrEmail(String cpf, String email);

    Set<Funcionario> findByEmpresaId(Long empresaId);
}
