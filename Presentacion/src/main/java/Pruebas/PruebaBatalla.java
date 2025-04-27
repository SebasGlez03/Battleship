/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;
import EntidadesDTO.*;
import PatronBuilder.*;
import PatronState.EstadoCasillaImpactada;
import PatronState.EstadoCasillaOcupada;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;


/**
 *
 * @author Carlo
 */
public class PruebaBatalla extends JFrame {

    private TableroDTO tableroJugador;   // Tablero del jugador
    private TableroDTO tableroEnemigo;   // Tablero del enemigo
    private JButton[][] botonesJugador;  // Botones para el tablero del jugador
    private JButton[][] botonesEnemigo;  // Botones para el tablero enemigo
    private NaveDTO naveActual;
    private boolean esHorizontal = true; // Orientación de las naves
    private JLabel lblOrientacion;
    private JComboBox<String> selectorBarco;
    private JButton btnListo;  // Botón para indicar que el jugador está listo
    private int barcosColocados = 0;  // Contador de barcos colocados por el jugador
    private int barcosEnemigosColocados = 0;  // Contador de barcos colocados por el enemigo
    private List<NaveDTO> navesEnemigas = new ArrayList<>();  // Lista para almacenar las naves enemigas

    public PruebaBatalla() {
        setTitle("Batalla Naval");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableroJugador = new TableroDTO();   // Tablero del jugador
        tableroEnemigo = new TableroDTO();   // Tablero del enemigo
        botonesJugador = new JButton[10][10];  // Botones para tablero jugador
        botonesEnemigo = new JButton[10][10];  // Botones para tablero enemigo

        JPanel panelTableroJugador = new JPanel(new GridLayout(10, 10));
        JPanel panelTableroEnemigo = new JPanel(new GridLayout(10, 10));
        JPanel panelControles = new JPanel();

        // Crear los botones del tablero jugador
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final int x = i;
                final int y = j;
                botonesJugador[i][j] = new JButton();
                botonesJugador[i][j].setBackground(Color.LIGHT_GRAY);
                botonesJugador[i][j].addActionListener(e -> colocarBarcoJugador(x, y));
                panelTableroJugador.add(botonesJugador[i][j]);
            }
        }

        // Crear los botones del tablero enemigo
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final int x = i;
                final int y = j;
                botonesEnemigo[i][j] = new JButton();
                botonesEnemigo[i][j].setBackground(Color.CYAN);
                botonesEnemigo[i][j].addActionListener(e -> atacarEnemigo(x, y));
                panelTableroEnemigo.add(botonesEnemigo[i][j]);
            }
        }

        // Selector de tipo de nave
        selectorBarco = new JComboBox<>(new String[]{"Submarino", "Crucero", "Porta Aviones", "Barco"});
        selectorBarco.addActionListener(e -> seleccionarBarco());
        panelControles.add(selectorBarco);

        // Botón para cambiar orientación
        JButton btnOrientacion = new JButton("Cambiar Orientación");
        lblOrientacion = new JLabel("Orientación: Horizontal");

        btnOrientacion.addActionListener(e -> {
            esHorizontal = !esHorizontal;
            lblOrientacion.setText("Orientación: " + (esHorizontal ? "Horizontal" : "Vertical"));
        });

        panelControles.add(btnOrientacion);
        panelControles.add(lblOrientacion);

        // Botón para indicar que el jugador está listo
        btnListo = new JButton("Listo");
        btnListo.addActionListener(e -> colocarBarcosEnemigosAleatoriamente());
        panelControles.add(btnListo);

        // Layout principal
        setLayout(new BorderLayout());
        add(panelTableroJugador, BorderLayout.WEST);
        add(panelTableroEnemigo, BorderLayout.EAST);
        add(panelControles, BorderLayout.SOUTH);

        // Inicializa con el primer barco
        seleccionarBarco();
    }

    private void seleccionarBarco() {
        String tipo = (String) selectorBarco.getSelectedItem();
        Director director = null;

        switch (tipo) {
            case "Submarino":
                director = new Director(new SubmarinoBuilder());
                break;
            case "Crucero":
                director = new Director(new CruceroBuilder());
                break;
            case "Porta Aviones":
                director = new Director(new PortaAvionesBuilder());
                break;
            case "Barco":
                director = new Director(new BarcoBuilder());
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de nave desconocido.");
                return;
        }

        naveActual = director.construir();
    }

    private void colocarBarcoJugador(int x, int y) {
        if (naveActual == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una nave primero.");
            return;
        }

        int tamano = naveActual.getTamano();

        if ((esHorizontal && y + tamano > 10) || (!esHorizontal && x + tamano > 10)) {
            JOptionPane.showMessageDialog(this, "No cabe en esa orientación");
            return;
        }

        // Verificar que todas las casillas estén disponibles
        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = tableroJugador.getCasilla(xi, yi);
            if (casilla == null || !casilla.estaDisponible()) {
                JOptionPane.showMessageDialog(this, "Casillas ocupadas");
                return;
            }
        }

        // Colocamos el barco
        naveActual.getPosiciones().clear();
        Color colorCasilla = obtenerColor(naveActual.getColor());

        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = tableroJugador.getCasilla(xi, yi);
            casilla.setEstado(new EstadoCasillaOcupada());  // Cambiar estado a ocupado

            naveActual.agregarCasilla(casilla);
            botonesJugador[xi][yi].setBackground(colorCasilla);
        }

        barcosColocados++;
        JOptionPane.showMessageDialog(this, naveActual.getNombre() + " colocado en tu tablero");

        // Verificar si se colocaron todos los barcos
        if (barcosColocados == 11) {  // 2 + 2 + 4 + 3 = 11
            JOptionPane.showMessageDialog(this, "¡Has colocado todos tus barcos!");
        }
    }

    // Método para colocar barcos aleatorios en el tablero enemigo
    // Método para colocar barcos aleatorios en el tablero enemigo
