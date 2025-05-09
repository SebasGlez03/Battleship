/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Musica;

import EntidadesDTO.Musica;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Oley
 */
/**
 * Clase que actúa como controlador para la reproducción de música. Se encarga
 * de manejar las acciones del usuario y controlar el modelo Musica.
 */
public class MusicaControlador {
    // Modelo que gestiona la reproducción de audio

    private Musica modelo;

    /**
     * Constructor del controlador. Inicializa el modelo de música.
     */
    public MusicaControlador() {
        this.modelo = new Musica();
    }
 /**
     * Maneja las acciones ejecutadas por el usuario (simulado).
     * @param e Acción que contiene el comando a ejecutar.
     */
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
    // Reproduce el archivo si hay uno cargado

    private void reproducir() {
        if (modelo.getArchivoActual() != null) {
            modelo.reproducir();
            System.out.println("Reproduciendo");
        } else {
            System.out.println("No hay archivo seleccionado");
        }
    }
    // Pausa la reproducción

    private void pausar() {
        modelo.pausar();
        System.out.println("Pausado");
    }
    // Detiene y cierra el clip

    private void detener() {
        modelo.detener();
        System.out.println("Detenido");
    }
/**
     * Carga un archivo de audio desde una ruta.
     * @param rutaArchivo Ruta del archivo de audio.
     */
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
/**
     * Ajusta el volumen de la reproducción.
     * @param volumen Valor entre 0.0 y 1.0.
     */
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
