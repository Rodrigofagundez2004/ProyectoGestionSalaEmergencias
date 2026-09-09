package ucu.edu.aed.implementaciones.lineales.colas;

import java.util.Comparator;
import java.util.NoSuchElementException;

import ucu.edu.aed.implementaciones.lineales.pilas.Pila;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;
import ucu.edu.aed.implementaciones.lineales.listas.ListaEnlazada;
import ucu.edu.aed.tda.lineales.TDACola;

/**
 * Implementación de {@link TDACola} en la que cada elemento se atiende
 * según su prioridad y no según su orden de llegada.
 *
 * <p>Se apoya en {@link ListaDoblementeEnlazada}, igual que {@link Pila}
 * y {@link Cola} se apoyan en {@link ListaEnlazada}. Los elementos se
 * insertan siempre en la posición que les corresponde según el
 * {@link Comparator} recibido, de modo que el frente de la cola
 * ({@code obtener(0)}) sea siempre el de mayor prioridad. Entre elementos
 * de igual prioridad se respeta el orden de llegada (FIFO).</p>
 *
 * @param <T> el tipo de los elementos almacenados en la cola
 */
public class ColaConPrioridad<T> extends ListaDoblementeEnlazada<T> implements TDACola<T> {

    private final Comparator<T> comparador;

    /**
     * Crea una cola con prioridad vacía.
     *
     * @param comparador determina la prioridad entre dos elementos:
     *                    un valor positivo indica que el primer argumento
     *                    tiene mayor prioridad que el segundo
     */
    public ColaConPrioridad(Comparator<T> comparador) {
        this.comparador = comparador;
    }

    @Override
    public T frente() {
        if (esVacio()) {
            throw new NoSuchElementException("La cola con prioridad está vacía.");
        }
        return obtener(0);
    }

    /**
     * Inserta el dato en la posición que le corresponde según su prioridad.
     *
     * @param dato el elemento a insertar
     * @return {@code true} si el elemento fue agregado correctamente;
     *         {@code false} si el dato es nulo
     */
    @Override
    public boolean poneEnCola(T dato) {
        if (dato == null) {
            return false;
        }

        int j = 0;
        while (j < tamaño() && comparador.compare(obtener(j), dato) >= 0) {
            j++;
        }
        agregar(j, dato);

        return true;
    }

    @Override
    public T quitaDeCola() {
        if (esVacio()) {
            throw new NoSuchElementException("La cola con prioridad está vacía.");
        }
        return remover(0);
    }
}
