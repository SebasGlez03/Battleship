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
import java.util.ArrayList;
import java.util.List;
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
public class Atacar extends JFrame {

    private TableroDTO miTablero = new TableroDTO();
    private TableroDTO enemigoTablero = new TableroDTO();

    private JButton[][] botonesMiTablero = new JButton[10][10];
    private JButton[][] botonesTableroEnemigo = new JButton[10][10];

    private boolean esHorizontal = true;
    private boolean juegoIniciado = false;
    private JComboBox<String> selectorBarco;
    private JComboBox<String> selectorTablero;
    private NaveDTO naveActual;
    
    private List<NaveDTO> listaMisBarcos = new ArrayList<>();  // Lista para mis barcos
private List<NaveDTO> listaBarcosEnemigos = new ArrayList<>();  // Lista para los barcos enemigos

    public Atacar() {
        setTitle("Configuración de Juego - Colocación de Barcos");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelTableros = new JPanel(new GridLayout(1, 2));
        panelTableros.add(crearPanelTablero(botonesMiTablero, miTablero, false));
        panelTableros.add(crearPanelTablero(botonesTableroEnemigo, enemigoTablero, true));

        JPanel panelControles = new JPanel();

        selectorBarco = new JComboBox<>(new String[]{"Submarino", "Crucero", "Porta Aviones", "Barco"});
        selectorBarco.addActionListener(e -> seleccionarBarco());
        panelControles.add(new JLabel("Barco:"));
        panelControles.add(selectorBarco);

        selectorTablero = new JComboBox<>(new String[]{"Mi tablero", "Tablero enemigo"});
        panelControles.add(new JLabel("Colocar en:"));
        panelControles.add(selectorTablero);

        JButton btnOrientacion = new JButton("Cambiar orientación");
        btnOrientacion.addActionListener(e -> esHorizontal = !esHorizontal);
        panelControles.add(btnOrientacion);

        JButton btnListo = new JButton("Listo para jugar");
        btnListo.addActionListener(e -> {
            juegoIniciado = true;
            ocultarBarcosEnemigos();
            JOptionPane.showMessageDialog(this, "¡El juego ha comenzado! Haz clic en el tablero enemigo para atacar.");
        });
        panelControles.add(btnListo);

        setLayout(new BorderLayout());
        add(panelTableros, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        seleccionarBarco();
    }

    private JPanel crearPanelTablero(JButton[][] botones, TableroDTO tablero, boolean esEnemigo) {
        JPanel panel = new JPanel(new GridLayout(10, 10));
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                final int fx = x;
                final int fy = y;
                botones[x][y] = new JButton();
                botones[x][y].setBackground(Color.LIGHT_GRAY);
                int finalX = x, finalY = y;
                botones[x][y].addActionListener(e -> {
                    if (!juegoIniciado) {
                        colocarBarco(finalX, finalY);
                    } else if (esEnemigo) {
                        atacar(finalX, finalY);
                    }
                });
                panel.add(botones[x][y]);
            }
        }
        return panel;
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
        JOptionPane.showMessageDialog(this, "Selecciona un barco primero.");
        return;
    }

    TableroDTO tablero = selectorTablero.getSelectedIndex() == 0 ? miTablero : enemigoTablero;
    JButton[][] botones = selectorTablero.getSelectedIndex() == 0 ? botonesMiTablero : botonesTableroEnemigo;
    int tamano = naveActual.getTamano();

    if ((esHorizontal && y + tamano > 10) || (!esHorizontal && x + tamano > 10)) {
        JOptionPane.showMessageDialog(this, "No cabe en esa orientación.");
        return;
    }

    for (int i = 0; i < tamano; i++) {
        int xi = esHorizontal ? x : x + i;
        int yi = esHorizontal ? y + i : y;
        CasillaDTO casilla = tablero.getCasilla(xi, yi);
        if (!casilla.estaDisponible()) {
            JOptionPane.showMessageDialog(this, "Casillas ocupadas.");
            return;
        }
    }

    Color color = obtenerColor(naveActual.getColor());
    naveActual.getPosiciones().clear();

    for (int i = 0; i < tamano; i++) {
        int xi = esHorizontal ? x : x + i;
        int yi = esHorizontal ? y + i : y;
        CasillaDTO casilla = tablero.getCasilla(xi, yi);
        casilla.setOcupada(true);
        naveActual.agregarCasilla(casilla);
        botones[xi][yi].setBackground(color);
    }

    // Agregar el barco a la lista correspondiente
    if (selectorTablero.getSelectedIndex() == 0) {
        listaMisBarcos.add(naveActual);  // Agregar a la lista de mis barcos
    } else {
        listaBarcosEnemigos.add(naveActual);  // Agregar a la lista de los barcos enemigos
    }

    JOptionPane.showMessageDialog(this, naveActual.getNombre() + " colocado en " + selectorTablero.getSelectedItem());
}




    private void ocultarBarcosEnemigos() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                CasillaDTO casilla = enemigoTablero.getCasilla(x, y);
                if (casilla.estaOcupada()) {
                    botonesTableroEnemigo[x][y].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private void atacar(int x, int y) {
    CasillaDTO casilla = enemigoTablero.getCasilla(x, y);
    if (casilla.estaAtacada()) {
        JOptionPane.showMessageDialog(this, "Ya atacaste esta casilla.");
        return;
    }

    casilla.setAtacada(true);  // Marcar la casilla como atacada

    if (casilla.estaOcupada()) {
        botonesTableroEnemigo[x][y].setBackground(Color.RED);
        JOptionPane.showMessageDialog(this, "¡Acierto!");
    } else {
        botonesTableroEnemigo[x][y].setBackground(Color.WHITE);
        JOptionPane.showMessageDialog(this, "Fallaste.");
    }

    // Verificar si se hundió algún barco enemigo después del ataque
    if (verificarHundimientoBarcosEnemigos()) {
        // Ya se ha mostrado el mensaje de victoria dentro de verificarHundimientoBarcosEnemigos()
        juegoIniciado = false;  // Terminar el juego
    }
}



    // Método para verificar si todos los barcos están hundidos
  private boolean verificarHundimientoBarcosEnemigos() {
    boolean todosHundidos = true;
    List<NaveDTO> barcosAEliminar = new ArrayList<>();  // Lista temporal para almacenar los barcos ya hundidos

    // Recorremos todos los barcos enemigos
    for (NaveDTO nave : listaBarcosEnemigos) {
        boolean hundido = true;

        // Recorremos las casillas del barco
        for (CasillaDTO casilla : nave.getPosiciones()) {
            if (!casilla.estaAtacada()) {
                hundido = false;  // Si alguna casilla no está atacada, el barco no está hundido
                todosHundidos = false;
                break;
            }
        }

        // Si el barco está hundido, lo agregamos a la lista de barcos a eliminar
        if (hundido) {
            JOptionPane.showMessageDialog(this, nave.getNombre() + " ha sido hundido!");
            barcosAEliminar.add(nave);
        }
    }

    // Eliminar los barcos que ya han sido hundidos de la lista
    listaBarcosEnemigos.removeAll(barcosAEliminar);

    // Verificar si todos los barcos enemigos están hundidos
    if (todosHundidos) {
        JOptionPane.showMessageDialog(this, "¡Has hundido todos los barcos enemigos! ¡Ganaste!");
        juegoIniciado = false;  // Terminar el juego
        desactivarTableroEnemigo();  // Aquí puedes desactivar el tablero enemigo para evitar más jugadas
    reiniciarJuego();
    }
    

    return todosHundidos;
}






// Desactiva las casillas del tablero enemigo después de ganar
private void desactivarTableroEnemigo() {
    for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
            botonesTableroEnemigo[x][y].setEnabled(false);  // Desactiva todos los botones para que no se puedan hacer más clics
        }
    }
}


