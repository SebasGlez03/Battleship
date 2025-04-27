/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import Entidades.Jugador;
import PatronBuilder.State.*;


/**
 *
 * @author Carlo
 */
public class PartidaDTO {
    private JugadorDTO jugador1;
    private JugadorDTO jugador2;
    private JugadorDTO jugadorActual;
    private EstadoPartida estado;
    private JugadorDTO ganador;

    public PartidaDTO(JugadorDTO jugador1, JugadorDTO jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1;
        this.estado = new EnCurso();
    }

    public JugadorDTO getJugador1() {
        return jugador1;
    }

    public JugadorDTO getJugador2() {
        return jugador2;
    }

    public JugadorDTO getJugadorActual() {
        return jugadorActual;
    }

    public JugadorDTO getGanador() {
        return ganador;
    }

    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    public void ejecutarTurno() {
        estado.manejarTurno(this);
    }

    public void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

    public void finalizarPartida(JugadorDTO ganador) {
        this.ganador = ganador;
        setEstado(new Finalizada());
    }
}

