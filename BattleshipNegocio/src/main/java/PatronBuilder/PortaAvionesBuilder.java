/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import EntidadesDTO.NaveDTO;
import EntidadesDTO.PortaAvionesDTO;
import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class PortaAvionesBuilder implements NaveBuilder {
    private PortaAvionesDTO portaaviones;

    public PortaAvionesBuilder() {
        portaaviones = new PortaAvionesDTO();
    }

    public void construirNombre() {
        portaaviones.nombre = "Submarino";
    }

    public void construirTamano() {
        portaaviones.tamano = 4;
    }

    public void construirColor() {
        portaaviones.color = Color.GREEN;
    }

    public NaveDTO getNave() {
        return portaaviones;
    }
}
