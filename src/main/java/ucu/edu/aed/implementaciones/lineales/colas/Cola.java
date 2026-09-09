package ucu.edu.aed.implementaciones.lineales.colas;

import ucu.edu.aed.implementaciones.lineales.Nodo;
import ucu.edu.aed.implementaciones.lineales.listas.ListaEnlazada;
import ucu.edu.aed.tda.lineales.TDACola;

public class Cola<T> extends ListaEnlazada<T> implements TDACola<T> {

    /**
     * Nodo que apunta al último elemento ingresado en la cola (frente de salida).
     */
    protected Nodo<T> frente;

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
     * Retorna el dato del elemento que se encuentra al frente de la cola.
     *
     * @return el dato del elemento al frente de la cola
     * @throws NullPointerException si la cola está vacía
     */
    @Override
    public T frente() {
        if (this.frente == null) {
            throw new NullPointerException("No hay elementos en la cola.");
        }

        return this.frente.getDato();
    }

    /**
     * Agrega un elemento al final de la cola.
     *
     * @param dato el elemento a agregar
     * @return {@code true} si el elemento fue agregado correctamente;
     *         {@code false} si el dato es nulo
     */
    @Override
    public boolean poneEnCola(T dato) {
        if (dato != null) {
            if (this.frente == null) {
                this.primero = new Nodo<>(dato, null);
                this.frente = this.primero;

                return true;
            }
            Nodo<T> nodo = new Nodo<>(dato, null);
            this.frente.setSiguiente(nodo);
            this.frente = nodo;

            return true;
        }
        
        return false;
    }

    /**
     * Remueve y retorna el elemento que se encuentra al inicio de la cola.
     *
     * <p>
     * El elemento removido es el que lleva más tiempo en la cola,
     * siguiendo el principio FIFO.
     * </p>
     *
     * @return el elemento removido, o {@code null} si la cola está vacía
     */
    @Override
    public T quitaDeCola() {
        if (this.primero == null) {
            return null;
        }
        Nodo<T> nodoEliminado = this.primero;
        this.primero = nodoEliminado.getSiguiente();

        if (this.primero == null) {
            this.frente = null;
        }
        nodoEliminado.setSiguiente(null);

        return nodoEliminado.getDato();
    }

    
}