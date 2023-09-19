package com.prashik.keycloaksecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String MANAGER = "manager";
    private final JwtAuthConverter jwtAuthConverter;
    
    @Autowired
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;
    
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/test/anonymous", "/test/anonymous/**","/test/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/test/user").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/test/manager").hasAnyRole(ADMIN, MANAGER);
                //.anyRequest().authenticated();
                //.and().formLogin().loginPage("http://localhost:8080/realms/cognologix/protocol/openid-connect/auth?response_type=code&client_id=dummyclient&redirect_uri=http://localhost:8081/api/test/user&state=beaeb00c-6f22-4d94-9ef7-b947f83e7346&login=true&scope=openid").permitAll();
        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
