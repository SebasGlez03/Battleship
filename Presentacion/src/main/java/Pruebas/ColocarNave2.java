/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Pruebas;

import EntidadesDTO.CasillaDTO;
import EntidadesDTO.NaveDTO;
import EntidadesDTO.TableroDTO;
import EntidadesDTO.TipoNave;
import PatronBuilder.Director;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Carlo
 */
public class ColocarNave2 extends javax.swing.JFrame {

    
    private NaveDTO naveSeleccionada;  // Variable para almacenar el barco seleccionado
    private Director director;
    private CasillaDTO[][] tablero;  // Tablero de 10x10
    private boolean[][] casillasOcupadas;
    
    // Contadores para los barcos y portaaviones
    private int contadorBarcos = 0;
    private int contadorPortaaviones = 0;
    private static final int MAX_BARCO = 3;
    private static final int MAX_PORTAAVIONES = 2;
    

    /**
     * Creates new form ColocarNave
     */
    public ColocarNave2() {
         initComponents();
        
        this.director = new Director();
        this.tablero = new CasillaDTO[10][10];
        this.casillasOcupadas = new boolean[10][10]; 
        inicializarTablero();
        
        
        
        barco1.addActionListener(e -> seleccionarBarco(TipoNave.BARCO));
        btnPortaaviones.addActionListener(e -> seleccionarPortaaviones(TipoNave.PORTAAVIONES));

    }
    
