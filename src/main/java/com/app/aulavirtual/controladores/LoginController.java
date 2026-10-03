package com.app.aulavirtual.controladores;

import org.springframework.web.bind.annotation.RestController;

import com.app.aulavirtual.dto.LoginRequest;
import com.app.aulavirtual.servicios.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/login")
@RestController
public class LoginController {

    public AuthService authService;

    public LoginController(AuthService authService){
        this.authService = authService;
    }
    
    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest){

        String token = authService
        .login(loginRequest.getUsuario(), loginRequest.getContrasena());

        return token;
    }

}
