package uy.edu.ucu.aed.tdas;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for implemented methods.
 */
public class GrafoDirigido_Junit5 {
    String instanceVariable;

    @BeforeEach
    public void setUp() {
        // Initialize any resources or objects needed for the tests
        instanceVariable = "Value before test";
    }

    @AfterEach
    public void tearDown() {
        // Release any resources or clean up after the tests
        instanceVariable = null;
    }

    @Test
    public void testGrafoVacio() {
        TGrafoDirigido grafo = new TGrafoDirigido(Collections.emptyList(), Collections.emptyList());
        TCaminos caminos = grafo.todosLosCaminos("A", "B");
        assertNotNull(caminos);
        assertEquals(0, caminos.getCaminos().size());
    }

    @Test
    public void testOrigenNoExiste() {
        Collection<IVertice> vertices = new java.util.ArrayList<>();
        vertices.add(new TVertice("B"));
        vertices.add(new TVertice("C"));
        Collection<IArista> aristas = new java.util.ArrayList<IArista>() {
            {
                add(new TArista("B", "C", 2.0));
            }
        };
        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);
        TCaminos caminos = grafo.todosLosCaminos("A", "B");
        assertEquals(0, caminos.getCaminos().size());
    }

    @Test
    public void testDestinoNoExiste() {
        Collection<IVertice> vertices = new java.util.ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        Collection<IArista> aristas = new java.util.ArrayList<IArista>() {
            {
                add(new TArista("A", "B", 2.0));
            }
        };
        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);
        TCaminos caminos = grafo.todosLosCaminos("A", "C");
        assertEquals(0, caminos.getCaminos().size());
    }

    @Test
    public void testUnicoCamino() {
        Collection<IVertice> vertices = new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        vertices.add(new TVertice("C"));

        Collection<IArista> aristas = new java.util.ArrayList<IArista>() {
            {
                add(new TArista("A", "B", 1));
                add(new TArista("B", "C", 1));
            }
        };

        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);
        TCaminos caminos = grafo.todosLosCaminos("A", "C");
        assertEquals(1, caminos.getCaminos().size());
        assertEquals("A -> B -> C (Costo total: 2.0)"+ "\n", caminos.imprimirCaminos());
    }

    @Test
    public void testGrafoConCicloSinCaminoAlDestino() {
        Collection<IVertice> vertices = new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        vertices.add(new TVertice("C"));

        Collection<IArista> aristas = new java.util.ArrayList<IArista>() {
            {
                add(new TArista("A", "B", 1));
                add(new TArista("B", "C", 2));
                add(new TArista("C", "A", 1));
            }
        };

        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);
        TCaminos caminos = grafo.todosLosCaminos("A", "D");
        assertEquals(0, caminos.getCaminos().size());
    }

    @Test
    public void testOrigenIgualDestino() {
        Collection<IVertice> vertices = new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));

        Collection<IArista> aristas = new java.util.ArrayList<IArista>() {
            {
                add(new TArista("A", "B", 1));
                add(new TArista("B", "A", 2));
            }
        };

        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);
        TCaminos caminos = grafo.todosLosCaminos("A", "A");
        assertEquals(0, caminos.getCaminos().size());
        assertEquals("", caminos.imprimirCaminos());
    }

    @Test
    public void testMultiplesCaminos() {
        Collection<IVertice> vertices = new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        vertices.add(new TVertice("C"));
        vertices.add(new TVertice("D"));

        Collection<IArista> aristas = new java.util.ArrayList<IArista>() {
            {
                add(new TArista("A", "B", 1));
                add(new TArista("A", "C", 2));
                add(new TArista("B", "D", 1));
                add(new TArista("C", "D", 1));
            }
        };

        TGrafoDirigido grafo = new TGrafoDirigido(vertices, aristas);
        TCaminos caminos = grafo.todosLosCaminos("A", "D");
        assertEquals(2, caminos.getCaminos().size());
        assertEquals("A -> B -> D (Costo total: 2.0)"+ "\n" + "A -> C -> D (Costo total: 3.0)"+ "\n", caminos.imprimirCaminos());
    }
}