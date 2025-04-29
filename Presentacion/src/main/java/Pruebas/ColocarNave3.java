/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import EntidadesDTO.CasillaDTO;
import EntidadesDTO.NaveDTO;
import EntidadesDTO.TipoNave;
import PatronBuilder.Director;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Carlo
 */
public class ColocarNave3 extends JPanel {

    private NaveDTO naveSeleccionada;  // Variable para almacenar el barco seleccionado
    private Director director;
    private CasillaDTO[][] tablero;  // Tablero de 10x10
    private boolean[][] casillasOcupadas; // Para validar si la casilla ya está ocupada

    public ColocarNave3() {
        this.director = new Director();
        this.tablero = new CasillaDTO[10][10];
        this.casillasOcupadas = new boolean[10][10]; // Inicializa el tablero con casillas no ocupadas
        initTablero();
        initUI();
    }

    private void initTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = new CasillaDTO(i, j); // Asumimos que CasillaDTO tiene constructor con coordenadas
            }
        }
    }

    private void initUI() {
        setLayout(null);

        // Botón de selección del barco
        JButton barco1 = new JButton("Barco 1");
        barco1.setBounds(10, 10, 100, 30);
        barco1.addActionListener(e -> seleccionarBarco(TipoNave.BARCO));
        add(barco1);

        // Agregar el evento de arrastre
        barco1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                seleccionarBarco(TipoNave.BARCO);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                barco1MouseReleased(e);  // Usamos el método al soltar el mouse
            }
        });

        // Panel de tableros y más configuraciones (suponiendo que esté configurado)
        // ...
        setPreferredSize(new Dimension(600, 600));
    }

    private void seleccionarBarco(TipoNave tipoSeleccionado) {
        // Aquí seleccionas el barco cuando se hace clic en él
        if (naveSeleccionada == null) {
            // Lógica para seleccionar el barco (ejemplo con el Barco 1)
            naveSeleccionada = director.construirNave(tipoSeleccionado);
            System.out.println("Barco seleccionado: " + naveSeleccionada.getNombre());
        }
    }

    private void barco1MouseReleased(MouseEvent evt) {
        if (naveSeleccionada != null) {
            int x = obtenerCoordenadaX(evt);
            int y = obtenerCoordenadaY(evt);

            // Validar si las coordenadas están dentro del tablero
            if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                if (casillasOcupadas[x][y]) {
                    System.out.println("La casilla ya está ocupada.");
                } else {
                    // Colocar la casilla en la nave
                    CasillaDTO casilla = tablero[x][y];
                    naveSeleccionada.agregarCasilla(casilla);
                    casillasOcupadas[x][y] = true;  // Marca la casilla como ocupada
                    System.out.println("Casilla agregada a la nave " + naveSeleccionada.getNombre() + " en las coordenadas (" + x + ", " + y + ")");
                    repaint();  // Redibujamos el tablero
                }
            } else {
                System.out.println("Las coordenadas están fuera del tablero.");
            }
        } else {
            System.out.println("No se ha seleccionado un barco para colocar.");
        }
    }

    private int obtenerCoordenadaX(MouseEvent evt) {
        // Ejemplo para obtener la coordenada X basada en el evento
        return (evt.getX() - 10) / 50;  // Ejemplo de cálculo, ajusta según el tamaño de las casillas
    }

    private int obtenerCoordenadaY(MouseEvent evt) {
        // Ejemplo para obtener la coordenada Y basada en el evento
        return (evt.getY() - 10) / 50;  // Ejemplo de cálculo, ajusta según el tamaño de las casillas
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja el tablero de casillas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Dibuja cada casilla (ajusta el tamaño de las casillas según necesites)
                if (casillasOcupadas[i][j]) {
                    g.setColor(Color.RED); // Casillas ocupadas de color rojo
                } else {
                    g.setColor(Color.WHITE); // Casillas libres de color blanco
                }
                g.fillRect(i * 50 + 10, j * 50 + 50, 50, 50);
                g.setColor(Color.BLACK); // Bordes de las casillas
                g.drawRect(i * 50 + 10, j * 50 + 50, 50, 50);
            }
        }
        // Dibuja las naves en las posiciones correspondientes
        if (naveSeleccionada != null) {
            for (CasillaDTO casilla : naveSeleccionada.getPosiciones()) {
                g.setColor(Color.BLUE); // Color de la nave
                g.fillRect(casilla.getX() * 50 + 10, casilla.getY() * 50 + 50, 50, 50);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Colocar Naves");
        ColocarNave3 panel = new ColocarNave3();
        frame.add(panel);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
