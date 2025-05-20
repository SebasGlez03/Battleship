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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author Carlo
 */
/**
 * Clase que implementa un servidor para un juego entre dos jugadores,
 * gestionando conexiones, intercambio de mensajes y la lógica de turnos.
 */
public class SocketServidor {

    private ServerSocket serverSocket;
    private Socket socketJugador1, socketJugador2;
    private BufferedReader entradaJugador1, entradaJugador2;
    private PrintWriter salidaJugador1, salidaJugador2;

    private List<String> coordenadasJugador1;
    private List<String> coordenadasJugador2;

    /**
     * Inicia el servidor en el puerto especificado y espera la conexión de dos
     * jugadores.
     *
     * @param puerto Puerto donde se escucharán las conexiones entrantes.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Esperando conexion de los jugadores...");

        aceptarConexiones();
        procesarTableros();
    }

    /**
     * Acepta las conexiones de los dos jugadores y recibe sus nombres.
     *
     * @throws IOException si ocurre un error en la conexión o lectura.
     */
    private void aceptarConexiones() throws IOException {
        socketJugador1 = serverSocket.accept();
        entradaJugador1 = new BufferedReader(new InputStreamReader(socketJugador1.getInputStream(), "UTF-8"));
        salidaJugador1 = new PrintWriter(new OutputStreamWriter(socketJugador1.getOutputStream(), "UTF-8"), true);
        System.out.println("Jugador 1 conectado.");

        socketJugador2 = serverSocket.accept();
        entradaJugador2 = new BufferedReader(new InputStreamReader(socketJugador2.getInputStream(), "UTF-8"));
        salidaJugador2 = new PrintWriter(new OutputStreamWriter(socketJugador2.getOutputStream(), "UTF-8"), true);
        System.out.println("Jugador 2 conectado.");

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

        JSONObject respuesta1 = new JSONObject();
        respuesta1.put("tipo", "rival");
        respuesta1.put("contenido", nombreJugador2);
        salidaJugador1.println(respuesta1.toString());

        JSONObject respuesta2 = new JSONObject();
        respuesta2.put("tipo", "rival");
        respuesta2.put("contenido", nombreJugador1);
        salidaJugador2.println(respuesta2.toString());
    }

    /**
     * Envía un mensaje al jugador a través del PrintWriter especificado.
     *
     * @param salida El PrintWriter por el que se enviará el mensaje.
     * @param mensaje El mensaje a enviar.
     */
    private void enviarMensaje(PrintWriter salida, Mensaje mensaje) {
        salida.println(mensaje.toString());
        salida.flush();
    }

    /**
     * Recibe un mensaje desde el BufferedReader especificado.
     *
     * @param entrada El BufferedReader desde donde se recibirá el mensaje.
     * @return Un objeto Mensaje con el tipo y contenido extraído del JSON
     * recibido.
     * @throws IOException si ocurre un error de lectura.
     */
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

    /**
     * Procesa la fase inicial donde se reciben los tableros de ambos jugadores,
     * valida que estén listos y los intercambia para comenzar el juego.
     *
     * @throws IOException si ocurre un error de comunicación.
     */

    private void procesarTableros() throws IOException {

        this.coordenadasJugador1 = coordenadasJugador1;
        this.coordenadasJugador2 = coordenadasJugador2;

        Mensaje mensaje1 = recibirMensaje(entradaJugador1);
        Mensaje mensaje2 = recibirMensaje(entradaJugador2);

        if (!"estado".equals(mensaje1.getTipo()) || !"READY".equals(mensaje1.getContenido())
                || !"estado".equals(mensaje2.getTipo()) || !"READY".equals(mensaje2.getContenido())) {
            System.out.println("Uno o ambos jugadores no estan listos.");
            return;
        }
        System.out.println("Ambos jugadores estan listos.");

        Mensaje permiso = new Mensaje("permiso", "ENVIAR_TABLERO");
        enviarMensaje(salidaJugador1, permiso);
        enviarMensaje(salidaJugador2, permiso);

        Mensaje tablero1 = recibirMensaje(entradaJugador1);
        Mensaje coordenadas1 = recibirMensaje(entradaJugador1);

        Mensaje tablero2 = recibirMensaje(entradaJugador2);
        Mensaje coordenadas2 = recibirMensaje(entradaJugador2);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();

        String coordenadas1Str = (String) coordenadas1.getContenido();
        String coordenadas2Str = (String) coordenadas2.getContenido();

        List<String> coordenadasJugador1 = gson.fromJson(coordenadas1Str, listType);
        List<String> coordenadasJugador2 = gson.fromJson(coordenadas2Str, listType);

        System.out.println("Coordenadas jugador 1: " + coordenadasJugador1);
        System.out.println("Coordenadas jugador 2: " + coordenadasJugador2);

        Mensaje confirmacion = new Mensaje("confirmacion", "CONFIRMACION_TABLERO_RECIBIDO");
        enviarMensaje(salidaJugador1, confirmacion);
        enviarMensaje(salidaJugador2, confirmacion);

        Mensaje notificacion = new Mensaje("inicio", "TABLEROS_LISTOS");
        enviarMensaje(salidaJugador1, notificacion);
        enviarMensaje(salidaJugador2, notificacion);

        Mensaje mensajeCoordenadas1 = new Mensaje("coordenadas", coordenadas1Str);
        Mensaje mensajeCoordenadas2 = new Mensaje("coordenadas", coordenadas2Str);
        enviarMensaje(salidaJugador2, mensajeCoordenadas1);
        enviarMensaje(salidaJugador1, mensajeCoordenadas2);

        manejarTurnos(coordenadasJugador1, coordenadasJugador2);
    }

