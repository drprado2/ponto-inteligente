package com.drprado.pontointeligente.unit.domain.services;

import com.drprado.pontointeligente.domain.entities.Empresa;
import com.drprado.pontointeligente.domain.repositories.EmpresaRepository;
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
public class EmpresaServiceTest {

    @Autowired
    private EmpresaService empresaService;

    @MockBean
    private EmpresaRepository empresaRepository;

    @Test
    public void buscandoEmpresaPorCnpj(){
        final String CNPJ = "102033445677";
        Empresa empresa = new Empresa("Minha Empresa", CNPJ);
        Mockito.when(empresaRepository.findByCnpj(CNPJ)).thenReturn(empresa);

        Optional<Empresa> result = empresaService.buscarPorCNPJ(CNPJ);

        assertEquals(empresa.getCnpj(), result.get().getCnpj());
    }

    @Test
    public void criandoEmpresaValida(){
        final String CNPJ = "102033445677";
        Empresa empresa = new Empresa("Minha Empresa", CNPJ);
        Mockito.when(empresaRepository.save(empresa)).thenReturn(empresa);

        Empresa result = empresaService.salvar(empresa);

        Mockito.verify(empresaRepository, Mockito.times(1)).save(empresa);
        assertEquals(CNPJ, result.getCnpj());
    }
}
