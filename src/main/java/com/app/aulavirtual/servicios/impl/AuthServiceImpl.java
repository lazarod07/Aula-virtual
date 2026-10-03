package com.app.aulavirtual.servicios.impl;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import com.app.aulavirtual.repositorios.UserRepository;
import com.app.aulavirtual.servicios.AuthService;

@Service 
public class AuthServiceImpl implements AuthService {

    public AuthenticationManager authenticationManager;
    public JwtEncoder encoder;
    public UserRepository usuRepository;
    public PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtEncoder encoder, UserRepository usuRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.usuRepository = usuRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String usuario, String contrasena) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(usuario, contrasena));

        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
        .issuer("jwt-aula")
        .issuedAt(now)
        .expiresAt(now.plusMillis(300000))
        .subject(authentication.getName()).build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        
        return encoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }



}
