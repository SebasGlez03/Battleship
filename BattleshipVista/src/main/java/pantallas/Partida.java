/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas;

import Entidades.Casilla;
import Sonido.ReproductorSonido;
import Sockets.Mensaje;
import Sockets.SocketCliente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Carlo
 */
public class Partida extends javax.swing.JFrame {

    /**
     * Matriz que representa el tablero del jugador con casillas.
     */
    private Casilla[][] tablero;
    /**
     * Matriz que representa el tablero del enemigo con casillas.
     */
    private Casilla[][] tableroEnemigo;
    /**
     * Lista de puntos con coordenadas ocupadas en el tablero del jugador.
     */
    private List<Point> coordenadasOcupadas = new ArrayList<>();
    /**
     * Lista de puntos con coordenadas ocupadas en el tablero enemigo.
     */
    private List<Point> coordenadasOcupadasEnemigo = new ArrayList<>();

<<<<<<< Updated upstream:BattleshipVista/src/main/java/pantallas/ColocarNave4.java
    /**
     * Conjunto que almacena las coordenadas ya atacadas para evitar ataques
     * duplicados.
     */
    private Set<Point> coordenadasAtacadas = new HashSet<>();

    /**
     * Cliente socket para la comunicación con el servidor del juego.
     */
    private SocketCliente socketCliente;

