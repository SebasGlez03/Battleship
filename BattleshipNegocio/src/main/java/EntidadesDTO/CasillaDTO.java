/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import PatronObserver.*;
import PatronState.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class CasillaDTO implements Subject {

    private int x;
    private int y;
    private EstadoCasilla estado;
    private List<Observer> observadores = new ArrayList<>();

    public CasillaDTO(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = new EstadoCasillaDisponible(); // Empieza como disponible
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

    public void cambiarEstado() {
        estado.cambiarEstado(this);
        notificarObservadores();
    }

    
    // Método para reiniciar el estado de la casilla
    public void reiniciarEstado() {
        this.estado = new EstadoCasillaDisponible(); // Restablece al estado inicial
        notificarObservadores(); // Notifica a los observadores del cambio
    }
    
    // Métodos de Observer Pattern

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


