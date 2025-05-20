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
 * Interfaz que representa a un observador en el patrón Observer.
 *
 * Cualquier clase que implemente esta interfaz podrá ser notificada cuando
 * ocurra un cambio.
 */
public interface Observer {

    /**
     * Método que se llama cuando hay un cambio y se necesita notificar al
     * observador.
     */
    void actualizar();
    /*
    Prueba
    */
}
