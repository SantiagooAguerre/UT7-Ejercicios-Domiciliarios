package uy.edu.ucu.aed.tas;

import uy.edu.ucu.aed.tdas.*;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TGrafoDirigido gd = UtilGrafos.cargarGrafo("./src/aeropuertos_2.txt", "./src/conexiones_2.txt",
                false, TGrafoDirigido.class);
        System.out.println(gd.tieneCiclo());
        System.out.println(gd.ordenTopologico());
    }
}
