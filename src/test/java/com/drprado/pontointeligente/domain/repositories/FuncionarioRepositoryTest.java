package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.crosscutting.IoC.ContextResolver;
import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private volatile static boolean isSetUpRunned = false;
    private static Empresa empresaPadrao;

    @Before
    public void oneTimeSetUp(){
        synchronized (FuncionarioRepositoryTest.class){
            if(!isSetUpRunned){
                Empresa empresaCriar = new Empresa("Empresa Teste", "1514171866");
                empresaPadrao = empresaRepository.save(empresaCriar);
            }
        }
    }

    @After
    public void tearDown(){
        funcionarioRepository.deleteAll();
    }

    @AfterClass
    public static void oneTimeTearDown(){
        ApplicationContext context = ContextResolver.getContext();
        EmpresaRepository repo = context.getBean(EmpresaRepository.class);
        repo.deleteAll();
    }

    @Test
    public void filtrandoPorCpf(){
        final String cpfFiltrar = "151623";

        List<Funcionario> funcionarios = Arrays.asList(
                new Funcionario(empresaPadrao.getId(),"Teste 1", "teste1@gmail.com", "tete1", cpfFiltrar),
                new Funcionario(empresaPadrao.getId(), "Teste 2", "teste2@gmail.com", "tete2", "1213"),
                new Funcionario(empresaPadrao.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"),
                new Funcionario(empresaPadrao.getId(),"Teste 4", "teste4@gmail.com", "tete4", "133355"));
        funcionarioRepository.saveAll(funcionarios);

        Assert.assertEquals(4, funcionarioRepository.findAll().size());

        Funcionario funcFiltrado = funcionarioRepository.findByCpf(cpfFiltrar);

        Assert.assertEquals(cpfFiltrar, funcFiltrado.getCpf());
    }

    @Test
    public void filtrandoPorEmail(){
        final String emailFiltrar = "teste1@a.com";

        List<Funcionario> funcionarios = Arrays.asList(
                new Funcionario(empresaPadrao.getId(),"Teste 1", emailFiltrar, "tete1", "114499"),
                new Funcionario(empresaPadrao.getId(),"Teste 2", "teste2@gmail.com", "tete2", "1213"),
                new Funcionario(empresaPadrao.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"),
                new Funcionario(empresaPadrao.getId(),"Teste 4", "teste4@gmail.com", "tete4", "133355"));
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
                new Funcionario(empresaPadrao.getId(),"Teste 1", emailFiltrar, "tete1", "114499"),
                new Funcionario(empresaPadrao.getId(),"Teste 2", "teste2@gmail.com", "tete2", cpfFiltrar),
                new Funcionario(empresaPadrao.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"),
                new Funcionario(empresaPadrao.getId(),"Teste 4", "teste4@gmail.com", "tete4", "133355"));
        funcionarioRepository.saveAll(funcionarios);

        Funcionario funcFiltradoEmail = funcionarioRepository.findByCpfOrEmail("13666", emailFiltrar);
        Funcionario funcFiltradoCpf = funcionarioRepository.findByCpfOrEmail(cpfFiltrar, "13666");

        Assert.assertEquals(4, funcionarioRepository.findAll().size());
        Assert.assertEquals(emailFiltrar, funcFiltradoEmail.getEmail());
        Assert.assertEquals(cpfFiltrar, funcFiltradoCpf.getCpf());
    }

    @Test
    public void buscandoPorEmpresa(){
    List<Funcionario> funcionarios = Arrays.asList(
            new Funcionario(empresaPadrao.getId(),"Teste 1", "teste1@gmail.com", "tete1", "114499"),
            new Funcionario(empresaPadrao.getId(),"Teste 2", "teste2@gmail.com", "tete2", "151417"),
            new Funcionario(empresaPadrao.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"),
            new Funcionario(empresaPadrao.getId(),"Teste 4", "teste4@gmail.com", "tete4", "133355"));
        funcionarioRepository.saveAll(funcionarios);

        Set<Funcionario> result = funcionarioRepository.findByEmpresaId(empresaPadrao.getId());

        Assert.assertEquals(4, result.size());
    }
}
