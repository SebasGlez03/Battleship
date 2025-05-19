/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Carlo
 */
public class VentanaGanaste extends JFrame {

    private JLabel jLabelMensaje;
    private JButton jButtonSalir;

    public VentanaGanaste(String mensaje) {
        // Configura la ventana
        setUndecorated(true); // Sin bordes ni barra de título
        setSize(400, 200);
        setLocationRelativeTo(null); // Centrada
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fondo transparente
        setBackground(new Color(0, 0, 0, 150)); // Negro con transparencia

        // Panel de contenido personalizado
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Fondo con transparencia
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 200)); // Fondo más opaco
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Label de mensaje
        jLabelMensaje = new JLabel(mensaje, SwingConstants.CENTER);
        jLabelMensaje.setFont(new Font("Arial", Font.BOLD, 28));
        jLabelMensaje.setForeground(Color.WHITE);
        jLabelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón de salida
        jButtonSalir = new JButton("Salir");
        jButtonSalir.setFont(new Font("Arial", Font.PLAIN, 18));
        jButtonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButtonSalir.addActionListener(e -> dispose()); // o System.exit(0);

        // Espaciado
        panel.add(jLabelMensaje);
        panel.add(Box.createVerticalStrut(30));
        panel.add(jButtonSalir);

        // Contenedor
        setContentPane(panel);
    }

    // Para probar directamente
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            VentanaGanaste ventana = new VentanaGanaste("¡Ganaste!");
//            ventana.setVisible(true);
//        });
//    }
}