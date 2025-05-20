/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import PatronState.EstadoCasillaDisponible;
import PatronState.EstadoCasillaImpactada;
import PatronState.EstadoCasillaOcupada;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
/**
 * Clase que representa el tablero de juego para un jugador en Batalla Naval. El
 * tablero está compuesto por una matriz de casillas de 10x10, y permite
 * realizar acciones como ataques, conversión a texto, y recuperación de
 * posiciones de naves.
 */
public class Tablero {

    private Casilla[][] casillas = new Casilla[10][10];
    private String nombreJugador;

    /**
     * Constructor por defecto. Inicializa todas las casillas del tablero como
     * disponibles.
     */
    public Tablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
    }

    /**
     * Devuelve la casilla en las coordenadas indicadas.
     *
     * @param x Fila .
     * @param y Columna .
     * @return La casilla correspondiente o null si la coordenada está fuera de
     * rango.
     */
    public Casilla getCasilla(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            return null;
        }
        return casillas[x][y];
    }

    /**
     *
     * @param coordenada Cadena con la coordenada.
     * @return Mensaje con el resultado del ataque: "Impacto", "Agua", "Ya
     * atacaste esta casilla" o "Coordenada invalida".
     */
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

        casilla.cambiarEstado();
        return casilla.estaImpactada() ? "Impacto" : "Agua";
    }

    /**
     * Convierte el tablero actual en una representación de texto. Cada casilla
     * es representada por su estado.
     *
     * @return Representación en texto del tablero, línea por línea.
     */
    public String convertirTableroATexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sb.append(casillas[i][j].convertirATexto());
                if (j < 9) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Reconstruye el estado del tablero a partir de un texto previamente
     * generado.
     *
     * @param texto Texto con los estados de las casillas separados por comas y
     * líneas.
     */
    public void convertirTextoATablero(String texto) {
        String[] filas = texto.split("\n");
        for (int i = 0; i < 10; i++) {
            String[] columnas = filas[i].split(",");
            for (int j = 0; j < 10; j++) {
                String estadoTexto = columnas[j];
                if (estadoTexto.equals("Disponible")) {
                    casillas[i][j].setEstado(new EstadoCasillaDisponible());
                } else if (estadoTexto.equals("Ocupada")) {
                    casillas[i][j].setEstado(new EstadoCasillaOcupada());
                } else if (estadoTexto.equals("Impactada")) {
                    casillas[i][j].setEstado(new EstadoCasillaImpactada());
                }
            }
        }
    }

    /**
     * Establece el nombre del jugador dueño de este tablero.
     *
     * @param nombreJugador Nombre del jugador.
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    /**
     * Obtiene el nombre del jugador dueño de este tablero.
     *
     * @return Nombre del jugador.
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * Devuelve la matriz completa de casillas del tablero.
     *
     * @return Matriz de casillas.
     */
    public Casilla[][] getCasillas() {
        return casillas;
    }

    /**
     * Devuelve una lista con las coordenadas donde hay naves colocadas.
     *
     * @return Lista de coordenadas con naves.
     */
    // Devuelve una lista de coordenadas donde hay naves colocadas
    public List<String> obtenerCoordenadasConNaves() {
        List<String> coordenadas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (casillas[i][j].tieneNave()) {
                    String coord = (char) ('A' + i) + String.valueOf(j + 1);
                    coordenadas.add(coord);
                }
            }
        }
        return coordenadas;
    }

}
