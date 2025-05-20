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
/**
 * Enum que representa los distintos tipos de naves disponibles en el juego,
 * cada una con un nombre, tamaño (número de casillas que ocupa) y un color
 * distintivo para su representación visual.
 */
public enum TipoNave {

    /**
     *
     */
    PORTAAVIONES("Porta Aviones", 4, Color.GRAY),

    /**
     *
     */
    CRUCERO("Crucero", 3, Color.BLUE),

    /**
     *
     */
    SUBMARINO("Submarino", 2, Color.GREEN),

    /**
     *
     */
    BARCO("Barco", 1, Color.YELLOW);

    private final String nombre;
    private final int tamano;
    private final Color color;

    /**
     * Constructor del enum TipoNave.
     *
     * @param nombre Nombre legible del tipo de nave.
     * @param tamano Número de casillas que ocupa la nave.
     * @param color Color usado para representar visualmente la nave.
     */
    TipoNave(String nombre, int tamano, Color color) {
        this.nombre = nombre;
        this.tamano = tamano;
        this.color = color;
    }

    /**
     * Obtiene el nombre del tipo de nave.
     *
     * @return Nombre legible del tipo de nave.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el tamaño de la nave, es decir, cuántas casillas ocupa.
     *
     * @return Número de casillas que ocupa.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Obtiene el color visual asociado al tipo de nave.
     *
     * @return Color de la nave.
     */
    public Color getColor() {
        return color;
    }
}
