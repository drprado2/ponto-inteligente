package com.drprado.pontointeligente.integration.domain.repositories;

import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "51463645000100";
    private static final String razaoSocial = "Minha Empresa";

    @Before
    public void setUp(){
        Empresa empresa = new Empresa(razaoSocial, CNPJ);
        empresaRepository.save(empresa);
    }

    @After
    public void tearDown(){
        empresaRepository.deleteAll();
    }

    @Test
    public void buscandoPorCNPJ(){
        Empresa empresa = empresaRepository.findByCnpj(CNPJ);

        assertEquals(CNPJ, empresa.getCnpj());
    }
}
