package com.drprado.pontointeligente.integration.domain.services;

import com.drprado.pontointeligente.domain.builders.GenericFilterBuilder;
import com.drprado.pontointeligente.domain.builders.GenericFiltersBuilder;
import com.drprado.pontointeligente.domain.dto.*;
import com.drprado.pontointeligente.domain.enums.Perfil;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.services.FuncionarioService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    FuncionarioService funcionarioService;

    private volatile static boolean isSetUpRunned = false;
    private static Empresa matriz;
    private static Empresa filial;

    @Before
    public void setUp(){
        synchronized (FuncionarioServiceTest.class){
            if(!isSetUpRunned){
                matriz = empresaRepository.save(new Empresa("Matriz", "3333171866"));
                filial = empresaRepository.save(new Empresa("Filial", "4444171866"));

                List<Funcionario> funcionarios = Arrays.asList(
                        new Funcionario(matriz.getId(),"Teste 4", "teste4@gmail.com", "tete4", "133355"),
                        new Funcionario(filial.getId(),"Teste 2", "teste3@gmail.com", "tete2", "151417"),
                        new Funcionario(matriz.getId(),"Teste 2", "teste2@gmail.com", "tete2", "151417"),
                        new Funcionario(filial.getId(),"Teste 1", "teste1@gmail.com", "tete1", "114499"),
                        new Funcionario(matriz.getId(),"Teste 3", "teste3@gmail.com", "tete3", "141413"),
                        new Funcionario(filial.getId(),"Teste 1", "teste4@gmail.com", "tete3", "141413"));
                funcionarios.get(0).setPerfil(Perfil.ROLE_ADMIN);
                funcionarios.get(3).setPerfil(Perfil.ROLE_ADMIN);
                funcionarioRepository.saveAll(funcionarios);
                isSetUpRunned = true;
            }
        }
    }

    @Test
    public void filtrandoPorNome(){
        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("nome", "e 2"))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(2, result.stream().filter(f -> f.getNome().equals("Teste 2")).count());
    }

    @Test
    public void filtrandoPorCpf(){
        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("cpf", "133355"))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.stream().filter(f -> f.getCpf().equals("133355")).count());
    }

    @Test
    public void filtrandoPorEmail(){
        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("email", "2"))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.stream().filter(f -> f.getEmail().equals("teste2@gmail.com")).count());
    }

    @Test
    public void filtrandoPorEmpresa(){
        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("empresaId", matriz.getId()))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals(3, result.stream().filter(f -> f.getEmpresa().getId().equals(matriz.getId())).count());
    }

    @Test
    public void filtrandoPorPerfil(){
        Set<Perfil> perfis = Arrays.asList(Perfil.ROLE_ADMIN).stream().collect(Collectors.toSet());

        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("perfil", perfis))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(2, result.stream().filter(f -> f.getPerfil().equals(Perfil.ROLE_ADMIN)).count());
    }

    @Test
    public void combinandoFiltros(){
        Set<Perfil> perfis = Arrays.asList(Perfil.ROLE_ADMIN).stream().collect(Collectors.toSet());

        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("perfil", perfis))
                        .withFilter(new GenericFilterField("nome", "4"))
                        .build())
                .andFilter(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                    .withFilter(new GenericFilterField("nome", "2"))
                    .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1, result.stream().filter(f -> f.getPerfil().equals(Perfil.ROLE_ADMIN)).count());
        Assert.assertEquals(2, result.stream()
            .filter(f -> f.getPerfil().equals(Perfil.ROLE_USUARIO) && f.getNome().equals("Teste 2")).count());
    }

    @Test
    public void combinandoFiltrosEOrdenacao(){
        Set<Perfil> perfis = Arrays.asList(Perfil.ROLE_USUARIO).stream().collect(Collectors.toSet());

        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("perfil", perfis))
                        .withFilter(new GenericFilterField("nome", "1"))
                        .build())
                .andFilter(GenericFilterBuilder.of(FilterConnectionType.OR, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("nome", "2"))
                        .build())
                .andOrderBy(new GenericOrder("nome", OrderType.ASC))
                .andOrderBy(new GenericOrder("email", OrderType.ASC))
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.get(0).getNome().equals("Teste 1") && result.get(0).getEmail().equals("teste4@gmail.com"));
        Assert.assertTrue(result.get(1).getNome().equals("Teste 2") && result.get(1).getEmail().equals("teste2@gmail.com"));
        Assert.assertTrue(result.get(2).getNome().equals("Teste 2") && result.get(2).getEmail().equals("teste3@gmail.com"));
    }

    @Test
    public void combinandoFiltrosComAnd(){
        Set<Perfil> perfis = Arrays.asList(Perfil.ROLE_ADMIN).stream().collect(Collectors.toSet());

        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.AND, FilterConnectionType.OR)
                        .withFilter(new GenericFilterField("nome", "2"))
                        .withFilter(new GenericFilterField("nome", "1"))
                        .build())
                .andFilter(GenericFilterBuilder.of(FilterConnectionType.AND, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("perfil", perfis))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0).getNome().equals("Teste 1") && result.get(0).getPerfil().equals(Perfil.ROLE_ADMIN));
    }

    @Test
    public void filtrosNulosSemPreenchimentoCamposInexistentesDevemSerIgnorados(){
        GenericFilters filters = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.AND, FilterConnectionType.AND)
                        .withFilter(new GenericFilterField("nome", ""))
                        .withFilter(new GenericFilterField("nome", null))
                        .withFilter(new GenericFilterField("name", "nao tem"))
                        .build())
                .build();

        List<Funcionario> result = funcionarioService.buscaFiltrada(filters);
        List<Funcionario> all = funcionarioRepository.findAll();

        Assert.assertEquals(all.size(), result.size());
    }

    @Test
    public void filtrosComTiposErradosNaoDevemVoltarNada(){
        GenericFilters filterString = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.AND, FilterConnectionType.OR)
                        .withFilter(new GenericFilterField("nome", 24))
                        .withFilter(new GenericFilterField("nome", 12L))
                        .withFilter(new GenericFilterField("nome", true))
                        .build())
                .build();

        GenericFilters filterLong = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.AND, FilterConnectionType.OR)
                        .withFilter(new GenericFilterField("empresaId", "teste"))
                        .withFilter(new GenericFilterField("empresaId", true))
                        .build())
                .build();

        Set<Empresa> empresas = Arrays.asList(matriz).stream().collect(Collectors.toSet());
        GenericFilters filterCollection = GenericFiltersBuilder
                .where(GenericFilterBuilder.of(FilterConnectionType.AND, FilterConnectionType.OR)
                        .withFilter(new GenericFilterField("perfil", "teste"))
                        .withFilter(new GenericFilterField("perfil", true))
                        .withFilter(new GenericFilterField("perfil", 24))
                        .withFilter(new GenericFilterField("perfil", empresas))
                        .build())
                .build();

        List<Funcionario> resultString = funcionarioService.buscaFiltrada(filterString);
        List<Funcionario> resultLong = funcionarioService.buscaFiltrada(filterLong);
        List<Funcionario> resultCollection = funcionarioService.buscaFiltrada(filterCollection);

        Assert.assertEquals(0, resultString.size());
        Assert.assertEquals(0, resultLong.size());
        Assert.assertEquals(0, resultCollection.size());
    }
}