    /**
     * Constructor que inicializa la ventana, recibe las coordenadas ocupadas
     * del jugador y el cliente socket para comunicación.
     *
     * @param coordenadas String con coordenadas ocupadas por las naves del
     * jugador.
     * @param socketCliente Cliente para comunicación con el servidor.
     */
    public ColocarNave4(String coordenadas, SocketCliente socketCliente) {
=======
    private List<List<Point>> barcosEnemigos = new ArrayList<>();
    private Set<Point> coordenadasImpactadasEnemigo = new HashSet<>();

    private Set<Point> coordenadasImpactadasPropias = new HashSet<>();
    private List<List<Point>> barcosPropios;

    private Set<Point> coordenadasAtacadas = new HashSet<>();

    private SocketCliente socketCliente;

    // Constructor que recibe las coordenadas
    public Partida(String coordenadas, SocketCliente socketCliente) {
>>>>>>> Stashed changes:BattleshipVista/src/main/java/pantallas/Partida.java

        this.socketCliente = socketCliente;

        initComponents(); // Inicializa los componentes
        this.coordenadasOcupadas = parseCoordenadas(coordenadas); // Convierte las coordenadas del jugador

        this.barcosPropios = agruparBarcos(coordenadasOcupadas);
        // Inicializar el tablero y marcar las casillas ocupadas
        tablero = new Casilla[10][10];
        tableroEnemigo = new Casilla[10][10];
        panelTablero.setLayout(new GridLayout(10, 10));
        panelTableroEnemigo.setLayout(new GridLayout(10, 10));

        // Fondo decorativo (si es necesario)
        ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/imagenes/fondoTablero.jpg"));
        JLabel labelFondo = new JLabel(fondoIcon);
        labelFondo.setBounds(0, 0, panelFondoTablero.getWidth(), panelFondoTablero.getHeight());
        panelFondoTablero.add(labelFondo);
        panelFondoTablero.setComponentZOrder(labelFondo, panelFondoTablero.getComponentCount() - 1);

        inicializarTablero();
        inicializarTableroEnemigo();
        marcarCasillasOcupadas();

        try {
            recibirCoordenadasServidor();  // Este método debería llenar coordenadasOcupadasEnemigo
        } catch (IOException e) {
            e.printStackTrace();
        }

        iniciarEscuchaServidor();

        btnAtacar.addActionListener(e -> {

            try {
                int x = Integer.parseInt(txtX.getText().trim());
                int y = Integer.parseInt(txtY.getText().trim());

                if (x < 0 || x > 9 || y < 0 || y > 9) {
                    JOptionPane.showMessageDialog(this, "Coordenadas fuera de rango (0-9)");
                    return;
                }

                Point ataque = new Point(x, y);
                if (coordenadasAtacadas.contains(ataque)) {
                    JOptionPane.showMessageDialog(this, "Ya has atacado esta coordenada. Elige otra.");
                    return;
                }

                coordenadasAtacadas.add(ataque);

                realizarAtaque(x, y);

                JButton boton = (JButton) panelTableroEnemigo.getComponent(y * 10 + x);
                boton.setEnabled(false);

                // Limpiar los campos
                txtX.setText("");
                txtY.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce coordenadas válidas");
            }
        });

    }

    /**
     * Método para atacar una casilla en el tablero enemigo. Verifica si la
     * casilla tiene una nave y si no ha sido impactada.
     *
     * @param x Coordenada X del ataque.
     * @param y Coordenada Y del ataque.
     * @return true si el ataque impactó una nave, false en caso contrario.
     */
    private boolean atacar(int x, int y) {
        Casilla casilla = tableroEnemigo[x][y];
        if (casilla != null && casilla.tieneNave() && !casilla.estaImpactada()) {
            casilla.estaImpactada();
            return true;
        }
        return false;
    }

    /**
     * Inicializa el tablero propio con botones para cada casilla. Solo se
     * inicializa si el panel está vacío para evitar duplicados. Cada botón
     * representa una casilla de 40x40 píxeles con imagen de fondo.
     */
    private void inicializarTablero() {
        // Solo inicializamos el tablero si no ha sido inicializado antes
        if (panelTablero.getComponentCount() == 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    JButton casillaButton = new JButton();
                    casillaButton.setPreferredSize(new Dimension(35, 35));
                    casillaButton.setBackground(Color.LIGHT_GRAY);
                    casillaButton.setContentAreaFilled(false); // Sin decoración del SO
                    casillaButton.setFocusPainted(false);       // Sin contorno de foco
                    casillaButton.setMargin(new Insets(0, 0, 0, 0)); // Sin márgenes
                    casillaButton.setOpaque(true);               // Necesario para el fondo
                    casillaButton.setBorder(BorderFactory.createLineBorder(Color.black, 1, false)); // Bordes rectos

                    // Imagen de fondo
                    ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/casillaimg2.png"));
                    Image imagenEscalada = icono.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                    casillaButton.setIcon(new ImageIcon(imagenEscalada));

                    panelTablero.add(casillaButton);
                    tablero[i][j] = new Casilla(i, j);  // Asegúrate de tener el objeto Casilla correctamente inicializado
                }
            }
        }
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    /**
     * Inicializa el tablero enemigo con botones para cada casilla. Similar al
     * tablero propio, pero para representar el área del oponente. Solo se
     * inicializa si el panel está vacío.
     */
    private void inicializarTableroEnemigo() {
        if (panelTableroEnemigo.getComponentCount() == 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    JButton casillaButtonEnemigo = new JButton();
                    casillaButtonEnemigo.setPreferredSize(new Dimension(40, 40));
                    casillaButtonEnemigo.setBackground(Color.LIGHT_GRAY);
                    casillaButtonEnemigo.setContentAreaFilled(false);
                    casillaButtonEnemigo.setFocusPainted(false);
                    casillaButtonEnemigo.setMargin(new Insets(0, 0, 0, 0));
                    casillaButtonEnemigo.setOpaque(true);
                    casillaButtonEnemigo.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));

                    ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/casillaimg2.png"));
                    Image imagenEscalada = icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    casillaButtonEnemigo.setIcon(new ImageIcon(imagenEscalada));

