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
public class EstadoCasillaImpactada implements EstadoCasilla {

    @Override
    public void cambiarEstado(Casilla casilla) {
        // Ya está impactada, no cambia más
    }

    @Override
    public boolean estaDisponible() {
        return false;
    }

    @Override
    public boolean estaImpactada() {
        return true;
    }
}