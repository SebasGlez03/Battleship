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
public class BarcoDTO extends NaveDTO {
     public BarcoDTO() {
        construir();
    }

    @Override
    public void construir() {
        this.nombre = "Barco";
        this.tamano = 1;
        this.color = Color.GREEN;
    }
}
