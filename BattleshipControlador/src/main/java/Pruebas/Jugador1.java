/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Sockets.SocketCliente;
import Sockets.SocketServidor;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Carlo
 */
public class Jugador1 {
//public static void main(String[] args) {
//        try {
//            SocketCliente cliente = new SocketCliente();
//
//            cliente.conectar("127.0.0.1", 12345);
//
//            // Enviar nombre del jugador
//            cliente.enviar("Jugador1");
//
//            // Crear tablero con 25 casillas ocupadas aleatoriamente
//            int[][] tablero = new int[10][10];
//            int count = 0;
//
//            // Usamos un conjunto para asegurarnos de que no haya posiciones duplicadas
//            Set<String> posicionesOcupadas = new HashSet<>();
//            Random rand = new Random();
//
//            while (count < 25) {
//                int fila = rand.nextInt(10);
//                int columna = rand.nextInt(10);
//
//                // Convertimos las coordenadas a una cadena para comprobar duplicados
//                String posicion = fila + "," + columna;
//
//                // Si la casilla no está ocupada, la marcamos
//                if (!posicionesOcupadas.contains(posicion)) {
//                    tablero[fila][columna] = 1;
//                    posicionesOcupadas.add(posicion);
//                    count++;
//                }
//            }
//
//            // Enviar el tablero al servidor
//            cliente.enviarTablero(tablero);
//
//            // Recibir nombre del oponente
//            String nombreOponente = cliente.recibir();
//            System.out.println("Tu oponente es: " + nombreOponente);
//
//            // Confirmar conexión
//            cliente.confirmarConexion();
//
//            // Esperar confirmación del servidor de que el tablero fue recibido
//            String confirmacion = cliente.recibir();
//            System.out.println("Servidor: " + confirmacion);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
