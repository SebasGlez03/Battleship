/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import PatronObserver.*;
import PatronState.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class Casilla implements Subject {

   private int x;
    private int y;
    private EstadoCasilla estado;
    private Nave nave;  // <-- nueva referencia a la nave que ocupa esta casilla
    private List<Observer> observadores = new ArrayList<>();

    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = new EstadoCasillaDisponible();
        this.nave = null;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
        this.estado = new EstadoCasillaOcupada();  // cambia el estado a ocupada cuando se asigna una nave
        notificarObservadores();
    }

    
    
    public Nave getNave() {
        return nave;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    public EstadoCasilla getEstado() {
        return estado;
    }

    public boolean estaDisponible() {
        return estado.estaDisponible();
    }

    public boolean estaImpactada() {
        return estado.estaImpactada();
    }
    
    public boolean estaAveriada() {
    return estaImpactada() && nave != null && !nave.isHundido();
}


    public void cambiarEstado() {
        estado.cambiarEstado(this);
        notificarObservadores();
    }

    public void reiniciarEstado() {
        this.estado = new EstadoCasillaDisponible();
        notificarObservadores();
    }

    public String convertirATexto() {
        if (estado instanceof EstadoCasillaDisponible) {
            return "Disponible";
        } else if (estado instanceof EstadoCasillaOcupada) {
            return "Ocupada";
        } else if (estado instanceof EstadoCasillaImpactada) {
            return "Impactada";
        }
        return "Desconocido";
    }

    public boolean tieneNave() {
        return estado instanceof EstadoCasillaOcupada;
    }

    // Métodos del patrón Observer
    @Override
    public void agregarObservador(Observer o) {
        observadores.add(o);
    }

    @Override
    public void eliminarObservador(Observer o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observer o : observadores) {
            o.actualizar();
        }
    }
}
