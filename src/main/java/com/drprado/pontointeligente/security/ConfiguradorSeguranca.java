package com.drprado.pontointeligente.security;


import com.drprado.pontointeligente.crosscutting.util.CustomPasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfiguradorSeguranca extends WebSecurityConfigurerAdapter {

    // Com esse método podemos ignorar requests
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/signup","/about").permitAll() // #4
                .antMatchers("/admin/**").hasRole("ADMIN") // #6
                .anyRequest().authenticated() // 7
                .and()
                .httpBasic()
        .and()
        .csrf().disable();
    }

    // Configuração com usuários em memória
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("admin").password("admin").roles("USER", "ADMIN")
//                .and()
//                .withUser("adriano")
//                .password("102030")
//                .roles("USER");
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new CustomPasswordEncrypter())
                .withUser("admin").password("admin").roles("USER", "ADMIN")
                .and()
                .withUser("adriano")
                .password("102030")
                .roles("USER");
    }
}
