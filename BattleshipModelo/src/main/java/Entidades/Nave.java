/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import PatronObserver.*;
import PatronState.*;
import Sockets.Mensaje;
import Sockets.SocketCliente;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo
 */
/**
 * Representa una nave en el juego, con su tipo, orientación, estado , y las
 * casillas que ocupa en el tablero. Implementa el patrón Observer para
 * reaccionar a cambios en las casillas que ocupa.
 */
public class Nave implements Observer {

    private SocketCliente socket; // o SocketServidor si estás del lado servidor

    private TipoNave tipo;
    private List<Casilla> posiciones = new ArrayList<>();
    private boolean esHorizontal;
    private boolean hundido;
    private EstadoNave estado;

    /**
     * Crea una nave del tipo especificado.
     *
     * @param tipo Tipo de nave
     */
    public Nave(TipoNave tipo) {
        this.tipo = tipo;
        this.estado = new EstadoNaveActiva();
    }

<<<<<<< Updated upstream
    /**
     * Agrega una casilla a la lista de posiciones que ocupa esta nave. También
     * se registra como observador de la casilla.
     *
     * @param c Casilla a agregar.
     */
=======
    public void setSocket(SocketCliente socket) {
        this.socket = socket;
    }

>>>>>>> Stashed changes
    public void agregarCasilla(Casilla c) {
        posiciones.add(c);
        c.agregarObservador(this);
    }

    /**
     * Retorna la lista de casillas que ocupa esta nave.
     *
     * @return Lista de casillas.
     */
    public List<Casilla> getPosiciones() {
        return posiciones;
    }

    /**
     * Obtiene el nombre del tipo de nave.
     *
     * @return Nombre de la nave.
     */
    public String getNombre() {
        return tipo.getNombre();
    }

    /**
     * Retorna el tamaño de la nave, es decir, cuántas casillas ocupa.
     *
     * @return Tamaño de la nave.
     */
    public int getTamano() {
        return tipo.getTamano();
    }

    /**
     * Obtiene el color asignado a la nave.
     *
     * @return Color de la nave.
     */
    public Color getColor() {
        return tipo.getColor();
    }

    /**
     * Devuelve la coordenada X de la primera casilla de la nave.
     *
     * @return Coordenada X o -1 si no tiene posiciones asignadas.
     */
    public int getXInicio() {
        return posiciones.isEmpty() ? -1 : posiciones.get(0).getX();
    }

    /**
     * Devuelve la coordenada Y de la primera casilla de la nave.
     *
     * @return Coordenada Y o -1 si no tiene posiciones asignadas.
     */
    public int getYInicio() {
        return posiciones.isEmpty() ? -1 : posiciones.get(0).getY();
    }

    /**
     * Indica si la nave ya ha sido hundida.
     *
     * @return true si está hundida, false en caso contrario.
     */
    public boolean isHundido() {
        return hundido;
    }
    
    public boolean estaAveriada() {
    if (isHundido()) {
        return false;
    }
    for (Casilla c : posiciones) {
        if (c.estaImpactada()) {
            return true;  // Al menos una casilla impactada y no hundido
        }
    }
    return false;
}

    

    /**
     * Establece el estado de hundimiento de la nave.
     *
     * @param hundido true si ha sido hundida, false en caso contrario.
     */
    public void setHundido(boolean hundido) {
        this.hundido = hundido;
        this.estado = new EstadoNaveHundida();
    }

    /**
     * Determina si la nave está colocada horizontalmente en el tablero.
     *
     * @return true si está en posición horizontal, false si es vertical.
     */
    public boolean isHorizontal() {
        if (posiciones.size() < 2) {
            return true;
        }
        int deltaX = Math.abs(posiciones.get(0).getX() - posiciones.get(1).getX());
        int deltaY = Math.abs(posiciones.get(0).getY() - posiciones.get(1).getY());
        esHorizontal = (deltaY == 0);
        return esHorizontal;
    }

    /**
     * Cambia el estado actual de la nave utilizando su objeto estado actual.
     */
    public void cambiarEstado() {
        estado.cambiarEstado(this);
    }

    /**
     * Muestra el estado actual de la nave en consola.
     */
    public void mostrarEstado() {
        estado.mostrarEstado();
    }

    /**
     * Establece un nuevo estado para la nave (activa o hundida).
     *
     * @param estado Estado a asignar.
     */
    public void setEstado(EstadoNave estado) {
        this.estado = estado;
    }

    /**
     * Método del patrón Observer. Se llama cuando alguna de las casillas que
     * ocupa la nave cambia de estado. Si todas han sido impactadas, la nave se
     * marca como hundida.
     */
    // Método del Observer
    @Override
    public void actualizar() {
         boolean todasImpactadas = true;
    for (Casilla c : posiciones) {
        if (!c.estaImpactada()) {
            todasImpactadas = false;
            break;
        }
    }

    if (todasImpactadas && !hundido) {
        setHundido(true);
        String mensajeHundido = "¡La Nave " + getNombre() + " ha sido hundida!";

        // Mostrar mensaje local y enviar por red
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(null, mensajeHundido);

            if (socket != null) {
                Mensaje mensaje = new Mensaje("nave_hundida", mensajeHundido);
                socket.enviarMensaje(mensaje);
            }
        });
    }
    }

    /**
     * Devuelve el tipo de la nave.
     *
     * @return Objeto TipoNave asociado.
     */
    public TipoNave getTipo() {
        return tipo;
    }

}
