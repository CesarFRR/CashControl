package com.unal.cash.Model.Login;

// clase para poder usar el usuario logeado en todo el codigo 

public class SesionUsuario {
    private static String Susuario;

    private static boolean session;

    public SesionUsuario() {
        session = false;
    }

    public static void asignacionUsuario(String usuarioLog) {
        SesionUsuario.Susuario = usuarioLog;
        session=true;
    }
    
    public static String getUsuarioLog(){
        return Susuario;
    }

    public static boolean Logout(){
        Susuario = null;
        return true;
    }

    public static boolean session(){
        return (Susuario!=null && session);
    }
}
