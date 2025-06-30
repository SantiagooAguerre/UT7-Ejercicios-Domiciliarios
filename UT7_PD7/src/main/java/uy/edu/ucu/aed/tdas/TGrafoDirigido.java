package uy.edu.ucu.aed.tdas;

import java.util.*;

public class TGrafoDirigido implements IGrafoDirigido {

    private Map<Comparable, IVertice> vertices; // vertices del grafo.

    public TGrafoDirigido(Collection<IVertice> vertices, Collection<IArista> aristas) {
        this.vertices = new HashMap<>();
        for (IVertice vertice : vertices) {
            insertarVertice(vertice.getEtiqueta());
        }
        for (IArista arista : aristas) {
            insertarArista(arista);
        }

    }

    /**
     * Metodo encargado de eliminar una arista dada por un origen y destino. En
     * caso de no existir la adyacencia, retorna falso. En caso de que las
     * etiquetas sean invalidas, retorna falso.
     *
     */
    public boolean eliminarArista(Comparable nomVerticeOrigen, Comparable nomVerticeDestino) {
        if ((nomVerticeOrigen != null) && (nomVerticeDestino != null)) {
            IVertice vertOrigen = buscarVertice(nomVerticeOrigen);
            if (vertOrigen != null) {
                return vertOrigen.eliminarAdyacencia(nomVerticeDestino);
            }
        }
        return false;
    }

    /**
     * Metodo encargado de verificar la existencia de una arista. Las etiquetas
     * pasadas por par�metro deben ser v�lidas.
     *
     * @return True si existe la adyacencia, false en caso contrario
     */
    public boolean existeArista(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        IVertice vertOrigen = buscarVertice(etiquetaOrigen);
        IVertice vertDestino = buscarVertice(etiquetaDestino);
        if ((vertOrigen != null) && (vertDestino != null)) {
            return vertOrigen.buscarAdyacencia(vertDestino) != null;
        }
        return false;
    }

