package uy.edu.ucu.aed.tdas;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class TransferenciaFiable {
    public static class Arista {
        int destino;
        double probabilidad;

        public Arista(int destino, double probabilidad) {
            this.destino=destino;
            this.probabilidad=probabilidad;
        }
    }

    public static double[] caminoMasFiable(List<List<Arista>> grafo, int inicio) {
        int n=grafo.size();
        double[] prob=new double[n];
        Arrays.fill(prob, 0.0);
        prob[inicio] =1.0;

        PriorityQueue<int[]> cola=new PriorityQueue<>(
                (a, b) -> Double.compare(prob[b[0]], prob[a[0]])
        );
        cola.offer(new int[]{inicio});

        while (!cola.isEmpty()) {
            int actual=cola.poll()[0];
            for (Arista arista:grafo.get(actual)) {
                double nuevaProb=prob[actual] * arista.probabilidad;
                if (nuevaProb>prob[arista.destino]) {
                    prob[arista.destino]=nuevaProb;
                    cola.offer(new int[]{arista.destino});
                }
            }
        }

        return prob;
    }

}
