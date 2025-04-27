/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Prueba;
import EntidadesDTO.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Carlo
 */
public class PruebaDisparos extends JFrame {

    private PartidaDTO partida;
    private JButton[][] botonesDisparo;
    private JLabel lblTurno;

//    public PruebaDisparos(JugadorDTO jugador1, JugadorDTO jugador2) {
//        setTitle("Fase de Disparos - Batalla Naval");
//        setSize(600, 650);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        partida = new PartidaDTO(jugador1, jugador2);
//
//        botonesDisparo = new JButton[10][10];
//        JPanel panelTablero = new JPanel(new GridLayout(10, 10));
//        JPanel panelSuperior = new JPanel(new BorderLayout());
//
//        // Label para indicar de quién es el turno
//        lblTurno = new JLabel("Turno de: " + partida.getJugadorActual().getNombre(), SwingConstants.CENTER);
//        lblTurno.setFont(new Font("Arial", Font.BOLD, 20));
//        panelSuperior.add(lblTurno, BorderLayout.CENTER);
//
//        // Crear botones de disparo
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                final int x = i;
//                final int y = j;
//                botonesDisparo[i][j] = new JButton();
//                botonesDisparo[i][j].setBackground(Color.LIGHT_GRAY);
//                botonesDisparo[i][j].addActionListener((ActionEvent e) -> disparar(x, y));
//                panelTablero.add(botonesDisparo[i][j]);
//            }
//        }
//
//        setLayout(new BorderLayout());
//        add(panelSuperior, BorderLayout.NORTH);
//        add(panelTablero, BorderLayout.CENTER);
//    }

//    private void disparar(int x, int y) {
//        JugadorDTO oponente = (partida.getJugadorActual() == partida.getJugador1())
//            ? partida.getJugador2() : partida.getJugador1();
//
//        CasillaDTO objetivo = oponente.getTableroPrincipal().getCasilla(x, y);
//
//        if (objetivo == null) {
//            JOptionPane.showMessageDialog(this, "¡Coordenada inválida!");
//            return;
//        }
//
//        if (objetivo.estaImpactada()) {
//            JOptionPane.showMessageDialog(this, "¡Ya disparaste aquí!");
//            return;
//        }
//
//        objetivo.impactar();
//
//        if (casillaPerteneceANave(oponente, x, y)) {
//            botonesDisparo[x][y].setBackground(Color.RED);
//            JOptionPane.showMessageDialog(this, "¡Impacto!");
//
//            if (todasNavesHundidas(oponente)) {
//                partida.finalizarPartida(partida.getJugadorActual());
//                JOptionPane.showMessageDialog(this, "¡Ganador: " + partida.getGanador().getNombre() + "!");
//                System.exit(0);
//            }
//        } else {
//            botonesDisparo[x][y].setBackground(Color.WHITE);
//            JOptionPane.showMessageDialog(this, "¡Agua!");
//            partida.cambiarTurno();
//        }
//
//        lblTurno.setText("Turno de: " + partida.getJugadorActual().getNombre());
//    }

    private boolean casillaPerteneceANave(JugadorDTO jugador, int x, int y) {
//        List<NaveDTO> naves = jugador.getNaves();
//
//        for (NaveDTO nave : naves) {
//            for (CasillaDTO casilla : nave.getPosiciones()) {
//                if (casilla.getX() == x && casilla.getY() == y) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    private boolean todasNavesHundidas(JugadorDTO jugador) {
//        List<NaveDTO> naves = jugador.getNaves();
//
//        for (NaveDTO nave : naves) {
//            if (!nave.estaHundida()) {
//                return false;
//            }
//        }
        return true;
    }

//    public static void main(String[] args) {
//        // PRUEBA: Crear jugadores de prueba (¡deberían tener naves colocadas antes!)
//        JugadorDTO jugador1 = new JugadorDTO("Jugador 1", "Rojo");
//        JugadorDTO jugador2 = new JugadorDTO("Jugador 2", "Azul");
//
//        // Normalmente aquí deberías cargar sus naves correctamente
//
//        SwingUtilities.invokeLater(() -> new PruebaDisparos(jugador1, jugador2).setVisible(true));
//    }
}
