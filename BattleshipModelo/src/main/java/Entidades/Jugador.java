/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;



/**
 *
 * @author Carlo
 */
/**
 * Representa a un jugador dentro del juego.
 * Contiene el nombre del jugador y un estado que indica si fue confirmado o no para iniciar la partida.
 */
public class Jugador {
        private String nombre;
    private boolean confirmado;
 /**
     * Crea un nuevo jugador con el nombre proporcionado.
     * El jugador inicia sin estar confirmado.
     * 
     * @param nombre Nombre del jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.confirmado = false;
    }
 /**
     * Indica si el jugador ha sido confirmado para jugar.
     * 
     * @return true si está confirmado, false si no.
     */
    public boolean isConfirmado() {
        return confirmado;
    }
  /**
     * Establece el estado de confirmación del jugador.
     * 
     * @param confirmado true para marcar al jugador como confirmado, false en caso contrario.
     */
    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }
  /**
     * Devuelve el nombre del jugador.
     * 
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }
}