                    panelTableroEnemigo.add(casillaButtonEnemigo);
                    tableroEnemigo[i][j] = new Casilla(i, j);
                }
            }
        }
        panelTableroEnemigo.revalidate();
        panelTableroEnemigo.repaint();
    }

    /**
     * Actualiza el color de la casilla en el tablero enemigo para mostrar el
     * resultado de un ataque recibido.
     *
     * @param x La coordenada X del ataque.
     * @param y La coordenada Y del ataque.
     * @param acierto true si el ataque fue un impacto, false si fue agua.
     */
    public void mostrarResultadoAtaqueEnemigo(int x, int y, boolean acierto) {
        JButton boton = (JButton) panelTableroEnemigo.getComponent(y * 10 + x);
        if (acierto) {
            boton.setBackground(Color.RED);
        } else {
            boton.setBackground(Color.BLUE);
        }
    }

    /**
     * Envía un mensaje de ataque al servidor con las coordenadas indicadas.
     *
     * @param x La coordenada X a atacar.
     * @param y La coordenada Y a atacar.
     */
    private void realizarAtaque(int x, int y) {
        Mensaje ataque = new Mensaje("ataque", x + "," + y);
        socketCliente.enviarMensaje(ataque);
    }

    /**
     * Procesa un ataque recibido del enemigo, marcando la casilla
     * correspondiente como impactada o agua en el tablero propio.
     *
     * @param x La coordenada X atacada.
     * @param y La coordenada Y atacada.
     * @param esAcierto true si el ataque impactó una nave, false si fue agua.
     */
    public void recibirAtaque(int x, int y, boolean esAcierto) {
        Casilla casilla = tablero[x][y];
        casilla.estaImpactada();

        JButton boton = (JButton) panelTablero.getComponent(y * 10 + x);
        if (esAcierto) {
            boton.setBackground(Color.RED);
        } else {
            boton.setBackground(Color.BLUE);
        }
    }

<<<<<<< Updated upstream:BattleshipVista/src/main/java/pantallas/ColocarNave4.java
    /**
     * Inicia un hilo que escucha continuamente mensajes desde el servidor,
     * procesándolos según su tipo. Se ejecuta en segundo plano para no bloquear
     * la interfaz gráfica.
     */
