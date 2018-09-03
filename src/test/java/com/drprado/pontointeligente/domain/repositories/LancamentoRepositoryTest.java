package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.crosscutting.util.ClockFactory;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.entities.Lancamento;
import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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

    private Funcionario funcionarioAdriano;
    private Funcionario funcionarioJoao;

    @Before
    public void setUp(){
        funcionarioRepository.save(new Funcionario("Adriano", "adri@gmail.com", "102030", "123321"));
        funcionarioRepository.save(new Funcionario("João", "joao@gmail.com", "302010", "333444"));

        funcionarioAdriano = funcionarioRepository.findByCpf("123321");
        funcionarioJoao = funcionarioRepository.findByCpf("333444");


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

    public static Lancamento gerarLancamento(Funcionario funcionario, LocalDateTime date, TipoLancamentoHora tipo){
        return new Lancamento(date,
                "teste1",
                "Maringá",
                tipo,
                funcionario);
    }
}
