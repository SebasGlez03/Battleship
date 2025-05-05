/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import java.io.IOException;

/**
 *
 * @author Carlo
 */
public class ControladorRed {
   private SocketServidor servidor;
    private SocketCliente cliente;
    private boolean esServidor;

    public ControladorRed(SocketServidor servidor) {
        this.servidor = servidor;
        this.esServidor = true;
    }

    public ControladorRed(SocketCliente cliente) {
        this.cliente = cliente;
        this.esServidor = false;
    }

    public void enviar(String mensaje) {
        if (esServidor) servidor.enviarMensaje(mensaje);
        else cliente.enviarMensaje(mensaje);
    }

    public String recibir() throws IOException {
        if (esServidor) return servidor.recibirMensaje();
        else return cliente.recibirMensaje();
    }
}