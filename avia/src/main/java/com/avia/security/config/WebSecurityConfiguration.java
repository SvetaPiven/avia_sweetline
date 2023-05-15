package com.avia.security.config;

import com.avia.security.jwt.TokenProvider;

import com.avia.security.util.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final UserDetailsService userProvider;

    //   private final JwtTokenFilter jwtTokenFilter;

    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userProvider);
        provider.setPasswordEncoder(noOpPasswordEncoder());

        ProviderManager providerManager = new ProviderManager(provider);

        return providerManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //    .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/v1/**").hasRole("USER")
                .requestMatchers("").hasRole("USER")
                .anyRequest().authenticated();

        //   this.configureJwtFilter(http);
        //    http.addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    public HttpSecurity configureJwtFilter(HttpSecurity http) {
//        return http.addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}


//
////    @Bean
////    public AuthenticationTokenFilter authenticationTokenFilterBean(AuthenticationManager authenticationManager) throws Exception {
////        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter(tokenUtils, userDetailsService);
////        authenticationTokenFilter.setAuthenticationManager(authenticationManager);
////        return authenticationTokenFilter;
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}
//
//    //@Override
//    //protected void configure(HttpSecurity httpSecurity) throws Exception {
//    //@Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
////                .formLogin()
////                .loginPage("/login")
////                .defaultSuccessUrl("/")
////                .and()
//                .csrf()
//                .disable()
//                .exceptionHandling()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                /*For swagger access only*/
////                .antMatchers("/v2/api-docs", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**").permitAll()
////                .antMatchers("/actuator/**").permitAll()
////                .antMatchers(HttpMethod.GET, "/swagger-ui.html#").permitAll()
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .requestMatchers("/guest/**").permitAll()
//                .requestMatchers("/registration/**").permitAll()
//                .requestMatchers("/auth/**").permitAll()
//               // .antMatchers("/rest/**").hasAnyRole("ADMIN", "USER", "MODERATOR")
//                .requestMatchers("/rest/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated();
//                //.authenticationManager(new CustomAuthenticationManager());
//
//        // Custom JWT based authentication
////        httpSecurity
////                .addFilterBefore(authenticationTokenFilterBean(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
//        return httpSecurity.build();
//    }
//
//
//    //For swagger access only
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        web.ignoring()
////                .antMatchers("/v2/api-docs", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**");
////    }
//}