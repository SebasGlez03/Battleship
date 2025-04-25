/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import Entidades.Casilla;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public abstract class NaveDTO {

    public String nombre;
    public int tamano;
    public Color color;
    protected List<CasillaDTO> posiciones = new ArrayList<>();
    protected boolean esHorizontal; // Para almacenar la orientación (horizontal o vertical)
    private boolean hundido;

    public abstract void construir();

    public void agregarCasilla(CasillaDTO c) {
        posiciones.add(c);
    }

    public List<CasillaDTO> getPosiciones() {
        return posiciones;
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

    // Método para obtener la coordenada X de la primera casilla (posición inicial)
    public int getXInicio() {
        return posiciones.isEmpty() ? -1 : posiciones.get(0).getX(); // Retorna la X de la primera casilla
    }

    // Método para obtener la coordenada Y de la primera casilla (posición inicial)
    public int getYInicio() {
        return posiciones.isEmpty() ? -1 : posiciones.get(0).getY(); // Retorna la Y de la primera casilla
    }

    public boolean isHundido() {
        return hundido;
    }

    // Método para marcar el barco como hundido
    public void setHundido(boolean hundido) {
        this.hundido = hundido;
    }

    // Método para verificar si el barco es horizontal
    public boolean isHorizontal() {
        if (posiciones.size() < 2) {
            return true; // Si solo hay una casilla, se asume que está horizontal
        }

        // Verifica si las casillas están en la misma fila (horizontal)
        int deltaX = Math.abs(posiciones.get(0).getX() - posiciones.get(1).getX());
        int deltaY = Math.abs(posiciones.get(0).getY() - posiciones.get(1).getY());

        esHorizontal = (deltaY == 0); // Si deltaY es 0, significa que están en la misma fila (horizontal)
        return esHorizontal;
    }
}
