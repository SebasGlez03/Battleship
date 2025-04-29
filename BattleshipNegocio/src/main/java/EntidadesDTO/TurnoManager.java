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
    
    private JugadorDTO turnoActual;

    public TurnoManager() {
        this.turnoActual = JugadorDTO.JUGADOR_1; // Inicia Jugador 1
    }

    public JugadorDTO getTurnoActual() {
        return turnoActual;
    }

    public void cambiarTurno() {
        if (turnoActual == JugadorDTO.JUGADOR_1) {
            turnoActual = JugadorDTO.JUGADOR_2;
        } else {
            turnoActual = JugadorDTO.JUGADOR_1;
        }
    }

    public boolean esTurno(JugadorDTO jugador) {
        return turnoActual == jugador;
    }
}
