/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Sockets.Mensaje;
import Entidades.Casilla;
import Entidades.Tablero;
import PatronState.EstadoCasillaOcupada;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Carlo
 */
public class SocketServidor {

    private ServerSocket serverSocket;
    private Socket socketJugador1, socketJugador2;
    private BufferedReader entradaJugador1, entradaJugador2;
    private PrintWriter salidaJugador1, salidaJugador2;

    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Esperando conexión de los jugadores...");

        aceptarConexiones();
        procesarTableros();
    }

    private void aceptarConexiones() throws IOException {
        socketJugador1 = serverSocket.accept();
        entradaJugador1 = new BufferedReader(new InputStreamReader(socketJugador1.getInputStream(), "UTF-8"));
        salidaJugador1 = new PrintWriter(new OutputStreamWriter(socketJugador1.getOutputStream(), "UTF-8"), true);
        System.out.println("Jugador 1 conectado.");

        socketJugador2 = serverSocket.accept();
        entradaJugador2 = new BufferedReader(new InputStreamReader(socketJugador2.getInputStream(), "UTF-8"));
        salidaJugador2 = new PrintWriter(new OutputStreamWriter(socketJugador2.getOutputStream(), "UTF-8"), true);
        System.out.println("Jugador 2 conectado.");

        // Leer nombres como JSON
        String mensaje1 = entradaJugador1.readLine();
        String mensaje2 = entradaJugador2.readLine();
        JSONObject json1 = new JSONObject(mensaje1);
        JSONObject json2 = new JSONObject(mensaje2);

        String nombreJugador1 = "";
        String nombreJugador2 = "";

        if (json1.has("tipo") && json1.getString("tipo").equals("nombre")) {
            nombreJugador1 = json1.getString("contenido");
        }

        if (json2.has("tipo") && json2.getString("tipo").equals("nombre")) {
            nombreJugador2 = json2.getString("contenido");
        }

        System.out.println("Jugador 1: " + nombreJugador1);
        System.out.println("Jugador 2: " + nombreJugador2);

        // Enviar el nombre del rival como JSON
        JSONObject respuesta1 = new JSONObject();
        respuesta1.put("tipo", "rival");
        respuesta1.put("contenido", nombreJugador2);
        salidaJugador1.println(respuesta1.toString());

        JSONObject respuesta2 = new JSONObject();
        respuesta2.put("tipo", "rival");
        respuesta2.put("contenido", nombreJugador1);
        salidaJugador2.println(respuesta2.toString());
    }

    private void enviarMensaje(PrintWriter salida, Mensaje mensaje) {
        salida.println(mensaje.toString());
        salida.flush();
    }

    private Mensaje recibirMensaje(BufferedReader entrada) throws IOException {
        String linea = entrada.readLine();
        if (linea != null) {
            JSONObject json = new JSONObject(linea);
            String tipo = json.optString("tipo", "mensaje");
            String contenido = json.optString("contenido", "");
            return new Mensaje(tipo, contenido);
        }
        return null;
    }

    private void procesarTableros() throws IOException {
        Mensaje mensaje1 = recibirMensaje(entradaJugador1);
        Mensaje mensaje2 = recibirMensaje(entradaJugador2);

        if (!"estado".equals(mensaje1.getTipo()) || !"READY".equals(mensaje1.getContenido())
                || !"estado".equals(mensaje2.getTipo()) || !"READY".equals(mensaje2.getContenido())) {
            System.out.println("Uno o ambos jugadores no están listos.");
            return;
        }
        System.out.println("Ambos jugadores están listos.");

        // Enviar permiso para que envíen sus tableros
        Mensaje permiso = new Mensaje("permiso", "ENVIAR_TABLERO");
        enviarMensaje(salidaJugador1, permiso);
        enviarMensaje(salidaJugador2, permiso);

        // Recibir tableros y coordenadas
        Mensaje tablero1 = recibirMensaje(entradaJugador1);  // tipo: "tablero"
        Mensaje coordenadas1 = recibirMensaje(entradaJugador1);  // tipo: "coordenadas"

        Mensaje tablero2 = recibirMensaje(entradaJugador2);
        Mensaje coordenadas2 = recibirMensaje(entradaJugador2);

        System.out.println("Tablero jugador 1:\n" + tablero1.getContenido());
        System.out.println("Coordenadas jugador 1: " + coordenadas1.getContenido());

        System.out.println("Tablero jugador 2:\n" + tablero2.getContenido());
        System.out.println("Coordenadas jugador 2: " + coordenadas2.getContenido());

        // Convertir las coordenadas recibidas de JSON a List<String>
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();

        // Asegúrate de que getContenido() esté devolviendo una cadena (String)
        String coordenadas1Str = (String) coordenadas1.getContenido();
        String coordenadas2Str = (String) coordenadas2.getContenido();

        List<String> coordenadasJugador1 = gson.fromJson(coordenadas1Str, listType);
        List<String> coordenadasJugador2 = gson.fromJson(coordenadas2Str, listType);

        // Si la deserialización es exitosa, las coordenadas se han procesado correctamente
        System.out.println("Coordenadas jugador 1: " + coordenadasJugador1);
        System.out.println("Coordenadas jugador 2: " + coordenadasJugador2);

        

        // Paso 4: Confirmación de recepción
        Mensaje confirmacion = new Mensaje("confirmacion", "CONFIRMACION_TABLERO_RECIBIDO");
        enviarMensaje(salidaJugador1, confirmacion);
        enviarMensaje(salidaJugador2, confirmacion);

        // Paso 5: Notificar que ambos tableros están listos
        Mensaje notificacion = new Mensaje("inicio", "TABLEROS_LISTOS");
        enviarMensaje(salidaJugador1, notificacion);
        enviarMensaje(salidaJugador2, notificacion);

        System.out.println("Ambos tableros recibidos y confirmados. Listo para iniciar el juego.");
        
        // Enviar las coordenadas del jugador 1 al jugador 2
        Mensaje mensajeCoordenadas1 = new Mensaje("coordenadas", coordenadas1Str);
        enviarMensaje(salidaJugador2, mensajeCoordenadas1);

        // Enviar las coordenadas del jugador 2 al jugador 1
        Mensaje mensajeCoordenadas2 = new Mensaje("coordenadas", coordenadas2Str);
        enviarMensaje(salidaJugador1, mensajeCoordenadas2);
    }

    public Casilla[][] convertirTextoACasillas(String texto) {
        Casilla[][] tablero = new Casilla[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = new Casilla(i, j);
            }
        }

        String[] partes = texto.trim().split(" ");
        for (int i = 0; i < partes.length; i += 2) {
            int x = Integer.parseInt(partes[i]);
            int y = Integer.parseInt(partes[i + 1]);
            tablero[x][y].setEstado(new EstadoCasillaOcupada());
        }

        return tablero;
    }

    public boolean validarTablero(Casilla[][] tablero) {
        int ocupadas = 0;
        for (Casilla[] fila : tablero) {
            for (Casilla c : fila) {
                if (c.tieneNave()) {
                    ocupadas++;
                }
            }
        }

        return ocupadas == 25;
    }

    public Tablero recibirTablero(BufferedReader entrada) throws IOException {
        Mensaje mensaje = recibirMensaje(entrada);
        if ("tablero".equals(mensaje.getTipo())) {
            Tablero tablero = new Tablero();
            tablero.convertirTextoATablero((String) mensaje.getContenido());
            return tablero;
        }
        return null;
    }

    public void enviarCoordenadas(Socket socket, List<String> coordenadas) throws IOException {
        // Convertir la lista de coordenadas a formato JSON
        Gson gson = new Gson();
        String jsonCoordenadas = gson.toJson(coordenadas);

        // Crear el mensaje
        Mensaje mensaje = new Mensaje("coordenadas", jsonCoordenadas);

        // Enviar el mensaje al cliente
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        salida.println(gson.toJson(mensaje));
    }

    public void cerrar() throws IOException {
        entradaJugador1.close();
        salidaJugador1.close();
        socketJugador1.close();

        entradaJugador2.close();
        salidaJugador2.close();
        socketJugador2.close();

        serverSocket.close();
    }
}
