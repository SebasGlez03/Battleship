/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Oley
 */
public class Disparo {
     private int x;
    private int y;

    public Disparo(int x, int y) {
        this.x = x;
        this.y = y;
    }
public boolean ejecutarDisparo(Casilla casilla) {
    if (casilla.getX() == x && casilla.getY() == y) {
        boolean tieneNave = casilla.tieneNave();
        casilla.cambiarEstado();
        return tieneNave;
    }
    return false;
}


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
