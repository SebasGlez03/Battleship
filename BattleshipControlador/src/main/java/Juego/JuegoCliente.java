/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import Entidades.Jugador;
import Entidades.Tablero;
import Sockets.SocketCliente;
import java.io.IOException;

/**
 *
 * @author Carlo
 */
public class JuegoCliente {
//
//   private SocketCliente socketCliente;
//    private Tablero tableroCliente;
//    private Jugador jugador;
//
//    public JuegoCliente(String ip, int puerto) throws IOException {
//        socketCliente = new SocketCliente();
//        socketCliente.conectar(ip, puerto);
//        tableroCliente = new Tablero();
//        jugador = new Jugador("Jugador");
//
//         Recibir el tablero del servidor
//        socketCliente.recibirTablero(tableroCliente);
//    }
//
//    public void iniciarJuego() throws IOException {
//         Confirmar que el tablero está listo
//        confirmarTablero();
//
//         Esperar la confirmación del otro jugador antes de comenzar
//        socketCliente.recibirConfirmacionDelServidor();
//
//         Luego, puedes comenzar a atacar
//        socketCliente.enviar("A1");  // Ejemplo de ataque
//        socketCliente.recibirResultadoAtaque();  // Recibir el resultado del ataque
//    }
//
//     El jugador confirma que su tablero está listo
//    private void confirmarTablero() throws IOException {
//        socketCliente.enviar("CONFIRMADO");  // Enviar confirmación al servidor
//    }
}
