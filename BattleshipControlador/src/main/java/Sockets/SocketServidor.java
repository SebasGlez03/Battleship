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
    private Socket clientSocket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Esperando conexion del jugador 2...");
        clientSocket = serverSocket.accept();
        System.out.println("Jugador 2 conectado.");

        entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        salida = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    public String recibirMensaje() throws IOException {
        return entrada.readLine();
    }

    public void cerrar() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}