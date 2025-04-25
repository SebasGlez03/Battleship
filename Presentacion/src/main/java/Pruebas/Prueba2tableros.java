/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Entidades.Tablero;
import EntidadesDTO.BarcoDTO;
import EntidadesDTO.CasillaDTO;
import EntidadesDTO.CruceroDTO;
import EntidadesDTO.JugadorDTO;
import EntidadesDTO.NaveDTO;
import EntidadesDTO.PartidaDTO;
import EntidadesDTO.PortaAvionesDTO;
import EntidadesDTO.SubmarinoDTO;
import EntidadesDTO.TableroDTO;
import Patrones.BarcoBuilder;
import Patrones.CruceroBuilder;
import Patrones.NaveBuilder;
import Patrones.Observer;
import Patrones.PortaAvionesBuilder;
import Patrones.SubmarinoBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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
public class Prueba2tableros extends JFrame implements Observer {

    private PartidaDTO partida;
    private JButton[][] botonesJugador1 = new JButton[10][10];
    private JButton[][] botonesJugador2 = new JButton[10][10];
    private JComboBox<String> selectorBarco;
    private JComboBox<String> selectorTablero;
    private boolean esHorizontal = true;
    private NaveDTO naveActual;
    private boolean jugadoresListos = false;
    private boolean jugando = false;
    private CasillaDTO casillaSeleccionada;  // Casilla seleccionada para atacar
    private JButton btnAtacar;

