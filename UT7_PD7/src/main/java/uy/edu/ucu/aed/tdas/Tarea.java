package uy.edu.ucu.aed.tdas;

public class Tarea {
    private String codigo;
    private int tiempo;

    public Tarea(String codigo, int tiempo) {
        this.codigo = codigo;
        this.tiempo = tiempo;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getTiempo() {
        return tiempo;
    }

    @Override
    public String toString() {
        return codigo;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tarea && ((Tarea) obj).codigo.equals(this.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}

