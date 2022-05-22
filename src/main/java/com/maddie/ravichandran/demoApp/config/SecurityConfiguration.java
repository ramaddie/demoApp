package com.maddie.ravichandran.demoApp.config;

import com.maddie.ravichandran.demoApp.controller.MyController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration
{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        return http.antMatcher(MyController.CONTEXT_PATH)
                .authorizeRequests()
                .antMatchers(MyController.VER_1_REQUEST_PATH).permitAll()
                .antMatchers(MyController.VER_2_REQUEST_PATH).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .build();
    }
}
