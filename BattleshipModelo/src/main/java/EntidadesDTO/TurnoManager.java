/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

/**
 *
 * @author Carlo
 */
public class TurnoManager {
    
    private Jugador turnoActual;

    public TurnoManager() {
        this.turnoActual = Jugador.JUGADOR_1; // Inicia Jugador 1
    }

    public Jugador getTurnoActual() {
        return turnoActual;
    }

    public void cambiarTurno() {
        if (turnoActual == Jugador.JUGADOR_1) {
            turnoActual = Jugador.JUGADOR_2;
        } else {
            turnoActual = Jugador.JUGADOR_1;
        }
    }

    public boolean esTurno(Jugador jugador) {
        return turnoActual == jugador;
    }
}
