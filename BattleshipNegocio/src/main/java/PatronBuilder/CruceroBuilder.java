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

    @Override
    public void construirColor() {
        crucero.color = Color.GREEN;
    }

    @Override
    public NaveDTO getNave() {
        return crucero;
    }
}
