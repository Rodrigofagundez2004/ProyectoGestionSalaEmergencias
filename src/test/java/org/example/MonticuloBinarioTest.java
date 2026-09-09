package org.example;

import junit.framework.TestCase;
import ucu.edu.aed.implementaciones.jerarquicas.monticuloBinario.MonticuloBinario;

public class MonticuloBinarioTest extends TestCase {
    private MonticuloBinario<Integer> monticulo;

    @Override
    protected void setUp() {
        monticulo = new MonticuloBinario<>();
    }

    /*
        TESTS CASES PARA EL ESTADO INICIAL DEL MONTICULO
    */

    // Se verifica que un monticulo nuevo este vacio y tenga tamano cero
    public void testMonticuloNuevoEstaVacioYTieneTamanoCero() {
        assertTrue(monticulo.estaVacio());
        assertEquals(0, monticulo.tamano());
    }

    /*
        TESTS CASES PARA LA INSERCION DE ELEMENTOS
    */

    // Se inserta un unico elemento y se verifica que pase a ser la raiz
    public void testInsertarUnElementoActualizaTamanoYRaiz() {
        monticulo.insertar(7);

        assertFalse(monticulo.estaVacio());
        assertEquals(1, monticulo.tamano());
        assertEquals(Integer.valueOf(7), monticulo.obtener());
    }

    // Se inserta un elemento menor y se verifica que ocupe la raiz del min-heap
    public void testInsertarElementoMenorLoColocaComoRaiz() {
        monticulo.insertar(10);
        monticulo.insertar(4);
        monticulo.insertar(8);

        assertEquals(Integer.valueOf(4), monticulo.obtener());
        assertEquals(3, monticulo.tamano());
    }

    // Se consulta la raiz sin eliminarla del monticulo
    public void testObtenerNoEliminaElElemento() {
        monticulo.insertar(5);
        monticulo.insertar(2);

        assertEquals(Integer.valueOf(2), monticulo.obtener());
        assertEquals(Integer.valueOf(2), monticulo.obtener());
        assertEquals(2, monticulo.tamano());
    }

    /*
        TESTS CASES PARA LA ELIMINACION DE ELEMENTOS
    */

    // Se eliminan todos los elementos y se verifica el orden ascendente del min-heap
    public void testEliminarRetornaElementosEnOrdenAscendente() {
        monticulo.insertar(7);
        monticulo.insertar(1);
        monticulo.insertar(9);
        monticulo.insertar(3);
        monticulo.insertar(1);

        assertEquals(Integer.valueOf(1), monticulo.eliminar());
        assertEquals(Integer.valueOf(1), monticulo.eliminar());
        assertEquals(Integer.valueOf(3), monticulo.eliminar());
        assertEquals(Integer.valueOf(7), monticulo.eliminar());
        assertEquals(Integer.valueOf(9), monticulo.eliminar());
        assertTrue(monticulo.estaVacio());
        assertEquals(0, monticulo.tamano());
    }

    // Se elimina el unico elemento y se verifica que el monticulo quede vacio
    public void testEliminarUltimoElementoDejaElMonticuloVacio() {
        monticulo.insertar(12);

        assertEquals(Integer.valueOf(12), monticulo.eliminar());
        assertTrue(monticulo.estaVacio());
        assertEquals(0, monticulo.tamano());
    }

    // Se elimina la raiz y se verifica que el monticulo se reorganice correctamente
    public void testEliminarReorganizaElMonticuloDespuesDeQuitarLaRaiz() {
        monticulo.insertar(2);
        monticulo.insertar(6);
        monticulo.insertar(4);
        monticulo.insertar(9);
        monticulo.insertar(1);

        assertEquals(Integer.valueOf(1), monticulo.eliminar());
        assertEquals(Integer.valueOf(2), monticulo.obtener());
        assertEquals(Integer.valueOf(2), monticulo.eliminar());
        assertEquals(Integer.valueOf(4), monticulo.obtener());
    }

    // Se insertan elementos en orden descendente y se verifica que se conserve el minimo
    public void testInsertarElementosEnOrdenDescendenteMantieneElMinimo() {
        monticulo.insertar(5);
        monticulo.insertar(4);
        monticulo.insertar(3);
        monticulo.insertar(2);
        monticulo.insertar(1);

        assertEquals(Integer.valueOf(1), monticulo.obtener());
    }

    /*
        TESTS CASES PARA OPERACIONES SOBRE UN MONTICULO VACIO
    */

    // Se intenta obtener un elemento de un monticulo vacio y se espera una excepcion
    public void testObtenerEnMonticuloVacioLanzaExcepcion() {
        try {
            monticulo.obtener();
            fail("Se esperaba IllegalStateException");
        } catch (IllegalStateException exception) {
            // Comportamiento esperado.
        }
    }

    // Se intenta eliminar un elemento de un monticulo vacio y se espera una excepcion
    public void testEliminarEnMonticuloVacioLanzaExcepcion() {
        try {
            monticulo.eliminar();
            fail("Se esperaba IllegalStateException");
        } catch (IllegalStateException exception) {
            // Comportamiento esperado.
        }
    }
}
