package ucu.edu.aed.implementaciones.lineales.listas;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.implementaciones.lineales.Nodo;
import ucu.edu.aed.tda.lineales.TDALista;

public class ListaEnlazadaCircular<T> extends ListaEnlazada<T> implements TDALista<T> {
    protected Nodo<T> primero;

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento, null);
        Nodo<T> actual = this.primero;

        if (actual == null) {
            this.primero = nuevoNodo;
            nuevoNodo.setSiguiente(nuevoNodo);
        } else {
            while (actual.getSiguiente() != this.primero) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(this.primero);
        }
    }

    /**
     * Agrega un elemento en la posición indicada.
     *
     * <p>
     * Los elementos ubicados desde esa posición en adelante
     * desplazan su índice una posición hacia la derecha.
     * </p>
     *
     * @param index la posición en la que se insertará el elemento
     * @param elemento  el elemento a agregar
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    @Override
    public void agregar(int index, T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento, null);
        Nodo<T> actual = this.primero;

        if (index < 0 || index > this.tamaño()) {
            throw new IndexOutOfBoundsException();
        }
        if (actual == null) {
            nuevoNodo.setSiguiente(nuevoNodo);
            this.primero = nuevoNodo;
            return;
        }
        if (index == 0) {
            Nodo<T> ultimo = actual;

            while (ultimo.getSiguiente() != this.primero) {
                ultimo = ultimo.getSiguiente();
            }

            ultimo.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(this.primero);
            this.primero = nuevoNodo;
        }
        else {
            int i = 1;

            while (i < index && actual.getSiguiente() != this.primero) {
                actual = actual.getSiguiente();
                i++;
            }
            Nodo<T> nodoProximoAlSiguiente = actual.getSiguiente();

            actual.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(nodoProximoAlSiguiente);
        }
    }

    /**
     * Remueve y devuelve el elemento almacenado en la posición indicada.
     *
     * <p>
     * Los elementos posteriores, si existen, desplazan su índice
     * una posición hacia la izquierda.
     * </p>
     *
     * @param index la posición del elemento a remover
     * @return el elemento removido
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    @Override
    public T remover(int index) {
        Nodo<T> actual = this.primero;
        Nodo<T> nodoEliminado;
        int i = 1;

        if (index < 0 || index > this.tamaño()) {
            throw new IndexOutOfBoundsException();
        }
        if (actual == null) {
            return null;
        }
        if (index == 0) {
            if (actual.getSiguiente() == this.primero) {
                this.primero = null;
                actual.setSiguiente(null);
                return actual.getDato();
            }

            Nodo<T> ultimo = actual;

            while (ultimo.getSiguiente() != this.primero) {
                ultimo = ultimo.getSiguiente();
            }

            T primerDato = actual.getDato();
            this.primero = actual.getSiguiente();
            actual.setSiguiente(null);
            ultimo.setSiguiente(this.primero);

            return primerDato;
        }
        while (i < index) {
            actual = actual.getSiguiente();
            i++;
        }
        nodoEliminado = actual.getSiguiente();
        actual.setSiguiente(nodoEliminado.getSiguiente());
        nodoEliminado.setSiguiente(null);

        return nodoEliminado.getDato();
    }

    /**
     * Determina si la lista contiene el elemento indicado.
     *
     * <p>
     * La comparación del elemento queda sujeta al criterio definido
     * por la implementación, normalmente mediante {@code equals}.
     * </p>
     *
     * @param elemento el elemento a buscar
     * @return {@code true} si el elemento está presente en la lista;
     *         {@code false} en caso contrario
     */
    @Override
    public boolean contiene(T elemento) {
        Nodo<T> actual = this.primero;

        if (actual == null) {
            return false;
        }
        do { 
            if (actual.getDato().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != this.primero);

        return false;
    }

    /**
     * Retorna el índice de la primera ocurrencia del elemento indicado.
     *
     * <p>
     * La comparación del elemento queda sujeta al criterio definido
     * por la implementación, normalmente mediante {@code equals}.
     * </p>
     *
     * @param elemento el elemento a buscar
     * @return el índice de la primera ocurrencia del elemento, o {@code -1}
     *         si el elemento no se encuentra en la lista
     */
    @Override
    public int indiceDe(T elemento) {
        Nodo<T> actual = this.primero;
        int i = 0;

        do { 
            if (actual.getDato().equals(elemento)) {
                return i;
            }
            actual = actual.getSiguiente();
            i++;
        } while (actual != this.primero);

        return -1;
    }

    /**
     * Busca y retorna el primer elemento que cumple con el criterio dado.
     *
     * @param criterio el predicado que define la condición de búsqueda
     * @return el primer elemento que cumple el criterio, o {@code null}
     *         si no existe ninguno
     */
    @Override
    public T buscar(Predicate<T> criterio) {
        Nodo<T> actual = this.primero;

        if (actual == null) {
            return null;
        }

        do { 
            if (criterio.test(actual.getDato())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        } while (actual != this.primero);

        return null;
    }

    /**
     * Retorna una nueva lista con los elementos ordenados 
     * según el comparador dado.
     *
     * <p>
     * El criterio de orden está determinado por el objeto {@link Comparator}
     * recibido como parámetro.
     * </p>
     *
     * @param comparator el comparador que define el orden de los elementos
     * @return una lista ordenada según el criterio indicado
     */
    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        Nodo<T> actual = this.primero;
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<>();
        int j;

        do { 
            j = 0;

            while (j < nuevaLista.tamaño() && comparator.compare(actual.getDato(), nuevaLista.obtener(j)) > 0) {
                j++;
            }
            nuevaLista.agregar(j, actual.getDato());
            actual = actual.getSiguiente();
        } while (actual != this.primero);

        return nuevaLista;
    }

    /**
     * Retorna la cantidad de elementos almacenados en la lista.
     *
     * @return la cantidad de elementos de la lista
     */
    @Override
    public int tamaño() {
        int i = 0;
        Nodo<T> actual = this.primero;

        do { 
            i++;
            actual = actual.getSiguiente();
        } while (actual != this.primero);

        return i;
    }
}