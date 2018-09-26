package com.drprado.pontointeligente.security.controllers;

import com.drprado.pontointeligente.api.dtos.Response;
import com.drprado.pontointeligente.security.dtos.LoginDto;
import com.drprado.pontointeligente.security.dtos.TokenDto;
import com.drprado.pontointeligente.security.services.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<Response<TokenDto>> gerarTokenJwt(
            @Valid @RequestBody LoginDto authenticationDto, BindingResult result)
            throws AuthenticationException {
        Response<TokenDto> response = new Response<TokenDto>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDto.getUsername(), authenticationDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getUsername());
            String token = jwtHelper.obterToken(userDetails);
            response.setData(new TokenDto(token));

            return ResponseEntity.ok(response);

        }catch (BadCredentialsException error){
            List<String> errors = Arrays.asList("Senha inválida");
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }catch (UsernameNotFoundException error){
            List<String> errors = Arrays.asList("Usuário inexistente");
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }catch (Throwable error){
            List<String> errors = Arrays.asList(error.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
