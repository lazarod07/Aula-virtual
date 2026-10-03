package com.app.aulavirtual.configuracion;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import com.app.aulavirtual.seguridad.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class Seguridad {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            CustomUserDetailService customUserDetailService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider = 
        new DaoAuthenticationProvider(customUserDetailService);

        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean 
    SecretKey secretKey(@Value("${jwt.secret}") String secret) {
        return new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    }

    @Bean
    JwtDecoder decoder(SecretKey secretKey) {
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            t -> t.requestMatchers("/auth/**", "/h2-console/**").permitAll()
            .anyRequest().authenticated())
        .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin())
        )
        .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> {}));

        return httpSecurity.build();
        
    }

}
