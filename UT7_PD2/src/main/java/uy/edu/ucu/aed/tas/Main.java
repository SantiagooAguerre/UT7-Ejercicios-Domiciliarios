package uy.edu.ucu.aed.tas;

import uy.edu.ucu.aed.tdas.*;

import java.util.Collection;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TGrafoDirigido gd = UtilGrafos.cargarGrafo("./src/PD2_Ej1 - Vertices.txt", "./src/PD2_Ej1 - Aristas.txt",
                false, TGrafoDirigido.class);

        /** EJERCICIO 1 **/
        Double[][] matriz = UtilGrafos.obtenerMatrizCostos(gd.getVertices());
        UtilGrafos.imprimirMatrizMejorado(matriz, gd.getVertices(), "Matriz");

        /** EJERCICIO 2 **/
        UtilGrafos.imprimirMatrizMejorado(gd.floyd(), gd.getVertices(), "Matriz luego de FLOYD");

        /** EJERCICIO 3 **/
        Object[] etiquetasarray = gd.getEtiquetasOrdenado();
        for (int i = 0; i < etiquetasarray.length; i++) {
            System.out.println("excentricidad de " + etiquetasarray[i] + " : " + gd.obtenerExcentricidad((Comparable) etiquetasarray[i]));
        }
        System.out.println();
        System.out.println("Centro del grafo: " + gd.centroDelGrafo());

    }
}
