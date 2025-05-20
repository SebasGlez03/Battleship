/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronObserver;

/**
 *
 * @author Carlo
 */
/**
 * Interfaz que representa al sujeto observado en el patrón Observer.
 *
 * Define los métodos para manejar y notificar a los observadores.
 */
public interface Subject {

    /**
     * Agrega un observador a la lista.
     *
     * @param o el observador que se quiere agregar.
     */
    void agregarObservador(Observer o);

    /**
     * Elimina un observador de la lista.
     *
     * @param o el observador que se quiere quitar.
     */
    void eliminarObservador(Observer o);

    /**
     * Notifica a todos los observadores que hubo un cambio.
     */
    void notificarObservadores();
}
