package com.drprado.pontointeligente.security;

import com.auth0.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.UnsupportedEncodingException;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig  {
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private ResolvedorUsuarioAutenticao userService;
//
//    @Autowired
//    private SecurityExceptionsHandler securityExceptionsHandler;
////
////    @Value(value = "${com.auth0.domain}")
////    private String domain;
////
////    @Value(value = "${com.auth0.clientId}")
////    private String clientId;
////
////    @Value(value = "${com.auth0.clientSecret}")
////    private String clientSecret;
////
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // Disable CSRF (cross site request forgery)
//        http.csrf().disable();
//
//        // No session will be created or used by spring security
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
//
//        http.exceptionHandling().authenticationEntryPoint(securityExceptionsHandler);
//
//        // Entry points
//        http.authorizeRequests()//
//                .antMatchers(HttpMethod.POST,"/api/authentication/signin").permitAll()//
////                .antMatchers(HttpMethod.POST,"/api/funcionarios/").permitAll()//
//                .antMatchers("/h2-console/**/**").permitAll()
//                .antMatchers("/callback", "/login").permitAll()
//                // Disallow everything else..
//                .anyRequest().authenticated()
//                .and()
//                .logout().permitAll();

        // JWT Filter
//        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    @Autowired
//    public AuthenticationController authenticationController(AuthenticationController authController) throws UnsupportedEncodingException {
//        return authController;
//    }
//
//    public AuthenticationProvider getAuthenticationProvider() {
//        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
//        impl.setUserDetailsService(userService);
//        impl.setPasswordEncoder(passwordEncoder());
//        impl.setHideUserNotFoundExceptions(false) ;
//        return impl ;
//    }
//
//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(getAuthenticationProvider());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // Allow swagger to be accessed without authentication
//        web.ignoring().antMatchers("/v2/api-docs")//
//                .antMatchers("/swagger-resources/**")//
//                .antMatchers("/swagger-ui.html")//
//                .antMatchers("/configuration/**")//
//                .antMatchers("/webjars/**")//
//                .antMatchers("/public")
//
//                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
//                .and()
//                .ignoring()
//                .antMatchers("/h2-console/**/**");;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }

}
