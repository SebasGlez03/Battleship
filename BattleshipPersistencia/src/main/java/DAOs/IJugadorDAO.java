/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOs;

import Entidades.Jugador;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Carlo
 */
public interface IJugadorDAO {
    void guardarJugador(Jugador jugador, String id) throws SQLException ;
    Jugador cargarJugador(String id) throws SQLException ;
}
