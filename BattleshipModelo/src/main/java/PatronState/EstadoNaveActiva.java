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
 * Implementación de EstadoNave que representa el estado activo de la nave. En
 * este estado, la nave está operativa y en juego.
 */
public class EstadoNaveActiva implements EstadoNave {

    /**
     * Cambia el estado de la nave a EstadoNaveHundida, simulando que la nave ha
     * sido hundida.
     *
     * @param nave La nave cuyo estado se va a modificar.
     */
    @Override
    public void cambiarEstado(Nave nave) {
        nave.setEstado(new EstadoNaveHundida());  // Cambia el estado a Hundido
    }

    /**
     * Muestra un mensaje indicando que la nave está activa y en juego.
     */
    @Override
    public void mostrarEstado() {
        System.out.println("La nave esta activa y en juego.");
    }
}
