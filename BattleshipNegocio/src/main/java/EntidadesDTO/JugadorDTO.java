/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import Entidades.Tablero;

/**
 *
 * @author Carlo
 */
public class JugadorDTO {
     private String nombre;
    private String color;
    private TableroDTO tableroPrincipal;
    private TableroDTO tableroDisparos;

    public JugadorDTO(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.tableroPrincipal = new TableroDTO();
        this.tableroDisparos = new TableroDTO();
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public TableroDTO getTableroPrincipal() {
        return tableroPrincipal;
    }

    public TableroDTO getTableroDisparos() {
        return tableroDisparos;
    }
}
