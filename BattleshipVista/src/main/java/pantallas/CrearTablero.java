/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas;

import Entidades.Casilla;
import Entidades.TipoNave;
import Entidades.Nave;
import PatronBuilder.Director;
import Sockets.*;
import com.google.gson.Gson;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Carlo
 */
public class CrearTablero extends javax.swing.JFrame {

    
    
    private SocketCliente socketCliente; 
    private static final int TOTAL_CASILLAS_ESPERADAS = 25;
    private Nave naveSeleccionada;
    private Director director;
    private Casilla[][] tablero; 
    private boolean[][] casillasOcupadas;
    private JLabel imagenArrastrada;
    private ImageIcon imagenNaveOriginal;

    // Contadores para los barcos y portaaviones
    private int contadorBarcos = 0;
    private int contadorPortaaviones = 0;
    private int contadorCruceros = 0;
    private int contadorSubmarinos = 0;
    private static final int MAX_BARCO = 3;
    private static final int MAX_PORTAAVIONES = 2;
    private static final int MAX_CRUCEROS = 2;
    private static final int MAX_SUBMARINOS = 4;

    private String nombreJugador;

    /**
     * Creates new form ColocarNave
     */
    public CrearTablero(SocketServidor servidor) {
        this.nombreJugador = nombreJugador;

        initComponents();
        initComponents2();
    }

    public CrearTablero(String nombreJugador, SocketCliente cliente) {
        this.nombreJugador = nombreJugador;
        this.socketCliente = cliente;
        initComponents();
        initComponents2();
    }

