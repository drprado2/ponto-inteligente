package com.drprado.pontointeligente.security;
import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.drprado.pontointeligente.crosscutting.util.JsonDeserializer;
import com.drprado.pontointeligente.domain.dtos.funcionarios.FuncionarioDto;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.enums.Perfil;
import com.drprado.pontointeligente.domain.repositories.FuncionarioRepository;
import com.drprado.pontointeligente.domain.services.FuncionarioService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("unused")
@RequestMapping("/api/login")
@Controller
public class LoginController {

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private Auth0Configs auth0Configs;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private final static String redirectOnFail = "/api/login";
    private final static String redirectOnSuccess = "/portal/home";

    @RequestMapping(value = "", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) {
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/api/login/callback";
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", auth0Configs.getDomain()))
                .withScope("openid profile email user_metadata")
                .build();
        return "redirect:" + authorizeUrl;
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException, UnirestException {
        handle(req, res);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException, UnirestException {
        handle(req, res);
    }

    private void handle(HttpServletRequest req, HttpServletResponse res) throws IOException, UnirestException {
        try {
            Tokens tokens = controller.handle(req);
            UserInfoDto userInfo = getUserInfo(tokens);
            UserSecurity userSecurity = makeBridgeBetweenSecurityUserAndDomainUser(userInfo);

            String token = jwtTokenProvider.createToken(userSecurity, userInfo);

            SecurityContextHolder.getContext().setAuthentication(new AuthenticationToken(JWT.decode(token)));
            res.sendRedirect(redirectOnSuccess);
        } catch (AuthenticationException | IdentityVerificationException e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            res.sendRedirect(redirectOnFail);
        }
    }

    private UserSecurity makeBridgeBetweenSecurityUserAndDomainUser(UserInfoDto userInfo){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(userInfo.getEmail());
        FuncionarioDto dto = new FuncionarioDto(
                userInfo.getName(),
                userInfo.getEmail(),
                null,
                null,
                null,
                null,
                null,
                Perfil.ROLE_USUARIO,
                null);
        if(funcionario.isPresent()){
            dto.setId(funcionario.get().getId());
            Funcionario funcAtualizado = funcionarioService.atualizar(dto);
            return new UserSecurity(funcAtualizado);
        }else{
            Funcionario funcCriado = funcionarioService.criar(dto);
            return new UserSecurity(funcCriado);
        }
    }

    private UserInfoDto getUserInfo(Tokens tokens) throws UnirestException, IOException {
        HttpResponse<String> response = Unirest.post("https://adri-oauth-test.auth0.com/userinfo")
                .header("Authorization", "Bearer " + tokens.getAccessToken())
                .asString();
        UserInfoDto userInfoDto = JsonDeserializer.getInstance().readValue(response.getBody(), UserInfoDto.class);
        JSONObject jsonObj = new JSONObject(response.getBody());
        String nameKey = jsonObj.keySet().stream().filter(k -> ((String) k).matches("^.*domain.name$")).findFirst().get().toString();
        String cpfKey = jsonObj.keySet().stream().filter(k -> ((String) k).matches("^.*domain.cpf$")).findFirst().get().toString();
        String name = jsonObj.get(nameKey).toString();
        String cpf = jsonObj.get(cpfKey).toString();
        userInfoDto.setDomainName(name);
        userInfoDto.setDomainCpf(cpf);
        return userInfoDto;
    }
}
