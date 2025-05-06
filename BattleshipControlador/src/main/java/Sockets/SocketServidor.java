/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Entidades.Casilla;
import Entidades.Tablero;
import PatronState.EstadoCasillaOcupada;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Carlo
 */
public class SocketServidor {

    private ServerSocket serverSocket;
    private Socket socketJugador1, socketJugador2;
    private BufferedReader entradaJugador1, entradaJugador2;
    private PrintWriter salidaJugador1, salidaJugador2;

    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Esperando conexion de los jugadores...");

        // Aceptar conexiones y leer nombres
        aceptarConexiones();

        // Procesar los tableros y validaciones
        procesarTableros();
    }

    private void aceptarConexiones() throws IOException {
        // Aceptar conexión de jugadores
        socketJugador1 = serverSocket.accept();
        entradaJugador1 = new BufferedReader(new InputStreamReader(socketJugador1.getInputStream(), "ISO-8859-1"));
        salidaJugador1 = new PrintWriter(new OutputStreamWriter(socketJugador1.getOutputStream(), "ISO-8859-1"), true);
        System.out.println("Jugador 1 conectado.");

        socketJugador2 = serverSocket.accept();
        entradaJugador2 = new BufferedReader(new InputStreamReader(socketJugador2.getInputStream(), "ISO-8859-1"));
        salidaJugador2 = new PrintWriter(new OutputStreamWriter(socketJugador2.getOutputStream(), "ISO-8859-1"), true);
        System.out.println("Jugador 2 conectado.");

        // Leer nombres de los jugadores
        String nombreJugador1 = entradaJugador1.readLine();
        String nombreJugador2 = entradaJugador2.readLine();
        System.out.println("Jugador 1: " + nombreJugador1);
        System.out.println("Jugador 2: " + nombreJugador2);

        // Enviar el nombre del rival a cada jugador
        salidaJugador1.println(nombreJugador2);
        salidaJugador2.println(nombreJugador1);
    }

    private void procesarTableros() throws IOException {
        // 1. Esperar confirmación de que ambos jugadores están listos
        String estadoJugador1 = entradaJugador1.readLine();  // Espera "READY"
        String estadoJugador2 = entradaJugador2.readLine();  // Espera "READY"

        if (!"READY".equals(estadoJugador1) || !"READY".equals(estadoJugador2)) {
            System.out.println("Uno o ambos jugadores no están listos.");
            return;
        }

        System.out.println("Ambos jugadores están listos para enviar los tableros.");

        // 2. Notificar que ya pueden enviar tableros
        salidaJugador1.println("ENVIAR_TABLERO");
        salidaJugador2.println("ENVIAR_TABLERO");

        // 3. Recibir tableros
        String tablero1 = entradaJugador1.readLine();
        String tablero2 = entradaJugador2.readLine();

        System.out.println("Tablero Jugador 1: ");
        System.out.println("Tablero Jugador 2: ");

        salidaJugador1.println("TABLERO_RECIBIDO");
        salidaJugador2.println("TABLERO_RECIBIDO");

        // 4. Esperar confirmación final de cada jugador
        String confirmacion1 = entradaJugador1.readLine();
        String confirmacion2 = entradaJugador2.readLine();

        System.out.println("Confirmación Jugador 1: " + confirmacion1);
        System.out.println("Confirmación Jugador 2: " + confirmacion2);

// ✅ Cambiado: ahora acepta si empieza con "CONFIRMADO"
        if (!confirmacion1.startsWith("CONFIRMADO") || !confirmacion2.startsWith("CONFIRMADO")) {
            System.out.println("Uno o ambos jugadores no confirmaron sus tableros.");
            return;
        }

        // 5. Validar tableros (opcional)
        Casilla[][] tableroJugador1 = convertirTextoACasillas(tablero1);
        Casilla[][] tableroJugador2 = convertirTextoACasillas(tablero2);

        if (!validarTablero(tableroJugador1) || !validarTablero(tableroJugador2)) {
            System.out.println("Error al validar los tableros.");
            salidaJugador1.println("ERROR_VALIDACION");
            salidaJugador2.println("ERROR_VALIDACION");
            return;
        }

        System.out.println("Tableros validados correctamente.");

        // 6. Enviar tableros listos para empezar
        salidaJugador1.println("TABLEROS_LISTOS " + tablero2); // Jugador 1 recibe el tablero del 2
        salidaJugador2.println("TABLEROS_LISTOS " + tablero1); // Jugador 2 recibe el tablero del 1

        System.out.println("Ambos tableros enviados. El juego puede comenzar.");
    }

    // Convertir texto "READY x y x y ..." a un tablero 2D
    public Casilla[][] convertirTextoACasillas(String texto) {
        Casilla[][] tablero = new Casilla[10][10]; // Asumiendo tablero de 10x10

        // Inicializar todas las casillas con x, y
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = new Casilla(i, j); // Crear objetos Casilla
            }
        }

        String[] partes = texto.split(" ");

        // partes[0] es "READY"
        for (int i = 1; i < partes.length; i += 2) {
            int x = Integer.parseInt(partes[i]);
            int y = Integer.parseInt(partes[i + 1]);
            tablero[x][y].setEstado(new EstadoCasillaOcupada()); // Marca casilla como ocupada
        }

        return tablero;
    }

    // Validar que el tablero tenga exactamente 17 casillas ocupadas
    public boolean validarTablero(Casilla[][] tablero) {
        int casillasOcupadas = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j].tieneNave()) {
                    casillasOcupadas++;
                }
            }
        }
        return casillasOcupadas == 25; // Verifica que haya exactamente 25 casillas ocupadas
    }

    public void cerrar() throws IOException {
        entradaJugador1.close();
        salidaJugador1.close();
        socketJugador1.close();

        entradaJugador2.close();
        salidaJugador2.close();
        socketJugador2.close();

        serverSocket.close();
    }
}
