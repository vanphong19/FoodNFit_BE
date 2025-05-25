package com.vanphong.foodnfitbe.config;

import com.vanphong.foodnfitbe.application.service.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 👉 Cho phép gọi tất cả các API không cần xác thực
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**","/api/**"
//                        ).permitAll()
//
//                        .requestMatchers("/api/**")
//                        .access((authentication, context) -> {
//                            var authenticationObj = authentication.get();
//
//                            // Nếu đã login thì kiểm tra quyền
//                            if (authenticationObj != null && authenticationObj.isAuthenticated()) {
//                                boolean hasRole = context.getRequest().isUserInRole("USER") || context.getRequest().isUserInRole("ADMIN");
//                                return new AuthorizationDecision(hasRole);
//                            }
//
//                            // Nếu chưa login → vẫn cho phép test
//                            return new AuthorizationDecision(true);
//                        })
//
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
}
