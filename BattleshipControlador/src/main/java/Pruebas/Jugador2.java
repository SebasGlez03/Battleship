/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Sockets.SocketCliente;
import java.util.Scanner;

/**
 *
 * @author Carlo
 */
public class Jugador2 {
public static void main(String[] args) throws Exception {
        SocketCliente cliente = new SocketCliente();
        cliente.conectar("127.0.0.1", 12345);  // Conéctate a la IP y puerto del servidor

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tu nombre: ");
        String miNombre = scanner.nextLine();
        cliente.enviar(miNombre);  // Envía su nombre

        String nombreRival = cliente.recibir();  // Recibe el nombre del otro jugador
        System.out.println("Tu rival es: " + nombreRival);
    }
}
