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
    private boolean ocupada = false;
    private boolean atacada = false;
    private int x, y;

    public CasillaDTO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean estaDisponible() {
        return !ocupada;
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public boolean estaAtacada() {
        return atacada;
    }
    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    

    public void setAtacada(boolean atacada) {
        this.atacada = atacada;
    }

    @Override
    public String toString() {
        return "Casilla(" + x + ", " + y + ")";
    }
}

