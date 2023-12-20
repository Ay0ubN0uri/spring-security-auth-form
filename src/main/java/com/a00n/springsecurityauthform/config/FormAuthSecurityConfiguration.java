package com.a00n.springsecurityauthform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class FormAuthSecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (requests) -> requests
                        // .requestMatchers("/api/**").hasRole("ADMIN")
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true));
        http.httpBasic(Customizer.withDefaults());
        // http.csrf(t -> t.disable());
        // http.anonymous(t -> t.disable());
        http.headers(t -> t.frameOptions(f -> f.disable()));
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
