/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDTO;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.player.Player;

/**
 *
 * @author Oley
 */
/**
 * Clase que permite cargar, reproducir, pausar, detener y controlar el volumen
 * de un archivo de audio usando la API javax.sound.sampled.
 */
public class Musica {
    // Objeto para reproducir el audio

    private Clip clip;
    // Control para ajustar el volumen del clip

    private FloatControl volumenControl;
    // Archivo de audio actualmente cargado

    private File archivoActual;

    /**
     * Carga un archivo de audio desde la ruta especificada.
     *
     * @param ruta Ruta del archivo de audio.
     * @return true si se cargó correctamente, false si hubo un error.
     */
    public boolean cargarArchivo(String ruta) {
        try {
            archivoActual = new File(ruta);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(archivoActual);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumenControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }

            return true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reproduce el archivo de audio desde el inicio.
     */
    public void reproducir() {
        if (clip != null) {
            clip.setFramePosition(0); // desde el inicio
            clip.start();
        }
    }

    /**
     * Pausa la reproducción si está corriendo.
     */
    public void pausar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Detiene y cierra la reproducción del audio.
     */
    public void detener() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * Ajusta el volumen del audio.
     *
     * @param volumen Valor entre 0.0  y 1.0.
     */
    public void setVolumen(float volumen) {
        if (volumenControl != null) {
            if (volumen <= 0f) {
                volumenControl.setValue(volumenControl.getMinimum());
            } else {
                float dB = (float) (Math.log10(volumen) * 20);
                volumenControl.setValue(dB);
            }
        } else {
            System.out.println("Control de volumen no soportado");
        }
    }

    /**
     * Devuelve el archivo de audio actualmente cargado.
     *
     * @return Archivo de audio actual.
     */
    public File getArchivoActual() {
        return archivoActual;
    }

}
