package com.unal.cash.Model.Login;

// clase para poder usar el usuario logeado en todo el codigo 

import java.util.Objects;

public class SesionUsuario {
    private static String Susuario;

    public SesionUsuario() {
    }

    public static void asignacionUsuario(String usuarioLog) {
        SesionUsuario.Susuario = usuarioLog;
    }
    
    public static String getUsuarioLog(){
        return Susuario;
    }

    public static boolean session(){
        return Objects.nonNull(Susuario);
    }

    public static boolean Logout() {
        Susuario = null;
        return true;
    }
}
