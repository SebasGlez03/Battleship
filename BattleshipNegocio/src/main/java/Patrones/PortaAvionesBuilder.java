/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Patrones;

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

    @Override
    public void construirColor() {
        portaaviones.color = Color.GREEN;
    }

    @Override
    public NaveDTO getNave() {
        return portaaviones;
    }
}
