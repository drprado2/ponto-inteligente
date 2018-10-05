package com.drprado.pontointeligente.api.controllers;

import com.drprado.pontointeligente.domain.dtos.empresa.CriarEmpresaDto;
import com.drprado.pontointeligente.domain.dtos.empresa.ExemploDtoList;
import com.drprado.pontointeligente.domain.reports.TestReport;
import com.drprado.pontointeligente.domain.reports.TestReportGenerator;
import com.drprado.pontointeligente.domain.repositories.TestReportRepository;
import com.drprado.pontointeligente.domain.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TestReportGenerator relatorio;

    @Autowired
    private TestReportRepository testReportRepository;

    @GetMapping("/hello")
    @Secured("ROLE_PROFESSOR")
    public ResponseEntity<String> hello(@RequestParam(name = "nome") String nome){
        return new ResponseEntity<>("Olá " + nome, HttpStatus.OK);
    }

    @PostMapping("/erro")
    public ResponseEntity<String> erro(){
        return ResponseEntity
                .badRequest()
                .header("Teste", "Meu valor")
                .header("Teste 2", "meu valor 2")
                .body("Algo deu errado");
    }

    @GetMapping("/cookies")
    public ResponseEntity<String> comCookie(@CookieValue(value = "meu-cookie", required = false, defaultValue = "CookieInicial") String valorCookie, HttpServletResponse response){
        Cookie cook = new Cookie("meu-cookie", "CookieAlterado");
        response.addCookie(cook);
        return ResponseEntity.ok().body("Valor do cookie é " + valorCookie);
    }

    @GetMapping("/exceptions")
    public ResponseEntity exceptions(){
        empresaService.lancarErro();
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/unex-error")
    public ResponseEntity unexpectedEx(){
        throw new Error("ERRO INESPERADO!!!");
    }

    @PostMapping("/type-param")
    public ResponseEntity getWithTypeParam(@RequestBody @Nullable @Valid CriarEmpresaDto dto, Errors errors){
        if(errors.hasErrors()){
            List<String> errosTratados = errors.getFieldErrors()
                    .stream()
                    .map(e -> "O campo: " + e.getField() + " apresenta o seguinte erro: " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errosTratados);
        }
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/listas")
    public ResponseEntity<ExemploDtoList> exemploListasGet(@RequestBody @NotNull @Valid ExemploDtoList dto, Errors errors) {
        if (errors.hasErrors()) {
            List<String> errosTratados = errors.getFieldErrors()
                    .stream()
                    .map(e -> "O campo: " + e.getField() + " apresenta o seguinte erro: " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<TestReport>> relatorio(){
        List<TestReport> result = relatorio.generateEM();
        List<TestReport> all = testReportRepository.findAll();
        return ResponseEntity.ok(result);
    }
}
