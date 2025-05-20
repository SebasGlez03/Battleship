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
 * Clase que sirve para construir objetos de tipo Nave.
 *
 * Usa el patrón Builder para poder armar la nave paso a paso.
 */
public class NaveBuilder {

    private TipoNave tipoNave;

    /**
     * Establece el tipo de nave que se va a construir.
     *
     * @param tipoNave el tipo de nave que se quiere.
     * @return este mismo builder, para poder encadenar métodos si se quiere.
     */
    public NaveBuilder setTipoNave(TipoNave tipoNave) {
        this.tipoNave = tipoNave;
        return this;
    }

    /**
     * Crea y devuelve la nave con el tipo que se haya establecido antes.
     *
     * @return una nueva instancia de Nave con el tipo indicado.
     */
    public Nave build() {
        return new Nave(tipoNave);
    }
}
