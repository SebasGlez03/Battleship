/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Sockets.SocketCliente;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Carlo
 */
public class JuegoRedCliente {
public static void main(String[] args) {
        SocketCliente cliente = new SocketCliente();
        try {
            cliente.conectar("localhost", 1234); // Usa IP real si estás en otra máquina
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Ingresa coordenada a atacar (o 'salir'): ");
                String coordenada = scanner.nextLine();
                cliente.enviarMensaje(coordenada);
                if (coordenada.equalsIgnoreCase("salir")) break;

                String respuesta = cliente.recibirMensaje();
                System.out.println("Servidor dice: " + respuesta);
            }

            cliente.cerrar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
