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
public class CruceroDTO extends NaveDTO {
     public CruceroDTO() {
        construir();
    }

    @Override
    public void construir() {
        this.tipoNave = TipoNave.CRUCERO;
        this.color = Color.GREEN;
    }
}
