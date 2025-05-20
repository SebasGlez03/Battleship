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
/**
 * Clase que representa un mensaje genérico con un tipo y un contenido. El
 * contenido puede ser cualquier objeto.
 */
public class Mensaje {

    private String tipo;
    private Object contenido;

    /**
     * Constructor vacío para crear un mensaje sin inicializar.
     */
    public Mensaje() {
    }

    /**
     * Constructor que inicializa el mensaje con un tipo y contenido
     * específicos.
     *
     * @param tipo Tipo o categoría del mensaje.
     * @param contenido Contenido del mensaje, puede ser cualquier objeto.
     */
    public Mensaje(String tipo, Object contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    /**
     * Obtiene el tipo del mensaje.
     *
     * @return Tipo del mensaje.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene el contenido del mensaje.
     *
     * @return Contenido del mensaje.
     */
    public Object getContenido() {
        return contenido;
    }

    /**
     * Establece el tipo del mensaje.
     *
     * @param tipo Nuevo tipo del mensaje.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece el contenido del mensaje.
     *
     * @param contenido Nuevo contenido del mensaje.
     */
    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    /**
     * Convierte el mensaje a su representación en formato JSON.
     *
     * @return Cadena JSON que representa el mensaje.
     */
    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("tipo", this.tipo);
        json.put("contenido", this.contenido);
        return json.toString();
    }

}
