/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

/**
 *
 * @author Carlo
 */
public class TableroDTO {

    private CasillaDTO[][] casillas = new CasillaDTO[10][10];

    public TableroDTO() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new CasillaDTO(i, j);
            }
        }
    }

    public CasillaDTO getCasilla(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            return null;
        }
        return casillas[x][y];
    }
}

    
