package ucu.edu.aed.implementaciones.lineales;

/**
 * Nodo utilizado por las listas doblemente enlazadas (lineales y circulares).
 *
 * <p>A diferencia de {@link Nodo}, mantiene además una referencia al nodo
 * anterior, lo que permite recorrer la estructura en ambos sentidos.</p>
 *
 * @param <T> el tipo del dato almacenado en el nodo
 */
//Nodo muy parecido a la implementacion que ya teniamos de un nodo simple, pero claramente con la DOBLE referencia que se necesita para la estrctura de datos
public class NodoDoble<T> {

    private T dato;
    private NodoDoble<T> anterior;
    private NodoDoble<T> siguiente;

    public NodoDoble() {

    }

    public NodoDoble(T dato, NodoDoble<T> anterior, NodoDoble<T> siguiente) {
        this.dato = dato;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }

    public T getDato() {
        return this.dato;
    }

    public NodoDoble<T> getAnterior() {
        return this.anterior;
    }

    public NodoDoble<T> getSiguiente() {
        return this.siguiente;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }

    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }
}
