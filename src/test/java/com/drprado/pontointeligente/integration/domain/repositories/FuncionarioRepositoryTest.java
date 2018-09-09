package com.drprado.pontointeligente.integration.domain.repositories;

import com.drprado.pontointeligente.crosscutting.IoC.ContextResolver;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.enums.Perfil;
import com.drprado.pontointeligente.domain.querySpecifications.FuncionarioQuery;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private volatile static boolean isSetUpRunned = false;
    private static Empresa matriz;
    private static Empresa filial;

    @Before
    public void oneTimeSetUp(){
        synchronized (FuncionarioRepositoryTest.class){
            if(!isSetUpRunned){
                matriz = empresaRepository.save(new Empresa("Matriz", "3333171866"));
                filial = empresaRepository.save(new Empresa("Filial", "4444171866"));

                List<Funcionario> funcionarios = Arrays.asList(
                    new Funcionario(matriz.getId(),"Teste 4", "teste4@gmail.com", "tete4", "133355"),
                    new Funcionario(filial.getId(),"Teste 2", "teste2@gmail.com", "tete2", "151417"),
                    new Funcionario(matriz.getId(),"Teste 2", "teste2@gmail.com", "tete2", "151417"),
                    new Funcionario(filial.getId(),"Teste 1", "teste1@gmail.com", "tete1", "114499"),
                    new Funcionario(matriz.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"),
                    new Funcionario(filial.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"));
                funcionarios.get(0).setPerfil(Perfil.ROLE_ADMIN);
                funcionarios.get(3).setPerfil(Perfil.ROLE_ADMIN);
                funcionarioRepository.saveAll(funcionarios);
                isSetUpRunned = true;
            }
        }
    }

    @AfterClass
    public static void oneTimeTearDown(){
        ApplicationContext context = ContextResolver.getContext();
        EmpresaRepository repoEmpre = context.getBean(EmpresaRepository.class);
        FuncionarioRepository repoFunc = context.getBean(FuncionarioRepository.class);
        repoEmpre.deleteAll();
        repoFunc.deleteAll();
    }

    @Test
    public void filtrandoPorCpf(){
        final String cpfFiltrar = "133355";

        Funcionario funcFiltrado = funcionarioRepository.findByCpf(cpfFiltrar);

        assertEquals(cpfFiltrar, funcFiltrado.getCpf());
    }

    @Test
    public void filtrandoPorEmail(){
        final String emailFiltrar = "teste1@gmail.com";

        Funcionario funcFiltrado = funcionarioRepository.findByEmail(emailFiltrar);

        assertEquals(emailFiltrar, funcFiltrado.getEmail());
    }

    @Test
    public void filtrandoPorEmailOuEmail(){
        final String emailFiltrar = "teste1@gmail.com";
        final String cpfFiltrar = "133355";

        Funcionario funcFiltradoEmail = funcionarioRepository.findByCpfOrEmail("13666", emailFiltrar);
        Funcionario funcFiltradoCpf = funcionarioRepository.findByCpfOrEmail(cpfFiltrar, "13666");

        assertEquals(emailFiltrar, funcFiltradoEmail.getEmail());
        assertEquals(cpfFiltrar, funcFiltradoCpf.getCpf());
    }

    @Test
    public void buscandoPorEmpresa(){

        Set<Funcionario> result = funcionarioRepository.findByEmpresaId(matriz.getId());

        assertEquals(3, result.size());
    }

    @Test
    public void filtrandoComSpecPorNome(){
        Specification<Funcionario> spec1 = FuncionarioQuery.nameLike("e 2");
        Specification<Funcionario> spec2 = FuncionarioQuery.nameLike(null);
        Specification<Funcionario> spec3 = FuncionarioQuery.nameLike("");

        List<Funcionario> result1 = funcionarioRepository.findAll(spec1);
        List<Funcionario> result2 = funcionarioRepository.findAll(spec2);
        List<Funcionario> result3 = funcionarioRepository.findAll(spec3);

        assertEquals(2, result1.size());
        assertEquals("Teste 2", result1.get(0).getNome());
        assertEquals("Teste 2", result1.get(1).getNome());
        assertEquals(6, result2.size());
        assertEquals(6, result3.size());
    }

    @Test
    public void filtrandoComSpecPorEmail(){
        Specification<Funcionario> spec1 = FuncionarioQuery.emailLike("teste2@gmail.com");
        Specification<Funcionario> spec2 = FuncionarioQuery.emailLike(null);
        Specification<Funcionario> spec3 = FuncionarioQuery.emailLike("");

        List<Funcionario> result1 = funcionarioRepository.findAll(spec1);
        List<Funcionario> result2 = funcionarioRepository.findAll(spec2);
        List<Funcionario> result3 = funcionarioRepository.findAll(spec3);

        assertEquals(2, result1.size());
        assertEquals("teste2@gmail.com", result1.get(0).getEmail());
        assertEquals("teste2@gmail.com", result1.get(1).getEmail());
        assertEquals(6, result2.size());
        assertEquals(6, result3.size());
    }

    @Test
    public void filtrandoComSpecPorCpf(){
        Specification<Funcionario> spec1 = FuncionarioQuery.cpfEquals("133355");
        Specification<Funcionario> spec2 = FuncionarioQuery.cpfEquals(null);
        Specification<Funcionario> spec3 = FuncionarioQuery.cpfEquals("");

        List<Funcionario> result1 = funcionarioRepository.findAll(spec1);
        List<Funcionario> result2 = funcionarioRepository.findAll(spec2);
        List<Funcionario> result3 = funcionarioRepository.findAll(spec3);

        assertEquals(1, result1.size());
        assertEquals("133355", result1.get(0).getCpf());
        assertEquals(0, result2.size());
        assertEquals(0, result3.size());
    }

    @Test
    public void filtrandoComSpecPorEmpresa(){
        Specification<Funcionario> spec1 = FuncionarioQuery.empresaEquals(matriz.getId());
        Specification<Funcionario> spec2 = FuncionarioQuery.empresaEquals(filial.getId());
        Specification<Funcionario> spec3 = FuncionarioQuery.empresaEquals(null);

        List<Funcionario> result1 = funcionarioRepository.findAll(spec1);
        List<Funcionario> result2 = funcionarioRepository.findAll(spec2);
        List<Funcionario> result3 = funcionarioRepository.findAll(spec3);

        assertEquals(3, result1.size());
        assertEquals(3, result1.stream().filter(e -> e.getEmpresa().getId().equals(matriz.getId())).count());

        assertEquals(3, result2.size());
        assertEquals(3, result2.stream().filter(e -> e.getEmpresa().getId().equals(filial.getId())).count());

        assertEquals(0, result3.size());
    }

    @Test
    public void filtrandoComSpecPorPerfil(){
        Set<Perfil> perfis1 = Arrays.asList(Perfil.ROLE_USUARIO).stream().collect(Collectors.toSet());
        Set<Perfil> perfis2 = Arrays.asList(Perfil.ROLE_ADMIN).stream().collect(Collectors.toSet());
        Set<Perfil> perfis3 = Arrays.asList(Perfil.ROLE_USUARIO, Perfil.ROLE_ADMIN).stream().collect(Collectors.toSet());

        Specification<Funcionario> spec1 = FuncionarioQuery.perfilIn(perfis1);
        Specification<Funcionario> spec2 = FuncionarioQuery.perfilIn(perfis2);
        Specification<Funcionario> spec3 = FuncionarioQuery.perfilIn(perfis3);
        Specification<Funcionario> spec4 = FuncionarioQuery.perfilIn(null);
        Specification<Funcionario> spec5 = FuncionarioQuery.perfilIn(new HashSet<>());

        List<Funcionario> result1 = funcionarioRepository.findAll(spec1);
        List<Funcionario> result2 = funcionarioRepository.findAll(spec2);
        List<Funcionario> result3 = funcionarioRepository.findAll(spec3);
        List<Funcionario> result4 = funcionarioRepository.findAll(spec4);
        List<Funcionario> result5 = funcionarioRepository.findAll(spec5);

        assertEquals(4, result1.size());
        assertEquals(result1.stream().filter(f -> f.getPerfil().equals(Perfil.ROLE_USUARIO)).count(), 4);

        assertEquals(2, result2.size());
        assertEquals(result2.stream().filter(f -> f.getPerfil().equals(Perfil.ROLE_ADMIN)).count(), 2);

        assertEquals(6, result3.size());

        assertEquals(6, result4.size());

        assertEquals(6, result5.size());
    }

    @Test
    public void combinandoSpecs(){
        Set<Perfil> perfis = Arrays.asList(Perfil.ROLE_ADMIN).stream().collect(Collectors.toSet());
        Specification<Funcionario> spec1 = FuncionarioQuery.perfilIn(perfis);
        Specification<Funcionario> spec2 = FuncionarioQuery.nameLike("3");
        Specification<Funcionario> spec3 = FuncionarioQuery.empresaEquals(matriz.getId());

        Specification<Funcionario> finalQuery = Specification.where(spec1).or(spec2.and(spec3));

        List<Funcionario> result = funcionarioRepository.findAll(finalQuery);

        assertEquals(result.size(), 3);
    }

}
