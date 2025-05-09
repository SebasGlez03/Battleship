/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Oley
 */
public class TestDisparo {
       private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("🔗 Conectado al servidor.");
    }

    public void enviarDisparo(int x, int y) {
        if (socket != null && socket.isConnected()) {
            String mensaje = "DISPARO " + x + " " + y;
            salida.println(mensaje);
            salida.flush();
            System.out.println(" Disparo enviado a la casilla (" + x + ", " + y + ")");
        } else {
            System.out.println("️ No se puede enviar el disparo, el socket no está conectado.");
        }
    }

    public void recibirRespuesta() throws IOException {
        String respuesta = entrada.readLine();
        System.out.println("📩 Respuesta del servidor: " + respuesta);
    }

    public void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close();
        System.out.println("Conexión cerrada.");
    }

    public static void main(String[] args) {
 
 try {
            TestDisparo cliente = new TestDisparo();
            cliente.conectar("localhost", 12345); 
            cliente.enviarDisparo(1, 1); 
            cliente.recibirRespuesta(); 
            cliente.cerrar();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

//private static void inicializarTablero() {
//    // Coloca barcos en ciertas casillas
//    tablero[1][1] = 1;
//    tablero[2][2] = 1;
//    tablero[3][3] = 1;
//}
//
//private static boolean esAcierto(int x, int y) {
//    return tablero[x][y] == 1; // Si hay un barco (representado por 1)
//}
    
 
}