     private void inicializarTablero() {
    // Solo inicializamos el tablero si no ha sido inicializado antes
    if (panelTablero.getComponentCount() == 0) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton casillaButton = new JButton();
                casillaButton.setPreferredSize(new Dimension(50, 50));
                casillaButton.setBackground(Color.CYAN);
                casillaButton.setOpaque(true);
                casillaButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                
                // Usar final para capturar i y j en el listener
                final int x = i;
                final int y = j;

                casillaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        casillaButtonActionPerformed(x, y);
                    }
                });
                
                panelTablero.add(casillaButton); // Agregar solo si no hay componentes
                tablero[i][j] = new CasillaDTO(i, j);
            }
        }
    }
    panelTablero.revalidate();  // Actualiza el panel para que se muestre correctamente
    panelTablero.repaint();
}
     
     private void casillaButtonActionPerformed(int x, int y) {
     // Aquí puedes definir lo que debe suceder cuando se hace clic en una casilla.
        if (naveSeleccionada != null) {
            if (naveSeleccionada.getTipo() == TipoNave.PORTAAVIONES) {
                colocarPortaaviones(x, y);
            } else {
                colocarBarco(x, y);
            }
        } else {
            System.out.println("No se ha seleccionado un barco para colocar.");
        }
    System.out.println("Casilla seleccionada en (" + x + ", " + y + ")");
}
     
     private void seleccionarBarco(TipoNave tipoSeleccionado) {
        // Verificar si el número máximo de barcos ha sido alcanzado
        if (contadorBarcos < MAX_BARCO) {
            if (naveSeleccionada == null) {
                naveSeleccionada = director.construirNave(tipoSeleccionado);
                System.out.println("Barco seleccionado: " + naveSeleccionada.getNombre());
            } else {
                System.out.println("Ya se ha seleccionado un barco.");
            }
        } else {
            System.out.println("Ya has colocado el maximo de barcos.");
        }
    }
    
     private void colocarBarco(int x, int y) {
        // Colocar un barco (para una sola casilla)
        if (!casillasOcupadas[x][y]) {
            CasillaDTO casilla = tablero[x][y];
            naveSeleccionada.agregarCasilla(casilla);
            casillasOcupadas[x][y] = true;

            JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + y);
            casillaButton.setBackground(Color.RED);

            System.out.println("Casilla agregada a la nave " + naveSeleccionada.getNombre()
                    + " en las coordenadas (" + x + ", " + y + ")");
            repaint();
        } else {
            System.out.println("La casilla ya esta ocupada.");
        }
    }
     
     private void colocarPortaaviones(int x, int y) {
        // Colocar un portaaviones (4 casillas hacia la derecha)
        if (x >= 0 && x < 10 && y >= 0 && y <= 6) { // hasta columna 6 (6 + 4 = 10)
            boolean espacioDisponible = true;

            // Verificamos que haya espacio para el portaaviones
            for (int i = 0; i < 4; i++) {
                if (casillasOcupadas[x][y + i]) {
                    espacioDisponible = false;
                    break;
                }
            }

            if (espacioDisponible) {
                for (int i = 0; i < 4; i++) {
                    CasillaDTO casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.ORANGE); // Usa otro color para diferenciar
                }

                System.out.println("Portaaviones colocado en fila " + x + " desde columna " + y + " hasta " + (y + 3));
                repaint();
            } else {
                System.out.println("No hay espacio suficiente o casillas ocupadas para el portaaviones.");
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el portaaviones.");
        }
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelTablero = new javax.swing.JPanel();
        panelBarcos = new javax.swing.JPanel();
        contenedorBarco1 = new javax.swing.JPanel();
        barco1 = new javax.swing.JButton();
        btnPortaaviones = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        panelTablero.setPreferredSize(new java.awt.Dimension(500, 500));
        panelTablero.setLayout(new java.awt.GridLayout(10, 10));

        panelBarcos.setPreferredSize(new java.awt.Dimension(460, 285));

        contenedorBarco1.setPreferredSize(new java.awt.Dimension(50, 50));

        barco1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        barco1.setText("Barco");
        barco1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        barco1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        barco1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        barco1.setPreferredSize(new java.awt.Dimension(50, 50));
        barco1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco1MouseDragged(evt);
            }
        });
        barco1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barco1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                barco1MouseReleased(evt);
            }
        });

        btnPortaaviones.setText("PA");
        btnPortaaviones.setPreferredSize(new java.awt.Dimension(200, 50));
        btnPortaaviones.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnPortaavionesMouseDragged(evt);
            }
        });
        btnPortaaviones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPortaavionesMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnPortaavionesMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout panelBarcosLayout = new javax.swing.GroupLayout(panelBarcos);
        panelBarcos.setLayout(panelBarcosLayout);
        panelBarcosLayout.setHorizontalGroup(
            panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarcosLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contenedorBarco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBarcosLayout.createSequentialGroup()
                        .addComponent(barco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnPortaaviones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        panelBarcosLayout.setVerticalGroup(
            panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarcosLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPortaaviones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(163, 163, 163)
                .addComponent(contenedorBarco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contenedorBarco1.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBarcos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(panelBarcos, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                .addContainerGap(126, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void barco1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MousePressed
       seleccionarBarco(TipoNave.BARCO);// Asigna el barco seleccionado a naveSeleccionada
    
    }//GEN-LAST:event_barco1MousePressed

    
//    private void seleccionarBarco(TipoNave tipoSeleccionado) {
//        // Aquí seleccionas el barco cuando se hace clic en él
//        if (naveSeleccionada == null) {
//            // Lógica para seleccionar el barco (ejemplo con el Barco 1)
//            naveSeleccionada = director.construirNave(tipoSeleccionado);
//            System.out.println("Barco seleccionado: " + naveSeleccionada.getNombre());
//        }
//    }
    
    
    
    private void barco1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MouseDragged
       
    }//GEN-LAST:event_barco1MouseDragged

    
    
    private void barco1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MouseReleased
     if (naveSeleccionada != null) {
            Point puntoEnTablero = SwingUtilities.convertPoint(barco1, evt.getPoint(), panelTablero);

            int x = puntoEnTablero.y / 50; // fila
            int y = puntoEnTablero.x / 50; // columna

            if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                if (casillasOcupadas[x][y]) {
                    System.out.println("La casilla ya esta ocupada.");
                } else {
                    CasillaDTO casilla = tablero[x][y];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + y);
                    casillaButton.setBackground(Color.RED);

                    System.out.println("Casilla agregada a la nave " + naveSeleccionada.getNombre()
                            + " en las coordenadas (" + x + ", " + y + ")");
                    repaint();
                    
                    // Incrementar contador de barcos
                    if (naveSeleccionada.getTipo() == TipoNave.BARCO) {
                        contadorBarcos++;
                    }
                    naveSeleccionada = null; // Reiniciar la selección del barco
                }
            } else {
                System.out.println("Las coordenadas están fuera del tablero.");
            }
        } else {
            System.out.println("No se ha seleccionado un barco para colocar.");
        }
    }//GEN-LAST:event_barco1MouseReleased

    private void btnPortaavionesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaavionesMouseReleased
        if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.PORTAAVIONES) {
            Point puntoEnTablero = SwingUtilities.convertPoint(btnPortaaviones, evt.getPoint(), panelTablero);
            int x = puntoEnTablero.y / 50;
            int y = puntoEnTablero.x / 50;

            // Verificamos que haya espacio para 4 casillas hacia la derecha
            if (x >= 0 && x < 10 && y >= 0 && y <= 6) { // hasta columna 6 (6 + 4 = 10)
                boolean espacioDisponible = true;

                for (int i = 0; i < 4; i++) {
                    if (casillasOcupadas[x][y + i]) {
                        espacioDisponible = false;
                        break;
                    }
                }

                if (espacioDisponible) {
                    for (int i = 0; i < 4; i++) {
                        CasillaDTO casilla = tablero[x][y + i];
                        naveSeleccionada.agregarCasilla(casilla);
                        casillasOcupadas[x][y + i] = true;

                        JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                        casillaButton.setBackground(Color.ORANGE);
                    }

                    System.out.println("Portaaviones colocado en fila " + x + " desde columna " + y + " hasta " + (y + 3));
                    repaint();
                    
                    // Incrementar contador de portaaviones
                    if (naveSeleccionada.getTipo() == TipoNave.PORTAAVIONES) {
                        contadorPortaaviones++;
                    }
                    naveSeleccionada = null; // Reiniciar la selección del barco
                } else {
                    System.out.println("No hay espacio suficiente o casillas ocupadas para el portaaviones.");
                }
            } else {
                System.out.println("Las coordenadas estan fuera del rango para colocar el portaaviones.");
            }
        } else {
            System.out.println("No se ha seleccionado el portaaviones para colocar.");
        }
    }//GEN-LAST:event_btnPortaavionesMouseReleased

    private void btnPortaavionesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaavionesMousePressed
        seleccionarPortaaviones(TipoNave.PORTAAVIONES);
    }//GEN-LAST:event_btnPortaavionesMousePressed

    private void seleccionarPortaaviones(TipoNave tipoSeleccionado) {
       // Verificar si el número máximo de portaaviones ha sido alcanzado
        if (contadorPortaaviones < MAX_PORTAAVIONES) {
            if (naveSeleccionada == null) {
                naveSeleccionada = director.construirNave(tipoSeleccionado);
                System.out.println("Portaaviones seleccionado: " + naveSeleccionada.getNombre());
            } else {
                System.out.println("Ya se ha seleccionado un portaaviones.");
            }
        } else {
            System.out.println("Ya has colocado el maximo de portaaviones.");
        }
    }
    
    
    private void btnPortaavionesMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaavionesMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPortaavionesMouseDragged

    private int obtenerCoordenadaX(MouseEvent evt) {
        // Ejemplo para obtener  la coordenada X basada en el evento
        return (evt.getX() + 10) / 50;  // Ejemplo de cálculo, ajusta según el tamaño de las casillas
    }

    private int obtenerCoordenadaY(MouseEvent evt) {
        // Ejemplo para obtener la coordenada Y basada en el evento
        return (evt.getY() + 10) / 50;  // Ejemplo de cálculo, ajusta según el tamaño de las casillas
    }
    
    
    
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ColocarNave2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barco1;
    private javax.swing.JButton btnPortaaviones;
    private javax.swing.JPanel contenedorBarco1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelBarcos;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables
}
