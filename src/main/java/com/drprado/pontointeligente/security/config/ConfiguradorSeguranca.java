package com.drprado.pontointeligente.security.config;


import com.drprado.pontointeligente.crosscutting.util.CustomPasswordEncrypter;
import com.drprado.pontointeligente.security.filters.JWTAuthenticationFilter;
import com.drprado.pontointeligente.security.services.ResolvedorUsuarioAutenticao;
import com.drprado.pontointeligente.security.services.SecurityExceptionsHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguradorSeguranca extends WebSecurityConfigurerAdapter {

    @Autowired
    ResolvedorUsuarioAutenticao resolvedorUsuarioAutenticao;

    @Autowired
    private SecurityExceptionsHandler securityExceptionsHandler;

    @Autowired
    private CustomPasswordEncrypter customPasswordEncrypter;

    // Com esse método podemos ignorar requests
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

    // ************* JWT AUTH
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(securityExceptionsHandler)
//                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
        impl.setUserDetailsService(resolvedorUsuarioAutenticao);
        impl.setPasswordEncoder(customPasswordEncrypter);
        impl.setHideUserNotFoundExceptions(false) ;
        return impl ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // ************ IN MEMORY AUTH
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }
//
//    //     Configuração com usuários em memória
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("admin").password("admin").roles("USER", "ADMIN")
//                .and()
//                .withUser("adriano")
//                .password("102030")
//                .roles("USER");
//    }
}
