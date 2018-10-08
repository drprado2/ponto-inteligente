package com.drprado.pontointeligente.security.auth0;

import com.auth0.AuthenticationController;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
public class LoginController {

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private SecurityConfig appConfig;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) throws UnirestException {
        logger.debug("Performing login");

        HttpResponse<Object> response = Unirest.post("https://adri-oauth-test.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"nj6xW1A9QxU486Gy5VA3UD3hipOxUU3G\",\"client_secret\":\"ibhEFXUTLwU9JboBP5ezGZx9yKoJZNUPsC1MdtPxKI4XCOPIjLzfpkFBCBohx8ad\",\"audience\":\"https://adri-oauth-test.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                .asObject(Object.class);


        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/callback";
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", appConfig.getDomain()))
                .build();
        return "redirect:" + authorizeUrl;
    }

}
