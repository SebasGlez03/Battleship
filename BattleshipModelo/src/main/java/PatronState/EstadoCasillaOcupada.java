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
 * Representa el estado de una casilla que ya está ocupada.
 * 
 * En este estado la casilla ya no está disponible, pero aún no ha sido impactada.
 */
public class EstadoCasillaOcupada implements  EstadoCasilla {
/**
     * Cambia el estado de la casilla a impactada.
     * Esto simula que fue atacada.
     * 
     * @param casilla la casilla que va a cambiar de estado.
     */
    @Override
    public void cambiarEstado(Casilla casilla) {
        // Si es atacada, pasa a impactada
        casilla.setEstado(new EstadoCasillaImpactada());
    }
 /**
     * Indica que la casilla no está disponible.
     * 
     * @return false siempre, porque ya está ocupada.
     */
    @Override
    public boolean estaDisponible() {
        return false;
    }
 /**
     * Indica si la casilla ya fue impactada.
     * 
     * @return false porque todavía no ha sido atacada, solo está ocupada.
     */
    @Override
    public boolean estaImpactada() {
        return false;
    }
}
