/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronState;

import EntidadesDTO.*;

/**
 *
 * @author Carlo
 */
public class EstadoNaveActiva implements EstadoNave {
    @Override
    public void cambiarEstado(NaveDTO nave) {
        nave.setEstado(new EstadoNaveHundida());  // Cambia el estado a Hundido
    }

    @Override
    public void mostrarEstado() {
        System.out.println("La nave está activa y en juego.");
    }
}