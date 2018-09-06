package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.domain.entities.Lancamento;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    List<Lancamento> obterPorFuncionario(Long funcionarioId);

    Page<Lancamento> obterPaginadoPorFuncionario(Long funcionarioId, int currentPage, int itensPorPagina);

    List<Lancamento> obterPorDataDescricao(LocalDateTime dataInicio, LocalDateTime dataFim, String descricao);
}
