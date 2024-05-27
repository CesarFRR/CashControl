package com.unal.cash.Model.tranYmetpago;
/*
Esta clase permite que el usuario registre aquellas transacciones, las cuales son exclusivamente EGRESOS y además
que superan el presupuesto diario sugerido por la aplicación. 
*/
import JSON.JsonCRUD;
import Login.SesionUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transaccion {

    public Transaccion() {
    }
    
    //Método que registra la transacción y sube la respectiva información al JSON
    public void realizarTransaccion(Scanner scanner) {
        String usuario = SesionUsuario.getUsuarioLog();
        String fecha;
        double monto;
        int tipoTransaccion;
        String tipoTransaccionStr = "";
        double intereses;
        double cashback;
        double descuento;
        double costoTransaccion;
                
        CaracteristicasTransaccion infoTransaccion;
        infoTransaccion = new CaracteristicasTransaccion();
        
        System.out.println("**************************\nRegistro de Transacción (Egresos)\n**************************");
        System.out.println("\n¡El registro de una transacción es porque esta excedió el presupuesto diarío de ese día que la aplicación le recomienda!\n");

        while (true){
            
            //List<Double> montosFinalesTran = new ArrayList<>(); IGNORAR ESTA LINEA
            
            // Capturar la fecha en el formato día/mes/año
            System.out.print("Por favor ingrese la fecha de la transacción (dd/mm/yyyy): ");
            fecha = scanner.next();
            scanner.nextLine(); // Limpiar el buffer del scanner
            
            // Obtener características de la transacción
            double[] ctrTran = infoTransaccion.registroCaracteristicas(); // Llamada sin argumentos
            tipoTransaccion = (int) ctrTran[0];
            
            // Convertir tipo de transacción a texto
            switch (tipoTransaccion){
                case 1 -> tipoTransaccionStr = "Tarjeta débito";
                case 2 -> tipoTransaccionStr = "Tarjeta crédito";
                case 3 -> tipoTransaccionStr = "Efectivo";
                case 4 -> tipoTransaccionStr = "Transferencia por aplicación";
            }
            
            // Asignar valores a las variables
            monto = ctrTran[1];
            intereses = ctrTran[2];
            cashback = ctrTran[3];
            descuento = ctrTran[4];
            costoTransaccion = ctrTran[5];
           
            // Calcular monto final de la transacción
            double montoFinal = calcularMontoFinal(monto, intereses, cashback, descuento, costoTransaccion);
            
            //montosFinalesTran.add(montoFinal); IGNORAR ESTA LINEA

            // Verificar que la fecha no sea nula antes de agregarla a la lista
            if (fecha != null) {
                // Pasar los datos a JsonCRUD para guardarlos en el JSON
                List<String> data = List.of(usuario, fecha, tipoTransaccionStr, String.valueOf(monto),
                                            String.valueOf(intereses), String.valueOf(cashback),
                                            String.valueOf(descuento), String.valueOf(costoTransaccion));
                JsonCRUD.agregarEntradaAlJSON(data, montoFinal);
                System.out.println("\n\n¡Registro de información exitosa!\n\n");
            } else {
                System.out.println("Error: La fecha ingresada es nula. La transacción no se registrará.");
                continue; // Continuar con la siguiente iteración del bucle
            }
            
            // Consultar si desea registrar otra transacción
            System.out.print("¿Desea registrar otra transacción? (si/no): ");
            String respuesta = scanner.nextLine(); // Usar Scanner para leer entrada del usuario
            if (!respuesta.equalsIgnoreCase("si")) {
                break;
            }
        }
    }
    
    // Método que calcula el monto final de la transacción ya teniendo en cuenta todos los parámetros digitados por el usuario
    private double calcularMontoFinal(double monto, double intereses, double cashback, double descuento, double costoTransaccion) {
        return monto + intereses - cashback - descuento + costoTransaccion;
    }
}
