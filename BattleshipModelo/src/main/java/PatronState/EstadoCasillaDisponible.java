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
public class EstadoCasillaDisponible implements EstadoCasilla {

    @Override
    public void cambiarEstado(Casilla casilla) {
        casilla.setEstado(new EstadoCasillaOcupada());
    }

    @Override
    public boolean estaDisponible() {
        return true;
    }

    @Override
    public boolean estaImpactada() {
        return false;
    }
}
