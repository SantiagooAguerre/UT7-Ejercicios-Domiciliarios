package uy.edu.ucu.aed.tas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uy.edu.ucu.aed.tdas.*;
import uy.edu.ucu.aed.utils.ManejadorArchivosGenerico;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ejecutar("./src/tareas.txt", "./src/precedencias.txt", "./src/resultado.txt");
    }

    public static void ejecutar(String archivoTareas, String archivoPrecedencias, String archivoSalida) {
        // Leer líneas de archivo usando ManejadorArchivosGenerico
        String[] lineasTareas = ManejadorArchivosGenerico.leerArchivo(archivoTareas, false);
        String[] lineasPrecedencias = ManejadorArchivosGenerico.leerArchivo(archivoPrecedencias, false);

        // Construir lista de vértices
        Collection<IVertice> vertices = new ArrayList<>();
        for (String linea : lineasTareas) {
            String[] partes = linea.split(",");
            String codigo = partes[0].trim();
            vertices.add(new TVertice(codigo));
        }

        // Construir lista de aristas
        Collection<IArista> aristas = new ArrayList<>();
        for (String linea : lineasPrecedencias) {
            String[] partes = linea.split(",");
            String desde = partes[0].trim();
            String hasta = partes[1].trim();
            aristas.add(new TArista(desde, hasta, 1));
        }

        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);

        List<IVertice> orden = grafo.ordenParcial();
        grafo.listarTareas(orden);
        guardarOrden(orden, archivoSalida);
    }

    public static void guardarOrden(List<IVertice> orden, String archivoSalida) {
        if (orden == null) {
            System.out.println("No se puede guardar archivo: el grafo tiene un ciclo o está vacío.");
            return;
        }

        String[] lineas = new String[orden.size()];
        for (int i = 0; i < orden.size(); i++) {
            lineas[i] = orden.get(i).getEtiqueta().toString();
        }

        ManejadorArchivosGenerico.escribirArchivo(archivoSalida, lineas);
        System.out.println("Archivo generado: " + archivoSalida);
    }
}
