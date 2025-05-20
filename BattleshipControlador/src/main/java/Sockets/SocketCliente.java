/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Entidades.Tablero;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Carlo
 */
/**
 * Clase que maneja la conexión cliente con un servidor mediante sockets.
 * Permite enviar y recibir mensajes en formato JSON usando la clase Mensaje.
 */
public class SocketCliente {

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nombreJugador;
    private String nombreOponente;
    private Gson gson = new Gson();
    private List<String> coordenadas;

    /**
     * Establece la conexión con el servidor usando host y puerto especificados.
     * Inicializa los flujos de entrada y salida para comunicación.
     *
     * @param host Dirección del servidor.
     * @param puerto Puerto del servidor.
     * @throws IOException Si ocurre un error al conectar o crear los flujos.
     */
    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
        salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"), true);
        System.out.println("Conectado al servidor.");
    }

    /**
     * Envía un mensaje al servidor, serializándolo a JSON.
     *
     * @param mensaje Mensaje a enviar.
     */
    public void enviarMensaje(Mensaje mensaje) {
        String json = gson.toJson(mensaje);
        salida.println(json);
    }

    /**
     * Recibe un mensaje desde el servidor y lo deserializa a objeto Mensaje.
     *
     * @return Mensaje recibido.
     * @throws IOException Si ocurre un error en la lectura.
     */
    public Mensaje recibirMensaje() throws IOException {
        String json = entrada.readLine();
        return gson.fromJson(json, Mensaje.class);
    }

    /**
     * Envía el nombre del jugador al servidor y lo guarda localmente.
     *
     * @param nombre Nombre del jugador.
     */
    public void enviarNombre(String nombre) {
        this.nombreJugador = nombre;
        enviarMensaje(new Mensaje("nombre", nombre));
    }

    /**
     * Envía un mensaje al servidor indicando que el cliente está listo.
     */
    public void confirmarConexion() {
        enviarMensaje(new Mensaje("estado", "READY"));
    }

    /**
     * Envía el tablero convertido a texto al servidor.
     *
     * @param tablero Tablero de juego a enviar.
     */
    public void enviarTablero(Tablero tablero) {
        String textoTablero = tablero.convertirTableroATexto();
        enviarMensaje(new Mensaje("tablero", textoTablero));
    }

    /**
     * Envía una lista de coordenadas al servidor, serializadas en JSON.
     *
     * @param coordenadas Lista de coordenadas a enviar.
     */
    public void enviarCoordenadas(List<String> coordenadas) {
        String jsonCoordenadas = gson.toJson(coordenadas);
        enviarMensaje(new Mensaje("coordenadas", jsonCoordenadas));
    }

    /**
     * Cierra la conexión y libera los recursos usados.
     *
     * @throws IOException Si ocurre un error al cerrar los flujos o el socket.
     */
    public void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close();
    }

    /**
     * Obtiene el nombre del oponente conectado.
     *
     * @return Nombre del oponente.
     */
    public String getNombreOponente() {
        return nombreOponente;
    }

    /**
     * Obtiene el nombre del jugador local.
     *
     * @return Nombre del jugador.
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

}
