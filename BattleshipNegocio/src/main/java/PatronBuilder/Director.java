/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import EntidadesDTO.NaveDTO;

/**
 *
 * @author Carlo
 */
public class Director {
     private NaveBuilder builder;

    public Director(NaveBuilder builder) {
        this.builder = builder;
    }

    public NaveDTO construir() {
        return builder.construir();
    }
}
