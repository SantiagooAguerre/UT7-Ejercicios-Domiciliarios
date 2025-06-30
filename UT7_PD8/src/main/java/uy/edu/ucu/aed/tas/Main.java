package uy.edu.ucu.aed.tas;

import uy.edu.ucu.aed.tdas.*;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TGrafoDirigido gd = UtilGrafos.cargarGrafo("./src/aeropuertos.txt", "./src/conexiones.txt",
                false, TGrafoDirigido.class);

        TipoArcos arcosArbol = new TipoArcos();
        TipoArcos arcosRetroceso = new TipoArcos();
        TipoArcos arcosAvance = new TipoArcos();
        TipoArcos arcosCruzados = new TipoArcos();
        gd.clasificarArcos("Buenos_Aires", arcosArbol, arcosRetroceso, arcosAvance, arcosCruzados);
        System.out.println("Imprimir Arcos de árbol: ");
        arcosArbol.imprimir();
        System.out.println("Imprimir Arcos de retroceso: ");
        arcosRetroceso.imprimir();
        System.out.println("Imprimir Arcos de avance: ");
        arcosAvance.imprimir();
        System.out.println("Imprimir Arcos de cruzados: ");
        arcosCruzados.imprimir();
    }
}
