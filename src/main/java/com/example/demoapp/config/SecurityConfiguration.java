package com.example.demoapp.config;

import com.example.demoapp.controller.UserRequestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.securityMatcher(UserRequestController.CONTEXT_PATH + "/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(UserRequestController.CONTEXT_PATH + UserRequestController.VER_1_REQUEST_PATH).permitAll()
                        .requestMatchers(UserRequestController.CONTEXT_PATH + UserRequestController.VER_2_REQUEST_PATH).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
