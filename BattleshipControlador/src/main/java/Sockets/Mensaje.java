/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import org.json.JSONObject;

/**
 *
 * @author Carlo
 */
public class Mensaje {
   private String tipo;
    private Object contenido;

    public Mensaje() {}

    public Mensaje(String tipo, Object contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }
    
    @Override
public String toString() {
    JSONObject json = new JSONObject();
    json.put("tipo", this.tipo);
    json.put("contenido", this.contenido);
    return json.toString();
}
    
}
