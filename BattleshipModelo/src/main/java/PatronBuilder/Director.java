/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronBuilder;

import Entidades.Nave;
import Entidades.TipoNave;

/**
 *
 * @author Carlo
 */
/**
 * Clase que se encarga de construir una nave usando el NaveBuilder.
 *
 * Básicamente aquí se indica cómo debe armarse la nave, dependiendo del tipo
 * que se le pase.
 */
public class Director {

    /**
     * Este método construye una nave según el tipo que se le mande.
     *
     * @param tipo el tipo de nave que se quiere construir.
     * @return una nave ya armada con el tipo indicado.
     */
    public Nave construirNave(TipoNave tipo) {
        return new NaveBuilder()
                .setTipoNave(tipo)
                .build();
    }
}
