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
 * Interfaz que define el comportamiento para los estados de una nave. Cada
 * estado implementará cómo cambiar el estado de la nave y cómo mostrarlo.
 */
public interface EstadoNave {

    /**
     * Cambia el estado actual de la nave según la lógica definida en la
     * implementación.
     *
     * @param nave La nave cuyo estado se va a cambiar.
     */
    void cambiarEstado(Nave nave);

    /**
     * Muestra información o descripción del estado actual de la nave.
     */
    void mostrarEstado();
}
