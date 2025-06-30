package uy.edu.ucu.aed.tas;

import uy.edu.ucu.aed.tdas.*;

import java.util.Collection;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TGrafoDirigido gd = UtilGrafos.cargarGrafo("./src/aeropuertos.txt", "./src/conexiones.txt",
                false, TGrafoDirigido.class);

        /** EJERCICIO 1 **/
        Double[][] matriz = UtilGrafos.obtenerMatrizCostos(gd.getVertices());
        UtilGrafos.imprimirMatrizMejorado(matriz, gd.getVertices(), "VUELO SEGURO");
        UtilGrafos.imprimirMatrizMejorado(gd.floyd(), gd.getVertices(), "VUELO SEGURO (Floyd)");
        // 1. El costo de volar de Montevideo a Rio de Janeiro es: b) 3780
        // 2. El costo de volar de Montevideo a Curitiba es: a) 2580
        System.out.println(gd.centroDelGrafo());
        // 3. Los servicios de mantenimiento se instalan en: c) Curitiba

        /** EJERCICIO 2 **/
        System.out.println(gd.hayCamino("Montevideo", "Curitiba"));
        // ¿Existen conexión(es) entre Montevideo y Curitiba? Sí, existen conexiones.
        System.out.println(gd.hayCamino("Porto_Alegre", "Santos"));
        // ¿Existen conexión(es) entre Porto Alegre y Santos? Sí, existen conexiones.

        /** EJERCICIO 3 **/
        // 1.
        System.out.println(gd.bpf());
        System.out.println();

        // 2. El orden de ejecución es O(a), siendo a el número de aristas del grafo.

        // 3.
        System.out.println(gd.bpf("Montevideo"));
        System.out.println();

        // 4.
        System.out.println("ITINERARIOS POSIBLES DE MONTEVIDEO A RIO DE JANEIRO");
        gd.todosLosCaminos("Montevideo", "Rio_de_Janeiro").imprimirCaminosConsola();
    }
}
