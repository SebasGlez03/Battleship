/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Musica;

import  EntidadesDTO.Musica;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Oley
 */
public class MusicaControlador {
      private Musica modelo;

    public MusicaControlador( ) {
        this.modelo = new Musica();
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "REPRODUCIR":
                reproducir();
                break;
            case "PAUSAR":
                pausar();
                break;
            case "DETENER":
                detener();
                break;
            case "SELECCIONAR":
                seleccionarArchivo("C:\\\\Users\\\\Oley\\\\Documents\\\\GitHub\\\\ProyectoDS\\\\BaseDatos2024_2025\\\\Battleship\\\\ambient-piano-logo-165357.wav"); // deberás pasarla manualmente
                break;
            case "VOLUMEN":
                ajustarVolumen(0.5f); // volumen entre 0.0 y 1.0
                break;
        }
    }

    private void reproducir() {
        if (modelo.getArchivoActual() != null) {
            modelo.reproducir();
            System.out.println("Reproduciendo");
        } else {
            System.out.println("No hay archivo seleccionado");
        }
    }

    private void pausar() {
        modelo.pausar();
        System.out.println("Pausado");
    }

    private void detener() {
        modelo.detener();
        System.out.println("Detenido");
    }

    private void seleccionarArchivo(String rutaArchivo) {
        if (modelo.cargarArchivo(rutaArchivo)) {
            File archivo = new File(rutaArchivo);
            System.out.println("Archivo cargado: " + archivo.getName());
        } else {
            System.out.println("Error al cargar el archivo");
        }
    }

    public void ajustarVolumen(float volumen) {
           modelo.setVolumen(volumen);

    }
      public static void main(String[] args) {
        Musica modelo = new Musica();
        MusicaControlador controlador = new MusicaControlador();

        String ruta = "C:\\Users\\Oley\\Documents\\GitHub\\ProyectoDS\\BaseDatos2024_2025\\Battleship\\ambient-piano-logo-165357.mp3";

     
        boolean cargado = modelo.cargarArchivo(ruta);
        if (cargado) {
            System.out.println("Archivo cargado correctamente");
            modelo.reproducir();
        } else {
            System.out.println("No se pudo cargar el archivo (¿es MP3?)");
        }
    }
}
