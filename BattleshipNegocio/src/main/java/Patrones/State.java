/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Patrones;

import EntidadesDTO.PartidaDTO;



/**
 *
 * @author Carlo
 */
public interface State {
    public interface EstadoPartida {
    void manejarTurno(PartidaDTO partida);
}

public class EnCurso implements EstadoPartida {
    public void manejarTurno(PartidaDTO partida) {
        System.out.println("Turno del jugador: " + partida.getJugadorActual().getNombre());
    }
}

public class Finalizada implements EstadoPartida {
    public void manejarTurno(PartidaDTO partida) {
        System.out.println("La partida ha finalizado. Ganador: " + partida.getGanador().getNombre());
    }
}
}
