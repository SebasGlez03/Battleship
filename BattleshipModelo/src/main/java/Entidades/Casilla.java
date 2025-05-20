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
/**
 * Representa una casilla dentro del tablero del juego. Cada casilla tiene
 * coordenadas (x, y), un estado actual, y permite notificar a observadores
 * cuando su estado cambia.
 *
 * Implementa el patrón Observer para que otras clases puedan reaccionar a
 * cambios de estado.
 */
public class Casilla implements Subject {

    private int x;
    private int y;
    private EstadoCasilla estado;
    private List<Observer> observadores = new ArrayList<>();

    /**
     * Constructor que inicializa la casilla con coordenadas dadas y la
     * establece como disponible.
     *
     * @param x Coordenada horizontal de la casilla.
     * @param y Coordenada vertical de la casilla.
     */
    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = new EstadoCasillaDisponible();
    }

    /**
     * Obtiene la coordenada X de la casilla.
     *
     * @return Coordenada X.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada Y de la casilla.
     *
     * @return Coordenada Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Establece el nuevo estado de la casilla.
     *
     * @param estado Nuevo estado que se asignará a la casilla.
     */
    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el estado actual de la casilla.
     *
     * @return Estado actual de la casilla.
     */
    public EstadoCasilla getEstado() {
        return estado;
    }

    /**
     * Indica si la casilla está disponible.
     *
     * @return true si está disponible, false en caso contrario.
     */
    public boolean estaDisponible() {
        return estado.estaDisponible();
    }

    /**
     * Indica si la casilla ha sido impactada.
     *
     * @return true si fue impactada, false en caso contrario.
     */
    public boolean estaImpactada() {
        return estado.estaImpactada();
    }

    /**
     * Cambia el estado de la casilla según su lógica interna y notifica a los
     * observadores.
     */
    public void cambiarEstado() {
        estado.cambiarEstado(this);
        notificarObservadores();
    }

    /**
     * Reinicia el estado de la casilla a disponible y notifica a los
     * observadores del cambio.
     */
    public void reiniciarEstado() {
        this.estado = new EstadoCasillaDisponible();
        notificarObservadores();
    }

    /**
     * Convierte el estado de la casilla a una representación en texto.
     *
     * @return Una cadena que representa el estado.
     */
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

    /**
     * Indica si la casilla contiene una nave.
     *
     * @return true si está ocupada, false si no.
     */
    public boolean tieneNave() {
        return estado instanceof EstadoCasillaOcupada;
    }

    /**
     * Agrega un observador a la lista de observadores de esta casilla.
     *
     * @param o Observador que desea ser notificado de cambios.
     */
    // Métodos del patrón Observer
    @Override
    public void agregarObservador(Observer o) {
        observadores.add(o);
    }

    /**
     * Elimina un observador de la lista de observadores de esta casilla.
     *
     * @param o Observador a eliminar.
     */
    @Override
    public void eliminarObservador(Observer o) {
        observadores.remove(o);
    }

    /**
     * Notifica a todos los observadores registrados que hubo un cambio de
     * estado en esta casilla.
     */
    @Override
    public void notificarObservadores() {
        for (Observer o : observadores) {
            o.actualizar();
        }
    }
}
