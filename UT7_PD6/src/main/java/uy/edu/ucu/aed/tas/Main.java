package uy.edu.ucu.aed.tas;

import uy.edu.ucu.aed.tdas.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        TransferenciaFiable transferenciaFiable = new TransferenciaFiable();

        /****** EJERCICIO 14.23 ******/

        List<String> cursos = Arrays.asList("Matematicas", "Fisica", "Quimica", "Programacion", "Algoritmos", "Ciencias de la Computacion");
        
        List<String[]> prerrequisitos = new ArrayList<>();
        prerrequisitos.add(new String[]{"Matematicas", "Fisica"});
        prerrequisitos.add(new String[]{"Matematicas", "Programacion"});
        prerrequisitos.add(new String[]{"Fisica", "Quimica"});
        prerrequisitos.add(new String[]{"Programacion", "Algoritmos"});
        prerrequisitos.add(new String[]{"Algoritmos", "Ciencias de la Computacion"});
        
        int minSemestres = PlanDeCursos.calcMinSemestres(cursos, prerrequisitos);
        
        if (minSemestres == -1) {
            System.out.println("Hay un ciclo en los prerrequisitos, no se puede completar la carrera.");
        } else {
            System.out.println("El mínimo número de semestres para completar los cursos es: " + minSemestres);
        }

        /****** EJERCICIO 14.25 ******/

        int n = 5; // Computadoras
        List<List<TransferenciaFiable.Arista>>grafo= new ArrayList<>();
        for (int i=0;i<n;i++) grafo.add(new ArrayList<>());
        grafo.get(0).add(new TransferenciaFiable.Arista(1, 0.9));
        grafo.get(0).add(new TransferenciaFiable.Arista(2, 0.5));
        grafo.get(1).add(new TransferenciaFiable.Arista(2, 0.7));
        grafo.get(1).add(new TransferenciaFiable.Arista(3, 0.6));
        grafo.get(2).add(new TransferenciaFiable.Arista(3, 0.9));
        grafo.get(3).add(new TransferenciaFiable.Arista(4, 0.8));

        double[] resultado = transferenciaFiable.caminoMasFiable(grafo, 0);

        System.out.println("Probabilidades más fiables desde el nodo 0:");
        for (int i = 0; i < resultado.length; i++) {
            System.out.printf("A nodo %d: %.4f\n", i, resultado[i]);
        }
    }
}
