package com.transbox_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de TransBox.
 * Define los recursos públicos y los permisos por rol para las APIs.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // Las APIs REST usan autenticación y no requieren token CSRF en Apidog.
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

                .authorizeHttpRequests(auth -> auth

                    // Recursos públicos requeridos por la pantalla de inicio de sesión.
                    .requestMatchers(
                        "/login",
                        "/css/**",
                        "/img/**"
                    ).permitAll()

                    // Administración de usuarios y roles.
                    .requestMatchers(
                        "/api/usuarios/**",
                        "/api/roles/**"
                    ).hasRole("ADMINISTRADOR")

                    // Pedidos: personal administrativo, comercial y empresa logística.
                    .requestMatchers("/api/pedidos/**")
                    .hasAnyRole("ADMINISTRADOR", "VENDEDOR", "EMPRESA")

                    // Franjas de entrega: administración, vendedor y empresa.
                    .requestMatchers("/api/franjas/**")
                    .hasAnyRole("ADMINISTRADOR", "VENDEDOR", "EMPRESA")

                    // Vehículos: administración y empresa logística.
                    .requestMatchers("/api/vehiculos/**")
                    .hasAnyRole("ADMINISTRADOR", "EMPRESA")

                    // Las demás rutas requieren una sesión autenticada.
                    .anyRequest().authenticated()
                )

                // También habilita HTTP Basic para facilitar las pruebas de API en Apidog.
                .httpBasic(Customizer.withDefaults())

                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/panelCentral.html", true)
                    .failureUrl("/login?error")
                    .permitAll()
                )

                .logout(Customizer.withDefaults())
                .build();
    }
}
