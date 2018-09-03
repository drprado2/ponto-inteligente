package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.domain.entities.Funcionario;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(){
        funcionarioRepository.deleteAll();
    }

    @Test
    public void filtrandoPorCpf(){
        final String cpfFiltrar = "151623";

        List<Funcionario> funcionarios = Arrays.asList(new Funcionario("Teste 1", "teste1@gmail.com", "tete1", cpfFiltrar),
                new Funcionario("Teste 2", "teste2@gmail.com", "tete2", "1213"),
                new Funcionario("Teste 3", "teste3@gmail.com", "tete3", "141413"),
                new Funcionario("Teste 4", "teste4@gmail.com", "tete4", "133355"));
        funcionarioRepository.saveAll(funcionarios);

        Assert.assertEquals(4, funcionarioRepository.findAll().size());

        Funcionario funcFiltrado = funcionarioRepository.findByCpf(cpfFiltrar);

        Assert.assertEquals(cpfFiltrar, funcFiltrado.getCpf());
    }

    @Test
    public void filtrandoPorEmail(){
        final String emailFiltrar = "teste1@a.com";

        List<Funcionario> funcionarios = Arrays.asList(new Funcionario("Teste 1", emailFiltrar, "tete1", "114499"),
                new Funcionario("Teste 2", "teste2@gmail.com", "tete2", "1213"),
                new Funcionario("Teste 3", "teste3@gmail.com", "tete3", "141413"),
                new Funcionario("Teste 4", "teste4@gmail.com", "tete4", "133355"));
        funcionarioRepository.saveAll(funcionarios);

        Assert.assertEquals(4, funcionarioRepository.findAll().size());

        Funcionario funcFiltrado = funcionarioRepository.findByEmail(emailFiltrar);

        Assert.assertEquals(emailFiltrar, funcFiltrado.getEmail());
    }

    @Test
    public void filtrandoPorEmailOuEmail(){
        final String emailFiltrar = "teste1@a.com";
        final String cpfFiltrar = "12344321";

        List<Funcionario> funcionarios = Arrays.asList(
                new Funcionario("Teste 1", emailFiltrar, "tete1", "114499"),
                new Funcionario("Teste 2", "teste2@gmail.com", "tete2", cpfFiltrar),
                new Funcionario("Teste 3", "teste3@gmail.com", "tete3", "141413"),
                new Funcionario("Teste 4", "teste4@gmail.com", "tete4", "133355"));
        funcionarioRepository.saveAll(funcionarios);

        Funcionario funcFiltradoEmail = funcionarioRepository.findByCpfOrEmail("13666", emailFiltrar);
        Funcionario funcFiltradoCpf = funcionarioRepository.findByCpfOrEmail(cpfFiltrar, "13666");

        Assert.assertEquals(4, funcionarioRepository.findAll().size());
        Assert.assertEquals(emailFiltrar, funcFiltradoEmail.getEmail());
        Assert.assertEquals(cpfFiltrar, funcFiltradoCpf.getCpf());
    }
}
