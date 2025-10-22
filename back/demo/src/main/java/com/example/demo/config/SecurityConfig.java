package com.example.demo.config;

import com.example.demo.tools.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService; // Votre implémentation
    private final PasswordEncoder passwordEncoder; // Votre Bean de PasswordEncoder

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                                 UserDetailsService userDetailsService,
                                 PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Définition des règles HTTP Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactiver le CSRF (car nous utilisons JWT)

                // Configuration des autorisations
                .authorizeHttpRequests(auth -> auth
                        // Permettre l'accès public au endpoint de login
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        // Exiger l'authentification pour tous les autres endpoints
                        .anyRequest().authenticated()
                )

                // Gestion de la session sans état (car nous utilisons JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Fournir l'UserDetailsService
                .authenticationProvider(authenticationProvider())

                // 2. Ajouter le filtre JWT avant le filtre de UsernamePasswordAuthentication
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 3. Configuration du fournisseur d'authentification (Authentification Provider)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // 4. Configuration du gestionnaire d'authentification (Authentication Manager)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 5. Configuration de l'encodeur de mot de passe (Utilisation de BCrypt pour une sécurité optimale)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
