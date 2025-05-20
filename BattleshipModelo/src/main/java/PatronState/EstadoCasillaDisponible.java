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
 * Representa el estado de una casilla que está disponible.
 *
 * Cuando está en este estado, la casilla aún no ha sido usada o impactada.
 */
public class EstadoCasillaDisponible implements EstadoCasilla {

    /**
     * Cambia el estado de la casilla a ocupada.
     *
     * @param casilla la casilla a modificar.
     */
    @Override
    public void cambiarEstado(Casilla casilla) {
        casilla.setEstado(new EstadoCasillaOcupada());
    }

    /**
     * Indica que la casilla está disponible.
     *
     * @return true siempre porque este estado representa una casilla libre.
     */
    @Override
    public boolean estaDisponible() {
        return true;
    }

    /**
     * Indica si la casilla está impactada.
     *
     * @return false siempre, porque una casilla disponible no ha sido
     * impactada.
     */
    @Override
    public boolean estaImpactada() {
        return false;
    }
}
