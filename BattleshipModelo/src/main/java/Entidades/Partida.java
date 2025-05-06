/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

//import PatronBuilder.State.*;


/**
 *
 * @author Carlo
 */
public class Partida {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
//    private EstadoPartida estado;
    private Jugador ganador;

    public Partida(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1;
//        this.estado = new EnCurso();
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Jugador getGanador() {
        return ganador;
    }

//    public void setEstado(EstadoPartida estado) {
//        this.estado = estado;
//    }

//    public void ejecutarTurno() {
//        estado.manejarTurno(this);
//    }

    public void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

//    public void finalizarPartida(JugadorDTO ganador) {
//        this.ganador = ganador;
//        setEstado(new Finalizada());
//    }
}

