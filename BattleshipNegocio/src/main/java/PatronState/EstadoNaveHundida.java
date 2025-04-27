/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronState;

import EntidadesDTO.NaveDTO;

/**
 *
 * @author Carlo
 */
public class EstadoNaveHundida implements EstadoNave {
    @Override
    public void cambiarEstado(NaveDTO nave) {
        System.out.println("La nave ya está hundida y no puede cambiar de estado.");
    }

    @Override
    public void mostrarEstado() {
        System.out.println("La nave ha sido hundida.");
    }
}
