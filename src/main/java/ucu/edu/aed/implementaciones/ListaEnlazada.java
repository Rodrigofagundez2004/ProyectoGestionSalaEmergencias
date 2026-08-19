package ucu.edu.aed.implementaciones;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.tda.TDALista;

public class ListaEnlazada<T> implements TDALista<T> {
    protected Nodo<T> primero;

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento, null);
        Nodo<T> actual = this.primero;

        if (actual == null) {
            this.primero = nuevoNodo;
        } else {
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
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
        if (actual == null || index == 0) {
            nuevoNodo.setSiguiente(actual);
            this.primero = nuevoNodo;
        } else {
            int i = 1;

            while (i < index && actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
                i++;
            }
            Nodo<T> nodoProximoAlSiguiente = actual.getSiguiente();

            actual.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(nodoProximoAlSiguiente);
        }
    }

    /**
     * Obtiene el elemento almacenado en la posición indicada.
     *
     * @param index la posición del elemento a recuperar
     * @return el elemento ubicado en la posición indicada
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    @Override
    public T obtener(int index) {
        Nodo<T> actual = this.primero;
        int i = 0;
        
        if (index < 0 || index > this.tamaño()) {
            throw new IndexOutOfBoundsException();
        }
        while (i < index) {
            actual = actual.getSiguiente();
            i++;
        }

        return actual.getDato();
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
            T primerDato = actual.getDato();
            this.primero = actual.getSiguiente();
            actual.setSiguiente(null);

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
     * Remueve la primera ocurrencia del elemento indicado en la lista.
     *
     * <p>
     * La comparación del elemento queda sujeta al criterio definido
     * por la implementación, normalmente mediante {@code equals}.
     * </p>
     *
     * @param elemento el elemento a remover
     * @return {@code true} si el elemento fue encontrado y removido;
     *         {@code false} en caso contrario
     */
    @Override
    public boolean remover(T elemento) {
        Nodo<T> actual = this.primero;
        Nodo<T> eliminado;

        if (actual == null) {
            return false;
        }
        if (actual.getDato().equals(elemento)) {
            this.primero = actual.getSiguiente();

            return true;
        }
        while (actual.getSiguiente() != null) {
            T datoActual = actual.getSiguiente().getDato();

            if (datoActual.equals(elemento)) {
                eliminado = actual.getSiguiente();
                actual.setSiguiente(eliminado.getSiguiente());
                eliminado.setSiguiente(null);

                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
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
        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        }

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

        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                return i;
            }
            actual = actual.getSiguiente();
            i++;
        }

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

        while (actual != null) {
            if (criterio.test(actual.getDato())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

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

        while (actual != null) {
            j = 0;

            while (j < nuevaLista.tamaño() && comparator.compare(actual.getDato(), nuevaLista.obtener(j)) > 0) {
                j++;
            }
            nuevaLista.agregar(j, actual.getDato());
            actual = actual.getSiguiente();
        }

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

        while (actual != null) {
            i++;
            actual = actual.getSiguiente();
        }

        return i;
    }

    /**
     * Determina si la lista no contiene elementos.
     *
     * @return {@code true} si la lista está vacía;
     *         {@code false} en caso contrario
     */
    @Override
    public boolean esVacio() {
        return this.primero == null;
    }

    /**
     * Elimina todos los elementos de la lista.
     *
     * <p>
     * Luego de invocar este método, la lista queda vacía.
     * </p>
     */
    @Override
    public void vaciar() {
        this.primero = null;
    }

    
}