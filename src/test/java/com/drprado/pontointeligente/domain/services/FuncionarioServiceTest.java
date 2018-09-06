package com.drprado.pontointeligente.domain.services;

import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {
    @Autowired
    private FuncionarioService funcionarioService;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Test
    public void criandoFuncionarioValido(){
        Funcionario funcionario = new Funcionario("Adriano", "adriano@gmail.com", "102030", "10222");
        Mockito.when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Funcionario result = funcionarioService.salvar(funcionario);

        Mockito.verify(funcionarioRepository, Mockito.times(1)).save(funcionario);
        assertEquals(funcionario.getNome(), result.getNome());
    }

    @Test
    public void buscandoFuncionarioPorCpf(){
        final String CPF = "08655577796";
        Funcionario funcionario = new Funcionario("Adriano", "adriano@gmail.com", "102030", CPF);
        Mockito.when(funcionarioRepository.findByCpf(CPF)).thenReturn(funcionario);

        Optional<Funcionario> result = funcionarioService.buscarPorCpf(CPF);

        assertEquals(funcionario.getCpf(), result.get().getCpf());
    }

    @Test
    public void buscandoFuncionarioPorEmail(){
        final String EMAIL = "adriano@gmail.com";
        Funcionario funcionario = new Funcionario("Adriano", EMAIL, "102030", "1536448");
        Mockito.when(funcionarioRepository.findByEmail(EMAIL)).thenReturn(funcionario);

        Optional<Funcionario> result = funcionarioService.buscarPorEmail(EMAIL);

        assertEquals(EMAIL, result.get().getEmail());
    }

    @Test
    public void buscandoPorId(){
        Funcionario funcionario = new Funcionario("Adriano", "adri@gmail.com", "102030", "1536448");
        Mockito.when(funcionarioRepository.getOne(funcionario.getId())).thenReturn(funcionario);

        Optional<Funcionario> result = funcionarioService.buscarPorId(funcionario.getId());

        assertEquals(funcionario.getId(), result.get().getId());
    }
}
