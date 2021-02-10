package com.cloud.examsystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage")
                .permitAll(false)
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new RestAuthenticationSuccessHandler();
//    }
//
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new RestAuthenticationFailureHandler();

//    }

//
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return new RestLogoutSuccessHandler();
//    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter authFilter() throws Exception {
//        UsernamePasswordAuthenticationFilter authenticationFilter = new RestAuthenticationFilter(objectMapper);
//        authenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
//        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
//        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
//        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
//
//        return authenticationFilter;
//    }

//    @Bean
//    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new CompositeSessionAuthenticationStrategy(Arrays.asList(
//                new ChangeSessionIdAuthenticationStrategy()
//        ));
//    }
}