=======
>>>>>>> Stashed changes:BattleshipVista/src/main/java/pantallas/Partida.java
    public void iniciarEscuchaServidor() {
        new Thread(() -> {
            while (true) {
                try {
                    Mensaje mensaje = socketCliente.recibirMensaje();
                    System.out.println("Mensaje recibido: " + mensaje.getTipo() + " - " + mensaje.getContenido());

                    procesarMensaje(mensaje); // Procesa el mensaje

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start(); // Inicia el hilo
    }

    /**
     * Procesa un mensaje recibido, actualizando la interfaz y lógica según el
     * tipo: - resultado_ataque: muestra impacto o agua en tablero enemigo. -
     * ataque_recibido: actualiza el tablero propio y responde con resultado. -
     * fin_juego: muestra mensaje de fin de juego.
     *
     * @param mensaje El mensaje recibido desde el servidor o cliente.
     */
    private void procesarMensajeAsync(Mensaje mensaje) {
        new Thread(() -> {
            procesarMensaje(mensaje);
        }).start();
    }

    /**
     * Procesa un mensaje en un hilo aparte para no bloquear la interfaz.
     *
     * @param mensaje El mensaje a procesar.
     */
    private void procesarMensaje(Mensaje mensaje) {
        switch (mensaje.getTipo()) {
            case "resultado_ataque": {
                String[] datos = ((String) mensaje.getContenido()).split(",");
                int x = Integer.parseInt(datos[0]);
                int y = Integer.parseInt(datos[1]);
                boolean acierto = Boolean.parseBoolean(datos[2]);
                Point puntoImpactado = new Point(x, y);

                SwingUtilities.invokeLater(() -> {
                    JButton boton = (JButton) panelTableroEnemigo.getComponent(puntoImpactado.y * 10 + puntoImpactado.x);

<<<<<<< Updated upstream:BattleshipVista/src/main/java/pantallas/ColocarNave4.java
                    // Sonido de impacto o agua
                    if (acierto) {
                        ReproductorSonido.reproducirSonido("/sounds/impacto.wav");
                    } else {
                        ReproductorSonido.reproducirSonido("/sounds/agua.wav");
                    }

                    // Mensaje emergente para informar resultado del ataque
                    JOptionPane.showMessageDialog(this, acierto ? "impacto" : "agua");
=======
                    if (acierto) {
                        ReproductorSonido.reproducirSonido("/sounds/impacto.wav");
                        boton.setBackground(Color.YELLOW);

                        if (esBarcoHundido(puntoImpactado)) {
                            colorearBarcoEnemigoHundido(puntoImpactado);

                            // Obtener tamaño del barco hundido para determinar el tipo
                            List<Point> barcoHundido = null;
                            for (List<Point> barco : barcosEnemigos) {
                                if (barco.contains(puntoImpactado)) {
                                    barcoHundido = barco;
                                    break;
                                }
                            }
                            int tamaño = barcoHundido != null ? barcoHundido.size() : 0;
                            String tipoBarco = obtenerTipoBarcoPorTamano(tamaño);

                            JOptionPane.showMessageDialog(this, "¡Has destruido un " + tipoBarco + " enemigo!");
                        } else {
//                            ReproductorSonido.reproducirSonido("/sounds/impacto.wav");
                            JOptionPane.showMessageDialog(this, "¡Impacto!");
                        }

                        
                    } else {
                        boton.setBackground(Color.BLUE);
                        ReproductorSonido.reproducirSonido("/sounds/agua.wav");
                        JOptionPane.showMessageDialog(this, "¡Agua!");
                    }
>>>>>>> Stashed changes:BattleshipVista/src/main/java/pantallas/Partida.java
                });
                break;
            }

            case "ataque_recibido": {
                String[] datos = ((String) mensaje.getContenido()).split(",");
                int x = Integer.parseInt(datos[0]);
                int y = Integer.parseInt(datos[1]);
                Point puntoImpactado = new Point(x, y);

                boolean esAcierto = coordenadasOcupadas.contains(puntoImpactado);

                if (esAcierto) {
                    tablero[x][y].estaImpactada();

                    if (esBarcoPropioHundido(puntoImpactado)) {
                        colorearBarcoPropioHundido(puntoImpactado);
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    JButton boton = (JButton) panelTablero.getComponent(puntoImpactado.y * 10 + puntoImpactado.x);

                    if (esAcierto) {
                        if (esBarcoPropioHundido(puntoImpactado)) {
                            // Obtener tamaño del barco propio hundido para mostrar mensaje
                            List<Point> barcoHundido = null;
                            for (List<Point> barco : barcosPropios) {
                                if (barco.contains(puntoImpactado)) {
                                    barcoHundido = barco;
                                    break;
                                }
                            }
                            int tamaño = barcoHundido != null ? barcoHundido.size() : 0;
                            String tipoBarco = obtenerTipoBarcoPorTamano(tamaño);

                            JOptionPane.showMessageDialog(this, "¡Tu " + tipoBarco + " ha sido destruido!");
                        } else {
                            boton.setBackground(Color.YELLOW);
                            JOptionPane.showMessageDialog(this, "¡Has sido impactado!");
                        }
                    } else {
                        boton.setBackground(Color.BLUE);
                        JOptionPane.showMessageDialog(this, "¡Agua!");
                    }
                });

                Mensaje respuesta = new Mensaje("resultado_ataque", x + "," + y + "," + esAcierto);
                System.out.println(respuesta);
                socketCliente.enviarMensaje(respuesta);
                break;
            }

            case "fin_juego": {
                String resultado = (String) mensaje.getContenido();
                mostrarPantallaFinal(resultado);
                break;
            }
        }
    }
<<<<<<< Updated upstream:BattleshipVista/src/main/java/pantallas/ColocarNave4.java

    /**
     * Muestra la pantalla final según el resultado del juego. Si el resultado
     * contiene que has ganado, se abre la ventana de victoria. Si contiene "Has
     * perdido.", se abre la ventana de derrota.
     *
     * @param resultado Texto con el resultado del juego.
     */
    private void mostrarPantallaFinal(String resultado) {
        if (resultado.contains("Has ganado!")) {
            VentanaGanaste victoria = new VentanaGanaste(this, resultado);
            victoria.setVisible(true);
        } else if (resultado.contains("Has perdido.")) {
            VentanaPerdiste derrota = new VentanaPerdiste(this, resultado);
            derrota.setModal(true); // Igual aquí
            derrota.setVisible(true);
        }
    }

    /**
     * Recibe las coordenadas del enemigo en formato String, las parsea a una
     * lista de puntos y marca las casillas correspondientes en el tablero
     * enemigo.
     *
     * @param coordenadasEnemigo Coordenadas del enemigo en formato String.
     */
=======

    private String obtenerTipoBarcoPorTamano(int tamaño) {
        switch (tamaño) {
            case 4:
                return "Portaaviones";
            case 3:
                return "Crucero";
            case 2:
                return "Submarino";
            case 1:
                return "Barco";
            default:
                return "Barco desconocido";
        }
    }

    public void recibirAtaqueEnemigo(Point ataque) {
        // Marca la casilla como impactada (amarillo)
        JButton boton = (JButton) panelTablero.getComponent(ataque.y * 10 + ataque.x);
        boton.setBackground(Color.YELLOW);

        coordenadasImpactadasPropias.add(ataque);

        // Verificar si el barco propio está hundido
        if (esBarcoPropioHundido(ataque)) {
            // Colorea todo el barco hundido en rojo
            colorearBarcoPropioHundido(ataque);
            JOptionPane.showMessageDialog(this, "¡Uno de tus barcos fue hundido!");
        } else {
            JOptionPane.showMessageDialog(this, "Has recibido un impacto en (" + ataque.x + ", " + ataque.y + ")");
        }
    }

    private boolean esBarcoPropioHundido(Point puntoImpactado) {
        coordenadasImpactadasPropias.add(puntoImpactado);

        for (List<Point> barco : barcosPropios) {
            if (barco.contains(puntoImpactado)) {
                if (coordenadasImpactadasPropias.containsAll(barco)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void colorearBarcoPropioHundido(Point puntoImpactado) {
        Set<Point> barco = new HashSet<>();
        Queue<Point> cola = new LinkedList<>();
        cola.add(puntoImpactado);

        while (!cola.isEmpty()) {
            Point actual = cola.poll();
            if (barco.contains(actual)) {
                continue;
            }
            barco.add(actual);

            // Vecinos ortogonales (no diagonales)
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nx = actual.x + dir[0];
                int ny = actual.y + dir[1];
                Point vecino = new Point(nx, ny);

                // Solo agregamos si es parte del barco propio y ya estaba impactado
                if (coordenadasOcupadas.contains(vecino)) {
                    JButton b = (JButton) panelTablero.getComponent(ny * 10 + nx);
                    if (b.getBackground().equals(Color.YELLOW)) {
                        cola.add(vecino);
                    }
                }
            }
        }

        // Colorear todas las casillas del barco hundido de rojo
        for (Point p : barco) {
            JButton b = (JButton) panelTablero.getComponent(p.y * 10 + p.x);
            b.setBackground(Color.RED);
        }
    }

    private void colorearBarcoEnemigoHundido(Point puntoImpactado) {
        Set<Point> barco = new HashSet<>();
        Queue<Point> cola = new LinkedList<>();
        cola.add(puntoImpactado);

        while (!cola.isEmpty()) {
            Point actual = cola.poll();
            if (barco.contains(actual)) {
                continue;
            }
            barco.add(actual);

            // Verificamos vecinos ortogonales (no diagonales)
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : dirs) {
                int nx = actual.x + dir[0];
                int ny = actual.y + dir[1];
                Point vecino = new Point(nx, ny);

                // Solo agregamos si es parte del barco enemigo y ya estaba impactado (amarillo)
                if (coordenadasOcupadasEnemigo.contains(vecino)) {
                    JButton b = (JButton) panelTableroEnemigo.getComponent(ny * 10 + nx);
                    if (b.getBackground().equals(Color.YELLOW)) {
                        cola.add(vecino);
                    }
                }
            }
        }

        // Ahora coloreamos todas las casillas del barco de rojo
        for (Point p : barco) {
            JButton b = (JButton) panelTableroEnemigo.getComponent(p.y * 10 + p.x);
            b.setBackground(Color.RED);
        }
    }

    private void mostrarPantallaFinal(String resultado) {
        if (resultado.contains("Has ganado!")) {
            VentanaGanar victoria = new VentanaGanar(this);
            victoria.setVisible(true);
        } else if (resultado.contains("Has perdido.")) {
            VentanaPerder derrota = new VentanaPerder(this);
            derrota.setModal(true); // Igual aquí
            derrota.setVisible(true);
        }
    }

    // Método para recibir las coordenadas del enemigo
>>>>>>> Stashed changes:BattleshipVista/src/main/java/pantallas/Partida.java
    public void recibirCoordenadasEnemigo(String coordenadasEnemigo) {
        coordenadasOcupadasEnemigo = new ArrayList<>();
        barcosEnemigos.clear();

        // Parsear todas las coordenadas sin asumir orden ni tamaños
        coordenadasEnemigo = coordenadasEnemigo.substring(1, coordenadasEnemigo.length() - 1);
        String[] coords = coordenadasEnemigo.split(",\"");

        for (String coord : coords) {
            coord = coord.replace("\"", "").trim();
            String[] partes = coord.split(",");
            int x = Integer.parseInt(partes[0]);
            int y = Integer.parseInt(partes[1]);
            Point punto = new Point(x, y);
            coordenadasOcupadasEnemigo.add(punto);
        }

<<<<<<< Updated upstream:BattleshipVista/src/main/java/pantallas/ColocarNave4.java
        // Marcar las casillas correspondientes en el tablero enemigo
        for (Point coord : coordenadasOcupadasEnemigo) {
            int x = coord.x;
            int y = coord.y;

            // Verificar las coordenadas de cada casilla
            System.out.println("Marcando casilla en: (" + x + ", " + y + ")");

            // Obtener el botón correspondiente del tablero enemigo
            JButton casillaButtonEnemigo = (JButton) panelTableroEnemigo.getComponent(y * 10 + x);

            casillaButtonEnemigo.revalidate();
            casillaButtonEnemigo.repaint();  // Asegura que la interfaz se actualice
        }
    }

    /**
     * Espera y recibe las coordenadas del servidor para actualizar el tablero
     * enemigo.
     *
     * @throws IOException En caso de errores en la comunicación por socket.
     */
=======
        // Agrupar coordenadas en barcos conectados
        barcosEnemigos = agruparBarcos(coordenadasOcupadasEnemigo);

        // Debug: mostrar barcos
        System.out.println("Barcos enemigos detectados:");
        for (int i = 0; i < barcosEnemigos.size(); i++) {
            System.out.print("Barco " + (i + 1) + ": ");
            for (Point p : barcosEnemigos.get(i)) {
                System.out.print("(" + p.x + "," + p.y + ") ");
            }
            System.out.println();
        }
    }

    private boolean esBarcoHundido(Point puntoImpactado) {
        coordenadasImpactadasEnemigo.add(puntoImpactado);

        for (List<Point> barco : barcosEnemigos) {
            if (barco.contains(puntoImpactado)) {
                if (coordenadasImpactadasEnemigo.containsAll(barco)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<List<Point>> agruparBarcos(List<Point> coords) {
        List<List<Point>> barcos = new ArrayList<>();
        Set<Point> sinProcesar = new HashSet<>(coords);

        while (!sinProcesar.isEmpty()) {
            Point inicio = sinProcesar.iterator().next();
            List<Point> barco = new ArrayList<>();
            Queue<Point> cola = new LinkedList<>();
            cola.add(inicio);

            while (!cola.isEmpty()) {
                Point actual = cola.poll();
                if (sinProcesar.contains(actual)) {
                    sinProcesar.remove(actual);
                    barco.add(actual);

                    // Vecinos adyacentes (arriba, abajo, izquierda, derecha)
                    List<Point> vecinos = List.of(
                            new Point(actual.x + 1, actual.y),
                            new Point(actual.x - 1, actual.y),
                            new Point(actual.x, actual.y + 1),
                            new Point(actual.x, actual.y - 1)
                    );

                    for (Point vecino : vecinos) {
                        if (sinProcesar.contains(vecino)) {
                            cola.add(vecino);
                        }
                    }
                }
            }
            barcos.add(barco);
        }
        return barcos;
    }

    // Método para recibir las coordenadas del servidor
>>>>>>> Stashed changes:BattleshipVista/src/main/java/pantallas/Partida.java
    public void recibirCoordenadasServidor() throws IOException {
        // Esperar coordenadas del servidor para el tablero enemigo
        Mensaje respuestaEnemigo = socketCliente.recibirMensaje();
        if (respuestaEnemigo != null && respuestaEnemigo.getTipo().equals("coordenadas")) {
            String coordenadasEnemigo = (String) respuestaEnemigo.getContenido();
            recibirCoordenadasEnemigo(coordenadasEnemigo);  // Actualizar el tablero enemigo
        }
    }

    /**
     * Marca las casillas ocupadas en el tablero propio con un color específico.
     * Cambia el fondo de los botones correspondientes a verde para indicar
     * ocupación.
     */
    private void marcarCasillasOcupadas() {
        for (Point coord : coordenadasOcupadas) {
            // Coordenadas ocupadas
            int x = coord.x;
            int y = coord.y;

            // Obtener el botón correspondiente
            JButton casillaButton = (JButton) panelTablero.getComponent(y * 10 + x);

            // Cambiar el color o la imagen del botón
            casillaButton.setBackground(Color.GREEN);  // Cambiar a rojo para marcar como ocupada

        }
    }

    /**
     * Convierte una cadena de coordenadas en formato específico a una lista de
     * objetos Point. Valida que las coordenadas estén dentro del rango
     * permitido (0 a 9).
     *
     * @param coordenadas Cadena con coordenadas en formato parecido a:
     * ["x,y",...]
     * @return Lista de puntos con las coordenadas parseadas.
     */
    private List<Point> parseCoordenadas(String coordenadas) {
        List<Point> puntos = new ArrayList<>();

        // Eliminar los corchetes de la cadena
        coordenadas = coordenadas.substring(1, coordenadas.length() - 1);

        // Dividir la cadena por las comas que separan las coordenadas
        String[] coords = coordenadas.split(",\"");

        // Recorremos las coordenadas, eliminando las comillas y procesando
        for (String coord : coords) {
            // Limpiar las comillas y espacios
            coord = coord.replace("\"", "").trim();

            // Dividir el par "x,y" por la coma
            String[] partes = coord.split(",");

            // Convertir las partes a enteros y crear un objeto Point
            int x = Integer.parseInt(partes[0]);
            int y = Integer.parseInt(partes[1]);

            // Validar que las coordenadas estén dentro del rango permitido
            if (x < 0 || x >= 10 || y < 0 || y >= 10) {
                System.out.println("Coordenada fuera de rango: (" + x + ", " + y + ")");
                continue; // Si la coordenada está fuera de rango, no se procesa
            }

            // Agregar el punto a la lista
            puntos.add(new Point(x, y));
        }

        return puntos;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondoTablero = new javax.swing.JPanel();
        panelfondo = new javax.swing.JPanel();
        panelTablero = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelfondo2 = new javax.swing.JPanel();
        panelTableroEnemigo = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        txtX = new javax.swing.JTextField();
        txtY = new javax.swing.JTextField();
        btnAtacar = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelFondoTablero.setBackground(new java.awt.Color(204, 204, 204));

        panelfondo.setBackground(new java.awt.Color(102, 102, 102));
        panelfondo.setEnabled(false);
        panelfondo.setPreferredSize(new java.awt.Dimension(575, 575));

        panelTablero.setBackground(new java.awt.Color(0, 0, 0));
        panelTablero.setForeground(new java.awt.Color(0, 0, 0));
        panelTablero.setPreferredSize(new java.awt.Dimension(400, 400));
        panelTablero.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  0");
        jLabel1.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("  1");
        jLabel2.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("  2");
        jLabel3.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("  3");
        jLabel4.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("  4");
        jLabel5.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("  5");
        jLabel6.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("  6");
        jLabel7.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("  7");
        jLabel8.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("  8");
        jLabel9.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("  9");
        jLabel10.setPreferredSize(new java.awt.Dimension(25, 30));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("    0");
        jLabel11.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("    1");
        jLabel12.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel13.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("    2");
        jLabel13.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel14.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("    3");
        jLabel14.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel15.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("    4");
        jLabel15.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel16.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("    5");
        jLabel16.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel17.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("   6");
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel18.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("   7");
        jLabel18.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel19.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("   8");
        jLabel19.setPreferredSize(new java.awt.Dimension(30, 25));

        jLabel20.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("   9");
        jLabel20.setPreferredSize(new java.awt.Dimension(30, 25));

        javax.swing.GroupLayout panelfondoLayout = new javax.swing.GroupLayout(panelfondo);
        panelfondo.setLayout(panelfondoLayout);
        panelfondoLayout.setHorizontalGroup(
            panelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelfondoLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        panelfondoLayout.setVerticalGroup(
            panelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelfondoLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addGap(542, 542, 542))
        );

        panelfondo2.setBackground(new java.awt.Color(102, 102, 102));
        panelfondo2.setEnabled(false);
        panelfondo2.setPreferredSize(new java.awt.Dimension(575, 575));

        panelTableroEnemigo.setBackground(new java.awt.Color(0, 0, 0));
        panelTableroEnemigo.setForeground(new java.awt.Color(0, 0, 0));
        panelTableroEnemigo.setPreferredSize(new java.awt.Dimension(400, 400));
        panelTableroEnemigo.setLayout(new java.awt.GridLayout(1, 0));

        jLabel41.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("  0");
        jLabel41.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel42.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("  1");
        jLabel42.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel43.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("  2");
        jLabel43.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel44.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("  3");
        jLabel44.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel45.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("  4");
        jLabel45.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel46.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("  5");
        jLabel46.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel47.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("  6");
        jLabel47.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel48.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("  7");
        jLabel48.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel49.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("  8");
        jLabel49.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel50.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("  9");
        jLabel50.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel51.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("    0");
        jLabel51.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel52.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("    1");
        jLabel52.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel53.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("    2");
        jLabel53.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel54.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("    3");
        jLabel54.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel55.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("    4");
        jLabel55.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel56.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("    5");
        jLabel56.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel57.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("   6");
        jLabel57.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel58.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("   7");
        jLabel58.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel59.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("   8");
        jLabel59.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel60.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("   9");
        jLabel60.setPreferredSize(new java.awt.Dimension(35, 25));

        javax.swing.GroupLayout panelfondo2Layout = new javax.swing.GroupLayout(panelfondo2);
        panelfondo2.setLayout(panelfondo2Layout);
        panelfondo2Layout.setHorizontalGroup(
            panelfondo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfondo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelfondo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelfondo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelfondo2Layout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelTableroEnemigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        panelfondo2Layout.setVerticalGroup(
            panelfondo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelfondo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelfondo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelfondo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelfondo2Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelTableroEnemigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(496, 496, 496))
        );

        btnAtacar.setBackground(new java.awt.Color(255, 153, 102));
        btnAtacar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAtacar.setForeground(new java.awt.Color(255, 255, 255));
        btnAtacar.setText("ATACAR");
        btnAtacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtacarActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Ingresa coordenada X:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Ingresa coordenada Y:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("TU TABLERO");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("TABLERO ENEMIGO");

        javax.swing.GroupLayout panelFondoTableroLayout = new javax.swing.GroupLayout(panelFondoTablero);
        panelFondoTablero.setLayout(panelFondoTableroLayout);
        panelFondoTableroLayout.setHorizontalGroup(
            panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoTableroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelFondoTableroLayout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addGap(103, 103, 103)
                            .addComponent(jLabel22))
                        .addGroup(panelFondoTableroLayout.createSequentialGroup()
                            .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(210, 210, 210)
                            .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(46, 46, 46)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoTableroLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAtacar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181)))
                .addGap(96, 96, 96))
            .addGroup(panelFondoTableroLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panelfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(panelfondo2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
            .addGroup(panelFondoTableroLayout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(242, 242, 242))
        );
        panelFondoTableroLayout.setVerticalGroup(
            panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoTableroLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelfondo2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAtacar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondoTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondoTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtacarActionPerformed

    }//GEN-LAST:event_btnAtacarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtacar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelFondoTablero;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JPanel panelTableroEnemigo;
    private javax.swing.JPanel panelfondo;
    private javax.swing.JPanel panelfondo2;
    private javax.swing.JTextField txtX;
    private javax.swing.JTextField txtY;
    // End of variables declaration//GEN-END:variables
}
