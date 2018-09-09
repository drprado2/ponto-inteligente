package com.drprado.pontointeligente.integration.domain.repositories;

import com.drprado.pontointeligente.crosscutting.util.ClockFactory;
import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.entities.Lancamento;
import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.repositories.LancamentoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private LancamentoRepository lancamentoRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private Funcionario funcionarioAdriano;
    private Funcionario funcionarioJoao;
    private final LocalDateTime MOCK_DATE = LocalDateTime.of(2018, 8, 15, 10, 0);
    private final static ZoneId ZONE_ID = ZoneId.systemDefault();
    private Empresa empresaPadrao;

    @Before
    public void setUp(){
        empresaPadrao = empresaRepository.save(new Empresa("Teste Empresa", "101255566"));
        funcionarioRepository.save(new Funcionario(empresaPadrao.getId(),"Adriano", "adri@gmail.com", "102030", "123321"));
        funcionarioRepository.save(new Funcionario(empresaPadrao.getId(),"João", "joao@gmail.com", "302010", "333444"));

        funcionarioAdriano = funcionarioRepository.findByCpf("123321");
        funcionarioJoao = funcionarioRepository.findByCpf("333444");

        ClockFactory.setClock(Clock.fixed(MOCK_DATE.atZone(ZONE_ID).toInstant(), ZONE_ID));
    }

    @After
    public void tearDown(){
        funcionarioRepository.deleteAll();
        lancamentoRepository.deleteAll();
    }

    @Test
    public void filtrandoPorFuncionario(){
        List<Lancamento> lancamentos = Arrays.asList(
                gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()), TipoLancamentoHora.INICIO_TRABALHO),
                gerarLancamento(funcionarioJoao, LocalDateTime.now(ClockFactory.get()), TipoLancamentoHora.INICIO_TRABALHO),
                gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusHours(7), TipoLancamentoHora.TERMINO_TRABALHO));

        lancamentoRepository.saveAll(lancamentos);

        List<Lancamento> lancamentosAdriano = lancamentoRepository.findByFuncionarioId(funcionarioAdriano.getId());
        List<Lancamento> lancamentosJoao = lancamentoRepository.findByFuncionarioId(funcionarioJoao.getId());

        assertEquals(2, lancamentosAdriano.size());
        assertEquals(1, lancamentosJoao.size());
    }

    @Test
    public void filtrandoPorFuncionarioPaginado(){
        List<Lancamento> lancamentos = Arrays.asList(
                gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()), TipoLancamentoHora.INICIO_TRABALHO),
                gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()), TipoLancamentoHora.INICIO_TRABALHO),
                gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusHours(7), TipoLancamentoHora.TERMINO_TRABALHO));

        lancamentoRepository.saveAll(lancamentos);

        PageRequest requestPage1 = PageRequest.of(0, 2);
        PageRequest requestPage2 = PageRequest.of(1, 2);
        PageRequest requestPage3 = PageRequest.of(2, 2);

        Page<Lancamento> pagina1 = lancamentoRepository.findByFuncionarioId(funcionarioAdriano.getId(), requestPage1);
        Page<Lancamento> pagina2 = lancamentoRepository.findByFuncionarioId(funcionarioAdriano.getId(), requestPage2);
        Page<Lancamento> pagina3 = lancamentoRepository.findByFuncionarioId(funcionarioAdriano.getId(), requestPage3);

        assertEquals(2, pagina1.getNumberOfElements());
        assertEquals(3, pagina1.getTotalElements());
        assertEquals(2, pagina1.getTotalPages());

        assertEquals(1, pagina2.getNumberOfElements());

        assertEquals(0, pagina3.getNumberOfElements());
    }

    @Test
    public void findingBetweenDateAndDescriptionContains(){
        final LocalDateTime initialDateToFilter = LocalDateTime.now(ClockFactory.get()).plusDays(-2);
        final LocalDateTime finalDateToFilter = LocalDateTime.now(ClockFactory.get()).plusDays(2);
        final String descriptionToFilter =  "Descrição deve vir";

        List<Lancamento> lancamentos = Arrays.asList(
            gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusDays(3), TipoLancamentoHora.INICIO_ALMOCO),
            gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusDays(2), TipoLancamentoHora.INICIO_ALMOCO),
            gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusDays(1), TipoLancamentoHora.INICIO_ALMOCO),
            gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusDays(-2), TipoLancamentoHora.INICIO_ALMOCO),
            gerarLancamento(funcionarioAdriano, LocalDateTime.now(ClockFactory.get()).plusDays(-3), TipoLancamentoHora.INICIO_ALMOCO)
        );
        lancamentos.get(1).setDescricao(descriptionToFilter);
        lancamentos.get(3).setDescricao(descriptionToFilter);

        lancamentoRepository.saveAll(lancamentos);

        List<Lancamento> result = lancamentoRepository
                .findBetweenDateAndDescription(initialDateToFilter, finalDateToFilter, descriptionToFilter);

        assertEquals(2, result.size());
        assertEquals(result.get(0).getData(), LocalDateTime.now(ClockFactory.get()).plusDays(2));
        assertEquals(result.get(1).getData(), LocalDateTime.now(ClockFactory.get()).plusDays(-2));
    }

    public static Lancamento gerarLancamento(Funcionario funcionario, LocalDateTime date, TipoLancamentoHora tipo){
        return new Lancamento(date,
                "teste1",
                "Maringá",
                tipo,
                funcionario);
    }
}
