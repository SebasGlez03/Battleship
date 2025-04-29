/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import PatronBuilder.Director;
import EntidadesDTO.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Carlo
 */
public class PruebaColocarNave extends JFrame {

    private TableroDTO tablero;
    private JButton[][] botones;
    private NaveDTO naveActual;
    private boolean esHorizontal = true;
    private JLabel lblOrientacion;
    private JComboBox<String> selectorBarco;

    // Contadores de naves colocadas
    private int contadorPortaAviones = 0;
    private int contadorCruceros = 0;
    private int contadorSubmarinos = 0;
    private int contadorBarcos = 0;

    // Límites máximos de naves
    private final int MAX_PORTA_AVIONES = 2;
    private final int MAX_CRUCEROS = 2;
    private final int MAX_SUBMARINOS = 4;
    private final int MAX_BARCOS = 3;

    public PruebaColocarNave() {
        setTitle("Colocacion de Naves");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tablero = new TableroDTO();
        botones = new JButton[10][10];

        JPanel panelTablero = new JPanel(new GridLayout(10, 10));
        JPanel panelControles = new JPanel();

        // Crear botones del tablero
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final int x = i;
                final int y = j;
                botones[i][j] = new JButton();
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].addActionListener(e -> colocarBarco(x, y));
                panelTablero.add(botones[i][j]);
            }
        }

        // Selector de tipo de nave
        selectorBarco = new JComboBox<>(new String[]{"Barco", "Submarino", "Crucero", "Porta Aviones"});
        selectorBarco.addActionListener(e -> seleccionarBarco());
        panelControles.add(selectorBarco);

        // Botón de orientación
        JButton btnOrientacion = new JButton("Cambiar Orientacion");
        lblOrientacion = new JLabel("Orientacion: Horizontal");

        btnOrientacion.addActionListener(e -> {
            esHorizontal = !esHorizontal;
            lblOrientacion.setText("Orientacion: " + (esHorizontal ? "Horizontal" : "Vertical"));
            System.out.println("[Orientacion] Cambiada a: " + (esHorizontal ? "Horizontal" : "Vertical"));
        });

        panelControles.add(btnOrientacion);
        panelControles.add(lblOrientacion);

        setLayout(new BorderLayout());
        add(panelTablero, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        seleccionarBarco(); // Inicializa con la primera nave
    }

    private void seleccionarBarco() {
    String tipo = (String) selectorBarco.getSelectedItem();
    Director director = new Director();

    switch (tipo) {
        case "Submarino":
            if (contadorSubmarinos >= MAX_SUBMARINOS) {
                JOptionPane.showMessageDialog(this, "Ya has colocado todos los Submarinos disponibles.");
                return;
            }
            naveActual = director.construirNave(TipoNave.SUBMARINO);
            break;
        case "Crucero":
            if (contadorCruceros >= MAX_CRUCEROS) {
                JOptionPane.showMessageDialog(this, "Ya has colocado todos los Cruceros disponibles.");
                return;
            }
            naveActual = director.construirNave(TipoNave.CRUCERO);
            break;
        case "Porta Aviones":
            if (contadorPortaAviones >= MAX_PORTA_AVIONES) {
                JOptionPane.showMessageDialog(this, "Ya has colocado todos los Porta Aviones disponibles.");
                return;
            }
            naveActual = director.construirNave(TipoNave.PORTAAVIONES);
            break;
        case "Barco":
            if (contadorBarcos >= MAX_BARCOS) {
                JOptionPane.showMessageDialog(this, "Ya has colocado todos los Barcos disponibles.");
                return;
            }
            naveActual = director.construirNave(TipoNave.BARCO);
            break;
        default:
            JOptionPane.showMessageDialog(this, "Tipo de nave desconocido.");
            return;
    }

    System.out.println("[Seleccion] Nave seleccionada: " + naveActual.getNombre() + ", Tamano: " + naveActual.getTamano());
}

    private void colocarBarco(int x, int y) {
        if (naveActual == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una nave primero.");
            return;
        }

        // Validación de límites de naves
        String tipo = naveActual.getNombre();
        if ((tipo.equals("Porta Aviones") && contadorPortaAviones >= MAX_PORTA_AVIONES) ||
            (tipo.equals("Crucero") && contadorCruceros >= MAX_CRUCEROS) ||
            (tipo.equals("Submarino") && contadorSubmarinos >= MAX_SUBMARINOS) ||
            (tipo.equals("Barco") && contadorBarcos >= MAX_BARCOS)) {

            JOptionPane.showMessageDialog(this, "Ya has colocado todos los " + tipo + " disponibles.");
            System.out.println("[Limite] No puedes colocar mas " + tipo + ".");
            return;
        }

        int tamano = naveActual.getTamano();
        System.out.println("[Colocar] Intentando colocar " + tipo + " en (" + x + ", " + y + ") con orientacion " + (esHorizontal ? "Horizontal" : "Vertical"));

        if ((esHorizontal && y + tamano > 10) || (!esHorizontal && x + tamano > 10)) {
            JOptionPane.showMessageDialog(this, "No cabe en esa orientacion");
            System.out.println("[Error] No cabe en esa orientacion.");
            return;
        }

        // Verificar disponibilidad de casillas
        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = tablero.getCasilla(xi, yi);
            if (casilla == null || !casilla.estaDisponible()) {
                JOptionPane.showMessageDialog(this, "Casillas ocupadas");
                System.out.println("[Error] Casilla ocupada en (" + xi + ", " + yi + ")");
                return;
            }
        }

        // Colocar la nave
        naveActual.getPosiciones().clear();
        Color colorCasilla = obtenerColor(naveActual.getColor());

        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = tablero.getCasilla(xi, yi);
            casilla.cambiarEstado();
            naveActual.agregarCasilla(casilla);
            botones[xi][yi].setBackground(colorCasilla);
        }

        // Actualizar contadores
        switch (tipo) {
            case "Porta Aviones": contadorPortaAviones++; break;
            case "Crucero":       contadorCruceros++;     break;
            case "Submarino":     contadorSubmarinos++;   break;
            case "Barco":         contadorBarcos++;       break;
        }

        JOptionPane.showMessageDialog(this, tipo + " colocado");
        System.out.println("[Colocado exitoso] " + tipo + " colocado.\n");
    }

    private Color obtenerColor(Color color) {
        return switch (color.getRGB()) {
            case -16776961 -> Color.BLUE;
            case -65536    -> Color.RED;
            case -16711936 -> Color.GREEN;
            case -256      -> Color.YELLOW;
            case -8355712  -> Color.GRAY;
            default        -> Color.CYAN;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaColocarNave().setVisible(true));
    }
}

