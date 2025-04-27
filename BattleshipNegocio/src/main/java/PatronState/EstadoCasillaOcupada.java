/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronState;

import EntidadesDTO.CasillaDTO;

/**
 *
 * @author Carlo
 */
public class EstadoCasillaOcupada implements  EstadoCasilla {

    @Override
    public void cambiarEstado(CasillaDTO casilla) {
        // Si es atacada, pasa a impactada
        casilla.setEstado(new EstadoCasillaImpactada());
    }

    @Override
    public boolean estaDisponible() {
        return false;
    }

    @Override
    public boolean estaImpactada() {
        return false;
    }
}
