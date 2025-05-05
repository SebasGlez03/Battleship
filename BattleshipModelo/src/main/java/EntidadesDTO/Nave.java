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
public class Nave implements Observer {

    private TipoNave tipo;
    private List<Casilla> posiciones = new ArrayList<>();
    private boolean esHorizontal;
    private boolean hundido;
    private EstadoNave estado;

    public Nave(TipoNave tipo) {
        this.tipo = tipo;
        this.estado = new EstadoNaveActiva();
    }

    public void agregarCasilla(Casilla c) {
        posiciones.add(c);
        c.agregarObservador(this);
    }

    public List<Casilla> getPosiciones() {
        return posiciones;
    }

    public String getNombre() {
        return tipo.getNombre();
    }

    public int getTamano() {
        return tipo.getTamano();
    }

    public Color getColor() {
        return tipo.getColor();
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
        this.estado = new EstadoNaveHundida();
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

    // Método del Observer
    @Override
    public void actualizar() {
        boolean todasImpactadas = true;
        for (Casilla c : posiciones) {
            if (!c.estaImpactada()) {
                todasImpactadas = false;
                break;
            }
        }
        if (todasImpactadas && !hundido) {
            setHundido(true);
            System.out.println("¡El barco " + getNombre() + " ha sido hundido!");
        }
    }

    // Nuevo: obtener el tipo directamente
    public TipoNave getTipo() {
        return tipo;
    }
}
