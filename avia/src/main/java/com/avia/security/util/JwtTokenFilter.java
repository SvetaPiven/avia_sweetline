package com.avia.security.util;

import com.avia.security.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
//        String token = tokenProvider.extractToken(request);
//        if (token != null && !tokenProvider.validateToken(token)) {
//            Authentication auth = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        filterChain.doFilter(request, response);
        if (request.getServletPath().contains("/rest/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = tokenProvider.extractToken(request);
        if (token != null && !tokenProvider.isTokenExpired(token)) {
            Authentication auth = tokenProvider.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
//public class JwtTokenFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final TokenProvider tokenUtils;
//
//    private final UserDetailsService userDetailsService;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        /*1. Check if X-Auth-Token exists in header*/
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String authToken = httpRequest.getHeader(CustomHeaders.X_AUTH_TOKEN);
//
//        if (authToken != null) {
//
//            /*2. Validate and check username security details in token and in system*/
//            String username = tokenUtils.getUsernameFromToken(authToken);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                if (tokenUtils.validateToken(authToken, userDetails)) {
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}