/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Carlo
 */
public class Disparo {
    private int fila;
    private int columna;
    private boolean fueImpacto;

    public Disparo() {}

    public Disparo(int fila, int columna, boolean fueImpacto) {
        this.fila = fila;
        this.columna = columna;
        this.fueImpacto = fueImpacto;
    }

    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }

    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }

    public boolean isFueImpacto() { return fueImpacto; }
    public void setFueImpacto(boolean fueImpacto) { this.fueImpacto = fueImpacto; }
}

