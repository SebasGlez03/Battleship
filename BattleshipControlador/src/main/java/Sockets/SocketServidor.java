/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

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
public class SocketServidor {
   private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Esperando conexion del jugador...");
        socket = serverSocket.accept();
        System.out.println("Jugador conectado.");

        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);
    }

    public void enviar(String mensaje) {
        salida.println(mensaje);
    }

    public String recibir() throws IOException {
        return entrada.readLine();
    }

    public void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close();
        serverSocket.close();
    }
}