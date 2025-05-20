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
public interface EstadoCasilla {
    void cambiarEstado(Casilla casilla);
    boolean estaDisponible();
    boolean estaImpactada();
}

