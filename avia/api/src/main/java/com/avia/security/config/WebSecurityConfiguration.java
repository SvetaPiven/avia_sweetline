package com.avia.security.config;

//import com.avia.security.jwt.TokenProvider;

import com.avia.security.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final UserDetailsService userDetailsService;

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/v3/api-docs/**", "/v2/api-docs", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui/**", "/swagger-ui.html#", "/webjars/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/airlines/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/airports/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/document-types/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/flight-statuses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/plane-types/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/ticket-class/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/ticket-statuses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/rest/flights/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/rest/**").hasRole("ADMIN")
                .requestMatchers("/rest/auth").permitAll()
                .requestMatchers("/rest/**").hasAnyRole("ADMIN", "USER", "MODERATOR")
                .anyRequest().authenticated();

        return http.build();
    }
}