/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;

/**
 *
 * @author Carlo
 */
public class TestCliente {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Conectado al servidor.");
    }

    public void enviarTablero(int[][] tablero) {
    StringBuilder mensaje = new StringBuilder("TABLERO ");

    // Convertir el tablero a una cadena de texto
    for (int i = 0; i < tablero.length; i++) {
        for (int j = 0; j < tablero[i].length; j++) {
            mensaje.append(tablero[i][j]).append(" ");
        }
    }

    // Enviar el mensaje con el tablero al servidor
    if (socket != null && socket.isConnected()) {
        out.println(mensaje.toString()); // Suponiendo que 'out' es el OutputStream
        out.flush();
        System.out.println("✅ Tablero enviado al servidor.");
    } else {
        System.out.println("⚠️ No se puede enviar el tablero, el socket no está conectado.");
    }
}

    public void confirmarConexion() throws IOException {
        salida.println("CONFIRMADO");
    }

    public void recibirRespuesta() throws IOException {
        String respuesta = entrada.readLine();
        System.out.println("Respuesta del servidor: " + respuesta);
    }

    public void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            TestCliente cliente = new TestCliente();
            cliente.conectar("127.0.0.1", 12345);

            // Simular el tablero del jugador (tablero 10x10)
            int[][] tablero = new int[10][10];
            tablero[1][1] = 1; // Coloca un barco en la casilla (1, 1)
            tablero[2][2] = 1; // Coloca un barco en la casilla (2, 2)
            tablero[3][3] = 1; // Coloca un barco en la casilla (3, 3)
            // (Puedes seguir agregando barcos de la misma forma)

            // Enviar el tablero al servidor
            cliente.enviarTablero(tablero);
            
            // Recibir la confirmación
            cliente.recibirRespuesta();

            cliente.cerrar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
