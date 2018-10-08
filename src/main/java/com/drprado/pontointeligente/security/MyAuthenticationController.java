package com.drprado.pontointeligente.security;

import com.drprado.pontointeligente.api.dtos.Response;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

//@RestController
//@CrossOrigin("*")
//@RequestMapping("/api/authentication")
public class MyAuthenticationController {
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private FuncionarioRepository funcionarioRepository;
//
//    @PostMapping("/signin")
//    public ResponseEntity<Response<String>> signin(@RequestBody @Validated LoginDto dto){
//        try{
//            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
//            UserSecurity user = (UserSecurity)authenticate.getPrincipal();
//            String token = jwtTokenProvider.createToken(user);
//            Response<String> response = new Response<>();
//            response.setData(token);
//            return ResponseEntity.ok(response);
//
//        }catch (UsernameNotFoundException ex){
//            Response<String> response = new Response<>();
//            response.setErrors(Arrays.asList("Usuário não existe!"));
//            return ResponseEntity.badRequest().body(response);
//        }catch (LockedException ex){
//            Response<String> response = new Response<>();
//            response.setErrors(Arrays.asList("Conta bloqueada!"));
//            return ResponseEntity.badRequest().body(response);
//        }catch (AccountExpiredException ex){
//            Response<String> response = new Response<>();
//            response.setErrors(Arrays.asList("Conta expirada!"));
//            return ResponseEntity.badRequest().body(response);
//        }catch (CredentialsExpiredException ex){
//            Response<String> response = new Response<>();
//            response.setErrors(Arrays.asList("Credenciais expiradas! Por favor relogue."));
//            return ResponseEntity.badRequest().body(response);
//        }catch (DisabledException ex){
//            Response<String> response = new Response<>();
//            response.setErrors(Arrays.asList("Conta desabilitada!"));
//            return ResponseEntity.badRequest().body(response);
//        } catch (AuthenticationException ex){
//            Response<String> response = new Response<>();
//            response.setErrors(Arrays.asList("Senha inválida"));
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
}
