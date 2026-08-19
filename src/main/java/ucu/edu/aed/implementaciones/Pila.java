package ucu.edu.aed.Implementaciones;

import ucu.edu.aed.tda.TDAPila;

public class Pila<T> extends ListaEnlazada<T> implements TDAPila<T> {
    /**
     * Nodo que representa el tope de la pila.
     */
    protected Nodo<T> tope;

    /**
     * Retorna el elemento ubicado en el tope de la pila sin eliminarlo.
     *
     * @return el dato en el tope de la pila, o {@code null} si la pila está vacía
     */
    @Override
    public T tope() {
        if (this.tope == null) {
            return null;
        }
        
        return this.tope.getDato();
    }

    /**
     * Remueve y retorna el elemento en el tope de la pila.
     *
     * @return el elemento removido, o {@code null} si la pila está vacía
     */
    @Override
    public T saca() {
        if (this.tope != null) {
            Nodo<T> nodoEliminado = this.tope;
            this.tope = nodoEliminado.getSiguiente();
            nodoEliminado.setSiguiente(null);

            return nodoEliminado.getDato();
        }

        return null;
    }

    /**
     * Inserta un nuevo elemento en el tope de la pila.
     *
     * @param dato el elemento a insertar
     */
    @Override
    public void mete(T dato) {
        Nodo<T> nuevoTope = new Nodo<>(dato, this.tope);
        this.tope = nuevoTope;
    }

    
}