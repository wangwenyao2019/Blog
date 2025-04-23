package com.example.blog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers
                        ("/api/auth/**").permitAll()
                .requestMatchers
                        (HttpMethod.GET, "/api/posts/**").permitAll()
                .requestMatchers
                        ("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                );
         return http.build();
    }
}