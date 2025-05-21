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

    private Socket socket;// Canal de comunicación con el servidor
    private BufferedReader entrada;// Flujo de entrada desde el servidor
    private PrintWriter salida; // Flujo de salida hacia el servidor
    private String nombreJugador;// Nombre del jugador local
    private String nombreOponente;// Nombre del oponente (será recibido del servidor)
    private Gson gson = new Gson();// Utilidad para manejar conversión JSON
    private List<String> coordenadas;// Coordenadas del tablero (ataques, movimientos, etc.)

    /**
     * Método para establecer la conexión con el servidor.
     *
     * Al llamarse, se abre un socket TCP/IP con el host y puerto especificado.
     * Se configuran los flujos de entrada y salida utilizando codificación
     * ISO-8859-1, lo cual garantiza compatibilidad con caracteres acentuados y
     * especiales en mensajes.
     *
     * @param host Dirección IP o nombre de dominio del servidor.
     * @param puerto Número de puerto al que el cliente se conectará.
     * @throws IOException En caso de que falle la conexión o la creación de los
     * flujos.
     */
    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
        salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"), true);
        System.out.println("Conectado al servidor.");
    }

    /**
     * Envío genérico de mensajes al servidor.
     *
     * Este método encapsula el proceso de conversión de un objeto `Mensaje` a
     * su representación en formato JSON, y luego lo envía al servidor por el
     * flujo de salida. Esto permite una comunicación estructurada y flexible
     * entre el cliente y el servidor.
     *
     * @param mensaje Objeto que contiene el tipo y contenido del mensaje a
     * enviar.
     */
    public void enviarMensaje(Mensaje mensaje) {
        String json = gson.toJson(mensaje);
        salida.println(json);
    }

    /**
     * Recepción de mensajes del servidor.
     *
     * Este método bloquea la ejecución hasta recibir una línea del flujo de
     * entrada. Una vez recibido, se interpreta como una cadena JSON que
     * representa un objeto `Mensaje`, el cual es deserializado y devuelto como
     * resultado.
     *
     * @return Objeto Mensaje recibido del servidor.
     * @throws IOException En caso de error de lectura o si se pierde la
     * conexión.
     */
    public Mensaje recibirMensaje() throws IOException {
        String json = entrada.readLine();
        return gson.fromJson(json, Mensaje.class);
    }

    /**
     * Enviar el nombre del jugador al servidor.
     *
     * Este método guarda internamente el nombre del jugador y luego envía un
     * mensaje al servidor con dicho nombre, lo que usualmente sirve como una
     * etapa de identificación o autenticación dentro del protocolo del juego.
     *
     * @param nombre Nombre del jugador que se está conectando.
     */
    public void enviarNombre(String nombre) {
        this.nombreJugador = nombre;
        enviarMensaje(new Mensaje("nombre", nombre));
    }

    /**
     * Confirmar que el cliente está listo para comenzar la partida.
     *
     * Esta función simplemente envía un mensaje con la palabra clave "READY",
     * lo cual puede ser interpretado por el servidor como una señal para
     * continuar con la lógica del juego (por ejemplo, empezar una ronda).
     */
    public void confirmarConexion() {
        enviarMensaje(new Mensaje("estado", "READY"));
    }

    /**
     * Envío del tablero del jugador al servidor.
     *
     * Este método toma un objeto `Tablero` y lo convierte en una cadena de
     * texto utilizando el método `convertirTableroATexto`, que probablemente
     * representa el estado del juego en una estructura legible (e.g., matriz de
     * posiciones). Dicha cadena se envía al servidor como parte del flujo de
     * juego.
     *
     * @param tablero Objeto que representa el tablero actual del jugador.
     */
    public void enviarTablero(Tablero tablero) {
        String textoTablero = tablero.convertirTableroATexto();
        enviarMensaje(new Mensaje("tablero", textoTablero));
    }

    /**
     * Enviar lista de coordenadas al servidor.
     *
     * Este método permite al jugador enviar múltiples coordenadas (por ejemplo,
     * ataques o movimientos) serializadas en una lista en formato JSON. Esta
     * lista se convierte a una cadena JSON usando Gson y luego se envía
     * encapsulada en un mensaje.
     *
     * @param coordenadas Lista de coordenadas como strings (e.g., "A1", "B2",
     * etc.).
     */
    public void enviarCoordenadas(List<String> coordenadas) {
        String jsonCoordenadas = gson.toJson(coordenadas);
        enviarMensaje(new Mensaje("coordenadas", jsonCoordenadas));
    }

    /**
     * Cierre de la conexión con el servidor.
     *
     * Este método libera todos los recursos abiertos durante la conexión,
     * incluyendo los flujos de entrada/salida y el socket. Es fundamental
     * llamarlo al final del ciclo de vida del cliente para evitar fugas de
     * recursos o errores de red.
     *
     * @throws IOException En caso de error al cerrar alguno de los flujos o el
     * socket.
     */
    public void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close();
    }

    /**
     * Obtener el nombre del oponente.
     *
     * Este campo podría ser recibido en algún punto del juego, cuando el
     * servidor notifica al cliente del nombre del otro jugador. En este punto
     * del código no se asigna, pero se deja preparado para un futuro uso.
     *
     * @return Nombre del oponente, si ya fue asignado.
     */
    public String getNombreOponente() {
        return nombreOponente;
    }

    /**
     * Obtener el nombre del jugador local.
     *
     * Este método permite acceder al nombre del jugador que fue previamente
     * enviado al servidor. Puede ser útil para mostrarlo en la interfaz gráfica
     * o para fines de registro.
     *
     * @return Nombre del jugador local.
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

}
