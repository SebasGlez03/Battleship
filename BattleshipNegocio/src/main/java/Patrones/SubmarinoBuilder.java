/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Patrones;

import EntidadesDTO.NaveDTO;
import EntidadesDTO.SubmarinoDTO;
import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class SubmarinoBuilder implements NaveBuilder {
    private SubmarinoDTO submarino;

    public SubmarinoBuilder() {
        submarino = new SubmarinoDTO();
    }

    public void construirNombre() {
        submarino.nombre = "Submarino";
    }

    public void construirTamano() {
        submarino.tamano = 2;
    }

    public void construirColor() {
        submarino.color = Color.GREEN;
    }

    public NaveDTO getNave() {
        return submarino;
    }
}

