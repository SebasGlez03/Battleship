/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;


import Patrones.Observer;
import Patrones.Observer.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
public class TableroDTO implements Observable {

    private CasillaDTO[][] casillas = new CasillaDTO[10][10];
    private List<Observer> observadores = new ArrayList<>();
    private List<NaveDTO> barcos = new ArrayList<>(); // Lista para almacenar los barcos

    public TableroDTO() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new CasillaDTO(i, j);
            }
        }
    }

    public CasillaDTO getCasilla(int x, int y) {
        return casillas[x][y];
    }

    public void agregarBarco(NaveDTO barco, int x, int y, boolean esHorizontal) {
        // Método para agregar un barco al tablero, marcando las casillas ocupadas
        int tamano = barco.getTamano();
        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = casillas[xi][yi];
            casilla.setOcupada(true);
        }
        barcos.add(barco);
    }

    // Verifica si todos los barcos están colocados correctamente en el tablero
    public boolean todosLosBarcosColocados() {
        for (NaveDTO barco : barcos) {
            int xInicio = barco.getXInicio();
            int yInicio = barco.getYInicio();
            int tamano = barco.getTamano();
            boolean esHorizontal = barco.isHorizontal();

            for (int i = 0; i < tamano; i++) {
                int xi = esHorizontal ? xInicio : xInicio + i;
                int yi = esHorizontal ? yInicio + i : yInicio;
                CasillaDTO casilla = casillas[xi][yi];
                if (!casilla.estaOcupada()) {
                    return false; // Si alguna casilla no está ocupada, el barco no está completamente colocado
                }
            }
        }
        return true; // Si todos los barcos están completamente colocados
    }

    // Métodos Observer
    @Override
    public void agregarObservador(Observer o) {
        observadores.add(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observer o : observadores) {
            o.actualizar();
        }
    }

    // Métodos adicionales
    public boolean todosLosBarcosHundidos() {
    for (CasillaDTO[] fila : casillas) {
        for (CasillaDTO casilla : fila) {
            // Si hay una casilla ocupada y no está atacada, significa que el barco no está hundido
            if (casilla.estaOcupada() && !casilla.estaAtacada()) {
                System.out.println("Barco no hundido en: " + casilla); // Ahora imprime las coordenadas
                return false;
            }
        }
    }
    return true; // Si todas las casillas ocupadas han sido atacadas, todos los barcos están hundidos
}
    
    // Método para reiniciar el tablero (limpiar todos los barcos)
    public void reiniciarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j].setOcupada(false);  // Limpiar las casillas ocupadas
                casillas[i][j].setAtacada(false);  // Limpiar el estado de ataque
                casillas[i][j].setDisponible(true); // Hacer la casilla disponible
            }
        }
    }

}

