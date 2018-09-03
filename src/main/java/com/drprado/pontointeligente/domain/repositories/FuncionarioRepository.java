package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.domain.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByCpf(String cpf);

    Funcionario findByEmail(String email);

    // Esse OR é outra convenção, ele cria um  WHERE OR
    Funcionario findByCpfOrEmail(String cpf, String email);
}
