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
/**
 * Implementación de EstadoNave que representa el estado destruido de la nave.
 * En este estado, la nave está deshabilitada y no puede participar activamente.
 */

public class EstadoNaveDestruida implements EstadoNave {
      /**
     * Cambia el estado de la nave a EstadoNaveActiva,
     * simulando que la nave ha sido reparada y está activa nuevamente.
     *
     * @param nave La nave cuyo estado se va a modificar.
     */
    @Override
    public void cambiarEstado(Nave nave) {
        nave.setEstado(new EstadoNaveActiva());  // Cambia el estado a Activo
    }
 /**
     * Muestra un mensaje indicando que la nave está deshabilitada.
     */
    @Override
    public void mostrarEstado() {
        System.out.println("La nave esta deshabilitada.");
    }
}
