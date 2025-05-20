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
 
public class Tablero {

    private Casilla[][] casillas = new Casilla[10][10];
    private String nombreJugador;

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

    public String convertirTableroATexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sb.append(casillas[i][j].convertirATexto());
                if (j < 9) sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

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

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

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