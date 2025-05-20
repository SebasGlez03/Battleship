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
 * Representa el estado de una casilla que ya fue impactada.
 *
 * En este estado la casilla ya no puede cambiar ni ser usada otra vez.
 */
public class EstadoCasillaImpactada implements EstadoCasilla {

    /**
     * No hace nada porque la casilla ya está impactada y no cambia de estado.
     *
     * @param casilla la casilla que se intentaría modificar.
     */
    @Override
    public void cambiarEstado(Casilla casilla) {
        // Ya está impactada, no cambia más
    }

    /**
     * Indica que la casilla no está disponible.
     *
     * @return false porque ya fue usada o atacada.
     */
    @Override
    public boolean estaDisponible() {
        return false;
    }

    /**
     * Indica que la casilla está impactada.
     *
     * @return true siempre, porque este estado representa una casilla que fue
     * impactada.
     */
    @Override
    public boolean estaImpactada() {
        return true;
    }
}
