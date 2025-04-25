/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOs;

import Entidades.Tablero;
import java.io.IOException;

/**
 *
 * @author Carlo
 */
public interface ITableroDAO {
     void guardarTablero(Tablero tablero, String archivo) throws IOException;
    Tablero cargarTablero(String archivo) throws IOException, ClassNotFoundException;
}
