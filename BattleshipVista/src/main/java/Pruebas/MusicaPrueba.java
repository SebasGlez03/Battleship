/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Pruebas;

import Musica.MusicaControlador;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Oley
 */
public class MusicaPrueba extends javax.swing.JFrame {
  
    private JSlider sliderVolumen;
    private JButton btnAceptar;
    private JButton btnBack;
    private JLabel lblTitulo;
    private MusicaControlador controlador;

    /**
     * Creates new form Musica
     */
    public MusicaPrueba() {
        controlador = new MusicaControlador(); 
    initMyComponents(); // en lugar de initComponents()

    }
   private void initMyComponents() {
        lblTitulo = new JLabel("Settings");
        sliderVolumen = new JSlider();
        btnBack = new JButton("Back");
        btnAceptar = new JButton("Accept");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configuración de Volumen");

        sliderVolumen.setMinimum(0);
        sliderVolumen.setMaximum(100);
        sliderVolumen.setValue(50);
        sliderVolumen.setMajorTickSpacing(20);
        sliderVolumen.setPaintTicks(true);
        sliderVolumen.setPaintLabels(true);

        btnBack.setBackground(new Color(255, 0, 0));
        btnAceptar.setBackground(new Color(0, 255, 102));

        btnAceptar.addActionListener(e -> {
            float volumen = sliderVolumen.getValue() / 100f;
            controlador.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SELECCIONAR"));
            controlador.ajustarVolumen(volumen); // ✅ Ahora se pasa el volumen real
            controlador.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "REPRODUCIR"));  
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(btnBack)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                    .addComponent(btnAceptar)
                    .addGap(38, 38, 38))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(90, 90, 90)
                            .addComponent(sliderVolumen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(167, 167, 167)
                            .addComponent(lblTitulo)))
                    .addContainerGap(110, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(lblTitulo)
                    .addGap(35, 35, 35)
                    .addComponent(sliderVolumen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAceptar)
                        .addComponent(btnBack))
                    .addGap(30, 30, 30))
        );

        pack();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicaPrueba().setVisible(true);
            }
        });
    }
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

