/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronState;

import Entidades.Nave;

/**
 *
 * @author Carlo
 */
public class EstadoNaveDestruida implements EstadoNave {
    @Override
    public void cambiarEstado(Nave nave) {
        nave.setEstado(new EstadoNaveActiva());  // Cambia el estado a Activo
    }

    @Override
    public void mostrarEstado() {
        System.out.println("La nave esta deshabilitada.");
    }
}