    /**
     * Metodo encargado de verificar la existencia de un vertice dentro del
     * grafo.-
     *
     * La etiqueta especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return True si existe el vertice con la etiqueta indicada, false en caso
     *         contrario
     */
    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta) != null;
    }

    /**
     * Metodo encargado de verificar buscar un vertice dentro del grafo.-
     *
     * La etiqueta especificada como parametro debe ser valida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return El vertice encontrado. En caso de no existir, retorna nulo.
     */
    private IVertice buscarVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta);
    }

    /**
     * Metodo encargado de insertar una arista en el grafo (con un cierto
     * costo), dado su vertice origen y destino.- Para que la arista sea valida,
     * se deben cumplir los siguientes casos: 1) Las etiquetas pasadas por
     * parametros son v�lidas.- 2) Los vertices (origen y destino) existen
     * dentro del grafo.- 3) No es posible ingresar una arista ya existente
     * (miso origen y mismo destino, aunque el costo sea diferente).- 4) El
     * costo debe ser mayor que 0.
     *
     * @return True si se pudo insertar la adyacencia, false en caso contrario
     */
    public boolean insertarArista(IArista arista) {
        if ((arista.getEtiquetaOrigen() != null) && (arista.getEtiquetaDestino() != null)) {
            IVertice vertOrigen = buscarVertice(arista.getEtiquetaOrigen());
            IVertice vertDestino = buscarVertice(arista.getEtiquetaDestino());
            if ((vertOrigen != null) && (vertDestino != null)) {
                return vertOrigen.insertarAdyacencia(arista.getCosto(), vertDestino);
            }
        }
        return false;
    }

    /**
     * Metodo encargado de insertar un vertice en el grafo.
     *
     * No pueden ingresarse vertices con la misma etiqueta. La etiqueta
     * especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a ingresar.
     * @return True si se pudo insertar el vertice, false en caso contrario
     */
    public boolean insertarVertice(Comparable unaEtiqueta) {
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            IVertice vert = new TVertice(unaEtiqueta);
            getVertices().put(unaEtiqueta, vert);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    @Override
    public boolean insertarVertice(IVertice vertice) {
        Comparable unaEtiqueta = vertice.getEtiqueta();
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            getVertices().put(unaEtiqueta, vertice);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    public Object[] getEtiquetasOrdenado() {
        TreeMap<Comparable, IVertice> mapOrdenado = new TreeMap<>(this.getVertices());
        return mapOrdenado.keySet().toArray();
    }

    /**
     * @return the vertices
     */
    public Map<Comparable, IVertice> getVertices() {
        return vertices;
    }

    public TVertice centroDelGrafo() {
        if (vertices.isEmpty()) {
            return null;
        }

        double[] excentricidades = new double[vertices.size()];
        int i = 0;

        double max = 0;
        Comparable etiqueta = "";

        for (Comparable key : vertices.keySet()) {
            excentricidades[i] = (double) obtenerExcentricidad(key);

            if (excentricidades[i] > max) {
                max = excentricidades[i];
                etiqueta = key;
            }

            i++;
        }

        return (TVertice) vertices.get(etiqueta);
    }

    public Double[][] floyd() {
        Double[][] matrizCostos = UtilGrafos.obtenerMatrizCostos(vertices);
        Double[][] matrizFloyd = new Double[matrizCostos.length][matrizCostos[0].length];
        for (int i = 0; i < matrizCostos.length; i++) {
            for (int j = 0; j < matrizCostos[0].length; j++) {
                matrizFloyd[i][j] = matrizCostos[i][j];
            }
        }
        for (int k = 0; k < matrizFloyd.length; k++) {
            for (int i = 0; i < matrizFloyd[0].length; i++) {
                for (int j = 0; j < matrizFloyd[0].length; j++) {
                    if (matrizFloyd[i][k] + matrizFloyd[k][j] < matrizFloyd[i][j]) {
                        matrizFloyd[i][j] = matrizFloyd[i][k] + matrizFloyd[k][j];
                    }
                }
            }
        }
        return matrizFloyd;
    }

    public Comparable obtenerExcentricidad(Comparable etiquetaVertice) {
        Double[][] matrizFloyd = (Double[][]) floyd();

        int index = 0;
        int i = 0;

        for (Comparable key : vertices.keySet()) {
            if (key == etiquetaVertice) {
                index = i;
                break;
            }

            i++;
        }

        double excentricidad = 0;

        for (int j = 0; j < matrizFloyd.length; j++) {
            double distancia = matrizFloyd[index][j];
            if (distancia > excentricidad && distancia != Double.MAX_VALUE) {
                excentricidad = distancia;
            }
        }

        return excentricidad;
    }

    public boolean[][] warshall() {
        Double[][] matrizCostos = UtilGrafos.obtenerMatrizCostos(vertices);
        int n = matrizCostos.length;

        // Paso 1: construir la matriz de adyacencia
        boolean[][] matrizAdyacencia = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizCostos[i][j] != null && !matrizCostos[i][j].equals(Double.MAX_VALUE)) {
                    matrizAdyacencia[i][j] = true;
                }
            }
        }

        // Paso 2: inicializar matrizWarshall con la matriz de adyacencia
        boolean[][] matrizWarshall = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrizWarshall[i][j] = matrizAdyacencia[i][j];
            }
        }

        // Paso 3: aplicar el algoritmo de Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!matrizWarshall[i][j]) {
                        matrizWarshall[i][j] = matrizWarshall[i][k] && matrizWarshall[k][j];
                    }
                }
            }
        }

        return matrizWarshall;
    }

    @Override
    public boolean eliminarVertice(Comparable nombreVertice) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public Collection<TVertice> bpf() {
        desvisitarVertices();
        ArrayList<TVertice> resultado = new ArrayList<>();
        for (IVertice vertice : getVertices().values()) {
            if (!vertice.getVisitado()) {
                TVertice tVertice = (TVertice) vertice;
                resultado.addAll(bpf(tVertice.getEtiqueta()));
            }
        }

        return resultado;
    }

    @Override
    public Collection<TVertice> bpf(Comparable etiquetaOrigen) {
        IVertice vertice = buscarVertice(etiquetaOrigen);

        TVertice tVertice = (TVertice) vertice;
        desvisitarVertices();
        return bpf(tVertice);
    }

    // @Override
    // public Collection<TVertice> bpf(TVertice vertice) {
    // desvisitarVertices();
    // return bpf(vertice);
    // }

    private Collection<TVertice> bpf(IVertice vertice) {

        ArrayList<TVertice> resultado = new ArrayList<>();
        if (vertice != null && !vertice.getVisitado()) {
            vertice.setVisitado(true);
            resultado.add((TVertice) vertice);
            vertice.getAdyacentes().forEach((adyacente) -> {
                IAdyacencia ady = (IAdyacencia) adyacente;
                IVertice destino = ady.getDestino();
                if (!destino.getVisitado()) {
                    resultado.addAll(bpf((TVertice) destino));
                }
            });
        }
        return resultado;
    }

    @Override
    public void desvisitarVertices() {
        for (IVertice vertice : getVertices().values()) {
            vertice.setVisitado(false);
        }
    }

    @Override
    public TCaminos todosLosCaminos(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        desvisitarVertices();
        IVertice verticeOrigen = buscarVertice(etiquetaOrigen);
        if (verticeOrigen == null) {
            return new TCaminos();
        }
        TCamino camino = new TCamino((TVertice) verticeOrigen);
        TCaminos caminos = new TCaminos();
        return verticeOrigen.todosLosCaminos(etiquetaDestino, camino, caminos);
    }

    @Override
    public boolean tieneCiclo() {
        desvisitarVertices();
        int i = 0;
        boolean resultado = false;
        for (IVertice vertice : getVertices().values()) {
            TCamino camino = new TCamino((TVertice) vertice);
            Object[] etiquetas = getEtiquetasOrdenado();
            TCaminos caminos = new TCaminos();
            TCaminos todosLosCaminos = vertice.todosLosCaminosConCiclo((Comparable) etiquetas[i], camino, caminos);
            Object[] caminosExtra = todosLosCaminos.getCaminos().toArray();
            for (int j = 0; j < caminosExtra.length; j++) {
                resultado = vertice.tieneCiclo((TCamino) caminosExtra[j]);
                if (resultado) {
                    System.out.println(((TCamino) caminosExtra[j]).imprimirEtiquetas());
                }
            }
            i++;
        }
        return resultado;
    }

    public void listarTareas(List<IVertice> orden) {
    if (orden == null) {
        System.out.println("No se puede listar tareas: el grafo tiene un ciclo o está vacío.");
        return;
    }

    for (IVertice v : orden) {
        System.out.println(v.getEtiqueta());
    }
}


    @Override
    public ArrayList<IVertice> ordenParcial() {
        if (tieneCiclo() || getVertices().isEmpty()) {
            return null; // No se puede hacer orden topológico si hay ciclo o el grafo está vacío
        }

        desvisitarVertices(); // Asegurarse de que todos los vértices estén desvisitados
        LinkedList<IVertice> ordenados = new LinkedList<>();

        for (IVertice vertice : getVertices().values()) {
            if (!vertice.getVisitado()) {
                vertice.ordenTopologico(ordenados);
            }
        }

        // Convertimos el LinkedList a ArrayList como especifica la firma del método
        return new ArrayList<>(ordenados);
    }

}