    private void initComponents2() {
        labelNomJugador.setText("Jugador: " + nombreJugador);

        // Crear imagen de fondo
        ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/imagenes/fondoTablero.jpg"));
        JLabel labelFondo = new JLabel(fondoIcon);
        labelFondo.setBounds(0, 0, panelFondoTablero.getWidth(), panelFondoTablero.getHeight());

// Establecer layout nulo para permitir posición absoluta
        panelTablero.setLayout(new GridLayout(10, 10));

// Agregar el fondo como el último componente (al fondo)
        panelFondoTablero.add(labelFondo);
        panelFondoTablero.setComponentZOrder(labelFondo, panelFondoTablero.getComponentCount() - 1);

        this.director = new Director();
        this.tablero = new Casilla[10][10];
        this.casillasOcupadas = new boolean[10][10];
        inicializarTablero();

//        panelfondo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
        //BARCO 1
        barco1.setPreferredSize(new Dimension(30, 30));
        barco1.setBackground(Color.LIGHT_GRAY);
        barco1.setOpaque(true);               // Necesario para el fondo
        barco1.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));

        ImageIcon icono2 = new ImageIcon(getClass().getResource("/imagenes/casillaimg2.png"));
        Image imagenEscalada2 = icono2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        barco1.setIcon(new ImageIcon(imagenEscalada2));

        //BARCO 2
        barco2.setPreferredSize(new Dimension(30, 30));
        barco2.setBackground(Color.LIGHT_GRAY);
        barco2.setOpaque(true);               // Necesario para el fondo
        barco2.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));

        ImageIcon icono3 = new ImageIcon(getClass().getResource("/imagenes/casillaimg2.png"));
        Image imagenEscalada3 = icono3.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        barco2.setIcon(new ImageIcon(imagenEscalada3));

        //BARCO 3
        barco3.setPreferredSize(new Dimension(30, 30));
        barco3.setBackground(Color.LIGHT_GRAY);
        barco3.setOpaque(true);               // Necesario para el fondo
        barco3.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));

        ImageIcon icono4 = new ImageIcon(getClass().getResource("/imagenes/casillaimg2.png"));
        Image imagenEscalada4 = icono4.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        barco3.setIcon(new ImageIcon(imagenEscalada4));

        barco1.addActionListener(e -> seleccionarBarco(TipoNave.BARCO));
        barco2.addActionListener(e -> seleccionarBarco(TipoNave.BARCO));
        barco3.addActionListener(e -> seleccionarBarco(TipoNave.BARCO));

        //SUBMARINO 1
        btnSubmarino1.setPreferredSize(new Dimension(40, 75));
        btnSubmarino1.setBackground(Color.LIGHT_GRAY);
        btnSubmarino1.setOpaque(true);               // Necesario para el fondo
        btnSubmarino1.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono5 = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));
        Image imagenEscalada5 = icono5.getImage().getScaledInstance(40, 75, Image.SCALE_SMOOTH);
        btnSubmarino1.setIcon(new ImageIcon(imagenEscalada5));

        //SUBMARINO 2
        btnSubmarino2.setPreferredSize(new Dimension(40, 75));
        btnSubmarino2.setBackground(Color.LIGHT_GRAY);
        btnSubmarino2.setOpaque(true);               // Necesario para el fondo
        btnSubmarino2.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono6 = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));
        Image imagenEscalada6 = icono6.getImage().getScaledInstance(40, 75, Image.SCALE_SMOOTH);
        btnSubmarino2.setIcon(new ImageIcon(imagenEscalada6));

        //SUBMARINO 3
        btnSubmarino3.setPreferredSize(new Dimension(40, 75));
        btnSubmarino3.setBackground(Color.LIGHT_GRAY);
        btnSubmarino3.setOpaque(true);               // Necesario para el fondo
        btnSubmarino3.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono7 = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));
        Image imagenEscalada7 = icono7.getImage().getScaledInstance(40, 75, Image.SCALE_SMOOTH);
        btnSubmarino3.setIcon(new ImageIcon(imagenEscalada7));

        //SUBMARINO 4
        btnSubmarino4.setPreferredSize(new Dimension(40, 75));
        btnSubmarino4.setBackground(Color.LIGHT_GRAY);
        btnSubmarino4.setOpaque(true);               // Necesario para el fondo
        btnSubmarino4.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono8 = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));
        Image imagenEscalada8 = icono8.getImage().getScaledInstance(40, 75, Image.SCALE_SMOOTH);
        btnSubmarino4.setIcon(new ImageIcon(imagenEscalada8));

        btnSubmarino1.addActionListener(e -> seleccionarSubmarinos(TipoNave.SUBMARINO));
        btnSubmarino2.addActionListener(e -> seleccionarSubmarinos(TipoNave.SUBMARINO));
        btnSubmarino3.addActionListener(e -> seleccionarSubmarinos(TipoNave.SUBMARINO));
        btnSubmarino4.addActionListener(e -> seleccionarSubmarinos(TipoNave.SUBMARINO));

        //CRUCERO 1
        btnCrucero1.setPreferredSize(new Dimension(40, 100));
        btnCrucero1.setBackground(Color.LIGHT_GRAY);
        btnCrucero1.setOpaque(true);               // Necesario para el fondo
        btnCrucero1.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono9 = new ImageIcon(getClass().getResource("/imagenes/casilla3img2.png"));
        Image imagenEscalada9 = icono9.getImage().getScaledInstance(40, 100, Image.SCALE_SMOOTH);
        btnCrucero1.setIcon(new ImageIcon(imagenEscalada9));

        //CRUCERO 2
        btnCrucero2.setPreferredSize(new Dimension(40, 100));
        btnCrucero2.setBackground(Color.LIGHT_GRAY);
        btnCrucero2.setOpaque(true);               // Necesario para el fondo
        btnCrucero2.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono10 = new ImageIcon(getClass().getResource("/imagenes/casilla3img2.png"));
        Image imagenEscalada10 = icono10.getImage().getScaledInstance(40, 100, Image.SCALE_SMOOTH);
        btnCrucero2.setIcon(new ImageIcon(imagenEscalada10));

        btnCrucero1.addActionListener(e -> seleccionarCruceros(TipoNave.CRUCERO));
        btnCrucero2.addActionListener(e -> seleccionarCruceros(TipoNave.CRUCERO));

        //PORTAAVIONES 1
        btnPortaaviones.setPreferredSize(new Dimension(150, 40));
        btnPortaaviones.setBackground(Color.LIGHT_GRAY);
        btnPortaaviones.setOpaque(true);               // Necesario para el fondo
        btnPortaaviones.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono11 = new ImageIcon(getClass().getResource("/imagenes/casilla4img2.png"));
        Image imagenEscalada11 = icono11.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        btnPortaaviones.setIcon(new ImageIcon(imagenEscalada11));

        //PORTAAVIONES 2
        btnPortaaviones2.setPreferredSize(new Dimension(150, 40));
        btnPortaaviones2.setBackground(Color.LIGHT_GRAY);
        btnPortaaviones2.setOpaque(true);               // Necesario para el fondo
        btnPortaaviones2.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
        ImageIcon icono12 = new ImageIcon(getClass().getResource("/imagenes/casilla4img2.png"));
        Image imagenEscalada12 = icono12.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        btnPortaaviones2.setIcon(new ImageIcon(imagenEscalada12));

        btnPortaaviones.addActionListener(e -> seleccionarPortaaviones(TipoNave.PORTAAVIONES));
        btnPortaaviones2.addActionListener(e -> seleccionarPortaaviones(TipoNave.PORTAAVIONES));

    }

    private void inicializarTablero() {
        // Solo inicializamos el tablero si no ha sido inicializado antes
        if (panelTablero.getComponentCount() == 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    JButton casillaButton = new JButton();
                    casillaButton.setPreferredSize(new Dimension(40, 40));
                    casillaButton.setBackground(Color.LIGHT_GRAY);
                    casillaButton.setContentAreaFilled(false); // No decoración del SO
                    casillaButton.setFocusPainted(false);       // Sin contorno de foco
                    casillaButton.setMargin(new Insets(0, 0, 0, 0)); // Sin márgenes
                    casillaButton.setOpaque(true);               // Necesario para el fondo
                    casillaButton.setBorder(BorderFactory.createLineBorder(Color.black, 1, false)); // Bordes rectos

//                    // Imagen de fondo
                    ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/casillaimg2.png"));
                    Image imagenEscalada = icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    casillaButton.setIcon(new ImageIcon(imagenEscalada));

                    panelTablero.add(casillaButton);
                    tablero[i][j] = new Casilla(i, j);
                }
            }
        }
        panelTablero.revalidate();
        panelTablero.repaint();
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

    private void seleccionarCruceros(TipoNave tipoSeleccionado) {
        // Verificar si el número máximo de portaaviones ha sido alcanzado
        if (contadorCruceros < MAX_CRUCEROS) {
            if (naveSeleccionada == null) {
                naveSeleccionada = director.construirNave(tipoSeleccionado);
                System.out.println("Crucero seleccionado: " + naveSeleccionada.getNombre());
            } else {
                System.out.println("Ya se ha seleccionado un crucero.");
            }
        } else {
            System.out.println("Ya has colocado el maximo de crucero.");
        }
    }

    private void seleccionarSubmarinos(TipoNave tipoSeleccionado) {
        // Verificar si el número máximo de portaaviones ha sido alcanzado
        if (contadorSubmarinos < MAX_SUBMARINOS) {
            if (naveSeleccionada == null) {
                naveSeleccionada = director.construirNave(tipoSeleccionado);
                System.out.println("Submarino seleccionado: " + naveSeleccionada.getNombre());
            } else {
                System.out.println("Ya se ha seleccionado un Submarino.");
            }
        } else {
            System.out.println("Ya has colocado el maximo de Submarino.");
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

        panelFondoTablero = new javax.swing.JPanel();
        panelBarcos = new javax.swing.JPanel();
        barco1 = new javax.swing.JButton();
        btnPortaaviones = new javax.swing.JButton();
        btnPortaaviones2 = new javax.swing.JButton();
        barco2 = new javax.swing.JButton();
        barco3 = new javax.swing.JButton();
        btnCrucero2 = new javax.swing.JButton();
        btnCrucero1 = new javax.swing.JButton();
        btnSubmarino1 = new javax.swing.JButton();
        btnSubmarino2 = new javax.swing.JButton();
        btnSubmarino3 = new javax.swing.JButton();
        btnSubmarino4 = new javax.swing.JButton();
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
        btnReiniciarTablero = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        btnReady = new javax.swing.JButton();
        labelNomJugador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelFondoTablero.setBackground(new java.awt.Color(204, 204, 204));
        panelFondoTablero.setForeground(new java.awt.Color(255, 255, 255));
        panelFondoTablero.setPreferredSize(new java.awt.Dimension(25, 45));

        panelBarcos.setBackground(new java.awt.Color(153, 153, 153));
        panelBarcos.setPreferredSize(new java.awt.Dimension(455, 280));

        barco1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        barco1.setBorder(null);
        barco1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        barco1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        barco1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        barco1.setPreferredSize(new java.awt.Dimension(40, 40));
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

        btnPortaaviones.setPreferredSize(new java.awt.Dimension(150, 40));
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

        btnPortaaviones2.setPreferredSize(new java.awt.Dimension(150, 40));
        btnPortaaviones2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnPortaaviones2MouseDragged(evt);
            }
        });
        btnPortaaviones2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPortaaviones2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnPortaaviones2MouseReleased(evt);
            }
        });

        barco2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        barco2.setBorder(null);
        barco2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        barco2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        barco2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        barco2.setPreferredSize(new java.awt.Dimension(40, 40));
        barco2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco2MouseDragged(evt);
            }
        });
        barco2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barco2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                barco2MouseReleased(evt);
            }
        });

        barco3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        barco3.setBorder(null);
        barco3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        barco3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        barco3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        barco3.setPreferredSize(new java.awt.Dimension(40, 40));
        barco3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco3MouseDragged(evt);
            }
        });
        barco3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barco3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                barco3MouseReleased(evt);
            }
        });

        btnCrucero2.setPreferredSize(new java.awt.Dimension(40, 100));
        btnCrucero2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnCrucero2MouseDragged(evt);
            }
        });
        btnCrucero2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCrucero2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnCrucero2MouseReleased(evt);
            }
        });

        btnCrucero1.setPreferredSize(new java.awt.Dimension(40, 100));
        btnCrucero1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnCrucero1MouseDragged(evt);
            }
        });
        btnCrucero1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCrucero1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnCrucero1MouseReleased(evt);
            }
        });
        btnCrucero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrucero1ActionPerformed(evt);
            }
        });

        btnSubmarino1.setPreferredSize(new java.awt.Dimension(40, 75));
        btnSubmarino1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnSubmarino1MouseDragged(evt);
            }
        });
        btnSubmarino1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSubmarino1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSubmarino1MouseReleased(evt);
            }
        });

        btnSubmarino2.setPreferredSize(new java.awt.Dimension(40, 75));
        btnSubmarino2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnSubmarino2MouseDragged(evt);
            }
        });
        btnSubmarino2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSubmarino2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSubmarino2MouseReleased(evt);
            }
        });

        btnSubmarino3.setPreferredSize(new java.awt.Dimension(40, 75));
        btnSubmarino3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnSubmarino3MouseDragged(evt);
            }
        });
        btnSubmarino3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSubmarino3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSubmarino3MouseReleased(evt);
            }
        });

        btnSubmarino4.setPreferredSize(new java.awt.Dimension(40, 75));
        btnSubmarino4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnSubmarino4MouseDragged(evt);
            }
        });
        btnSubmarino4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSubmarino4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSubmarino4MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout panelBarcosLayout = new javax.swing.GroupLayout(panelBarcos);
        panelBarcos.setLayout(panelBarcosLayout);
        panelBarcosLayout.setHorizontalGroup(
            panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarcosLayout.createSequentialGroup()
                .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBarcosLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnSubmarino1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmarino2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmarino3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmarino4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBarcosLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBarcosLayout.createSequentialGroup()
                                .addComponent(barco2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(barco3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(barco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnPortaaviones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPortaaviones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelBarcosLayout.createSequentialGroup()
                                    .addComponent(btnCrucero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCrucero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panelBarcosLayout.setVerticalGroup(
            panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarcosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnPortaaviones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPortaaviones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCrucero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrucero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barco2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barco1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barco3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBarcosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSubmarino1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmarino3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmarino4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmarino2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

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
        jLabel1.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("  1");
        jLabel2.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("  2");
        jLabel3.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("  3");
        jLabel4.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("  4");
        jLabel5.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("  5");
        jLabel6.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("  6");
        jLabel7.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("  7");
        jLabel8.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("  8");
        jLabel9.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("  9");
        jLabel10.setPreferredSize(new java.awt.Dimension(25, 35));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("    0");
        jLabel11.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("    1");
        jLabel12.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel13.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("    2");
        jLabel13.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel14.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("    3");
        jLabel14.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel15.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("    4");
        jLabel15.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel16.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("    5");
        jLabel16.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel17.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("   6");
        jLabel17.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel18.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("   7");
        jLabel18.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel19.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("   8");
        jLabel19.setPreferredSize(new java.awt.Dimension(35, 25));

        jLabel20.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("   9");
        jLabel20.setPreferredSize(new java.awt.Dimension(35, 25));

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
                    .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
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
                    .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(496, 496, 496))
        );

        btnReiniciarTablero.setText("Reiniciar Tablero");
        btnReiniciarTablero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarTableroActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("¡Prepare your ships!");

        btnReady.setBackground(new java.awt.Color(255, 255, 0));
        btnReady.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReady.setForeground(new java.awt.Color(0, 0, 0));
        btnReady.setText("¡READY!");
        btnReady.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReady.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReady.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadyActionPerformed(evt);
            }
        });

        labelNomJugador.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelNomJugador.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelFondoTableroLayout = new javax.swing.GroupLayout(panelFondoTablero);
        panelFondoTablero.setLayout(panelFondoTableroLayout);
        panelFondoTableroLayout.setHorizontalGroup(
            panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoTableroLayout.createSequentialGroup()
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(panelBarcos, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(btnReiniciarTablero)))
                .addGap(10, 10, 10)
                .addComponent(panelfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnReady, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel21)))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoTableroLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labelNomJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(338, 338, 338))
        );
        panelFondoTableroLayout.setVerticalGroup(
            panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoTableroLayout.createSequentialGroup()
                .addGroup(panelFondoTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelNomJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReady, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoTableroLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(panelBarcos, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnReiniciarTablero)))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondoTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondoTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
