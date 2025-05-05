/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import EntidadesDTO.Nave;
import EntidadesDTO.TipoNave;

/**
 *
 * @author Carlo
 */
public class Director {

    public Nave construirNave(TipoNave tipo) {
        return new NaveBuilder()
            .setTipoNave(tipo)
            .build();
    }
}
