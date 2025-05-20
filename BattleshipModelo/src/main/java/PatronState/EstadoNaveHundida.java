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
 * Implementación de EstadoNave que representa el estado hundido de la nave. En
 * este estado, la nave ya no puede cambiar a otro estado porque está hundida.
 */
public class EstadoNaveHundida implements EstadoNave {

    /**
     * Indica que la nave está hundida y no permite cambiar de estado.
     *
     * @param nave La nave cuyo estado se intenta cambiar (sin efecto).
     */
    @Override
    public void cambiarEstado(Nave nave) {
        System.out.println("La nave ya está hundida y no puede cambiar de estado.");
    }

    /**
     * Muestra un mensaje indicando que la nave ha sido hundida.
     */
    @Override
    public void mostrarEstado() {
        System.out.println("La nave ha sido hundida.");
    }
}