/**
     * Acción que se ejecuta al presionar el botón "Ready". Verifica que el
     * tablero esté correctamente configurado, envía un mensaje "READY" al
     * servidor, espera permiso para enviar el tablero, envía las coordenadas
     * ocupadas en formato JSON, y gestiona la confirmación y el inicio del
     * juego.
     *
     * @param evt Evento generado por la acción del botón.
     */
    private void btnReadyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadyActionPerformed
        if (socketCliente == null) {
            socketCliente = new SocketCliente();  // Inicializar si aún no lo está
        }

        try {

            if (tableroListo()) {  // Verifica si las naves están bien colocadas
                // Crear y enviar mensaje READY
                Mensaje msgReady = new Mensaje("estado", "READY");
                socketCliente.enviarMensaje(msgReady);
                System.out.println("Enviado: READY");

                // Esperar permiso para enviar el tablero
                Mensaje respuesta = socketCliente.recibirMensaje();
                if (respuesta == null || !respuesta.getTipo().equals("permiso")
                        || !respuesta.getContenido().equals("ENVIAR_TABLERO")) {
                    System.out.println("No se recibió permiso válido para enviar el tablero.");
                    return;
                }

                System.out.println("Autorizado a enviar el tablero.");

                // Crear lista de coordenadas en formato ["x,y", "x,y", ...]
                List<String> coordenadasList = new ArrayList<>();
                int totalOcupadas = 0;

                for (int i = 0; i < casillasOcupadas.length; i++) {
                    for (int j = 0; j < casillasOcupadas[i].length; j++) {
                        if (casillasOcupadas[i][j]) {
                            coordenadasList.add(j + "," + i);  // Guardar como "x,y"
                            totalOcupadas++;
                        }
                    }
                }

                if (totalOcupadas != 25) {
                    System.out.println("Error: Debes colocar exactamente 25 casillas ocupadas. Actualmente: " + totalOcupadas);
                    return;
                }

                // Convertir la lista de coordenadas a JSON
                Gson gson = new Gson();
                String coordenadasJson = gson.toJson(coordenadasList);

                // Enviar las coordenadas como mensaje tipo "tablero"
                Mensaje msgTablero = new Mensaje("tablero", coordenadasJson);
                socketCliente.enviarMensaje(msgTablero);

                // Enviar coordenadas por separado como mensaje tipo "coordenadas"
                Mensaje msgCoord = new Mensaje("coordenadas", coordenadasJson);
                socketCliente.enviarMensaje(msgCoord);

                System.out.println("Coordenadas enviadas al servidor:\n" + coordenadasJson);

                // Esperar confirmación de recepción
                Mensaje confirmacion = socketCliente.recibirMensaje();
                if (confirmacion != null && "CONFIRMACION_TABLERO_RECIBIDO".equals(confirmacion.getContenido())) {
                    System.out.println("Confirmación del servidor recibida.");

                    // Esperar notificación de que ambos tableros están listos
                    Mensaje notificacion = socketCliente.recibirMensaje();
                    if (notificacion != null && "TABLEROS_LISTOS".equals(notificacion.getContenido())) {

                        System.out.println("Ambos tableros recibidos. Abriendo ColocarNave4...");

                        // Lanzar siguiente ventana
                        Partida ventanaColocarNave = new Partida(coordenadasJson, socketCliente);
                        ventanaColocarNave.setVisible(true);
                        this.dispose();
                    } else {
                        System.out.println("No se recibió la notificación de inicio.");
                    }
                } else {
                    System.out.println("No se recibió confirmación del servidor.");
                }

            } else {
                System.out.println("No se ha colocado todo correctamente en el tablero.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar al servidor.");
        }
    }//GEN-LAST:event_btnReadyActionPerformed
    /**
     * Acción que se ejecuta al presionar el botón Reiniciar Tablero. Reinicia
     * el estado del tablero a su configuración inicial, limpiando las casillas
     * ocupadas y permitiendo volver a colocar las naves.
     *
     * @param evt Evento generado por la acción del botón.
     */
    private void btnReiniciarTableroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarTableroActionPerformed
        reiniciarTablero();
    }//GEN-LAST:event_btnReiniciarTableroActionPerformed
    /**
     * Acción que se ejecuta al soltar el botón del mouse sobre el botón
     * submarino de 4 casillas. Intenta colocar un submarino horizontalmente en
     * el tablero, verificando que haya espacio libre para las 4 casillas y
     * actualizando el estado visual y lógico del tablero.
     *
     * @param evt Evento generado por la acción del mouse (soltar botón).
     */
    private void btnSubmarino4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino4MouseReleased
        if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.SUBMARINO) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnSubmarino4, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40;
        int y = puntoEnTablero.x / 40;

        // Verificamos que haya espacio para 2 casillas hacia la derecha
        if (x >= 0 && x < 10 && y >= 0 && y <= 8) { // hasta columna 8 (8 + 2 = 10)
            // Validar que no haya casillas ocupadas ni adyacentes
            if (esValidaColocacionConEspacio(x, y, 2)) {
                for (int i = 0; i < 2; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.PINK);
                }

                System.out.println("Submarino colocado en fila " + x + " desde columna " + y + " hasta " + (y + 1));
                repaint();

                contadorSubmarinos++;
                naveSeleccionada = null;

                // Hacer desaparecer el botón del submarino después de colocarlo
                Container contenedor = btnSubmarino4.getParent();
                contenedor.remove(btnSubmarino4);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No se puede colocar el submarino porque hay otra nave adyacente.");
                // Opcional: Regresar el botón a su posición original si quieres
                btnSubmarino4.setBounds(154, 278, 40, 75);
                panelBarcos.add(btnSubmarino4);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el submarino.");
            // Opcional: Regresar el botón a su posición original
            btnSubmarino4.setBounds(154, 278, 40, 75);
            panelBarcos.add(btnSubmarino4);
            panelBarcos.revalidate();
            panelBarcos.repaint();
        }
    } else {
        System.out.println("No se ha seleccionado el submarino para colocar.");
    }

    }//GEN-LAST:event_btnSubmarino4MouseReleased

    private void btnSubmarino4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino4MousePressed
        seleccionarSubmarinos(TipoNave.SUBMARINO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnSubmarino4.getWidth(), btnSubmarino4.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnSubmarino4, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnSubmarino4MousePressed
    /**
     * Se ejecuta mientras se arrastra el mouse con el botón del submarino de 4
     * casillas presionado. Actualiza la posición de la imagen que sigue el
     * cursor.
     *
     * @param evt Evento generado por el movimiento del mouse mientras se
     * arrastra.
     */
    private void btnSubmarino4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino4MouseDragged
        barcoMouseDragged(evt, btnSubmarino4);
    }//GEN-LAST:event_btnSubmarino4MouseDragged
    /**
     * Se ejecuta al soltar el botón del mouse sobre el submarino de 3 casillas.
     * Intenta colocar el submarino en el tablero verificando espacio y casillas
     * ocupadas, actualiza la visualización y estado lógico del tablero,
     * incrementa el contador y elimina el botón del panel si la colocación fue
     * exitosa.
     *
     * @param evt Evento generado por el mouse al soltar el botón.
     */
    private void btnSubmarino3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino3MouseReleased
        if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.SUBMARINO) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnSubmarino3, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40;
        int y = puntoEnTablero.x / 40;

        // Verificamos que haya espacio para 2 casillas hacia la derecha
        if (x >= 0 && x < 10 && y >= 0 && y <= 8) { // hasta columna 8 (8 + 2 = 10)
            // Validar que no haya casillas ocupadas ni adyacentes
            if (esValidaColocacionConEspacio(x, y, 2)) {
                for (int i = 0; i < 2; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.PINK);
                }

                System.out.println("Submarino colocado en fila " + x + " desde columna " + y + " hasta " + (y + 1));
                repaint();

                contadorSubmarinos++;
                naveSeleccionada = null;

                // Hacer desaparecer el botón del submarino después de colocarlo
                Container contenedor = btnSubmarino3.getParent();
                contenedor.remove(btnSubmarino3);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No se puede colocar el submarino porque hay otra nave adyacente.");
                // Opcional: Regresar el botón a su posición original si quieres
                btnSubmarino3.setBounds(108, 278, 40, 75);
                panelBarcos.add(btnSubmarino3);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el submarino.");
            // Opcional: Regresar el botón a su posición original
            btnSubmarino3.setBounds(108, 278, 40, 75);
            panelBarcos.add(btnSubmarino3);
            panelBarcos.revalidate();
            panelBarcos.repaint();
        }
    } else {
        System.out.println("No se ha seleccionado el submarino para colocar.");
    }
    }//GEN-LAST:event_btnSubmarino3MouseReleased
    /**
     * Se ejecuta al presionar el botón del submarino de 3 casillas. Selecciona
     * el tipo de nave SUBMARINO, carga la imagen asociada, crea una etiqueta
     * para el arrastre y la agrega al panel en la capa de drag.
     *
     * @param evt Evento generado por el mouse al presionar el botón.
     */
    private void btnSubmarino3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino3MousePressed
        seleccionarSubmarinos(TipoNave.SUBMARINO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnSubmarino3.getWidth(), btnSubmarino4.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnSubmarino3, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnSubmarino3MousePressed
    /**
     * Se ejecuta mientras se arrastra el mouse con el botón del submarino de 3
     * casillas presionado. Actualiza la posición de la imagen que sigue el
     * cursor.
     *
     * @param evt Evento generado por el movimiento del mouse mientras se
     * arrastra.
     */
    private void btnSubmarino3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino3MouseDragged
        barcoMouseDragged(evt, btnSubmarino3);
    }//GEN-LAST:event_btnSubmarino3MouseDragged
    /**
     * Se ejecuta al soltar el botón del mouse sobre el submarino de 2 casillas.
     * Intenta colocar el submarino en el tablero verificando espacio y casillas
     * ocupadas, actualiza la visualización y estado lógico del tablero,
     * incrementa el contador y elimina el botón del panel si la colocación fue
     * exitosa.
     *
     * @param evt Evento generado por el mouse al soltar el botón.
     */
    private void btnSubmarino2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino2MouseReleased
         if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.SUBMARINO) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnSubmarino2, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40;
        int y = puntoEnTablero.x / 40;

        // Verificamos que haya espacio para 2 casillas hacia la derecha
        if (x >= 0 && x < 10 && y >= 0 && y <= 8) { // hasta columna 8 (8 + 2 = 10)
            // Validar que no haya casillas ocupadas ni adyacentes
            if (esValidaColocacionConEspacio(x, y, 2)) {
                for (int i = 0; i < 2; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.PINK);
                }

                System.out.println("Submarino colocado en fila " + x + " desde columna " + y + " hasta " + (y + 1));
                repaint();

                contadorSubmarinos++;
                naveSeleccionada = null;

                // Hacer desaparecer el botón del submarino después de colocarlo
                Container contenedor = btnSubmarino2.getParent();
                contenedor.remove(btnSubmarino2);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No se puede colocar el submarino porque hay otra nave adyacente.");
                // Opcional: Regresar el botón a su posición original si quieres
                btnSubmarino2.setBounds(62, 278, 40, 75);
                panelBarcos.add(btnSubmarino2);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el submarino.");
            // Opcional: Regresar el botón a su posición original
            btnSubmarino2.setBounds(62, 278, 40, 75);
            panelBarcos.add(btnSubmarino2);
            panelBarcos.revalidate();
            panelBarcos.repaint();
        }
    } else {
        System.out.println("No se ha seleccionado el submarino para colocar.");
    }
    }//GEN-LAST:event_btnSubmarino2MouseReleased
