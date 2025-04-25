/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOs;

import Entidades.Disparo;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Carlo
 */
public interface IDisparoDAO {
    void guardarDisparos(List<Disparo> disparos, String archivo) throws IOException;
    List<Disparo> cargarDisparos(String archivo) throws IOException, ClassNotFoundException;
}
