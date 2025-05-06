/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;



/**
 *
 * @author Carlo
 */
public class Jugador {
        private String nombre;
    private boolean confirmado;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.confirmado = false;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getNombre() {
        return nombre;
    }
}
