/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.List;

/**
 *
 * @author Carlo
 */
public class Tablero {
    private List<Nave> naves;

    public Tablero() {}

    public Tablero(List<Nave> naves) {
        this.naves = naves;
    }

    public List<Nave> getNaves() { return naves; }
    public void setNaves(List<Nave> naves) { this.naves = naves; }
}
