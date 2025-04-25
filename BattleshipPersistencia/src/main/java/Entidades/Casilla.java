/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Carlo
 */
public class Casilla {
   private int fila;
    private int columna;
    private boolean impactada;

    public Casilla() {}

    public Casilla(int fila, int columna, boolean impactada) {
        this.fila = fila;
        this.columna = columna;
        this.impactada = impactada;
    }

    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }

    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }

    public boolean isImpactada() { return impactada; }
    public void setImpactada(boolean impactada) { this.impactada = impactada; }
}