    /**
     * Controla la lógica de turnos y comunicación entre jugadores durante el
     * juego.
     *
     * @param coordenadasJugador1 Lista con las coordenadas de las naves del
     * jugador 1.
     * @param coordenadasJugador2 Lista con las coordenadas de las naves del
     * jugador 2.
     * @throws IOException si ocurre un error en la comunicación.
     */
    private void manejarTurnos(List<String> coordenadasJugador1, List<String> coordenadasJugador2) throws IOException {
        boolean juegoActivo = true;
        int jugadorActual = 1; // 1 para Jugador 1, 2 para Jugador 2

        // Guardamos las coordenadas ocupadas por cada jugador en Sets para fácil búsqueda
        Set<String> casillasJugador1 = new HashSet<>(coordenadasJugador1);
        Set<String> casillasJugador2 = new HashSet<>(coordenadasJugador2);

        Set<String> casillasImpactadasJugador1 = new HashSet<>();
        Set<String> casillasImpactadasJugador2 = new HashSet<>();

        while (juegoActivo) {
            if (jugadorActual == 1) {
                enviarMensaje(salidaJugador1, new Mensaje("tu_turno", null));
                Mensaje ataqueJ1 = recibirMensaje(entradaJugador1);

                if (ataqueJ1 != null && "ataque".equals(ataqueJ1.getTipo())) {
                    System.out.println("Jugador 1 ataco: " + ataqueJ1.getContenido());
                    enviarMensaje(salidaJugador2, new Mensaje("ataque_recibido", ataqueJ1.getContenido()));

                    Mensaje resultadoJ1 = recibirMensaje(entradaJugador2);
                    if (resultadoJ1 != null && "resultado_ataque".equals(resultadoJ1.getTipo())) {
                        enviarMensaje(salidaJugador1, resultadoJ1);
                        System.out.println("Resultado del ataque recibido por el servidor: " + resultadoJ1.getContenido());

                        String contenido = (String) resultadoJ1.getContenido();
                        // Ejemplo contenido: "x,y,true" o "x,y,false"
                        String[] datos = contenido.split(",");
                        boolean acierto = false;
                        String coordenada = "";

                        if (datos.length >= 3) {
                            coordenada = datos[0] + "," + datos[1];
                            acierto = Boolean.parseBoolean(datos[2]);
                        }

                        if (acierto) {
                            casillasImpactadasJugador2.add(coordenada);

                            // Verificar si jugador 2 perdió todas sus naves
                            if (casillasImpactadasJugador2.containsAll(casillasJugador2)) {
                                // Fin de juego
                                enviarMensaje(salidaJugador1, new Mensaje("fin_juego", "¡Has ganado!"));
                                enviarMensaje(salidaJugador2, new Mensaje("fin_juego", "Has perdido."));
                                juegoActivo = false;
                                break;
                            }

                            // impacto: mismo jugador sigue
                            enviarMensaje(salidaJugador1, new Mensaje("tu_turno", null));
                        } else {
                            jugadorActual = 2;
                            enviarMensaje(salidaJugador2, new Mensaje("tu_turno", null));
                        }
                    }
                } else if (ataqueJ1 != null && "fin".equals(ataqueJ1.getTipo())) {
                    juegoActivo = false;
                    System.out.println("Juego finalizado por Jugador 1");
                }

            } else { // Turno del Jugador 2
                enviarMensaje(salidaJugador2, new Mensaje("tu_turno", null));
                Mensaje ataqueJ2 = recibirMensaje(entradaJugador2);

                if (ataqueJ2 != null && "ataque".equals(ataqueJ2.getTipo())) {
                    System.out.println("Jugador 2 ataco: " + ataqueJ2.getContenido());
                    enviarMensaje(salidaJugador1, new Mensaje("ataque_recibido", ataqueJ2.getContenido()));

                    Mensaje resultadoJ2 = recibirMensaje(entradaJugador1);
                    if (resultadoJ2 != null && "resultado_ataque".equals(resultadoJ2.getTipo())) {
                        enviarMensaje(salidaJugador2, resultadoJ2);
                        System.out.println("Resultado del ataque recibido por el servidor: " + resultadoJ2.getContenido());

                        String contenido = (String) resultadoJ2.getContenido();
                        String[] datos = contenido.split(",");
                        boolean acierto = false;
                        String coordenada = "";

                        if (datos.length >= 3) {
                            coordenada = datos[0] + "," + datos[1];
                            acierto = Boolean.parseBoolean(datos[2]);
                        }

                        if (acierto) {
                            casillasImpactadasJugador1.add(coordenada);

                            // Verificar si jugador 1 perdió todas sus naves
                            if (casillasImpactadasJugador1.containsAll(casillasJugador1)) {
                                enviarMensaje(salidaJugador2, new Mensaje("fin_juego", "¡Has ganado!"));
                                enviarMensaje(salidaJugador1, new Mensaje("fin_juego", "Has perdido."));
                                juegoActivo = false;
                                break;
                            }

                            // impacto: mismo jugador sigue
                            enviarMensaje(salidaJugador2, new Mensaje("tu_turno", null));
                        } else {
                            jugadorActual = 1;
                            enviarMensaje(salidaJugador1, new Mensaje("tu_turno", null));
                        }
                    }
                } else if (ataqueJ2 != null && "fin".equals(ataqueJ2.getTipo())) {
                    juegoActivo = false;
                    System.out.println("Juego finalizado por Jugador 2");
                }
            }
        }

        cerrar();
    }

