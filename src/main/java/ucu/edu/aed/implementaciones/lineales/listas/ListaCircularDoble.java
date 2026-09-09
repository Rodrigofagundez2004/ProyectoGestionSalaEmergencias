package ucu.edu.aed.implementaciones.lineales.listas;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.implementaciones.lineales.NodoDoble;
import ucu.edu.aed.tda.lineales.TDALista;

/**
 * Implementación de {@link TDALista} mediante una lista circular
 * doblemente enlazada.
 *
 * <p>Se mantiene una referencia al primer nodo ({@code primero}), cuyo
 * {@code anterior} apunta siempre al último nodo, y viceversa, cerrando
 * el círculo en ambos sentidos. Gracias a contar con nodo anterior y
 * siguiente, el recorrido hacia un índice puede iniciarse desde el
 * extremo más cercano, reduciendo a la mitad la cantidad de pasos en
 * el peor caso respecto de una lista circular simplemente enlazada.</p>
 *
 * @param <T> el tipo de los elementos almacenados en la lista
 */
public class ListaCircularDoble<T> implements TDALista<T> {

    protected NodoDoble<T> primero;
    protected int tamaño;

    private NodoDoble<T> nodoEn(int index) {
        NodoDoble<T> actual;

        if (index <= tamaño / 2) {
            actual = this.primero;
            for (int i = 0; i < index; i++) {
                actual = actual.getSiguiente();
            }
        } else {
            actual = this.primero.getAnterior();
            for (int i = tamaño - 1; i > index; i--) {
                actual = actual.getAnterior();
            }
        }

        return actual;
    }

    @Override
    public void agregar(T elem) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(elem, null, null);

        if (this.primero == null) {
            nuevoNodo.setAnterior(nuevoNodo);
            nuevoNodo.setSiguiente(nuevoNodo);
            this.primero = nuevoNodo;
        } else {
            NodoDoble<T> ultimo = this.primero.getAnterior();

            nuevoNodo.setAnterior(ultimo);
            nuevoNodo.setSiguiente(this.primero);
            ultimo.setSiguiente(nuevoNodo);
            this.primero.setAnterior(nuevoNodo);
        }
        this.tamaño++;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > this.tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        if (index == this.tamaño) {
            agregar(elem);
            return;
        }

        NodoDoble<T> siguiente = nodoEn(index);
        NodoDoble<T> anterior = siguiente.getAnterior();
        NodoDoble<T> nuevoNodo = new NodoDoble<>(elem, anterior, siguiente);

        anterior.setSiguiente(nuevoNodo);
        siguiente.setAnterior(nuevoNodo);
        if (index == 0) {
            this.primero = nuevoNodo;
        }
        this.tamaño++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= this.tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return nodoEn(index).getDato();
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= this.tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        NodoDoble<T> eliminado = nodoEn(index);

        if (this.tamaño == 1) {
            this.primero = null;
        } else {
            NodoDoble<T> anterior = eliminado.getAnterior();
            NodoDoble<T> siguiente = eliminado.getSiguiente();

            anterior.setSiguiente(siguiente);
            siguiente.setAnterior(anterior);
            if (eliminado == this.primero) {
                this.primero = siguiente;
            }
        }
        eliminado.setAnterior(null);
        eliminado.setSiguiente(null);
        this.tamaño--;

        return eliminado.getDato();
    }

    @Override
    public boolean remover(T elem) {
        int index = indiceDe(elem);

        if (index == -1) {
            return false;
        }
        remover(index);

        return true;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {
        NodoDoble<T> actual = this.primero;

        for (int i = 0; i < this.tamaño; i++) {
            boolean esIgual = (elem == null && actual.getDato() == null)
                    || (elem != null && elem.equals(actual.getDato()));

            if (esIgual) {
                return i;
            }
            actual = actual.getSiguiente();
        }

        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        NodoDoble<T> actual = this.primero;

        for (int i = 0; i < this.tamaño; i++) {
            if (criterio.test(actual.getDato())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaCircularDoble<T> nuevaLista = new ListaCircularDoble<>();
        NodoDoble<T> actual = this.primero;

        for (int i = 0; i < this.tamaño; i++) {
            int j = 0;

            while (j < nuevaLista.tamaño() && comparator.compare(actual.getDato(), nuevaLista.obtener(j)) > 0) {
                j++;
            }
            nuevaLista.agregar(j, actual.getDato());
            actual = actual.getSiguiente();
        }

        return nuevaLista;
    }

    @Override
    public int tamaño() {
        return this.tamaño;
    }

    @Override
    public boolean esVacio() {
        return this.tamaño == 0;
    }

    @Override
    public void vaciar() {
        this.primero = null;
        this.tamaño = 0;
    }
}
