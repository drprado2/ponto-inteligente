package com.drprado.pontointeligente.api.controllers;

import com.drprado.pontointeligente.api.dtos.Response;
import com.drprado.pontointeligente.domain.dtos.empresa.CriarEmpresaDto;
import com.drprado.pontointeligente.domain.dtos.empresa.ExemploDtoList;
import com.drprado.pontointeligente.domain.exceptions.MyCustomException;
import com.drprado.pontointeligente.domain.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Trata o HTTP STATUS CODE de alguma exceção que veia ocorrer no programa
    @ExceptionHandler({ MyCustomException.class })
    public ResponseEntity<Object> handleAll(MyCustomException error, WebRequest request) {
        return ResponseEntity.badRequest().body("Ocorreu um erro customizado: " + error.getMessage());
    }

    @GetMapping("/hello")
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
        throw new MyCustomException("Meu Erro Genéricos");
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

        return ResponseEntity.ok().body("Funcionou");
    }

    @PostMapping("/listas")
    public ResponseEntity exemploListasGet(@RequestBody @NotNull @Valid ExemploDtoList dto, Errors errors) {
        if (errors.hasErrors()) {
            List<String> errosTratados = errors.getFieldErrors()
                    .stream()
                    .map(e -> "O campo: " + e.getField() + " apresenta o seguinte erro: " + e.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errosTratados);
        }

        return ResponseEntity.ok("Funcionou com sucesso");
    }
}
