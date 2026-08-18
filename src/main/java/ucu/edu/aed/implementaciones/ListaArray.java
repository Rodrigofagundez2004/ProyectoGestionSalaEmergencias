package ucu.edu.aed.implementaciones;

import ucu.edu.aed.tda.TDALista;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class ListaArray <T> implements TDALista<T> {

    private static final int CAPACIDAD_INICIAL = 10;
    private T[] elementos;
    private int tamaño;

    public ListaArray() {
        this.elementos = (T[]) new Object[CAPACIDAD_INICIAL]; /* Debido al dise;o de java, no se
        puede crear directamente un array de tipo generico, por lo que se debe
        crear un array de tipo Object y luego convertirlo a su tipo real mediante un cast.
        */
        this.tamaño = 0;
    }

    @Override
    public void agregar(T elem) {
        if (tamaño == elementos.length) {
            elementos = Arrays.copyOf(elementos, elementos.length * 2);
        }
        elementos[tamaño++] = elem;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        if (tamaño == elementos.length) {
            elementos = Arrays.copyOf(elementos, elementos.length * 2);
        }
        System.arraycopy(elementos, index, elementos, index + 1, tamaño - index);
        elementos[index] = elem;
        tamaño++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return elementos[index];
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        T eliminado = elementos[index];
        if (index < tamaño - 1) { // Solo desplazamos los elementos si no es el último
            System.arraycopy(elementos, index + 1, elementos, index, tamaño - index - 1);
        }
        elementos[--tamaño] = null; 
        return eliminado;
    }

    @Override
    public boolean remover(T elem) {
        for (int i = 0; i < tamaño; i++) {
            if ((elem == null && elementos[i] == null) || (elem != null && elementos[i].equals(elem))) {
                remover(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contiene(T elem) {
        if (elem == null) {
            for (int i = 0; i < tamaño; i++) {
                if (elementos[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                if (elementos[i].equals(elem)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int indiceDe(T elem) {
        if (elem == null) {
            for (int i = 0; i < tamaño; i++) {
                if (elementos[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                if (elementos[i].equals(elem)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        for (int i = 0; i < tamaño; i++) {
            if (criterio.test(elementos[i])) {
                return elementos[i];
            }
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        Arrays.sort(elementos, 0, tamaño, Comparator.nullsFirst(comparator)); // Manejo nulls primero
        return this;
    }

    @Override
    public int tamaño() {
        return tamaño;
    }

    @Override
    public boolean esVacio() {
        return tamaño == 0;
    }

    @Override
    public void vaciar() {
        for (int i = 0; i < tamaño; i++) {
            elementos[i] = null;
        }
        tamaño = 0;
    }
}