/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Carlo
 */
public class VentanaGanaste extends JDialog {
        /** Etiqueta para mostrar el mensaje de victoria. */

 private JLabel jLabelMensaje;
     /** Botón para salir y cerrar la ventana. */

    private JButton jButtonSalir;

    /**
     * Constructor de la ventana de victoria.
     * 
     * @param owner   La ventana padre.
     * @param mensaje Mensaje que se mostrará en la ventana.
     */
    public VentanaGanaste(Frame owner, String mensaje) {
        super(owner, true); // true = modal

        setUndecorated(true);
        setSize(400, 200);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setBackground(new Color(0, 0, 0, 150));
        setAlwaysOnTop(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        jLabelMensaje = new JLabel(mensaje, SwingConstants.CENTER);
        jLabelMensaje.setFont(new Font("Arial", Font.BOLD, 28));
        jLabelMensaje.setForeground(Color.WHITE);
        jLabelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        jButtonSalir = new JButton("Salir");
        jButtonSalir.setFont(new Font("Arial", Font.PLAIN, 18));
        jButtonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButtonSalir.addActionListener(e -> dispose());

        panel.add(jLabelMensaje);
        panel.add(Box.createVerticalStrut(30));
        panel.add(jButtonSalir);

        setContentPane(panel);
    }
}