// Evento cuando se presiona el botón del submarino 2

    private void btnSubmarino2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino2MousePressed
        seleccionarSubmarinos(TipoNave.SUBMARINO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnSubmarino2.getWidth(), btnSubmarino2.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnSubmarino2, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnSubmarino2MousePressed
// Evento cuando se arrastra el botón submarino 2

    private void btnSubmarino2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino2MouseDragged
        barcoMouseDragged(evt, btnSubmarino2);
    }//GEN-LAST:event_btnSubmarino2MouseDragged
// Evento cuando se suelta el botón submarino 1 para colocar la nave en el tablero

    private void btnSubmarino1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino1MouseReleased
        if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.SUBMARINO) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnSubmarino1, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40;
        int y = puntoEnTablero.x / 40;

        // Verificamos que haya espacio para 2 casillas hacia la derecha
        if (x >= 0 && x < 10 && y >= 0 && y <= 8) { // hasta columna 8 (8 + 2 = 10)
            // Validar que no haya casillas ocupadas ni adyacentes
            if (esValidaColocacionConEspacio(x, y, 2)) {
                for (int i = 0; i < 2; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.PINK);
                }

                System.out.println("Submarino colocado en fila " + x + " desde columna " + y + " hasta " + (y + 1));
                repaint();

                contadorSubmarinos++;
                naveSeleccionada = null;

                // Hacer desaparecer el botón del submarino después de colocarlo
                Container contenedor = btnSubmarino1.getParent();
                contenedor.remove(btnSubmarino1);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No se puede colocar el submarino porque hay otra nave adyacente.");
                // Opcional: Regresar el botón a su posición original si quieres
                btnSubmarino1.setBounds(16, 278, 40, 75);
                panelBarcos.add(btnSubmarino1);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el submarino.");
            // Opcional: Regresar el botón a su posición original
            btnSubmarino1.setBounds(16, 278, 40, 75);
            panelBarcos.add(btnSubmarino1);
            panelBarcos.revalidate();
            panelBarcos.repaint();
        }
    } else {
        System.out.println("No se ha seleccionado el submarino para colocar.");
    }
    }//GEN-LAST:event_btnSubmarino1MouseReleased
