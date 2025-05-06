/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Entidades.Casilla;
import Entidades.Tablero;
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

    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
        salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"), true);
        System.out.println("Conectado al servidor.");
    }

    public void enviar(String mensaje) {
        salida.println(mensaje);
    }

    public String recibir() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String mensaje = reader.readLine(); // lee una línea
    return mensaje;  // Devolver el mensaje recibido
}

    public void enviarNombre(String nombre) {
        this.nombreJugador = nombre;
        enviar(nombre);
    }

    public String recibirNombreOponente() throws IOException {
        nombreOponente = recibir();
        System.out.println("Oponente conectado: " + nombreOponente);
        return nombreOponente;
    }

    public void confirmarConexion() {
        enviar("READY");
    }

    public void enviarTablero(Casilla[][] tablero) {
    StringBuilder sb = new StringBuilder("TABLERO");

    // Recorremos el tablero de casillas
    for (int i = 0; i < tablero.length; i++) {
        for (int j = 0; j < tablero[i].length; j++) {
            Casilla casilla = tablero[i][j];
            
            // Comprobamos si la casilla está ocupada (o cualquier otra condición que desees)
            if (casilla.tieneNave()) {  
                sb.append(" ").append(i).append(" ").append(j);  // Agregamos las coordenadas de la casilla ocupada
            }
        }
    }

    // Enviamos el tablero al servidor
    enviar(sb.toString());
}

    public Casilla[][] recibirTablero() throws IOException {
    String mensaje = recibir();  // Espera mensaje del tipo "TABLERO x y x y..."
    return convertirMensajeATablero(mensaje);  // Convertir el mensaje en un tablero de Casillas
}

   public Casilla[][] convertirMensajeATablero(String mensaje) {
    Casilla[][] tablero = new Casilla[10][10];
    
    // Inicializa el tablero con casillas vacías (ocupada = false)
    for (int i = 0; i < tablero.length; i++) {
        for (int j = 0; j < tablero[i].length; j++) {
            tablero[i][j] = new Casilla(i, j);  // Por defecto, todas las casillas están libres
        }
    }

    if (!mensaje.startsWith("TABLERO")) return tablero;

    String[] partes = mensaje.split(" ");
    for (int i = 1; i < partes.length; i += 2) {
        int fila = Integer.parseInt(partes[i]);
        int columna = Integer.parseInt(partes[i + 1]);
        
        // Marcamos la casilla como ocupada
        tablero[fila][columna].tieneNave();
    }

    return tablero;
}

    public void iniciarJuego() throws IOException {
        conectar("127.0.0.1", 12345);

        // Paso 1: Enviar nombre y recibir el del oponente
        enviarNombre("JugadorX");
        recibirNombreOponente();

        // Paso 2: Confirmar que estamos listos
        confirmarConexion();

        // Paso 3: Esperar orden del servidor
        String mensaje = recibir();
        if ("ENVIAR_TABLERO".equals(mensaje)) {
            Casilla[][] tablero = obtenerTableroDelJugador();
            enviarTablero(tablero);
        }

        // Paso 4: Esperar confirmación de tablero
        mensaje = recibir();
        if ("TABLERO_RECIBIDO".equals(mensaje)) {
            enviar("CONFIRMADO");
        }

        // Paso 5: Esperar el tablero del oponente
        mensaje = recibir();
        if (mensaje.startsWith("TABLEROS_LISTOS")) {
            Casilla[][] tableroOponente = convertirMensajeATablero(mensaje.replace("TABLEROS_LISTOS ", ""));
            System.out.println("Tablero del oponente recibido. ¡Comienza la batalla!");
        }

        // Paso 6: Iniciar ataques
        while (true) {
            String ataque = enviarAtaque();
            recibirRespuestaAtaque(ataque);
        }
    }

    private Casilla[][] obtenerTableroDelJugador() {
        // Creamos el tablero de Casillas
    Casilla[][] tablero = new Casilla[10][10]; // Tablero de 10x10

    // Llenamos el tablero con casillas y determinamos si están ocupadas o no
    for (int i = 0; i < tablero.length; i++) {
        for (int j = 0; j < tablero[i].length; j++) {
            // Aquí puedes agregar la lógica para determinar si la casilla está ocupada
            // Por ejemplo, si alguna casilla debe estar ocupada con un barco
            boolean ocupada = false;
            if (i == 0 && j < 3) {  // Ejemplo de una nave ocupando las primeras tres casillas
                ocupada = true;
            }
            tablero[i][j] = new Casilla(i, j); // Crear la casilla en la posición (i, j)
        }
    }

    return tablero;
    }

    public String enviarAtaque() throws IOException {
        String ataque = "3 4";  // Coordenadas del ataque, deben tomarse de la interfaz
        enviar(ataque);
        return ataque;
    }

    public void recibirRespuestaAtaque(String ataque) throws IOException {
        String resultado = recibir();
        System.out.println("Resultado del ataque a [" + ataque + "]: " + resultado);
    }

    public Socket getSocket() {
        return socket;
    }
}
