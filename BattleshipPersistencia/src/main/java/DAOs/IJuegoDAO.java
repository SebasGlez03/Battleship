/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOs;

import Entidades.Disparo;
import Entidades.Jugador;
import Entidades.Tablero;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface IJuegoDAO {
    void guardarJuego(Jugador jugador1, Tablero tablero1, List<Disparo> disparos1,
                      Jugador jugador2, Tablero tablero2, List<Disparo> disparos2,
                      String archivo) throws IOException;

    // Sirev para devolver los objetos cargados como un DTO personalizado o en un Map
    Object[] cargarJuego(String archivo) throws IOException, ClassNotFoundException;
}
