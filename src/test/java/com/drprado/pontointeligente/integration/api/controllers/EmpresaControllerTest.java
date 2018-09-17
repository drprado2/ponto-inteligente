package com.drprado.pontointeligente.integration.api.controllers;

import com.drprado.pontointeligente.domain.dtos.empresa.CriarEmpresaDto;
import com.drprado.pontointeligente.domain.dtos.empresa.ExemploDtoList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.assertj.core.api.Assertions;
import org.hamcrest.Condition;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpresaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    JacksonTester<Object[]> errorsJson;
    JacksonTester<CriarEmpresaDto> criarEmpresaJson;
    JacksonTester<ExemploDtoList> dtoListJson;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void whenExecuteHelloShouldReturnHelloAndStatusCode200() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/empresas/hello")
                .param("nome", "adriano"));

        perform
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.content()
                    .string(CoreMatchers.containsString("Olá adriano")));
    }

    @Test
    public void shouldReturnAnError() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/empresas/erro"));

        result
            .andExpect(MockMvcResultMatchers.status().is(400))
            .andExpect(MockMvcResultMatchers.header().string("Teste", "Meu valor"))
            .andExpect(MockMvcResultMatchers.header().string("Teste 2", "meu valor 2"))
            .andExpect(MockMvcResultMatchers.content().string("Algo deu errado"));
    }

    @Test
    public void shouldCreateACookie() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/empresas/cookies"));

        result
            .andExpect(MockMvcResultMatchers.cookie().value("meu-cookie", "CookieAlterado"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Valor do cookie é CookieInicial"));
    }

    @Test
    public void whenExistsACookieShouldReturnContentWithCookieValue() throws Exception {
        Cookie cookie = new Cookie("meu-cookie", "Teste Cookie");
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/empresas/cookies").cookie(cookie));

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.cookie().value("meu-cookie", "CookieAlterado"))
            .andExpect(MockMvcResultMatchers.content().string("Valor do cookie é Teste Cookie"));
    }

    @Test
    public void shouldReturnATratedErrorWhenExceptionOccur() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/empresas/exceptions"));

        Object[] expectResult = {"Ocorreu o meu CUSTOM ERRO", "Meu erro CUSTOM"};
        JsonContent<Object[]> jsonExpect = errorsJson.write(expectResult);

        result
            .andExpect(MockMvcResultMatchers.status().is5xxServerError())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.content().json(jsonExpect.getJson()));
    }

    @Test
    public void whenAUnexpectedExceptionOccurrShouldReturnATratedError() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/empresas/unex-error"));

        MvcResult mvcResult = result.andReturn();

        Object[] resultObj = errorsJson.parse(mvcResult.getResponse().getContentAsString()).getObject();

        Assert.assertTrue(resultObj[0].toString().equals("Ocorreu um erro INESPERADO"));
        Assert.assertTrue(resultObj[1].toString().equals("ERRO INESPERADO!!!"));
    }

    @Test
    public void shouldReturnOk() throws Exception {
        JSONObject jsonRequest = new JSONObject();

        final String razaoSocial = "Minha Razão Social";
        final String cnpj = "72.972.468/0001-59";
        final String cpf = "29507745637";
        final String email = "teste2@gmail.com";
        final LocalDateTime dateTime = LocalDateTime.of(2018, 9, 16, 15, 14, 10);
        final LocalDate localDate = LocalDate.of(2018, 9, 17);
        final LocalTime localTime = LocalTime.of(15, 25);

        jsonRequest.put("razaoSocial", razaoSocial);
        jsonRequest.put("cnpj", cnpj);
        jsonRequest.put("cpf", cpf);
        jsonRequest.put("email", email);
        jsonRequest.put("dateTime", dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        jsonRequest.put("date", localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        jsonRequest.put("time", localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/empresas/type-param")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest.toString()));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        CriarEmpresaDto jsonResult = criarEmpresaJson.parse(result.andReturn().getResponse().getContentAsString()).getObject();

        Predicate<CriarEmpresaDto> condition = dto -> dto.getRazaoSocial().equals(razaoSocial)
                && dto.getCnpj().equals(cnpj)
                && dto.getCpf().equals(cpf)
                && dto.getEmail().equals(email)
                && dto.getDateTime().equals(dateTime)
                && dto.getDate().equals(localDate)
                && dto.getTime().equals(localTime);
        Assertions.assertThat(condition);
    }

    @Test
    public void ifRequestToListComingWithoutBodyRequestShouldReturnAnError() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/empresas/listas"));
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Object[] errors = errorsJson.parse(result.andReturn().getResponse().getContentAsString()).getObject();
        Assertions.assertThat(errors[0]).isEqualTo("Ocorreu um erro na desserialização da sua requisição");
    }

    @Test
    public void requestListShouldReturnOk() throws Exception {
        JSONObject jsonResult = new JSONObject();

        final String razaoSocial = "Minha Razão Social";
        final String cnpj = "72.972.468/0001-59";
        final String cpf = "29507745637";
        final String email = "teste2@gmail.com";
        final LocalDateTime dateTime = LocalDateTime.of(2018, 9, 16, 15, 14, 10);
        final LocalDate localDate = LocalDate.of(2018, 9, 17);
        final LocalTime localTime = LocalTime.of(15, 25);

        JSONObject criarEmpresaDto = new JSONObject();
        criarEmpresaDto.put("razaoSocial", razaoSocial);
        criarEmpresaDto.put("cnpj", cnpj);
        criarEmpresaDto.put("cpf", cpf);
        criarEmpresaDto.put("email", email);
        criarEmpresaDto.put("dateTime", dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        criarEmpresaDto.put("date", localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        criarEmpresaDto.put("time", localTime.format(DateTimeFormatter.ofPattern("HH:mm")));

        jsonResult.put("empresas", new JSONArray(new JSONObject[]{criarEmpresaDto}));
        jsonResult.put("longs", new JSONArray(new Long[]{1L}));
        jsonResult.put("datas", new JSONArray(new String[]{"15-08-2018"}));
        jsonResult.put("dataTempo", new JSONArray(new String[]{"2018-09-17T05:07:11.482Z"}));
        jsonResult.put("tempos", new JSONArray(new String[]{"15:10:10"}));
        jsonResult.put("textos", new JSONArray(new String[]{"Texto 1"}));
        jsonResult.put("doubles", new JSONArray(new Double[]{25.3}));
        jsonResult.put("booleans", new JSONArray(new Boolean[]{true}));
        jsonResult.put("guids", new JSONArray(new String[]{UUID.randomUUID().toString()}));
        jsonResult.put("ints", new JSONArray(new Integer[]{-23}));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/empresas/listas")
                .contentType("application/json;charset=UTF-8")
                .content(jsonResult.toString()));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        ExemploDtoList resultObj = dtoListJson.parse(result.andReturn().getResponse().getContentAsString()).getObject();
    }
}
