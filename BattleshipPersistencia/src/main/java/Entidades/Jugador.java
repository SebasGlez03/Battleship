/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class Jugador {
private String nombre;
    private String colorHex;

    public Jugador() {}

    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.colorHex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public Color getColor() {
        return Color.decode(colorHex);
    }
}
