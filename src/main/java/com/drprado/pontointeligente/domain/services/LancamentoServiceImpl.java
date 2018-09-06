package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.domain.entities.Lancamento;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.repositories.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return lancamentoRepository.save(lancamento);
    }

    @Override
    public List<Lancamento> obterPorFuncionario(Long funcionarioId) {
        return lancamentoRepository.findByFuncionarioId(funcionarioId);
    }

    @Override
    public Page<Lancamento> obterPaginadoPorFuncionario(Long funcionarioId, int currentPage, int itensPorPagina) {
        PageRequest pageRequest = PageRequest.of(currentPage, itensPorPagina);
        return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public List<Lancamento> obterPorDataDescricao(LocalDateTime dataInicio, LocalDateTime dataFim, String descricao) {
        return lancamentoRepository.findBetweenDateAndDescription(dataInicio, dataFim, descricao);
    }
}
