package com.drprado.pontointeligente.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/api/logout")
public class LogoutController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected String logout(final HttpServletRequest req) {
        invalidateSession(req);
        return "redirect:" + req.getContextPath() + "/api/login/";
    }

    private void invalidateSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

}
