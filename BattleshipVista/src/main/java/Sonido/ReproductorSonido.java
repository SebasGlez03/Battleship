/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sonido;


import javax.sound.sampled.*;
import java.io.*;

/**
 *
 * @author Oley
 */
public class ReproductorSonido {
    
    /**
     *
     * @param rutaSonido
     */
    public static void reproducirSonido(String rutaSonido) {
        try {
            InputStream audioSrc = ReproductorSonido.class.getResourceAsStream(rutaSonido);
            if (audioSrc == null) {
                System.err.println("Archivo de sonido no encontrado: " + rutaSonido);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido: " + e.getMessage());
        }
    }
}