// Evento cuando se presiona el botón submarino 1 

    private void btnSubmarino1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino1MousePressed
        seleccionarSubmarinos(TipoNave.SUBMARINO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnSubmarino1.getWidth(), btnSubmarino1.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnSubmarino1, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnSubmarino1MousePressed
// Evento para arrastrar el botón submarino 1

    private void btnSubmarino1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmarino1MouseDragged
        barcoMouseDragged(evt, btnSubmarino1);
    }//GEN-LAST:event_btnSubmarino1MouseDragged

    private void btnCrucero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrucero1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrucero1ActionPerformed
// Evento cuando se suelta el botón crucero 1 para colocar en tablero 

    private void btnCrucero1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrucero1MouseReleased
        if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.CRUCERO) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnCrucero1, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40;
        int y = puntoEnTablero.x / 40;

        // Verificamos que haya espacio para 3 casillas hacia la derecha
        if (x >= 0 && x < 10 && y >= 0 && y <= 7) { // hasta columna 7 (7 + 3 = 10)
            if (esValidaColocacionConEspacio(x, y, 3)) {
                for (int i = 0; i < 3; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.GREEN);
                }

                System.out.println("Crucero colocado en fila " + x + " desde columna " + y + " hasta " + (y + 2));
                repaint();

                contadorCruceros++;
                naveSeleccionada = null;

                // Hacer desaparecer el botón del crucero después de colocarlo
                Container contenedor = btnCrucero1.getParent();
                contenedor.remove(btnCrucero1);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No se puede colocar el crucero porque hay otra nave adyacente.");
                // Opcional: Regresar el botón a su posición original
                btnCrucero1.setBounds(30, 114, 40, 100);
                panelBarcos.add(btnCrucero1);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el Crucero.");
            btnCrucero1.setBounds(30, 114, 40, 100);
            panelBarcos.add(btnCrucero1);
            panelBarcos.revalidate();
            panelBarcos.repaint();
        }
    } else {
        System.out.println("No se ha seleccionado el Crucero para colocar.");
    }
    }//GEN-LAST:event_btnCrucero1MouseReleased
    /**
     * Evento que se dispara cuando se presiona el botón del crucero 1.
     * Selecciona el tipo de nave CRUCERO, carga la imagen correspondiente y
     * crea una etiqueta que seguirá el cursor mientras se arrastra la nave.
     *
     * @param evt Evento del mouse presionado
     */
    private void btnCrucero1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrucero1MousePressed
        seleccionarSubmarinos(TipoNave.CRUCERO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnCrucero1.getWidth(), btnCrucero1.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnCrucero1, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnCrucero1MousePressed
    /**
     * Evento que se dispara al arrastrar el botón del crucero 1. Actualiza la
     * posición de la nave mientras se arrastra.
     *
     * @param evt Evento del mouse arrastrado
     */
    private void btnCrucero1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrucero1MouseDragged
        barcoMouseDragged(evt, btnCrucero1);
    }//GEN-LAST:event_btnCrucero1MouseDragged
    /**
     * Evento que se dispara cuando se suelta el botón del crucero 2. Verifica
     * si hay espacio disponible para colocar la nave en el tablero, actualiza
     * las casillas ocupadas, cambia el color de las casillas y elimina el botón
     * de la interfaz para reflejar que el crucero ha sido colocado.
     *
     * @param evt Evento del mouse liberado
     */
    private void btnCrucero2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrucero2MouseReleased
         if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.CRUCERO) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnCrucero2, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40;
        int y = puntoEnTablero.x / 40;

        // Verificamos que haya espacio para 3 casillas hacia la derecha
        if (x >= 0 && x < 10 && y >= 0 && y <= 7) { // hasta columna 7 (7 + 3 = 10)
            if (esValidaColocacionConEspacio(x, y, 3)) {
                for (int i = 0; i < 3; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.GREEN);
                }

                System.out.println("Crucero colocado en fila " + x + " desde columna " + y + " hasta " + (y + 2));
                repaint();

                contadorCruceros++;
                naveSeleccionada = null;

                // Hacer desaparecer el botón del crucero después de colocarlo
                Container contenedor = btnCrucero2.getParent();
                contenedor.remove(btnCrucero2);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No se puede colocar el crucero porque hay otra nave adyacente.");
                // Opcional: Regresar el botón a su posición original
                btnCrucero2.setBounds(140, 114, 40, 100);
                panelBarcos.add(btnCrucero2);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el Crucero.");
            btnCrucero2.setBounds(140, 114, 40, 100);
            panelBarcos.add(btnCrucero2);
            panelBarcos.revalidate();
            panelBarcos.repaint();
        }
    } else {
        System.out.println("No se ha seleccionado el Crucero para colocar.");
    }
    }//GEN-LAST:event_btnCrucero2MouseReleased
    /**
     * Evento que se dispara al presionar el botón del crucero 2. Similar al
     * crucero 1, selecciona la nave, carga la imagen y crea la etiqueta para el
     * arrastre.
     *
     * @param evt Evento del mouse presionado
     */
    private void btnCrucero2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrucero2MousePressed
        seleccionarSubmarinos(TipoNave.CRUCERO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnCrucero2.getWidth(), btnCrucero2.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnCrucero2, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnCrucero2MousePressed
    /**
     * Evento que se dispara al arrastrar el botón del crucero 2. Actualiza la
     * posición de la nave mientras se arrastra.
     *
     * @param evt Evento del mouse arrastrado
     */
    private void btnCrucero2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrucero2MouseDragged
        barcoMouseDragged(evt, btnCrucero2);
    }//GEN-LAST:event_btnCrucero2MouseDragged
    /**
     * Evento que se ejecuta al soltar el botón del mouse sobre el botón barco3.
     *
     * Este método verifica si hay una nave seleccionada para colocar en el
     * tablero. Convierte la posición del cursor en el panel tablero a
     * coordenadas del tablero lógico. Si la casilla está libre, agrega la
     * casilla a la nave, marca la casilla como ocupada, actualiza el color en
     * la interfaz, incrementa el contador de barcos y remueve el botón de la
     * nave para que desaparezca del panel de barcos. Si la casilla está ocupada
     * o fuera del tablero, regresa el barco a su posición inicial.
     */
    private void barco3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco3MouseReleased
        if (naveSeleccionada != null) {
        Point puntoEnTablero = SwingUtilities.convertPoint(barco3, evt.getPoint(), panelTablero);

        int x = puntoEnTablero.y / 40; // fila
        int y = puntoEnTablero.x / 40; // columna

        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            // Validamos que no haya ninguna nave adyacente o en la misma casilla
            if (!esValidaColocacionConEspacio(x, y, 1)) {
                System.out.println("No se puede colocar el barco porque hay otra nave adyacente.");
                barco3.setBounds(88, 226, 40, 40); // posición original
                panelBarcos.add(barco3);
                panelBarcos.revalidate();
                panelBarcos.repaint();
                return;
            }

            if (casillasOcupadas[x][y]) {
                System.out.println("La casilla ya está ocupada.");
                barco3.setBounds(88, 226, 40, 40); // posición original
                panelBarcos.add(barco3);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            } else {
                Casilla casilla = tablero[x][y];
                naveSeleccionada.agregarCasilla(casilla);
                casillasOcupadas[x][y] = true; // Marcar la casilla como ocupada

                JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + y);
                casillaButton.setBackground(Color.RED); // Actualiza la interfaz con el color

                System.out.println("Casilla agregada a la nave " + naveSeleccionada.getNombre()
                        + " en las coordenadas (" + x + ", " + y + ")");
                repaint();

                // Incrementar contador de barcos
                if (naveSeleccionada.getTipo() == TipoNave.BARCO) {
                    contadorBarcos++;
                }

                naveSeleccionada = null; // Reiniciar la selección del barco

                // Hacer que la nave desaparezca después de colocarla en el tablero
                Container contenedor = barco3.getParent(); // Obtener el panel donde estaba el botón
                contenedor.remove(barco3);                 // Eliminar el botón del panel
                contenedor.revalidate();                   // Revalidar el layout
                contenedor.repaint();                      // Repaint para refrescar la interfaz
            }
        } else {
            System.out.println("Las coordenadas están fuera del tablero.");
        }
    } else {
        System.out.println("No se ha seleccionado un barco para colocar.");
    }
    }//GEN-LAST:event_barco3MouseReleased
    /**
     * Evento que se ejecuta al presionar el botón del mouse sobre barco3.
     *
     * Selecciona la nave tipo BARCO para colocar y prepara la imagen que se
     * arrastrará siguiendo al cursor durante la operación de drag and drop.
     */
    private void barco3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco3MousePressed
        seleccionarSubmarinos(TipoNave.BARCO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(barco3.getWidth(), barco3.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(barco3, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_barco3MousePressed

    /**
     * Evento que se ejecuta mientras se arrastra el mouse con el botón barco3
     * presionado.
     *
     * Maneja el movimiento visual del botón barco3 mientras se arrastra.
     */
    private void barco3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco3MouseDragged
        barcoMouseDragged(evt, barco3);
    }//GEN-LAST:event_barco3MouseDragged
    /**
     * Evento que se ejecuta al soltar el botón del mouse sobre el botón barco2.
     * Funciona igual que barco3MouseReleased, pero para el botón barco2.
     */
    private void barco2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco2MouseReleased
        if (naveSeleccionada != null) {
        Point puntoEnTablero = SwingUtilities.convertPoint(barco2, evt.getPoint(), panelTablero);

        int x = puntoEnTablero.y / 40; // fila
        int y = puntoEnTablero.x / 40; // columna

        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            // Validamos que no haya ninguna nave adyacente o en la misma casilla
            if (!esValidaColocacionConEspacio(x, y, 1)) {
                System.out.println("No se puede colocar el barco porque hay otra nave adyacente.");
                barco2.setBounds(30, 226, 40, 40); // posición original
                panelBarcos.add(barco2);
                panelBarcos.revalidate();
                panelBarcos.repaint();
                return;
            }

            if (casillasOcupadas[x][y]) {
                System.out.println("La casilla ya está ocupada.");
                barco2.setBounds(30, 226, 40, 40); // posición original
                panelBarcos.add(barco2);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            } else {
                Casilla casilla = tablero[x][y];
                naveSeleccionada.agregarCasilla(casilla);
                casillasOcupadas[x][y] = true; // Marcar la casilla como ocupada

                JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + y);
                casillaButton.setBackground(Color.RED); // Actualiza la interfaz con el color

                System.out.println("Casilla agregada a la nave " + naveSeleccionada.getNombre()
                        + " en las coordenadas (" + x + ", " + y + ")");
                repaint();

                // Incrementar contador de barcos
                if (naveSeleccionada.getTipo() == TipoNave.BARCO) {
                    contadorBarcos++;
                }

                naveSeleccionada = null; // Reiniciar la selección del barco

                // Hacer que la nave desaparezca después de colocarla en el tablero
                Container contenedor = barco2.getParent(); // Obtener el panel donde estaba el botón
                contenedor.remove(barco2);                 // Eliminar el botón del panel
                contenedor.revalidate();                   // Revalidar el layout
                contenedor.repaint();                      // Repaint para refrescar la interfaz
            }
        } else {
            System.out.println("Las coordenadas están fuera del tablero.");
        }
    } else {
        System.out.println("No se ha seleccionado un barco para colocar.");
    }
    }//GEN-LAST:event_barco2MouseReleased
    /**
     * Evento que se ejecuta al presionar el botón del mouse sobre barco2.
     * Similar a barco3MousePressed, pero para barco2.
     */
    private void barco2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco2MousePressed
        seleccionarSubmarinos(TipoNave.BARCO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(barco2.getWidth(), barco2.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(barco2, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_barco2MousePressed
    /**
     * Evento que se ejecuta mientras se arrastra el mouse con el botón barco2
     * presionado. Similar a barco3MouseDragged, pero para barco2.
     */
    private void barco2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco2MouseDragged
        barcoMouseDragged(evt, barco2);
    }//GEN-LAST:event_barco2MouseDragged
    /**
     * Evento que se ejecuta al soltar el botón del mouse sobre el botón
     * btnPortaaviones2.
     *
     * Verifica que se haya seleccionado el portaaviones, comprueba si hay
     * espacio suficiente en el tablero para colocarlo horizontalmente (4
     * casillas) sin pisar casillas ocupadas. Si el espacio está disponible,
     * coloca la nave, colorea las casillas, incrementa el contador y remueve el
     * botón del panel de barcos. Si no, regresa el botón a su posición inicial
     * y muestra mensaje.
     */
    private void btnPortaaviones2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaaviones2MouseReleased
         if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.PORTAAVIONES) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnPortaaviones2, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40; // fila
        int y = puntoEnTablero.x / 40; // columna

        // Validar que la nave quepa y que las coordenadas estén dentro del tablero
        if (x >= 0 && x < 10 && y >= 0 && y <= 6) { // hasta columna 6 (6 + 4 = 10)
            
            // Validar espacio y alrededores libres
            if (!esValidaColocacionConEspacio(x, y, 4)) {
                System.out.println("No se puede colocar el portaaviones porque hay otra nave adyacente o casilla ocupada.");
                btnPortaaviones2.setBounds(30, 62, 150, 40); // posición original
                panelBarcos.add(btnPortaaviones2);
                panelBarcos.revalidate();
                panelBarcos.repaint();
                return;
            }

            boolean espacioDisponible = true;

            for (int i = 0; i < 4; i++) {
                if (casillasOcupadas[x][y + i]) {
                    espacioDisponible = false;
                    btnPortaaviones2.setBounds(30, 62, 150, 40); // posición original
                    panelBarcos.add(btnPortaaviones2);
                    panelBarcos.revalidate();
                    panelBarcos.repaint();
                    break;
                }
            }

            if (espacioDisponible) {
                for (int i = 0; i < 4; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.ORANGE);
                }

                System.out.println("Portaaviones colocado en fila " + x + " desde columna " + y + " hasta " + (y + 3));
                repaint();

                contadorPortaaviones++;
                naveSeleccionada = null; // Reiniciar selección

                // Remover botón del panel
                Container contenedor = btnPortaaviones2.getParent();
                contenedor.remove(btnPortaaviones2);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No hay espacio suficiente o casillas ocupadas para el portaaviones.");
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el portaaviones.");
        }
    } else {
        System.out.println("No se ha seleccionado el portaaviones para colocar.");
    }
    }//GEN-LAST:event_btnPortaaviones2MouseReleased
    /**
     * Evento que se ejecuta al presionar el botón del mouse sobre
     * btnPortaaviones2. Prepara la selección del portaaviones y la imagen para
     * arrastrar.
     */
    private void btnPortaaviones2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaaviones2MousePressed
        seleccionarSubmarinos(TipoNave.PORTAAVIONES);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnPortaaviones2.getWidth(), btnPortaaviones2.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnPortaaviones2, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnPortaaviones2MousePressed

    private void btnPortaaviones2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaaviones2MouseDragged

    }//GEN-LAST:event_btnPortaaviones2MouseDragged
    /**
     * Evento que se ejecuta al soltar el botón del mouse sobre el botón
     * btnPortaaviones. Funciona igual que btnPortaaviones2MouseReleased, pero
     * para btnPortaaviones.
     */
    private void btnPortaavionesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaavionesMouseReleased
         if (naveSeleccionada != null && naveSeleccionada.getTipo() == TipoNave.PORTAAVIONES) {
        Point puntoEnTablero = SwingUtilities.convertPoint(btnPortaaviones, evt.getPoint(), panelTablero);
        int x = puntoEnTablero.y / 40; // fila
        int y = puntoEnTablero.x / 40; // columna

        // Validar que la nave quepa y que las coordenadas estén dentro del tablero
        if (x >= 0 && x < 10 && y >= 0 && y <= 6) { // hasta columna 6 (6 + 4 = 10)
            
            // Validar espacio y alrededores libres
            if (!esValidaColocacionConEspacio(x, y, 4)) {
                System.out.println("No se puede colocar el portaaviones porque hay otra nave adyacente o casilla ocupada.");
                btnPortaaviones.setBounds(30, 16, 150, 40); // posición original
                panelBarcos.add(btnPortaaviones);
                panelBarcos.revalidate();
                panelBarcos.repaint();
                return;
            }

            boolean espacioDisponible = true;

            for (int i = 0; i < 4; i++) {
                if (casillasOcupadas[x][y + i]) {
                    espacioDisponible = false;
                    btnPortaaviones.setBounds(30, 16, 150, 40); // posición original
                    panelBarcos.add(btnPortaaviones);
                    panelBarcos.revalidate();
                    panelBarcos.repaint();
                    break;
                }
            }

            if (espacioDisponible) {
                for (int i = 0; i < 4; i++) {
                    Casilla casilla = tablero[x][y + i];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y + i] = true;

                    JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + (y + i));
                    casillaButton.setBackground(Color.ORANGE);
                }

                System.out.println("Portaaviones colocado en fila " + x + " desde columna " + y + " hasta " + (y + 3));
                repaint();

                contadorPortaaviones++;
                naveSeleccionada = null; // Reiniciar selección

                // Remover botón del panel
                Container contenedor = btnPortaaviones.getParent();
                contenedor.remove(btnPortaaviones);
                contenedor.revalidate();
                contenedor.repaint();

            } else {
                System.out.println("No hay espacio suficiente o casillas ocupadas para el portaaviones.");
            }
        } else {
            System.out.println("Las coordenadas están fuera del rango para colocar el portaaviones.");
        }
    } else {
        System.out.println("No se ha seleccionado el portaaviones para colocar.");
    }
    }//GEN-LAST:event_btnPortaavionesMouseReleased
    /**
     * Evento que se ejecuta al presionar el botón del mouse sobre
     * btnPortaaviones. Prepara la selección del portaaviones y la imagen para
     * arrastrar.
     */
    private void btnPortaavionesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaavionesMousePressed
        seleccionarSubmarinos(TipoNave.PORTAAVIONES);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(btnPortaaviones.getWidth(), btnPortaaviones.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(btnPortaaviones, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_btnPortaavionesMousePressed
    /**
     * Evento que se ejecuta mientras se arrastra el mouse con el botón
     * btnPortaaviones presionado. Maneja el movimiento visual del botón durante
     * el arrastre.
     */
    private void btnPortaavionesMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPortaavionesMouseDragged
        barcoMouseDragged(evt, btnPortaaviones);
    }//GEN-LAST:event_btnPortaavionesMouseDragged
    /**
     * Evento que se ejecuta al soltar el botón del mouse sobre el botón barco1.
     *
     * Este método maneja la colocación del barco en el tablero cuando el
     * usuario lo suelta. - Convierte la posición del cursor en coordenadas del
     * tablero lógico. - Verifica que la posición esté dentro del tablero. -
     * Verifica si la casilla está ocupada: - Si está ocupada, regresa el barco
     * a su posición inicial y muestra un mensaje. - Si no está ocupada, agrega
     * la casilla a la nave, marca la casilla como ocupada, cambia el color del
     * botón correspondiente para reflejar la ocupación, incrementa el contador
     * de barcos si corresponde, y remueve el botón del barco para que
     * desaparezca visualmente. - Si no hay ninguna nave seleccionada, muestra
     * un mensaje indicándolo.
     *
     * @param evt Evento del mouse que contiene la posición del cursor.
     */
    private void barco1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MouseReleased
        if (naveSeleccionada != null) {
        Point puntoEnTablero = SwingUtilities.convertPoint(barco1, evt.getPoint(), panelTablero);

        int x = puntoEnTablero.y / 40; // fila
        int y = puntoEnTablero.x / 40; // columna

        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            // Validamos que no haya ninguna nave adyacente o en la misma casilla
            if (!esValidaColocacionConEspacio(x, y, 1)) {
                System.out.println("No se puede colocar el barco porque hay otra nave adyacente.");
                barco1.setBounds(140, 226, 40, 40); // posición original
                panelBarcos.add(barco1);
                panelBarcos.revalidate();
                panelBarcos.repaint();
                return;
            }

            if (casillasOcupadas[x][y]) {
                System.out.println("La casilla ya está ocupada.");
                barco1.setBounds(140, 226, 40, 40); // posición original
                panelBarcos.add(barco1);
                panelBarcos.revalidate();
                panelBarcos.repaint();
            } else {
                Casilla casilla = tablero[x][y];
                naveSeleccionada.agregarCasilla(casilla);
                casillasOcupadas[x][y] = true; // Marcar la casilla como ocupada

                JButton casillaButton = (JButton) panelTablero.getComponent(x * 10 + y);
                casillaButton.setBackground(Color.RED); // Actualiza la interfaz con el color

                System.out.println("Casilla agregada a la nave " + naveSeleccionada.getNombre()
                        + " en las coordenadas (" + x + ", " + y + ")");
                repaint();

                // Incrementar contador de barcos
                if (naveSeleccionada.getTipo() == TipoNave.BARCO) {
                    contadorBarcos++;
                }

                naveSeleccionada = null; // Reiniciar la selección del barco

                // Hacer que la nave desaparezca después de colocarla en el tablero
                Container contenedor = barco1.getParent(); // Obtener el panel donde estaba el botón
                contenedor.remove(barco1);                 // Eliminar el botón del panel
                contenedor.revalidate();                   // Revalidar el layout
                contenedor.repaint();                      // Repaint para refrescar la interfaz
            }
        } else {
            System.out.println("Las coordenadas están fuera del tablero.");
        }
    } else {
        System.out.println("No se ha seleccionado un barco para colocar.");
    }
    }//GEN-LAST:event_barco1MouseReleased
    /**
     * Evento que se ejecuta al presionar el botón del mouse sobre el botón
     * barco1.
     *
     * Este método selecciona el barco tipo BARCO para colocar y prepara la
     * imagen que seguirá el cursor durante el arrastre (drag and drop). Agrega
     * el botón barco1 a la capa de drag para que se pueda mover libremente.
     *
     * @param evt Evento del mouse.
     */
    private void barco1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MousePressed
        seleccionarSubmarinos(TipoNave.BARCO);

        // Cargar imagen de submarino
        imagenNaveOriginal = new ImageIcon(getClass().getResource("/imagenes/casilla2img2.png"));

        // Crear la etiqueta que seguirá el cursor
        imagenArrastrada = new JLabel(imagenNaveOriginal);
        imagenArrastrada.setSize(barco1.getWidth(), barco1.getHeight());

        // Agregar al panel principal
        this.getLayeredPane().add(barco1, JLayeredPane.DRAG_LAYER);
    }//GEN-LAST:event_barco1MousePressed
    /**
     * Evento que se ejecuta mientras se arrastra el botón barco1 con el mouse
     * presionado.
     *
     * Actualiza la posición del botón barco1 para seguir el cursor durante el
     * drag.
     *
     * @param evt Evento del mouse.
     */
    private void barco1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MouseDragged
        barcoMouseDragged(evt, barco1);
    }//GEN-LAST:event_barco1MouseDragged
    /**
     * Verifica si el tablero está listo, es decir, si todas las casillas
     * esperadas están ocupadas.
     *
     * @return true si el número de casillas ocupadas es igual al total
     * esperado, false en otro caso.
     */
    private boolean tableroListo() {
        int ocupadas = 0;

        // Recorremos las casillas y contamos las ocupadas
        for (int i = 0; i < casillasOcupadas.length; i++) {
            for (int j = 0; j < casillasOcupadas[i].length; j++) {
                if (casillasOcupadas[i][j]) {
                    ocupadas++;
                }
            }
        }

        // Comparar las casillas ocupadas con el total esperado
        return ocupadas == TOTAL_CASILLAS_ESPERADAS;
    }

    /**
     * Reinicia el tablero y los contadores, restableciendo todo a su estado
     * inicial. - Limpia el panel del tablero y elimina todas las casillas
     * ocupadas. - Restablece el estado de cada casilla del tablero. - Reinstala
     * los botones y paneles de las naves. - Reinicia los contadores de barcos,
     * portaaviones, cruceros y submarinos. - Actualiza la interfaz gráfica.
     */
    private void barcoMouseDragged(java.awt.event.MouseEvent evt, JButton barco) {
        Point puntoPantalla = evt.getLocationOnScreen();
        Point puntoVentana = this.getLocationOnScreen();
        int x = puntoPantalla.x - puntoVentana.x;
        int y = puntoPantalla.y - puntoVentana.y;
        barco.setLocation(x - barco.getWidth() / 2, y - barco.getHeight() / 2);
    }

    private void reiniciarTablero() {
        // Limpiar tablero
        panelTablero.removeAll();
        for (int i = 0; i < casillasOcupadas.length; i++) {
            for (int j = 0; j < casillasOcupadas[i].length; j++) {
                casillasOcupadas[i][j] = false; // Marcar todas las casillas como desocupadas
                tablero[i][j].reiniciarEstado(); // Restablecer el estado de la casilla
            }
        }

        // Volver a crear los botones del tablero
        inicializarTablero();

        // Restaurar las naves (si se eliminaban visualmente)
        panelBarcos.removeAll();
        inicializarNaves(); // Crea y agrega los botones de las naves

        // Reiniciar contadores y estados si aplica
        contadorBarcos = 0;
        contadorPortaaviones = 0;
        contadorCruceros = 0;
        contadorSubmarinos = 0;

        // Actualizar la interfaz
        panelTablero.revalidate();
        panelTablero.repaint();
        panelBarcos.revalidate();
        panelBarcos.repaint();
    }

    /**
     * Inicializa los botones para las naves en el panel de barcos. - Ajusta la
     * posición y tamaño de cada botón de nave. - Añade cada botón al panel
     * correspondiente. - Actualiza la interfaz gráfica para reflejar los
     * cambios.
     */
    private void inicializarNaves() {
        // Crear los botones de los barcos y asignarles su posición
//    barco1.setText("Barco 1"); // O asigna el nombre correspondiente
        barco1.setBounds(140, 226, 40, 40); // Ajustar la posición y tamaño del barco
        panelBarcos.add(barco1);
        barco2.setBounds(30, 226, 40, 40); // Ajustar la posición y tamaño del barco
        panelBarcos.add(barco2);
        barco3.setBounds(88, 226, 40, 40); // Ajustar la posición y tamaño del barco
        panelBarcos.add(barco3);

        // Crear el botón para el portaaviones
        btnPortaaviones.setBounds(30, 16, 150, 40); // Ajustar la posición y tamaño
        panelBarcos.add(btnPortaaviones);
        btnPortaaviones2.setBounds(30, 62, 150, 40); // Ajustar la posición y tamaño
        panelBarcos.add(btnPortaaviones2);

        // Crear el botón para el cruceros
        btnCrucero1.setBounds(30, 114, 40, 100); // Ajustar la posición y tamaño
        panelBarcos.add(btnCrucero1);
        btnCrucero2.setBounds(140, 114, 40, 100); // Ajustar la posición y tamaño
        panelBarcos.add(btnCrucero2);

        // Crear el botón para el Submarinos
        btnSubmarino1.setBounds(16, 278, 40, 75); // Ajustar la posición y tamaño
        panelBarcos.add(btnSubmarino1);
        btnSubmarino2.setBounds(62, 278, 40, 75); // Ajustar la posición y tamaño
        panelBarcos.add(btnSubmarino2);
        btnSubmarino3.setBounds(108, 278, 40, 75); // Ajustar la posición y tamaño
        panelBarcos.add(btnSubmarino3);
        btnSubmarino4.setBounds(154, 278, 40, 75); // Ajustar la posición y tamaño
        panelBarcos.add(btnSubmarino4);

        // Agregar más barcos según sea necesario
        // (repite el proceso para otros barcos si los hay)
        // Actualizar la interfaz
        panelBarcos.revalidate();
        panelBarcos.repaint();
    }

     
    private boolean esValidaColocacionConEspacio(int fila, int colInicio, int tamaño) {
    int filas = casillasOcupadas.length;
    int columnas = casillasOcupadas[0].length;

    for (int i = 0; i < tamaño; i++) {
        int c = colInicio + i;

        // Revisa las casillas alrededor (incluye diagonales)
        for (int f = fila - 1; f <= fila + 1; f++) {
            for (int cc = c - 1; cc <= c + 1; cc++) {
                if (f >= 0 && f < filas && cc >= 0 && cc < columnas) {
                    if (casillasOcupadas[f][cc]) {
                        return false; // Hay una nave adyacente
                    }
                }
            }
        }
    }
    return true; // No hay naves en la zona
}

    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ColocarNave2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                String nombreJugador = "Jugador 2";  // Suponiendo que este nombre viene de la interfaz anterior
//                ColocarNave2 frame = new ColocarNave2(nombreJugador);
//                frame.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barco1;
    private javax.swing.JButton barco2;
    private javax.swing.JButton barco3;
    private javax.swing.JButton btnCrucero1;
    private javax.swing.JButton btnCrucero2;
    private javax.swing.JButton btnPortaaviones;
    private javax.swing.JButton btnPortaaviones2;
    private javax.swing.JButton btnReady;
    private javax.swing.JButton btnReiniciarTablero;
    private javax.swing.JButton btnSubmarino1;
    private javax.swing.JButton btnSubmarino2;
    private javax.swing.JButton btnSubmarino3;
    private javax.swing.JButton btnSubmarino4;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel labelNomJugador;
    private javax.swing.JPanel panelBarcos;
    private javax.swing.JPanel panelFondoTablero;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JPanel panelfondo;
    // End of variables declaration//GEN-END:variables
}
