package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.domain.entities.Lancamento;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
// Aqui é uma forma de vc criar suas próprias queries customizadas
@NamedQueries({
        // NomeDaClasse.nomeDoMetodo
        @NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
                // o :variavel é o mesmo nome da variável disponível no método
                query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId") })
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

    // Esse Page e Pageable é para paginação
    Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
