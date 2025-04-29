/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import EntidadesDTO.BarcoDTO;
import EntidadesDTO.NaveDTO;
import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class BarcoBuilder implements NaveBuilder {
    private BarcoDTO barco;

    public BarcoBuilder() {
        barco = new BarcoDTO();
    }

    public void construirNombre() {
        barco.nombre = "Submarino";
    }

    public void construirTamano() {
        barco.tamano = 1;
    }

    public void construirColor() {
        barco.color = Color.GREEN;
    }

    public NaveDTO getNave() {
        return barco;
    }
}
