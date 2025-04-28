/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import java.awt.Color;

/**
 *
 * @author Carlo
 */
public class SubmarinoDTO extends NaveDTO {
     public SubmarinoDTO() {
        construir();
    }

    @Override
    public void construir() {
        this.nombre = "Submarino";
        this.tamano = 2;
        this.color = Color.GREEN;
    }
}
