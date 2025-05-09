/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Carlo
 */
public class MostrarTableros extends JPanel  {
    private boolean[][] tablero1;
    private boolean[][] tablero2;

    private JPanel panelTablero1;
    private JPanel panelTablero2;

    public MostrarTableros(boolean[][] tablero1, boolean[][] tablero2) {
        this.tablero1 = tablero1;
        this.tablero2 = tablero2;

        setLayout(new GridLayout(1, 2));  // Dos paneles, uno al lado del otro
        panelTablero1 = new JPanel();
        panelTablero2 = new JPanel();

        panelTablero1.setLayout(new GridLayout(10, 10));  // Tablero de 10x10
        panelTablero2.setLayout(new GridLayout(10, 10));

        inicializarTablero(panelTablero1, tablero1);
        inicializarTablero(panelTablero2, tablero2);

        add(panelTablero1);
        add(panelTablero2);
    }

    private void inicializarTablero(JPanel panel, boolean[][] tablero) {
        panel.removeAll();  // Limpiar el panel antes de agregar los botones

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton casillaButton = new JButton();
                casillaButton.setPreferredSize(new Dimension(50, 50));
                casillaButton.setBackground(tablero[i][j] ? Color.RED : Color.LIGHT_GRAY);  // Si está ocupada, color rojo

                casillaButton.setContentAreaFilled(false); // No decoración del SO
                casillaButton.setFocusPainted(false);       // Sin contorno de foco
                casillaButton.setMargin(new Insets(0, 0, 0, 0)); // Sin márgenes
                casillaButton.setOpaque(true);               // Necesario para el fondo
                casillaButton.setBorder(BorderFactory.createLineBorder(Color.black, 1, false)); // Bordes rectos

                // Añadir el botón al panel
                panel.add(casillaButton);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}

