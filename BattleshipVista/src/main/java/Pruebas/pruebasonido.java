/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

/**
 *
 * @author Carlo
 */
public class pruebasonido {
 public static void main(String[] args) {
        System.out.println("Reproduciendo sonido de impacto...");
        ReproductorSonido.reproducirSonido("/sounds/agua.wav");
        
        // Evita que el programa se cierre antes de que el sonido termine
        try {
            Thread.sleep(3000); // Espera 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Fin de la prueba.");
    }
}
