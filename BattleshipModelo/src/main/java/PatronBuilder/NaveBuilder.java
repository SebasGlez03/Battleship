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
public class NaveBuilder {
    private TipoNave tipoNave;

    public NaveBuilder setTipoNave(TipoNave tipoNave) {
        this.tipoNave = tipoNave;
        return this;
    }

    public Nave build() {
        return new Nave(tipoNave);
    }
}
