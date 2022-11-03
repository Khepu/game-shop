package com.gmakris.gameshop.gateway.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ApiSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        final ServerHttpSecurity http
    ) {
        return http
            .csrf().disable()
            .authorizeExchange()
                .pathMatchers("/admin/**").hasRole("ADMIN")
                .pathMatchers("/auth/register", "/games", "/games/*").permitAll()
                .anyExchange().authenticated()
            .and()
                .httpBasic()
            .and()
                .formLogin().disable()
            .build();
    }
}
