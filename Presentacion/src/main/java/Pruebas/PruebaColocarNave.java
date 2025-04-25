/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import EntidadesDTO.BarcoDTO;
import EntidadesDTO.CasillaDTO;
import EntidadesDTO.CruceroDTO;
import EntidadesDTO.NaveDTO;
import EntidadesDTO.PortaAvionesDTO;
import EntidadesDTO.SubmarinoDTO;
import EntidadesDTO.TableroDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Carlo
 */
public class PruebaColocarNave extends JFrame {

    private TableroDTO tablero;
    private JButton[][] botones;
    private NaveDTO  naveActual;
    private boolean esHorizontal = true;
    private JLabel lblOrientacion;
    private JComboBox<String> selectorBarco;
    

    public PruebaColocarNave() {
        setTitle("Colocación de Naves");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tablero = new TableroDTO();
        botones = new JButton[10][10];

        JPanel panelTablero = new JPanel(new GridLayout(10, 10));
        JPanel panelControles = new JPanel();

        // Crear botones de tablero
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
        selectorBarco = new JComboBox<>(new String[]{"Submarino", "Crucero", "Porta Aviones", "Barco"});
        selectorBarco.addActionListener(e -> seleccionarBarco());
        panelControles.add(selectorBarco);

        // Botón de orientación
        JButton btnOrientacion = new JButton("Cambiar Orientación");
        lblOrientacion = new JLabel("Orientación: Horizontal");

        btnOrientacion.addActionListener(e -> {
            esHorizontal = !esHorizontal;
            lblOrientacion.setText("Orientación: " + (esHorizontal ? "Horizontal" : "Vertical"));
        });

        panelControles.add(btnOrientacion);
        panelControles.add(lblOrientacion);

        // Layout principal
        setLayout(new BorderLayout());
        add(panelTablero, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        // Inicializa con el primer barco
        seleccionarBarco();
    }

    private void seleccionarBarco() {
        String tipo = (String) selectorBarco.getSelectedItem();
        switch (tipo) {
            case "Submarino":
                naveActual = new SubmarinoDTO();
                break;
            case "Crucero":
                naveActual = new CruceroDTO();
                break;
            case "Porta Aviones":
                naveActual = new PortaAvionesDTO();
                break;
            case "Barco":
                naveActual = new BarcoDTO();
                break;
        }
    }

   private void colocarBarco(int x, int y) {
        if (naveActual == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una nave primero.");
            return;
        }

        int tamano = naveActual.getTamano();

        if ((esHorizontal && y + tamano > 10) || (!esHorizontal && x + tamano > 10)) {
            JOptionPane.showMessageDialog(this, "No cabe en esa orientación");
            return;
        }

        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = tablero.getCasilla(xi, yi);
            if (casilla == null || !casilla.estaDisponible()) {
                JOptionPane.showMessageDialog(this, "Casillas ocupadas");
                return;
            }
        }

        naveActual.getPosiciones().clear();
        Color colorCasilla = obtenerColor(naveActual.getColor());

        for (int i = 0; i < tamano; i++) {
            int xi = esHorizontal ? x : x + i;
            int yi = esHorizontal ? y + i : y;
            CasillaDTO casilla = tablero.getCasilla(xi, yi);
            casilla.setOcupada(true);
            naveActual.agregarCasilla(casilla);
            botones[xi][yi].setBackground(colorCasilla);
        }

        JOptionPane.showMessageDialog(this, naveActual.getNombre() + " colocado");
    }
    
    
    private Color obtenerColor(Color color) {
    if (color.equals(Color.BLUE)) {
        return Color.BLUE;   // Azul
    } else if (color.equals(Color.RED)) {
        return Color.RED;    // Rojo
    } else if (color.equals(Color.GREEN)) {
        return Color.GREEN;  // Verde
    } else if (color.equals(Color.YELLOW)) {
        return Color.YELLOW; // Amarillo
    } else if (color.equals(Color.GRAY)) {
        return Color.GRAY;   // Gris
    } else {
        return Color.CYAN;
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaColocarNave().setVisible(true));
    }
}
