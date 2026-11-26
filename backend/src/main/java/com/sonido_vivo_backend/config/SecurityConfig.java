
package com.sonido_vivo_backend.config;

import com.sonido_vivo_backend.security.JwtAuthFilter;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    // Codificación de contraseñas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Administrador de autenticación
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Evita el registro automático del filtro JWT como filtro Servlet
    @Bean
    FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration(
            JwtAuthFilter jwt) {

        FilterRegistrationBean<JwtAuthFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(jwt);
        registration.setEnabled(false);

        return registration;
    }

    // Configuración principal de seguridad
    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthFilter jwt) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Consulta pública del catálogo
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos/**",
                                "/api/categorias/**"
                        ).permitAll()

                        // Administración de usuarios
                        .requestMatchers("/api/usuarios/**")
                        .hasRole("ADMIN")

                        // Administración de categorías
                        .requestMatchers("/api/categorias/**")
                        .hasRole("ADMIN")

                        // Administración de productos
                        .requestMatchers("/api/productos/**")
                        .hasRole("ADMIN")

                        // Administración de órdenes
                        .requestMatchers(HttpMethod.PUT, "/api/ordenes/**")
                        .hasRole("ADMIN")

                        // Resto de operaciones autenticadas
                        .anyRequest().authenticated()
                )

                // Registro del filtro JWT en Spring Security
                .addFilterBefore(
                        jwt,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    // Configuración CORS para el frontend
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
