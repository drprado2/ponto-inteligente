package com.drprado.pontointeligente.security.auth0;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@SuppressWarnings("unused")
@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/portal/home", method = RequestMethod.GET)
    protected ResponseEntity<Object> home(final Map<String, Object> model, final Principal principal) {
        logger.info("Home page");
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/login").body(null);
        }
        return ResponseEntity.ok(principal);
    }

}
