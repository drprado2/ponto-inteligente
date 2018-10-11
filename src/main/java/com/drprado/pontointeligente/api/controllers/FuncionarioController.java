package com.drprado.pontointeligente.api.controllers;

import com.drprado.pontointeligente.domain.dtos.funcionarios.FuncionarioDto;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.services.FuncionarioService;
import org.hibernate.event.internal.AbstractSaveEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/")
    @Secured("ROLE_MASTER")
    public ResponseEntity<Funcionario> criar(@RequestBody FuncionarioDto request){
        Funcionario funcionario = funcionarioService.criar(request);
        return ResponseEntity.ok(funcionario);
    }
}
