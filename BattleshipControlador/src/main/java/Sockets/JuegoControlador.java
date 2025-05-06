/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Entidades.Tablero;
import java.io.IOException;

/**
 *
 * @author Carlo
 */
public class JuegoControlador {
//    private ControladorRed red;
//    private Tablero miTablero;
//    private Tablero tableroEnemigo;
//    private boolean esServidor;
//    
//    public JuegoControlador(boolean esServidor) throws IOException {
//        this.esServidor = esServidor;
//        if (esServidor) {
//            SocketServidor servidor = new SocketServidor();
//            servidor.iniciarServidor(1234);
//            red = new ControladorRed(servidor);
//        } else {
//            SocketCliente cliente = new SocketCliente();
//            cliente.conectar("localhost", 1234);
//            red = new ControladorRed(cliente);
//        }
//        
//        iniciarEscucha();
//    }
//
//    private void iniciarEscucha() {
//        new Thread(() -> {
//            try {
//                while (true) {
//                    String coordenada = red.recibir();
//                    // Aplica ataque en mi tablero
//                    String resultado = aplicarAtaque(coordenada); // "Agua", "Impacto", etc.
//                    red.enviar(resultado);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//    public void enviarAtaque(String coordenada) {
//        red.enviar(coordenada);
//        try {
//            String respuesta = red.recibir();
//            System.out.println("Respuesta recibida: " + respuesta);
//            // Actualiza la vista del tablero enemigo con el resultado
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String aplicarAtaque(String coordenada) {
//        // Aquí puedes integrar tu lógica para aplicar el ataque
//        // al tablero (por ejemplo, verificar si la coordenada está ocupada por un barco)
//        return "Agua";  // o "Impacto", "Hundido", etc.
//    }
}
