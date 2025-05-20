/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronState;

import Entidades.Casilla;

/**
 *
 * @author Carlo
 */
/**
 * Interfaz que representa el estado de una casilla.
 *
 * Se usa para definir qué puede hacer una casilla dependiendo del estado en el
 * que este.
 */
public interface EstadoCasilla {

    /**
     * Cambia el estado actual de la casilla a otro, según la lógica que tenga
     * el estado.
     *
     * @param casilla la casilla a la que se le va a cambiar el estado.
     */
    void cambiarEstado(Casilla casilla);

    /**
     * Indica si la casilla está disponible.
     *
     * @return true si está disponible, false si no.
     */
    boolean estaDisponible();

    /**
     * Indica si la casilla ya fue impactada.
     *
     * @return true si fue impactada, false si no.
     */
    boolean estaImpactada();
}
