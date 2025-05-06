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
public class Musica {
       private Clip clip;
    private FloatControl volumenControl;
    private File archivoActual;

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

    public void reproducir() {
        if (clip != null) {
            clip.setFramePosition(0); // desde el inicio
            clip.start();
        }
    }

    public void pausar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void detener() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

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

    public File getArchivoActual() {
        return archivoActual;
    }


    
}
