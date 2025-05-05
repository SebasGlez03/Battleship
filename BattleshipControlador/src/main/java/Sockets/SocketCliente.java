/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Carlo
 */
public class SocketCliente {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public void conectar(String ip, int puerto) throws IOException {
        socket = new Socket(ip, puerto);
        System.out.println("Conectado al servidor.");
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
    }
}