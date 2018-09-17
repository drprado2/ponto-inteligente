package com.drprado.pontointeligente.unit.domain.services;

import com.drprado.pontointeligente.crosscutting.util.ClockFactory;
import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.entities.Lancamento;
import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;
import com.drprado.pontointeligente.domain.repositories.LancamentoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @Autowired
    LancamentoService lancamentoService;

    @MockBean
    LancamentoRepository lancamentoRepository;

    private Empresa mockEmpresa;

    @Before
    public void setUp(){
        final Instant mockDate = LocalDateTime.of(2018,8,15,10,0)
                .atZone(ZoneId.systemDefault()).toInstant();

        mockEmpresa = Mockito.mock(Empresa.class);
        Mockito.when(mockEmpresa.getId()).thenReturn(1L);

        Clock clock = Clock.fixed(mockDate, ZoneId.systemDefault());
        ClockFactory.setClock(clock);
    }

    @Test
    public void salvandoLancamentoValido(){
        Funcionario funcionario = new Funcionario(mockEmpresa.getId(),"Adriano", "adriano@gmail.com", "102030", "085777996");
        Lancamento lancamento = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste",
                "Maringá",
                TipoLancamentoHora.INICIO_TRABALHO,
                funcionario);
        Mockito.when(lancamentoRepository.save(lancamento)).thenReturn(lancamento);

        Lancamento result = lancamentoService.salvar(lancamento);

        Mockito.verify(lancamentoRepository, times(1)).save(lancamento);
        assertEquals(lancamento.getId(), result.getId());
    }

    @Test
    public void buscandoLancamentosPorFuncionario(){
        Funcionario funcionario = new Funcionario(mockEmpresa.getId(),"Adriano", "adriano@gmail.com", "102030", "085777996");
        funcionario.setId(1L);
        Funcionario funcionario2 = new Funcionario(mockEmpresa.getId(),"Pedro", "pedro@gmail.com", "102030", "085777996");
        funcionario2.setId(2L);
        Lancamento lancamento1 = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste 1",
                "Maringá",
                TipoLancamentoHora.INICIO_TRABALHO,
                funcionario);
        lancamento1.setId(3L);
        Lancamento lancamento2 = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste 2",
                "Maringá",
                TipoLancamentoHora.TERMINO_TRABALHO,
                funcionario);
        lancamento2.setId(4L);
        Lancamento lancamento3 = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste 3",
                "Maringá",
                TipoLancamentoHora.INICIO_ALMOCO,
                funcionario2);
        lancamento3.setId(5L);

        List<Lancamento> lancamentos = Arrays.asList(lancamento1, lancamento2);
        Mockito.when(lancamentoRepository.findByFuncionarioId(funcionario.getId())).thenReturn(lancamentos);

        List<Lancamento> result = lancamentoService.obterPorFuncionario(funcionario.getId());

        assertEquals(2, result.size());
        assertEquals(result.stream().filter(l -> l.getId().equals(lancamento1.getId())).findFirst().get().getId(), lancamento1.getId());
        assertEquals(result.stream().filter(l -> l.getId().equals(lancamento2.getId())).findFirst().get().getId(), lancamento2.getId());
    }

    @Test
    public void buscandoLancamentosPorFuncionarioPaginado(){
        Funcionario funcionario = new Funcionario(mockEmpresa.getId(), "Adriano", "adriano@gmail.com", "102030", "085777996");
        funcionario.setId(1L);
        Lancamento lancamento1 = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste 1",
                "Maringá",
                TipoLancamentoHora.INICIO_TRABALHO,
                funcionario);
        lancamento1.setId(3L);
        Lancamento lancamento2 = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste 2",
                "Maringá",
                TipoLancamentoHora.TERMINO_TRABALHO,
                funcionario);
        lancamento2.setId(4L);
        Lancamento lancamento3 = new Lancamento(
                LocalDateTime.now(ClockFactory.get()),
                "Teste 3",
                "Maringá",
                TipoLancamentoHora.INICIO_ALMOCO,
                funcionario);
        lancamento3.setId(5L);

        List<Lancamento> lancamentos = Arrays.asList(lancamento1, lancamento2);
        PageRequest mockPageRequest = PageRequest.of(1, 2);
        Page<Lancamento> pageResult = Mockito.mock(Page.class);
        int currentPage = 1;
        int itensPerPage = 10;
        Mockito
            .when(lancamentoRepository
                .findByFuncionarioId(Mockito.eq(funcionario.getId()),
                Mockito.argThat(p -> p.getPageNumber() == currentPage && p.getPageSize() == itensPerPage)))
            .thenReturn(pageResult);

        Page<Lancamento> result = lancamentoService.obterPaginadoPorFuncionario(funcionario.getId(), currentPage, itensPerPage);

        assertEquals(pageResult, result);
    }
}
