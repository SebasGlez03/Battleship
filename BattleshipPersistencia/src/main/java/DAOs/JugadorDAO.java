/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionBD;
import Entidades.Jugador;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlo
 */
public class JugadorDAO implements IJugadorDAO {

    @Override
    public void guardarJugador(Jugador jugador, String id) throws SQLException {
        String sql = "INSERT INTO jugadores (id, nombre, color_hex) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, jugador.getNombre());
            stmt.setString(3, jugador.getColorHex());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JugadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Jugador cargarJugador(String id) throws SQLException {
        String sql = "SELECT nombre, color_hex FROM jugadores WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String color = rs.getString("color_hex");
                    Jugador jugador = new Jugador();
                    jugador.setNombre(nombre);
                    jugador.setColorHex(color);
                    return jugador;
                }
            }
        }
        return null;
    }

}