private void colocarBarcosEnemigosAleatoriamente() {
    Random rand = new Random();
    // Aquí definimos los tipos de barcos y sus cantidades
    String[] tiposBarcos = {"Submarino", "Crucero", "Porta Aviones", "Barco"};
    // Número de barcos por tipo
    int[] cantidadBarcos = {4, 2, 2, 3};  // 4 Submarinos, 2 Cruceros, 2 Portaaviones, 3 Barcos

    // Recorremos los tipos de barcos
    for (int i = 0; i < tiposBarcos.length; i++) {
        for (int j = 0; j < cantidadBarcos[i]; j++) {
            NaveDTO nave = null;
            Director director = null;

            // Elegir el tipo de barco y su respectivo builder
            switch (tiposBarcos[i]) {
                case "Submarino":
                    director = new Director(new SubmarinoBuilder());
                    break;
                case "Crucero":
                    director = new Director(new CruceroBuilder());
                    break;
                case "Porta Aviones":
                    director = new Director(new PortaAvionesBuilder());
                    break;
                case "Barco":
                    director = new Director(new BarcoBuilder());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de nave desconocido.");
                    return;
            }

            // Crear la nave con el builder
            nave = director.construir();

            // Intentar colocar la nave en el tablero enemigo de manera aleatoria
            boolean colocado = false;
            while (!colocado) {
                // Generar coordenadas aleatorias para la colocación
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                boolean horizontal = rand.nextBoolean();  // También aleatorio, orientación

                // Verificar si la posición es válida para colocar la nave
                if (esPosicionValida(tableroEnemigo, x, y, nave.getTamano(), horizontal)) {
                    colocarBarcoEnemigo(x, y, nave, horizontal);
                    navesEnemigas.add(nave);  // Agregar la nave a la lista de naves enemigas
                    colocado = true;  // Cambiar el estado a colocado
                }
            }
        }
    }

    JOptionPane.showMessageDialog(this, "Barcos enemigos colocados aleatoriamente.");
}


    // Verificar si una posición es válida para colocar un barco
    private boolean esPosicionValida(TableroDTO tablero, int x, int y, int tamano, boolean horizontal) {
        for (int i = 0; i < tamano; i++) {
            int xi = horizontal ? x : x + i;
            int yi = horizontal ? y + i : y;

            CasillaDTO casilla = tablero.getCasilla(xi, yi);
            if (casilla == null || !casilla.estaDisponible()) {
                return false;
            }
        }
        return true;
    }

    // Colocar un barco en el tablero enemigo
    private void colocarBarcoEnemigo(int x, int y, NaveDTO nave, boolean horizontal) {
        int tamano = nave.getTamano();
        Color colorCasilla = obtenerColor(nave.getColor());

        for (int i = 0; i < tamano; i++) {
            int xi = horizontal ? x : x + i;
            int yi = horizontal ? y + i : y;
            CasillaDTO casilla = tableroEnemigo.getCasilla(xi, yi);
            casilla.setEstado(new EstadoCasillaOcupada());  // Cambiar estado a ocupado

            nave.agregarCasilla(casilla);
            botonesEnemigo[xi][yi].setBackground(colorCasilla);
        }
    }

    // Método para atacar al enemigo
    private void atacarEnemigo(int x, int y) {
        CasillaDTO casilla = tableroEnemigo.getCasilla(x, y);
        if (casilla.estaImpactada()) {
            JOptionPane.showMessageDialog(this, "¡Ya atacaste esta casilla!");
        } else {
            casilla.setEstado(new EstadoCasillaImpactada()); // Cambiar a estado impactada
            botonesEnemigo[x][y].setBackground(Color.RED);  // Indicar que fue atacada

            // Comprobar si la casilla pertenece a una nave enemiga
            boolean impactoExitoso = false;
            for (NaveDTO nave : navesEnemigas) {
                if (nave.getPosiciones().contains(casilla)) {
                    impactoExitoso = true;
                    JOptionPane.showMessageDialog(this, "¡Impacto exitoso!");
                    nave.setHundido(true); // Marcar el barco como hundido si está completamente impactado
                    break;
                }
            }

            if (!impactoExitoso) {
                JOptionPane.showMessageDialog(this, "¡El ataque falló!");
            }
        }
    }

    private Color obtenerColor(Color color) {
        if (color.equals(Color.BLUE)) {
            return Color.BLUE;
        } else if (color.equals(Color.RED)) {
            return Color.RED;
        } else if (color.equals(Color.GREEN)) {
            return Color.GREEN;
        } else if (color.equals(Color.YELLOW)) {
            return Color.YELLOW;
        } else if (color.equals(Color.GRAY)) {
            return Color.GRAY;
        } else {
            return Color.CYAN;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaBatalla().setVisible(true));
    }
}
