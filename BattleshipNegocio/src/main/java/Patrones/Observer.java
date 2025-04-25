/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Patrones;

/**
 *
 * @author Carlo
 */
public interface Observer {

    public void actualizar();

    public interface Observable {

        void agregarObservador(Observer o);

        void notificarObservadores();
    }
}
