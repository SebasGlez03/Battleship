/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package EntidadesDTO;

/**
 *
 * @author PC
 */
public enum TipoNave {
    
    BARCO(1),
    SUBMARINO(2),
    CRUCERO(3),
    PORTAAVIONES(4);
    
    private final int tamanio;
    
    TipoNave(int tamanio) {
        this.tamanio = tamanio;
    }

    public int getTamanio() {
        return tamanio;
    }
    
}
