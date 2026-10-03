package com.app.aulavirtual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
public class LoginRequest {

    public String usuario;

    public String contrasena;

}
