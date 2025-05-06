/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import Entidades.Jugador;
import Entidades.Tablero;
import Sockets.SocketServidor;
import java.io.IOException;

/**
 *
 * @author Carlo
 */
public class JuegoServidor {
//  private SocketServidor socketServidor;
//    private Tablero tableroServidor;
//    private Jugador jugador1;
//    private Jugador jugador2;
//
//    public JuegoServidor(int puerto) throws IOException {
//        socketServidor = new SocketServidor();
//        socketServidor.iniciarServidor(puerto);
//        tableroServidor = new Tablero();
//        jugador1 = new Jugador("Jugador 1");
//        jugador2 = new Jugador("Jugador 2");
//
//        // Enviar el tablero al cliente
//        socketServidor.enviarTablero(tableroServidor);
//    }
//
//    public void iniciarJuego() throws IOException {
//        while (true) {
//            // Procesa el ataque de cada cliente
//            socketServidor.procesarAtaque(tableroServidor);
//        }
//    }
//
//    // Método que recibe la confirmación de un jugador
//    public void recibirConfirmacion(Jugador jugador) throws IOException {
//        if (jugador == jugador1) {
//            jugador1.setConfirmado(true);
//            socketServidor.enviarMensaje("Jugador 1 ha confirmado su tablero", jugador2); // Notifica al Jugador 2
//        } else if (jugador == jugador2) {
//            jugador2.setConfirmado(true);
//            socketServidor.enviarMensaje("Jugador 2 ha confirmado su tablero", jugador1); // Notifica al Jugador 1
//        }
//
//        // Luego, envía la confirmación al otro jugador
//        if (jugador1.isConfirmado() && jugador2.isConfirmado()) {
//            // Ambos jugadores confirmaron, se inicia la partida
//            iniciarPartida();
//        }
//    }
//
//    // Método para enviar confirmación a otro jugador
//    public void enviarConfirmacion(Jugador jugador) throws IOException {
//        socketServidor.enviarMensaje("Confirmación enviada", jugador);  // Envía un mensaje de confirmación
//    }
//
//    // Método para iniciar la partida cuando ambos jugadores han confirmado
//    private void iniciarPartida() throws IOException {
//        // Aquí puedes iniciar el juego, cambiar a la fase de ataque o lo que necesites
//        socketServidor.enviarMensaje("Ambos jugadores han confirmado, la partida empieza ahora!", jugador1);
//        socketServidor.enviarMensaje("Ambos jugadores han confirmado, la partida empieza ahora!", jugador2);
//    }
}