private void reiniciarJuego() {
    // Limpiar las listas de barcos
    listaMisBarcos.clear();
    listaBarcosEnemigos.clear();

    // Limpiar los tableros
    for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
            // Para mi tablero
            CasillaDTO casillaMiTablero = miTablero.getCasilla(x, y);
            casillaMiTablero.setOcupada(false);
            casillaMiTablero.setAtacada(false);
            botonesMiTablero[x][y].setBackground(Color.LIGHT_GRAY);

            // Para el tablero enemigo
            CasillaDTO casillaEnemigoTablero = enemigoTablero.getCasilla(x, y);
            casillaEnemigoTablero.setOcupada(false);
            casillaEnemigoTablero.setAtacada(false);
            botonesTableroEnemigo[x][y].setBackground(Color.LIGHT_GRAY);
        }
    }

    // Habilitar los botones del tablero enemigo para permitir ataques
    for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
            botonesTableroEnemigo[x][y].setEnabled(true);
        }
    }

    // Reiniciar el estado del juego
    juegoIniciado = false;

    // Mostrar mensaje de reinicio
    JOptionPane.showMessageDialog(this, "El juego ha sido reiniciado. Coloca tus barcos y comienza de nuevo.");
}








    private Color obtenerColor(String nombreColor) {
        switch (nombreColor.toLowerCase()) {
            case "azul":
                return Color.BLUE;
            case "rojo":
                return Color.RED;
            case "verde":
                return Color.GREEN;
            case "amarillo":
                return Color.YELLOW;
            case "gris":
                return Color.GRAY;
            default:
                return Color.CYAN;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Atacar().setVisible(true));
    }
}
