/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Carlo
 */
public class TestServidor {
   private ServerSocket serverSocket;
    private Socket socketJugador1, socketJugador2;
    private BufferedReader entradaJugador1, entradaJugador2;
    private PrintWriter salidaJugador1, salidaJugador2;

    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Esperando conexión de los jugadores...");

        // Aceptar conexión de jugadores
        socketJugador1 = serverSocket.accept();
        entradaJugador1 = new BufferedReader(new InputStreamReader(socketJugador1.getInputStream()));
        salidaJugador1 = new PrintWriter(socketJugador1.getOutputStream(), true);
        System.out.println("Jugador 1 conectado.");

        socketJugador2 = serverSocket.accept();
        entradaJugador2 = new BufferedReader(new InputStreamReader(socketJugador2.getInputStream()));
        salidaJugador2 = new PrintWriter(socketJugador2.getOutputStream(), true);
        System.out.println("Jugador 2 conectado.");

        // Leer confirmación de ambos jugadores
        entradaJugador1.readLine(); // Ignorar confirmación de jugador 1
        entradaJugador2.readLine(); // Ignorar confirmación de jugador 2

        // Recibir los tableros de los jugadores
        String tablero1 = entradaJugador1.readLine();
        String tablero2 = entradaJugador2.readLine();

        System.out.println("Tablero recibido de Jugador 1: " + tablero1);
        System.out.println("Tablero recibido de Jugador 2: " + tablero2);

        // Confirmar la recepción de los tableros
        salidaJugador1.println("CONFIRMACION_TABLERO_RECIBIDO");
        salidaJugador2.println("CONFIRMACION_TABLERO_RECIBIDO");

        // Validar y procesar los tableros
        if (validarTablero(tablero1) && validarTablero(tablero2)) {
            System.out.println("✔️ Tableros validados correctamente.");
        } else {
            System.out.println("❌ Error en la validación de los tableros.");
        }

        // Cerrar conexiones
        entradaJugador1.close();
        salidaJugador1.close();
        socketJugador1.close();

        entradaJugador2.close();
        salidaJugador2.close();
        socketJugador2.close();

        serverSocket.close();
    }

    private boolean validarTablero(String tablero) {
        // Simple validación: verificar que el tablero comience con "READY" y contenga al menos 17 casillas
        if (!tablero.startsWith("READY")) {
            return false;
        }
        String[] partes = tablero.split(" ");
        return partes.length > 1 && partes.length % 2 == 1; // Al menos una coordenada (x, y)
    }

    public static void main(String[] args) {
        try {
            new TestServidor().iniciarServidor(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
