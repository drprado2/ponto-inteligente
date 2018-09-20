package com.drprado.pontointeligente.integration.domain.reports;

import com.drprado.pontointeligente.crosscutting.IoC.ContextResolver;
import com.drprado.pontointeligente.crosscutting.util.ClockFactory;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.FilterConnectionType;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilterField;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilters;
import com.drprado.pontointeligente.domain.builders.GenericFilterBuilder;
import com.drprado.pontointeligente.domain.builders.GenericFiltersBuilder;
import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.entities.Lancamento;
import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;
import com.drprado.pontointeligente.domain.reports.TestReport;
import com.drprado.pontointeligente.domain.reports.TestReportGenerator;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.repositories.LancamentoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestReportGeneratorTest {

    private static List<Lancamento> lancamentosJoao;
    private static List<Lancamento> lancamentosMaria;
    private static List<Lancamento> lancamentosRoberto;
    private static List<Lancamento> lancamentosJoaquim;
    private static List<Lancamento> lancamentosRenata;

    private static Funcionario joao;
    private static Funcionario maria;
    private static Funcionario roberto;
    private static Funcionario joaquim;
    private static Funcionario renata;

    private static Empresa rommusTech;
    private static Empresa empireTech;
    private static Empresa shieldTech;

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private LancamentoRepository lancamentoRepository;

    private volatile static boolean setUpWasRun = false;

    @Autowired
    private TestReportGenerator testReportGenerator;

    @Before
    public synchronized void oneTimeSetUp(){
        if(!setUpWasRun){
            rommusTech = empresaRepository.saveAndFlush(new Empresa("Rommus Tech", "13363217000108"));
            empireTech = empresaRepository.saveAndFlush(new Empresa("Empire Tech", "39404980000123"));
            shieldTech = empresaRepository.saveAndFlush(new Empresa("Shield Tech", "50874713000152"));

            joao = funcionarioRepository.saveAndFlush(new Funcionario(rommusTech.getId(), "João", "joao@gmail.com", "102030", "08657287986"));
            maria = funcionarioRepository.saveAndFlush(new Funcionario(empireTech.getId(), "Maria", "maria@gmail.com", "102030", "43874671348"));
            roberto = funcionarioRepository.saveAndFlush(new Funcionario(rommusTech.getId(), "Roberto", "roberto@gmail.com", "102030", "38384987718"));
            joaquim = funcionarioRepository.saveAndFlush(new Funcionario(empireTech.getId(), "Joaquim", "joaquim@outlook.com", "102030", "83667244177"));
            renata = funcionarioRepository.saveAndFlush(new Funcionario(rommusTech.getId(), "Renata", "renata@outlook.com", "102030", "58874794347"));

            Clock clock = Clock.fixed(LocalDateTime.of(2018,9,10,15,10).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
            ClockFactory.setClock(clock);
            LocalDateTime hoje = LocalDateTime.now(clock);

            List<Lancamento> lancamentos = Arrays.asList(
                    new Lancamento(hoje.plusDays(10), "João iniciando o trabalho", "Empresa", TipoLancamentoHora.INICIO_TRABALHO, joao),
                    new Lancamento(hoje.plusDays(8), "João iniciando o almoço", "Empresa", TipoLancamentoHora.INICIO_ALMOCO, joao),
                    new Lancamento(hoje.plusDays(5), "João terminando o almoço", "Empresa", TipoLancamentoHora.TERMINO_ALMOCO, joao),
                    new Lancamento(hoje.plusDays(2), "João terminando o trabalho", "Empresa", TipoLancamentoHora.TERMINO_TRABALHO, joao),
                    new Lancamento(hoje.plusDays(-4), "Maria iniciando o trabalho", "Empresa", TipoLancamentoHora.INICIO_TRABALHO, maria),
                    new Lancamento(hoje.plusDays(4), "Maria iniciando o almoço", "Empresa", TipoLancamentoHora.INICIO_ALMOCO, maria),
                    new Lancamento(hoje.plusDays(10), "Maria terminando o almoço", "Empresa", TipoLancamentoHora.TERMINO_ALMOCO, maria),
                    new Lancamento(hoje.plusDays(8), "Maria terminando o trabalho", "Empresa", TipoLancamentoHora.TERMINO_TRABALHO, maria),
                    new Lancamento(hoje.plusDays(10), "Roberto iniciando o trabalho", "Empresa", TipoLancamentoHora.INICIO_TRABALHO, roberto),
                    new Lancamento(hoje.plusDays(10), "Roberto iniciando o almoço", "Empresa", TipoLancamentoHora.INICIO_ALMOCO, roberto),
                    new Lancamento(hoje.plusDays(2), "Roberto terminando o almoço", "Empresa", TipoLancamentoHora.TERMINO_ALMOCO, roberto),
                    new Lancamento(hoje.plusDays(-3), "Roberto terminando o trabalho", "Empresa", TipoLancamentoHora.TERMINO_TRABALHO, roberto),
                    new Lancamento(hoje.plusDays(-8), "Joaquim iniciando o trabalho", "Empresa", TipoLancamentoHora.INICIO_TRABALHO, joaquim),
                    new Lancamento(hoje.plusDays(-10), "Joaquim iniciando o almoço", "Empresa", TipoLancamentoHora.INICIO_ALMOCO, joaquim),
                    new Lancamento(hoje.plusDays(10), "Joaquim terminando o almoço", "Empresa", TipoLancamentoHora.TERMINO_ALMOCO, joaquim),
                    new Lancamento(hoje.plusDays(15), "Joaquim terminando o trabalho", "Empresa", TipoLancamentoHora.TERMINO_TRABALHO, joaquim));
            lancamentoRepository.saveAll(lancamentos);

            lancamentos = lancamentoRepository.findAll();
            lancamentosJoao = lancamentos.stream().filter(l -> l.getFuncionario().getId() == joao.getId()).collect(Collectors.toList());
            lancamentosMaria = lancamentos.stream().filter(l -> l.getFuncionario().getId() == maria.getId()).collect(Collectors.toList());
            lancamentosRoberto = lancamentos.stream().filter(l -> l.getFuncionario().getId() == roberto.getId()).collect(Collectors.toList());
            lancamentosJoaquim = lancamentos.stream().filter(l -> l.getFuncionario().getId() == joaquim.getId()).collect(Collectors.toList());
            lancamentosRenata = lancamentos.stream().filter(l -> l.getFuncionario().getId() == renata.getId()).collect(Collectors.toList());
            setUpWasRun = true;
        }
    }

    @Test
    public void reportWhitoutFiltersShouldReturnAll(){
        List<TestReport> testReportsEM = testReportGenerator.generateEM();
        List<TestReport> testReportsRepo = testReportGenerator.generateRepo();

        Assertions.assertThat(testReportsEM.size()).isEqualTo(18);
        Assertions.assertThat(testReportsRepo.size()).isEqualTo(18);
    }

    @Test
    public void reportWithFiltersShouldReturnFiltered(){
        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("funcionarioNome", "maria"))
                        .build())
                .build();

        List<TestReport> result = testReportGenerator.generateRepo(filters);
        Assertions.assertThat(result.size()).isEqualTo(4);
        Assertions.assertThat(result.stream().filter(r -> r.getFuncionarioCpf().equals(maria.getCpf())).count()).isEqualTo(4);
    }
}
