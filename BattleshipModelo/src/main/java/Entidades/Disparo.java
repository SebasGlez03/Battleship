/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Oley
 */
/**
 * Representa un disparo realizado por un jugador hacia una posición específica
 * del tablero. Contiene la lógica para verificar si impactó una casilla y
 * cambiar su estado.
 */
public class Disparo {

    private int x;
    private int y;

    /**
     * Crea un nuevo disparo con las coordenadas especificadas.
     *
     * @param x Coordenada X del disparo.
     * @param y Coordenada Y del disparo.
     */
    public Disparo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Ejecuta el disparo sobre una casilla específica. Si las coordenadas del
     * disparo coinciden con las de la casilla, se cambia su estado y se
     * verifica si tenía una nave.
     *
     * @param casilla Casilla a verificar.
     * @return true si la casilla tenía una nave y fue impactada, false en caso
     * contrario.
     */

    public boolean ejecutarDisparo(Casilla casilla) {
        if (casilla.getX() == x && casilla.getY() == y) {
            boolean tieneNave = casilla.tieneNave();
            casilla.cambiarEstado();
            return tieneNave;
        }
        return false;
    }

    /**
     * Devuelve la coordenada X del disparo.
     *
     * @return Coordenada X.
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la coordenada Y del disparo.
     *
     * @return Coordenada Y.
     */
    public int getY() {
        return y;
    }
}
