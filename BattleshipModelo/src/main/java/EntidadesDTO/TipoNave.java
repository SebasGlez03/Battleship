/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import java.awt.Color;

/**
 *
 * @author Carlo
 */
public enum TipoNave {
    PORTAAVIONES("Porta Aviones", 4, Color.GRAY),
    CRUCERO("Crucero", 3, Color.BLUE),
    SUBMARINO("Submarino", 2, Color.GREEN),
    BARCO("Barco", 1, Color.YELLOW);

    private final String nombre;
    private final int tamano;
    private final Color color;

    TipoNave(String nombre, int tamano, Color color) {
        this.nombre = nombre;
        this.tamano = tamano;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTamano() {
        return tamano;
    }

    public Color getColor() {
        return color;
    }
}
