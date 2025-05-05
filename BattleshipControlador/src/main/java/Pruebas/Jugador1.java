/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Sockets.SocketServidor;
import java.util.Scanner;

/**
 *
 * @author Carlo
 */
public class Jugador1 {

   public static void main(String[] args) throws Exception {
        SocketServidor servidor = new SocketServidor();
        servidor.iniciarServidor(12345);  // Espera en puerto 12345

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tu nombre: ");
        String miNombre = scanner.nextLine();
        servidor.enviar(miNombre);  // Envía su nombre

        String nombreRival = servidor.recibir();  // Recibe el nombre del otro jugador
        System.out.println("Tu rival es: " + nombreRival);
    }
}
