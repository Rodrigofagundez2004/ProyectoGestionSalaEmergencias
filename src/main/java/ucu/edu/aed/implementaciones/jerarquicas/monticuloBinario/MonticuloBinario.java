package ucu.edu.aed.implementaciones.jerarquicas.monticuloBinario;

import ucu.edu.aed.tda.jerarquico.TDAMonticuloBinario;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de un montículo binario mínimo (min-heap).
 */
public class MonticuloBinario<T extends Comparable<T>> implements TDAMonticuloBinario<T> {

    private List<T> heap;

    /**
     * Constructor para inicializar el montículo binario.
     */
    public MonticuloBinario() {
        this.heap = new ArrayList<>();
    }

    @Override
    public void insertar(T elemento) {
        heap.add(elemento);          // Agregar al final
        subir(heap.size() - 1);      // Reorganizar hacia arriba
    }

    @Override
    public T eliminar() {
        if (estaVacio()) {
            throw new IllegalStateException("El montículo está vacío.");
        }
        T raiz = heap.get(0);        // Obtener el valor raíz (mínimo)
        T ultimo = heap.remove(heap.size() - 1); // Eliminar el último elemento
        if (!heap.isEmpty()) {
            heap.set(0, ultimo);    // Reemplazar la raíz con el último elemento
            bajar(0);               // Reorganizar hacia abajo
        }
        return raiz;
    }

    @Override
    public T obtener() {
        if (estaVacio()) {
            throw new IllegalStateException("El montículo está vacío.");
        }
        return heap.get(0); // El valor mínimo será siempre la raíz (índice 0)
    }

    @Override
    public boolean estaVacio() {
        return heap.isEmpty();
    }

    @Override
    public int tamano() {
        return heap.size();
    }

    /**
     * Método privado para reorganizar el montículo hacia arriba.
     *
     * @param indice Índice del nodo que se evaluará.
     */
    private void subir(int indice) {
        int padre = (indice - 1) / 2; // Índice del nodo padre
        while (indice > 0 && heap.get(indice).compareTo(heap.get(padre)) < 0) {
            intercambiar(indice, padre);
            indice = padre;
            padre = (indice - 1) / 2;
        }
    }

    /**
     * Método privado para reorganizar el montículo hacia abajo.
     *
     * @param indice Índice del nodo que se evaluará.
     */
    private void bajar(int indice) {
        int menor = indice;
        int izquierdo = 2 * indice + 1;
        int derecho = 2 * indice + 2;

        if (izquierdo < heap.size() && heap.get(izquierdo).compareTo(heap.get(menor)) < 0) {
            menor = izquierdo;
        }

        if (derecho < heap.size() && heap.get(derecho).compareTo(heap.get(menor)) < 0) {
            menor = derecho;
        }

        if (menor != indice) { // Si el hijo menor es más pequeño que el nodo actual
            intercambiar(indice, menor);
            bajar(menor); // Continuar reorganización desde el nuevo índice
        }
    }

    /**
     * Método privado para intercambiar dos elementos en el montículo.
     *
     * @param i Índice del primer elemento.
     * @param j Índice del segundo elemento.
     */
    private void intercambiar(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}