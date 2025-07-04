package uy.edu.ucu.aed.tdas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TVertice<T> implements IVertice {

    private final Comparable etiqueta;
    private LinkedList<IAdyacencia> adyacentes;
    private boolean visitado;
    private T datos;

    public Comparable getEtiqueta() {
        return etiqueta;
    }

    public LinkedList<IAdyacencia> getAdyacentes() {
        return adyacentes;
    }

    public TVertice(Comparable unaEtiqueta) {
        this.etiqueta = unaEtiqueta;
        adyacentes = new LinkedList();
        visitado = false;
    }

    public void setVisitado(boolean valor) {
        this.visitado = valor;
    }

    public boolean getVisitado() {
        return this.visitado;
    }

    @Override
    public IAdyacencia buscarAdyacencia(IVertice verticeDestino) {
        if (verticeDestino != null) {
            return buscarAdyacencia(verticeDestino.getEtiqueta());
        }
        return null;
    }

    @Override
    public Double obtenerCostoAdyacencia(IVertice verticeDestino) {
        IAdyacencia ady = buscarAdyacencia(verticeDestino);
        if (ady != null) {
            return ady.getCosto();
        }
        return Double.MAX_VALUE;
    }

    @Override
    public boolean insertarAdyacencia(Double costo, IVertice verticeDestino) {
        if (buscarAdyacencia(verticeDestino) == null) {
            TAdyacencia ady = new TAdyacencia(costo, verticeDestino);
            return adyacentes.add(ady);
        }
        return false;
    }

    @Override
    public boolean eliminarAdyacencia(Comparable nomVerticeDestino) {
        IAdyacencia ady = buscarAdyacencia(nomVerticeDestino);
        if (ady != null) {
            adyacentes.remove(ady);
            return true;
        }
        return false;
    }

    @Override
    public IVertice primerAdyacente() {
        if (this.adyacentes.getFirst() != null) {
            return this.adyacentes.getFirst().getDestino();
        }
        return null;
    }

    public IVertice siguienteAdyacente(IVertice w) {
        IAdyacencia adyacente = buscarAdyacencia(w.getEtiqueta());
        int index = adyacentes.indexOf(adyacente);
        if (index + 1 < adyacentes.size()) {
            return adyacentes.get(index + 1).getDestino();
        }
        return null;
    }

    @Override
    public IAdyacencia buscarAdyacencia(Comparable etiquetaDestino) {
        for (IAdyacencia adyacencia : adyacentes) {
            if (adyacencia.getDestino().getEtiqueta().compareTo(etiquetaDestino) == 0) {
                return adyacencia;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public T getDatos() {
        return datos;
    }

    @Override
    public TCaminos todosLosCaminos(Comparable etVertDest, TCamino caminoPrevio, TCaminos todosLosCaminos) {
        this.setVisitado(true);
        for (IAdyacencia adyacencia : this.getAdyacentes()) {
            TVertice destino = (TVertice) adyacencia.getDestino();
            if (!destino.getVisitado()) {
                if (destino.getEtiqueta().compareTo(etVertDest) == 0) {
                    TCamino copia = caminoPrevio.copiar();
                    copia.agregarAdyacencia((TAdyacencia) adyacencia);
                    todosLosCaminos.getCaminos().add(copia);
                } else {
                    TCamino copia = caminoPrevio.copiar();
                    copia.agregarAdyacencia((TAdyacencia) adyacencia);
                    destino.todosLosCaminos(etVertDest, copia, todosLosCaminos);
                }
            }
        }
        this.setVisitado(false);
        return todosLosCaminos;
    }

    @Override
    public TCaminos todosLosCaminosConCiclo(Comparable etVertDest, TCamino caminoPrevio, TCaminos todosLosCaminos) {
        this.setVisitado(true);
        for (IAdyacencia adyacencia : this.getAdyacentes()) {
            TVertice destino = (TVertice) adyacencia.getDestino();
            TCamino copia = caminoPrevio.copiar();
            copia.agregarAdyacencia((TAdyacencia) adyacencia);
            if (destino.getEtiqueta().compareTo(etVertDest) == 0) {
                if (copia.getOtrosVertices().size() > 0) {
                    todosLosCaminos.getCaminos().add(copia);
                }
            } else {
                if (!caminoPrevio.getOtrosVertices().contains(destino.getEtiqueta())) {
                    destino.todosLosCaminosConCiclo(etVertDest, copia, todosLosCaminos);
                } else
                    todosLosCaminos.getCaminos().add(copia);
            }

        }
        this.setVisitado(false);
        return todosLosCaminos;
    }

    @Override
    public String toString() {
        return etiqueta.toString();
    }

    @Override
    public boolean tieneCiclo(TCamino unCamino) {
        boolean resultado = false;
        Collection<Comparable> otrosVertices = unCamino.getOtrosVertices();
        TVertice origen = unCamino.getOrigen();
        ArrayList<Comparable> aux = new ArrayList<>();
        aux.add(origen.getEtiqueta());
        for (Comparable vertices : otrosVertices) {
            if (aux.contains(vertices)) {
                resultado = true;
            }
            aux.add(vertices);
        }
        return resultado;
    }

    @Override
    public LinkedList<IVertice> ordenParcial(List<IVertice> vertices) {
        LinkedList<IVertice> resultado = new LinkedList<>();
        for (IVertice vertice : vertices) {
            if (vertice.getVisitado()) {
                continue;
            }
            resultado.add(vertice);
            vertice.setVisitado(true);
            LinkedList<IVertice> adyacentes = new LinkedList<>();
            for (IAdyacencia adyacencia : vertice.getAdyacentes()) {
                IVertice destino = adyacencia.getDestino();
                if (!destino.getVisitado()) {
                    adyacentes.add(destino);
                }
            }
            while (!adyacentes.isEmpty()) {
                IVertice adyacente = adyacentes.removeFirst();
                resultado.add(adyacente);
                adyacente.setVisitado(true);
                for (IAdyacencia ady : adyacente.getAdyacentes()) {
                    IVertice destino = ady.getDestino();
                    if (!destino.getVisitado() && !adyacentes.contains(destino)) {
                        adyacentes.add(destino);
                    }
                }
            }
        }
        return resultado;
    }

    @Override
    public boolean existeCamino(Comparable destino, LinkedList<TVertice> visitados) {
    if (this.etiqueta.equals(destino)) {
        return true;
    }
    visitados.add(this);
    for (IAdyacencia adyacencia : this.adyacentes) {
        TVertice adyacente = (TVertice) adyacencia.getDestino();
        if (!visitados.contains(adyacente)) {
            if (adyacente.existeCamino(destino, visitados)) {
                return true;
            }
        }
    }
    return false;
}


}
