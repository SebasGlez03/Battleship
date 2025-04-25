/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

/**
 *
 * @author Carlo
 */
public class CasillaDTO {
    private boolean ocupada = false;   // Si la casilla está ocupada por un barco
    private boolean atacada = false;   // Si la casilla ha sido atacada
    private boolean disponible = true; // Si la casilla está disponible para colocar un barco
    private int x, y;                  // Coordenadas de la casilla

    public CasillaDTO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Verifica si la casilla está disponible para colocar un barco
    public boolean estaDisponible() {
        return disponible && !ocupada;
    }
    
    public void setDisponible(boolean disponible) {
    this.disponible = disponible;
}


    // Verifica si la casilla está ocupada por un barco
    public boolean estaOcupada() {
        return ocupada;
    }

    // Establece si la casilla está ocupada por un barco
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
        this.disponible = !ocupada;  // Si la casilla está ocupada, ya no estará disponible para más barcos
    }

    // Verifica si la casilla ha sido atacada
    public boolean estaAtacada() {
        return atacada;
    }

    // Establece el estado de ataque de la casilla
    public void setAtacada(boolean atacada) {
        this.atacada = atacada;
    }

    // Retorna las coordenadas X de la casilla
    public int getX() {
        return x;
    }

    // Establece las coordenadas X de la casilla
    public void setX(int x) {
        this.x = x;
    }

    // Retorna las coordenadas Y de la casilla
    public int getY() {
        return y;
    }

    // Establece las coordenadas Y de la casilla
    public void setY(int y) {
        this.y = y;
    }

    // Retorna una representación en cadena de la casilla
    @Override
    public String toString() {
        return "Casilla(" + x + ", " + y + ") - Ocupada: " + ocupada + ", Atacada: " + atacada;
    }
}