    /**
     * Envía un mensaje de ataque al jugador 2 indicando las coordenadas del
     * ataque y si hubo impacto o no.
     *
     * @param x Coordenada X del ataque en el tablero.
     * @param y Coordenada Y del ataque en el tablero.
     * @param impacto Indica si el ataque impactó en una nave.
     *
     * Si la conexión con el jugador 2 no está inicializada, se muestra un error
     * por consola. En caso de excepción durante el envío, se imprime el stack
     * trace.
     */
    public void enviarAtaqueAJugador2(int x, int y, boolean impacto) {
        if (salidaJugador2 == null) {
            System.err.println("Error: conexion con Jugador 2 no inicializada");
            return;
        }
        try {
            String mensaje = "ATAQUE:" + x + "," + y + "," + (impacto ? "1" : "0");
            salidaJugador2.println(mensaje);
            salidaJugador2.flush();
            System.out.println("Mensaje enviado a Jugador 2: " + mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte una cadena de texto que representa posiciones ocupadas en el
     * tablero en una matriz de objetos Casilla, donde las posiciones indicadas
     * estarán marcadas con el estado de casilla ocupada.
     *
     * @param texto Cadena con pares de coordenadas separados por espacios,
     * indicando las posiciones ocupadas.
     * @return Matriz 10x10 de Casilla con las posiciones ocupadas configuradas.
     */
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

    /**
     * Valida que el tablero tenga exactamente 25 casillas ocupadas, que es la
     * cantidad requerida para un tablero válido.
     *
     * @param tablero Matriz 10x10 de Casilla a validar.
     * @return true si hay exactamente 25 casillas ocupadas, false en caso
     * contrario.
     */
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

    /**
     * Cierra todas las conexiones de entrada, salida y sockets asociados a
     * ambos jugadores y al servidor.
     *
     * @throws IOException Si ocurre un error al cerrar alguna conexión o
     * socket.
     *
     * Muestra un mensaje en consola al cerrar todas las conexiones
     * correctamente.
     */
    public void cerrar() throws IOException {
        if (entradaJugador1 != null) {
            entradaJugador1.close();
        }
        if (salidaJugador1 != null) {
            salidaJugador1.close();
        }
        if (socketJugador1 != null) {
            socketJugador1.close();
        }

        if (entradaJugador2 != null) {
            entradaJugador2.close();
        }
        if (salidaJugador2 != null) {
            salidaJugador2.close();
        }
        if (socketJugador2 != null) {
            socketJugador2.close();
        }

        if (serverSocket != null) {
            serverSocket.close();
        }

        System.out.println("Conexiones cerradas.");
    }
}
