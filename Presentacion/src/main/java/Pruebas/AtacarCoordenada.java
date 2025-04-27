/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import EntidadesDTO.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AtacarCoordenada extends JFrame {
//
//    private TableroDTO miTablero = new TableroDTO();
//    private TableroDTO enemigoTablero = new TableroDTO();
//
//    private JButton[][] botonesMiTablero = new JButton[10][10];
//    private JButton[][] botonesTableroEnemigo = new JButton[10][10];
//
//    private boolean esHorizontal = true;
//    private boolean juegoIniciado = false;
//    private JComboBox<String> selectorBarco;
//    private JTextField coordenadasInput;
//    private JTextField coordenadasAtaqueInput;
//    private NaveDTO naveActual;
//
//    private List<NaveDTO> listaMisBarcos = new ArrayList<>();
//    private List<NaveDTO> listaBarcosEnemigos = new ArrayList<>();
//
//    public AtacarCoordenada() {
//        setTitle("Batalla Naval - Coloca y Ataca");
//        setSize(1000, 600);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panelTableros = new JPanel(new GridLayout(1, 2));
//        panelTableros.add(crearPanelTablero(botonesMiTablero, miTablero, false));
//        panelTableros.add(crearPanelTablero(botonesTableroEnemigo, enemigoTablero, true));
//
//        JPanel panelControles = new JPanel();
//
//        selectorBarco = new JComboBox<>(new String[]{"Submarino", "Crucero", "Porta Aviones", "Barco"});
//        selectorBarco.addActionListener(e -> seleccionarBarco());
//        panelControles.add(new JLabel("Barco:"));
//        panelControles.add(selectorBarco);
//
//        coordenadasInput = new JTextField(10);
//        JButton btnColocarBarco = new JButton("Colocar Barco");
//        btnColocarBarco.addActionListener(e -> colocarBarcoDesdeTexto());
//        panelControles.add(new JLabel("Coord. Barco (Ej: A1):"));
//        panelControles.add(coordenadasInput);
//        panelControles.add(btnColocarBarco);
//
//        coordenadasAtaqueInput = new JTextField(10);
//        JButton btnAtacar = new JButton("Atacar");
//        btnAtacar.addActionListener(e -> atacarManual());
//        panelControles.add(new JLabel("Coord. Ataque (Ej: A1):"));
//        panelControles.add(coordenadasAtaqueInput);
//        panelControles.add(btnAtacar);
//
//        JButton btnListo = new JButton("Listo para jugar");
//        btnListo.addActionListener(e -> {
//            juegoIniciado = true;
////            ocultarBarcosEnemigos();
//            JOptionPane.showMessageDialog(this, "¡El juego ha comenzado!");
//        });
//        panelControles.add(btnListo);
//
//        setLayout(new BorderLayout());
//        add(panelTableros, BorderLayout.CENTER);
//        add(panelControles, BorderLayout.SOUTH);
//
//        seleccionarBarco();
//    }
//
//    private JPanel crearPanelTablero(JButton[][] botones, TableroDTO tablero, boolean esEnemigo) {
//        JPanel panel = new JPanel(new GridLayout(10, 10));
//        for (int x = 0; x < 10; x++) {
//            for (int y = 0; y < 10; y++) {
//                final int fx = x, fy = y;
//                botones[x][y] = new JButton();
//                botones[x][y].setBackground(Color.LIGHT_GRAY);
//                botones[x][y].addActionListener(e -> {
//                    if (!juegoIniciado) {
//                        colocarBarco(fx, fy);
//                    } else if (esEnemigo) {
//                        atacar(fx, fy);
//                    }
//                });
//                panel.add(botones[x][y]);
//            }
//        }
//        return panel;
//    }
//
//    private void seleccionarBarco() {
//        String tipo = (String) selectorBarco.getSelectedItem();
//        switch (tipo) {
//            case "Submarino" -> naveActual = new SubmarinoDTO();
//            case "Crucero" -> naveActual = new CruceroDTO();
//            case "Porta Aviones" -> naveActual = new PortaAvionesDTO();
//            case "Barco" -> naveActual = new BarcoDTO();
//        }
//    }
//
//    private void colocarBarcoDesdeTexto() {
//        String coord = coordenadasInput.getText().trim().toUpperCase();
//        if (!esCoordenadaValida(coord)) {
//            JOptionPane.showMessageDialog(this, "Coordenada inválida.");
//            return;
//        }
//        int x = coord.charAt(0) - 'A';
//        int y = Integer.parseInt(coord.substring(1)) - 1;
//        colocarBarco(x, y);
//    }
//
//    private void colocarBarco(int finalX, int finalY) {
//        if (naveActual == null || !miTablero.getCasilla(finalX, finalY).estaDisponible()) {
//            JOptionPane.showMessageDialog(this, "No se puede colocar el barco aquí.");
//            return;
//        }
//
//        if ((esHorizontal && finalY + naveActual.getTamano() > 10) || (!esHorizontal && finalX + naveActual.getTamano() > 10)) {
//            JOptionPane.showMessageDialog(this, "El barco no cabe en el tablero.");
//            return;
//        }
//
//        List<CasillaDTO> posiciones = new ArrayList<>();
//        for (int i = 0; i < naveActual.getTamano(); i++) {
//            int x = esHorizontal ? finalX : finalX + i;
//            int y = esHorizontal ? finalY + i : finalY;
//            CasillaDTO casilla = miTablero.getCasilla(x, y);
//            if (!casilla.estaDisponible()) {
//                JOptionPane.showMessageDialog(this, "Una casilla ya está ocupada.");
//                return;
//            }
//            posiciones.add(casilla);
//        }
//
//        for (CasillaDTO casilla : posiciones) {
//            casilla.setOcupada(true);
//            casilla.setDisponible(false);
//            naveActual.agregarCasilla(casilla);
//            botonesMiTablero[casilla.getX()][casilla.getY()].setBackground(obtenerColor(naveActual.getColor()));
//        }
//
//        listaMisBarcos.add(naveActual);
//        JOptionPane.showMessageDialog(this, naveActual.getNombre() + " colocado.");
//    }
//
//    private boolean esCoordenadaValida(String coord) {
//        if (coord.length() < 2 || coord.length() > 3) return false;
//        char letra = coord.charAt(0);
//        try {
//            int num = Integer.parseInt(coord.substring(1));
//            return letra >= 'A' && letra <= 'J' && num >= 1 && num <= 10;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    private void atacarManual() {
//        String coord = coordenadasAtaqueInput.getText().trim().toUpperCase();
//        if (!esCoordenadaValida(coord)) {
//            JOptionPane.showMessageDialog(this, "Coordenada inválida.");
//            return;
//        }
//
//        int x = coord.charAt(0) - 'A';
//        int y = Integer.parseInt(coord.substring(1)) - 1;
//        atacar(x, y);
//    }
//
//    private void atacar(int x, int y) {
//        CasillaDTO casilla = enemigoTablero.getCasilla(x, y);
//        if (casilla.estaAtacada()) {
//            JOptionPane.showMessageDialog(this, "Ya atacaste esa casilla.");
//            return;
//        }
//
//        casilla.setAtacada(true);
//        if (casilla.estaOcupada()) {
//            botonesTableroEnemigo[x][y].setBackground(Color.RED);
//            JOptionPane.showMessageDialog(this, "¡Impacto!");
//        } else {
//            botonesTableroEnemigo[x][y].setBackground(Color.WHITE);
//            JOptionPane.showMessageDialog(this, "Agua...");
//        }
//
//        if (verificarHundimientoBarcosEnemigos()) {
//            JOptionPane.showMessageDialog(this, "¡Ganaste! Todos los barcos enemigos han sido hundidos.");
////            reiniciarJuego();
//        }
//    }
//
//    private boolean verificarHundimientoBarcosEnemigos() {
//        List<NaveDTO> hundidos = new ArrayList<>();
//        for (NaveDTO nave : listaBarcosEnemigos) {
//            boolean hundido = true;
//            for (CasillaDTO casilla : nave.getPosiciones()) {
//                if (!casilla.estaAtacada()) {
//                    hundido = false;
//                    break;
//                }
//            }
//            if (hundido) {
//                hundidos.add(nave);
//            }
//        }
//        listaBarcosEnemigos.removeAll(hundidos);
//        return listaBarcosEnemigos.isEmpty();
//    }
//
////    private void reiniciarJuego() {
////        listaMisBarcos.clear();
////        listaBarcosEnemigos.clear();
////        miTablero.reiniciarTablero();
////        enemigoTablero.reiniciarTablero();
////        juegoIniciado = false;
////
////        for (int i = 0; i < 10; i++) {
////            for (int j = 0; j < 10; j++) {
////                botonesMiTablero[i][j].setBackground(Color.LIGHT_GRAY);
////                botonesTableroEnemigo[i][j].setBackground(Color.LIGHT_GRAY);
////            }
////        }
////
////        JOptionPane.showMessageDialog(this, "Juego reiniciado.");
////    }
////
////    private void ocultarBarcosEnemigos() {
////        for (int i = 0; i < 10; i++) {
////            for (int j = 0; j < 10; j++) {
////                botonesTableroEnemigo[i][j].setBackground(Color.GRAY);
////            }
////        }
////    }
//
//    private Color obtenerColor(Color color) {
//        if (color.equals(Color.GREEN)) return Color.GREEN;
//        if (color.equals(Color.BLUE)) return Color.BLUE;
//        if (color.equals(Color.RED)) return Color.RED;
//        return Color.YELLOW;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AtacarCoordenada().setVisible(true));
//    }
}

