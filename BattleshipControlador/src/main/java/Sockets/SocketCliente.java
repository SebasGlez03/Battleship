/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Entidades.Casilla;
import Entidades.Tablero;
import PatronState.EstadoCasillaOcupada;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.System.in;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Carlo
 */
public class SocketCliente {

  private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nombreJugador;
    private String nombreOponente;
    private Gson gson = new Gson();
    private List<String> coordenadas;

    // Método para conectar al servidor
    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
        salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"), true);
        System.out.println("Conectado al servidor.");
    }

    
    // Método para enviar un mensaje al servidor
    public void enviarMensaje(Mensaje mensaje) {
        String json = gson.toJson(mensaje);
        salida.println(json);
    }

    // Método para recibir un mensaje del servidor
    public Mensaje recibirMensaje() throws IOException {
        String json = entrada.readLine();
        return gson.fromJson(json, Mensaje.class);
    }

    // Método para enviar el nombre del jugador al servidor
    public void enviarNombre(String nombre) {
        this.nombreJugador = nombre;
        enviarMensaje(new Mensaje("nombre", nombre));
    }

    // Método para confirmar la conexión al servidor
    public void confirmarConexion() {
        enviarMensaje(new Mensaje("estado", "READY"));
    }

    // Método para enviar el tablero del jugador al servidor
    public void enviarTablero(Tablero tablero) {
        String textoTablero = tablero.convertirTableroATexto();
        enviarMensaje(new Mensaje("tablero", textoTablero));
    }

    // Método para enviar las coordenadas de ataque al servidor
    public void enviarCoordenadas(List<String> coordenadas) {
        String jsonCoordenadas = gson.toJson(coordenadas);
        enviarMensaje(new Mensaje("coordenadas", jsonCoordenadas));
    }

    // Método para cerrar la conexión
    public void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close();
    }

    // Getter por si necesitas usar el nombre del oponente en otra clase
    public String getNombreOponente() {
        return nombreOponente;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

}
