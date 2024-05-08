package com.unal.cash.Model.menusConsola;

import com.unal.cash.Model.Login.LoginIS;
import com.unal.cash.Model.Login.Registroinfo;
import java.util.Scanner;
import com.unal.cash.Model.crud.ModificarInfo;
import com.unal.cash.Model.crud.ConsultaInfo;

public class Menusbscs {
    private final Scanner scanner;
    
    public Menusbscs(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenuInicio() {
        System.out.println("\n**************************");
        System.out.println("*   Menú de inicio       *");
        System.out.println("**************************");
        System.out.println("Hola, bienvenido a Cash Control");
        System.out.println("Opciones:");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Crear cuenta");
        System.out.println("0. Salir");
    }

    public void mostrarMenuOperaciones() {
        Menusbscs menus = new Menusbscs(scanner);
        System.out.println("\n**************************");
        System.out.println("*   Menú de Operaciones  *");
        System.out.println("**************************");
        System.out.println("1. Consultar");
        System.out.println("2. Modificar");
        //System.out.println("0. Salir");
        boolean running = true;
        while (running){
        int opcionOperaciones = menus.obtenerOpcion(2);
        switch (opcionOperaciones) {
            case 1 -> {
                menus.menuConsultar();
                menus.volverAlMenuOperaciones(scanner);
            }
            case 2 -> {
                menus.menuModificar();
                menus.volverAlMenuOperaciones(scanner);
            }
            default -> {
                System.out.print("Por favor, ingrese una opción válida ");
                continue;
            }
            }
        
    }
    }

    public int obtenerOpcion(int maxOpcion) {
        int opcion;
        do {
            System.out.print("\nIngrese el número de la opción deseada (0-" + maxOpcion + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingrese una opcion válida: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
        } while (opcion < 0 || opcion > maxOpcion);
        return opcion;
    }

    public void menuIniciarSesion() {
        System.out.println("\n**************************");
        System.out.println("*   Menú Iniciar Sesión  *");
        System.out.println("**************************");
        System.out.println("Frame 2.1: Iniciar sesión");
        LoginIS login = new LoginIS();
        login.iniciarSesion();
    }

    public void menuCrearCuenta() {
        System.out.println("\n**************************");
        System.out.println("*   Menú Crear Cuenta    *");
        System.out.println("**************************");
        System.out.println("Frame 2.2: Crear Cuenta");

        // Llenar los datos de la cuenta
        Registroinfo registroInfo = new Registroinfo();
        registroInfo.IngresoInfoStr();
        registroInfo.ingresoinfoDouble();
        registroInfo.insertarInfoBD(registroInfo.getdatosStr(), registroInfo.getdatosDbl());

        // Después de crear la cuenta, dirigir al usuario al menú de inicio de sesión
        menuIniciarSesion();
    }

    public void menuConsultar() {
        System.out.println("\n**************************");
        System.out.println("*   Menú Consultar       *");
        System.out.println("**************************");
        System.out.println("Frame 4.1: Consultar");
        ConsultaInfo consultaInfo = new ConsultaInfo();
        consultaInfo.consultarInformacion();
    }

    public void menuModificar() {
        System.out.println("\n**************************");
        System.out.println("*   Menú Modificar       *");
        System.out.println("**************************");
        System.out.println("Frame 4.2: Modificar");
        ModificarInfo modificarInfo = new ModificarInfo();
        modificarInfo.modificarInfrmcn();
    }
    
    public void volverAlMenuOperaciones(Scanner scanner) {
        Menusbscs menus = new Menusbscs(scanner);
        System.out.println("\nPresione 0 para volver al menú de opciones: ");
        while (true) {
            String opcion = scanner.nextLine();
            if (opcion.equals("0")) {
                menus.mostrarMenuOperaciones();
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
}
////