    public Prueba2tableros() {
        JugadorDTO jugador1 = new JugadorDTO("Jugador 1", "Azul");
        JugadorDTO jugador2 = new JugadorDTO("Jugador 2", "Rojo");
        partida = new PartidaDTO(jugador1, jugador2);

        jugador1.getTableroPrincipal().agregarObservador(this);
        jugador2.getTableroPrincipal().agregarObservador(this);

        setTitle("Juego Batalla Naval");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        

        JPanel panelTableros = new JPanel(new GridLayout(1, 2));
        panelTableros.add(crearTablero(botonesJugador1, jugador1.getTableroPrincipal(), false));
        panelTableros.add(crearTablero(botonesJugador2, jugador2.getTableroPrincipal(), false)); // Cambié de "true" a "false"

        JPanel panelControles = new JPanel();
        selectorBarco = new JComboBox<>(new String[]{"Submarino", "Crucero", "Porta Aviones", "Barco"});
        selectorBarco.addActionListener(e -> seleccionarBarco());
        panelControles.add(new JLabel("Barco:"));
        panelControles.add(selectorBarco);

        selectorTablero = new JComboBox<>(new String[]{"Jugador 1", "Jugador 2"});
        panelControles.add(new JLabel("Colocar en:"));
        panelControles.add(selectorTablero);

        JButton btnOrientacion = new JButton("Cambiar orientación");
        btnOrientacion.addActionListener(e -> esHorizontal = !esHorizontal);
        panelControles.add(btnOrientacion);

        JButton btnListo = new JButton("Listo para jugar");
        btnListo.addActionListener(e -> iniciarJuego());
        panelControles.add(btnListo);
        
        JButton btnAtacar = new JButton("Atacar");
        btnAtacar.setEnabled(false);  // Inicialmente deshabilitado
        btnAtacar.addActionListener(e -> atacarCasilla());
        panelControles.add(btnAtacar);

        setLayout(new BorderLayout());
        add(panelTableros, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        seleccionarBarco();
    }

    private void iniciarJuego() {
        // Verifica si ambos jugadores han colocado todos los barcos
        if (partida.getJugador1().getTableroPrincipal().todosLosBarcosColocados()
                && partida.getJugador2().getTableroPrincipal().todosLosBarcosColocados()) {
            jugadoresListos = true;
            jugando = true;  // El juego comienza a partir de aquí
            JOptionPane.showMessageDialog(this, "Comienza el juego. ¡A disparar!");
            partida.ejecutarTurno(); // Inicia el primer turno
        } else {
            JOptionPane.showMessageDialog(this, "Ambos jugadores deben colocar todos sus barcos antes de comenzar.");
        }
    }

    private JPanel crearTablero(JButton[][] botones, TableroDTO tablero, boolean esEnemigo) {
    JPanel panel = new JPanel(new GridLayout(10, 10));
    for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
            final int fx = x, fy = y;
            botones[x][y] = new JButton();
            botones[x][y].setBackground(Color.LIGHT_GRAY);

            if (esEnemigo) {
                botones[x][y].addActionListener(e -> seleccionarCasilla(fx, fy));
            } else {
                // Si no es enemigo, entonces se permite colocar barcos
                botones[x][y].addActionListener(e -> colocarBarco(fx, fy));
            }

            panel.add(botones[x][y]);
        }
    }
    return panel;
}


    private void seleccionarCasilla(int x, int y) {
        // Selecciona la casilla y la marca
        casillaSeleccionada = partida.getJugadorActual().getTableroPrincipal().getCasilla(x, y);

        // Actualiza la interfaz para mostrar cuál casilla está seleccionada
        botonesJugador1[x][y].setBackground(Color.YELLOW);  // O cualquier color para resaltar la casilla seleccionada

        // Habilitar el botón "Atacar"
        btnAtacar.setEnabled(true);  // Suponiendo que tienes un botón de ataque
    }
    
    


    private void seleccionarBarco() {
        String tipo = (String) selectorBarco.getSelectedItem();
        NaveBuilder builder;
        switch (tipo) {
            case "Submarino":
                builder = new SubmarinoBuilder();
                break;
            case "Crucero":
                builder = new CruceroBuilder();
                break;
            case "Porta Aviones":
                builder = new PortaAvionesBuilder();
                break;
            case "Barco":
                builder = new BarcoBuilder();
                break;
            default:
                return;
        }
        builder.construirNombre();
        builder.construirTamano();
        builder.construirColor();
        naveActual = builder.getNave();
    }

    private void colocarBarco(int x, int y) {
        

        if (!jugadoresListos) {
            JugadorDTO jugador = selectorTablero.getSelectedIndex() == 0 ? partida.getJugadorActual() : (partida.getJugadorActual() == partida.getJugador1() ? partida.getJugador2() : partida.getJugador1());
            TableroDTO tablero = jugador.getTableroPrincipal();
            JButton[][] botones = selectorTablero.getSelectedIndex() == 0 ? botonesJugador1 : botonesJugador2;

            int tamano = naveActual.getTamano();
            if ((esHorizontal && y + tamano > 10) || (!esHorizontal && x + tamano > 10)) {
                JOptionPane.showMessageDialog(this, "No cabe en esa orientación.");
                return;
            }

            for (int i = 0; i < tamano; i++) {
                int xi = esHorizontal ? x : x + i;
                int yi = esHorizontal ? y + i : y;
                CasillaDTO casilla = tablero.getCasilla(xi, yi);
                if (casilla == null || !casilla.estaDisponible()) {
                    JOptionPane.showMessageDialog(this, "Casillas ocupadas.");
                    return;
                }
            }

            for (int i = 0; i < tamano; i++) {
                int xi = esHorizontal ? x : x + i;
                int yi = esHorizontal ? y + i : y;
                CasillaDTO casilla = tablero.getCasilla(xi, yi);
                casilla.setOcupada(true);
                botones[xi][yi].setBackground(Color.DARK_GRAY);
            }

            JOptionPane.showMessageDialog(this, naveActual.getNombre() + " colocado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se puede colocar barcos después de comenzar el juego.");
        }
    }

    private void atacarCasilla() {
    if (casillaSeleccionada == null) {
        JOptionPane.showMessageDialog(this, "Por favor selecciona una casilla para atacar.");
        return;
    }

    if (casillaSeleccionada.estaAtacada()) {
        JOptionPane.showMessageDialog(this, "Ya atacaste esta casilla.");
        return;
    }

    // Marca la casilla como impactada
    casillaSeleccionada.setAtacada(true);

    if (casillaSeleccionada.estaOcupada()) {
        // Si la casilla está ocupada (con un barco)
        botonesJugador2[casillaSeleccionada.getX()][casillaSeleccionada.getY()].setBackground(Color.RED);
        JOptionPane.showMessageDialog(this, "¡Impacto!");
    } else {
        // Si la casilla está vacía
        botonesJugador2[casillaSeleccionada.getX()][casillaSeleccionada.getY()].setBackground(Color.BLUE);
        JOptionPane.showMessageDialog(this, "Agua");
    }

    // Cambia de turno
    partida.cambiarTurno();
    partida.ejecutarTurno();

    // Desactiva el botón "Atacar" y resalta la casilla seleccionada
    btnAtacar.setEnabled(false);
    // Resalta la casilla para que se sepa que ya fue atacada
    botonesJugador2[casillaSeleccionada.getX()][casillaSeleccionada.getY()].setBackground(Color.GRAY);
}

    public void actualizar() {
        if (partida.getJugador1().getTableroPrincipal().todosLosBarcosHundidos()) {
            partida.finalizarPartida(partida.getJugador2());
        } else if (partida.getJugador2().getTableroPrincipal().todosLosBarcosHundidos()) {
            partida.finalizarPartida(partida.getJugador1());
        }

        if (partida.getGanador() != null) {
            JOptionPane.showMessageDialog(this, "¡Juego terminado! Ganador: " + partida.getGanador().getNombre());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Prueba2tableros().setVisible(true));
    }
}
