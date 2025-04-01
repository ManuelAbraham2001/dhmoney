package com.dh.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();

            // Configurar SOLO el origen específico
            configuration.setAllowedOrigins(List.of("http://localhost:3000"));

            // Métodos HTTP permitidos
            configuration.setAllowedMethods(List.of(
                    "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
            ));

            // Encabezados permitidos
            configuration.setAllowedHeaders(List.of("*"));

            // Permitir credenciales
            configuration.setAllowCredentials(true);

            return configuration;
        };
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors((cors) -> corsConfigurationSource())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/accounts/v3/api-docs"
                        ).permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation("http://localhost:8080/realms/dh");
    }

//    @Bean
//    public ReactiveJwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
//        return JwtDecoders.fromIssuerLocation("http://localhost:8080/realms/dh");
//    }
}
