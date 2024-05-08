package com.unal.cash.Model.PerfilesConsumo;

import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PerfilesConsumo {
    private static final String[] DIAS_SEMANA = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    private static final int DIAS_EN_SEMANA = 7;
    private static final int SEMANAS_EN_MES = 4;
    
    private static final DecimalFormat df = new DecimalFormat("#.00");
    double porcentajeAhorro;
    double porcentajeInversion;
    private double gastosRecurrentes;
    private int perfilConsumo;
    double ingresosMensuales;

    public PerfilesConsumo() {
    }
    
    public PerfilesConsumo(double ingresosMensuales, double gastosRecurrentes, double porcentajeAhorro, double porcentajeInversion, int perfilConsumo) {
       this.ingresosMensuales = ingresosMensuales;
       this.gastosRecurrentes = gastosRecurrentes;
       this.porcentajeAhorro = porcentajeAhorro;
       this.porcentajeInversion = porcentajeInversion;
       this.perfilConsumo = perfilConsumo;
   }
    
    public double[] porcentajesAhorroEInversion(){
        double getPorcentajeAhorro;
        double getPorcentajeInversion;
        Scanner sc = new Scanner(System.in);
      
        while(true){
            System.out.println("\nPor favor digite el porcentaje de ingresos destinado al ahorro: ");
            try{
            getPorcentajeAhorro = sc.nextDouble();
            if (getPorcentajeAhorro<0){
                throw new InputMismatchException();
            }
            break;
            }catch (InputMismatchException e){
                System.out.println("\nError: Debe ingresar un número válido. Vuelva a ingresarlo.");
                continue;
            }
        }
        
        while(true){
            System.out.println("\nPor favor digite el porcentaje de ingresos destinado a inversión: ");
            try{
            getPorcentajeInversion = sc.nextDouble();
            if (getPorcentajeInversion<0){
                throw new InputMismatchException();
            }
            break;
            }catch (InputMismatchException e){
                System.out.println("\nError: Debe ingresar un número válido. Vuelva a ingresarlo.");
                continue;
            }
        }
       
        double porcentajesAhrrInvrsn [] = {getPorcentajeAhorro/100, getPorcentajeInversion/100};
        
        return porcentajesAhrrInvrsn;
    }
   

    public Map<String, Double> calcularDistribucionSemanal() {
        Map<String, Double> distribucionSemanal = new HashMap<>();

        // Calcular el excedente mensual disponible para distribuir
        double excedenteMensual = ingresosMensuales - gastosRecurrentes;
        double ahorroMensual = excedenteMensual * porcentajeAhorro;
        double inversionMensual = excedenteMensual * porcentajeInversion;
        double presupuestoSemanal = ingresosMensuales - ahorroMensual - inversionMensual;

        // Imprimir ahorro e inversión mensual
       System.out.println("Ahorro mensual: $" + df.format(ahorroMensual));
       System.out.println("Inversion mensual: $" + df.format(inversionMensual));
       
        // Verificar que el presupuesto semanal no exceda el excedente mensual
        if (presupuestoSemanal < 0) {
            System.out.println("Error: El presupuesto semanal excede el excedente mensual disponible.");
            return distribucionSemanal;
        }

        // Calcular presupuesto diario a partir del presupuesto semanal
        double presupuestoDiario = presupuestoSemanal / (DIAS_EN_SEMANA * SEMANAS_EN_MES);

        // Configurar el perfil de consumo seleccionado
        switch (perfilConsumo) {
           case 1:
               distribuirConstante(presupuestoDiario, distribucionSemanal, perfilConsumo);
               break;
           case 2:
               distribuirFinDeSemanaGastador(presupuestoDiario, distribucionSemanal, perfilConsumo);
               break;
           case 3:
               distribuirEntreSemanaGastador(presupuestoDiario, distribucionSemanal, perfilConsumo);
               break;
           case 4:
               distribuirViernesGastador(presupuestoDiario, distribucionSemanal, perfilConsumo);
               break;
           default:
               throw new IllegalArgumentException("Perfil de consumo inválido");
       }
        return distribucionSemanal;
    }

    public double calcularPonderacionDia(String dia, int perfilConsumo) {
        double ponderacion = 0;
        switch (perfilConsumo) {
            case 1 -> ponderacion = 1.0;
            case 2 -> ponderacion = (dia.equals("Sábado") || dia.equals("Domingo")) ? 1.2 : 0.92;
            case 3 -> ponderacion = (dia.equals("Martes") || dia.equals("Miércoles")) ? 1.2 : 0.92;
            case 4 -> ponderacion = dia.equals("Viernes") ? 1.18 : 0.97;
            default -> throw new IllegalArgumentException("Perfil de consumo invalido");
        }

        return ponderacion;
    }
         
    public void distribuirConstante(double presupuestoDiarioBase, Map<String, Double> distribucionSemanal, int perfilConsumo) {
        for (String dia : DIAS_SEMANA) {
            double ponderacionDia = calcularPonderacionDia(dia, perfilConsumo);
            double presupuestoDiario = presupuestoDiarioBase * ponderacionDia;
            distribucionSemanal.put(dia, presupuestoDiario);
        }
    }

    public void distribuirFinDeSemanaGastador(double presupuestoDiarioBase, Map<String, Double> distribucionSemanal, int perfilConsumo) {
        double gastoEntreSemana = presupuestoDiarioBase;
        double gastoFinDeSemana = presupuestoDiarioBase;

        for (String dia : DIAS_SEMANA) {
           double ponderacionDia = calcularPonderacionDia(dia, perfilConsumo);
           if (dia.equals("Sabado") || dia.equals("Domingo")) {// si son alguno de estos  valores se asigna findesemana 
               double presupuestoDiario = gastoFinDeSemana * ponderacionDia;
               distribucionSemanal.put(dia, presupuestoDiario);
           } else {
               double presupuestoDiario = gastoEntreSemana  * ponderacionDia;// sino se asigna entre semana
               distribucionSemanal.put(dia, presupuestoDiario);
           }
       }
    }

    public void distribuirEntreSemanaGastador(double presupuestoDiarioBase, Map<String, Double> distribucionSemanal, int perfilConsumo) {
        double gastoEntreSemana = presupuestoDiarioBase;
        double gastoFinDeSemana = presupuestoDiarioBase;

        for (String dia : DIAS_SEMANA) {
           double ponderacionDia = calcularPonderacionDia(dia, perfilConsumo);
           if (dia.equals("Martes") || dia.equals("Miercoles")) { // en entre semana gastador se asigna solo martes y miercoles 
               double presupuestoDiario = gastoFinDeSemana * ponderacionDia;
               distribucionSemanal.put(dia, presupuestoDiario);
           } else {
               double presupuestoDiario = gastoEntreSemana * ponderacionDia;
               distribucionSemanal.put(dia, presupuestoDiario);
           }
       }
    }

    public void distribuirViernesGastador(double presupuestoDiarioBase, Map<String, Double> distribucionSemanal, int perfilConsumo) {
        double gastoViernes = presupuestoDiarioBase;
        double gastoDiario = presupuestoDiarioBase;

        for (String dia : DIAS_SEMANA) { //solo cambia el valor del viernes
           double ponderacionDia = calcularPonderacionDia(dia, perfilConsumo);
           if (dia.equals("Viernes")) {
               double presupuestoDiario = gastoViernes * ponderacionDia;
               distribucionSemanal.put(dia, presupuestoDiario);
           } else {
               double presupuestoDiario = gastoDiario * ponderacionDia;
               distribucionSemanal.put(dia, presupuestoDiario);
           }
       }
    }
}
