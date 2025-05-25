package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.infrastructure.jpaRepository.JpaUserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JpaUserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println("🔍 Incoming request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("🔐 Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No valid Authorization header → skip auth");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = jwtService.extractAllClaims(token);
            String email = claims.getSubject();

            var userEntity = userRepo.findByEmail(email).orElse(null);
            if (userEntity != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = new User(
                        userEntity.getEmail(),
                        userEntity.getPasswordHash(),
                        Collections.emptyList()
                );

                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("✅ Authenticated user: " + email);
            }
        } catch (Exception e) {
            System.out.println("⚠️ JWT parse failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        boolean skip = path.startsWith("/auth") || path.startsWith("/swagger") || path.startsWith("/v3");
        if (skip) System.out.println("🛑 Skipping filter for: " + path);
        return skip;
    }
}
