/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import EntidadesDTO.CruceroDTO;
import EntidadesDTO.NaveDTO;
import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class CruceroBuilder implements NaveBuilder {
    private CruceroDTO crucero;

    public CruceroBuilder() {
        crucero = new CruceroDTO();
    }

    public void construirNombre() {
        crucero.nombre = "Submarino";
    }

    public void construirTamano() {
        crucero.tamano = 3;
    }

    public void construirColor() {
        crucero.color = Color.GREEN;
    }

    public NaveDTO getNave() {
        return crucero;
    }
}
