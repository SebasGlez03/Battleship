/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

/**
 *
 * @author Carlo
 */
public class Tablero {

    private Casilla[][] casillas = new Casilla[10][10];

    public Tablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
    }

    public Casilla getCasilla(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            return null;
        }
        return casillas[x][y];
    }

    // Método para atacar una casilla en el tablero
    public String atacarCasilla(String coordenada) {
        int fila = coordenada.charAt(0) - 'A';
        int columna = Integer.parseInt(coordenada.substring(1)) - 1;

        if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10) {
            return "Coordenada invalida";
        }

        Casilla casilla = casillas[fila][columna];

        if (casilla.estaImpactada()) {
            return "Ya atacaste esta casilla";
        }

        // Ejecuta el ataque (esto cambiará su estado si es Ocupada → Impactada)
        casilla.cambiarEstado();

        if (casilla.estaImpactada()) {
            return "Impacto";
        } else {
            return "Agua";
        }

    }
}
