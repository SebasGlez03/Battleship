/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import EntidadesDTO.*;
import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class PortaAvionesBuilder implements NaveBuilder {
    @Override
    public NaveDTO construir() {
        return new PortaAvionesDTO();
    }
}
