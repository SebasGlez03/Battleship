/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.List;

/**
 *
 * @author Carlo
 */
public class Nave {
    private String tipo; // "PortaAviones", "Crucero", etc.
    private int longitud;
    private List<Casilla> posiciones;

    public Nave() {}

    public Nave(String tipo, int longitud, List<Casilla> posiciones) {
        this.tipo = tipo;
        this.longitud = longitud;
        this.posiciones = posiciones;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getLongitud() { return longitud; }
    public void setLongitud(int longitud) { this.longitud = longitud; }

    public List<Casilla> getPosiciones() { return posiciones; }
    public void setPosiciones(List<Casilla> posiciones) { this.posiciones = posiciones; }
}
