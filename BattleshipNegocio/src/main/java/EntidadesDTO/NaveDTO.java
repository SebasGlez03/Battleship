/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;


import PatronObserver.*;
import PatronState.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public abstract class NaveDTO implements Observer {

    public String nombre;
    public int tamano;
    public Color color;
    protected List<CasillaDTO> posiciones = new ArrayList<>();
    protected boolean esHorizontal;
    private boolean hundido;

    private EstadoNave estado;

    public NaveDTO() {
        estado = new EstadoNaveActiva();
    }

    public abstract void construir();

    public void agregarCasilla(CasillaDTO c) {
        posiciones.add(c);
        c.agregarObservador(this);
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

    public int getXInicio() {
        return posiciones.isEmpty() ? -1 : posiciones.get(0).getX();
    }

    public int getYInicio() {
        return posiciones.isEmpty() ? -1 : posiciones.get(0).getY();
    }

    public boolean isHundido() {
        return hundido;
    }

    public void setHundido(boolean hundido) {
        this.hundido = hundido;
        estado = new EstadoNaveHundida();
    }

    public boolean isHorizontal() {
        if (posiciones.size() < 2) {
            return true;
        }

        int deltaX = Math.abs(posiciones.get(0).getX() - posiciones.get(1).getX());
        int deltaY = Math.abs(posiciones.get(0).getY() - posiciones.get(1).getY());

        esHorizontal = (deltaY == 0);
        return esHorizontal;
    }

    public void cambiarEstado() {
        estado.cambiarEstado(this);
    }

    public void mostrarEstado() {
        estado.mostrarEstado();
    }

    public void setEstado(EstadoNave estado) {
        this.estado = estado;
    }

    // Método de Observer
    @Override
    public void actualizar() {
        boolean todasImpactadas = true;
        for (CasillaDTO c : posiciones) {
            if (!c.estaImpactada()) {
                todasImpactadas = false;
                break;
            }
        }

        if (todasImpactadas && !hundido) {
            setHundido(true);
            System.out.println("🚢 ¡El barco " + getNombre() + " ha sido hundido!");
        }
    }
}
