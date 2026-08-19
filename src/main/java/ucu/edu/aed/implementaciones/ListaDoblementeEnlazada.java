package ucu.edu.aed.implementaciones;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.tda.TDALista;

/**
 * Implementación de {@link TDALista} mediante una lista doblemente enlazada
 * (no circular).
 *
 * <p>Se mantienen referencias al primer y al último nodo, y un contador de
 * elementos, lo que permite que {@code tamaño()} sea O(1) y que se pueda
 * agregar al final en O(1). Al contar con referencia al nodo anterior,
 * el recorrido hacia un índice se realiza desde el extremo más cercano
 * (inicio o final), lo que en el peor caso reduce a la mitad la cantidad
 * de pasos respecto de una lista simplemente enlazada.</p>
 *
 * @param <T> el tipo de los elementos almacenados en la lista
 */
public class ListaDoblementeEnlazada<T> implements TDALista<T> {

    protected NodoDoble<T> primero;
    protected NodoDoble<T> ultimo;
    protected int tamaño;

    /**
     * Devuelve el nodo ubicado en la posición indicada.
     *
     * <p>Recorre desde el inicio o desde el final, según cuál extremo
     * esté más cerca del índice buscado.</p>
     *
     * @param index la posición del nodo a buscar (se asume válida)
     * @return el nodo ubicado en esa posición
     */
    private NodoDoble<T> nodoEn(int index) {
        NodoDoble<T> actual;

        if (index <= tamaño / 2) {
            actual = this.primero;
            for (int i = 0; i < index; i++) {
                actual = actual.getSiguiente();
            }
        } else {
            actual = this.ultimo;
            for (int i = tamaño - 1; i > index; i--) {
                actual = actual.getAnterior();
            }
        }

        return actual;
    }

    @Override
    public void agregar(T elem) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(elem, this.ultimo, null);

        if (this.ultimo == null) {
            this.primero = nuevoNodo;
        } else {
            this.ultimo.setSiguiente(nuevoNodo);
        }
        this.ultimo = nuevoNodo;
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
        if (index == 0) {
            NodoDoble<T> nuevoNodo = new NodoDoble<>(elem, null, this.primero);

            if (this.primero != null) {
                this.primero.setAnterior(nuevoNodo);
            } else {
                this.ultimo = nuevoNodo;
            }
            this.primero = nuevoNodo;
            this.tamaño++;
            return;
        }

        NodoDoble<T> siguiente = nodoEn(index);
        NodoDoble<T> anterior = siguiente.getAnterior();
        NodoDoble<T> nuevoNodo = new NodoDoble<>(elem, anterior, siguiente);

        anterior.setSiguiente(nuevoNodo);
        siguiente.setAnterior(nuevoNodo);
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

        NodoDoble<T> nodoEliminado = nodoEn(index);
        NodoDoble<T> anterior = nodoEliminado.getAnterior();
        NodoDoble<T> siguiente = nodoEliminado.getSiguiente();

        if (anterior != null) {
            anterior.setSiguiente(siguiente);
        } else {
            this.primero = siguiente;
        }
        if (siguiente != null) {
            siguiente.setAnterior(anterior);
        } else {
            this.ultimo = anterior;
        }
        nodoEliminado.setSiguiente(null);
        nodoEliminado.setAnterior(null);
        this.tamaño--;

        return nodoEliminado.getDato();
    }

    @Override
    public boolean remover(T elem) {
        NodoDoble<T> actual = this.primero;

        while (actual != null) {
            boolean esIgual = (elem == null && actual.getDato() == null)
                    || (elem != null && elem.equals(actual.getDato()));

            if (esIgual) {
                NodoDoble<T> anterior = actual.getAnterior();
                NodoDoble<T> siguiente = actual.getSiguiente();

                if (anterior != null) {
                    anterior.setSiguiente(siguiente);
                } else {
                    this.primero = siguiente;
                }
                if (siguiente != null) {
                    siguiente.setAnterior(anterior);
                } else {
                    this.ultimo = anterior;
                }
                actual.setSiguiente(null);
                actual.setAnterior(null);
                this.tamaño--;

                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {
        NodoDoble<T> actual = this.primero;
        int i = 0;

        while (actual != null) {
            boolean esIgual = (elem == null && actual.getDato() == null)
                    || (elem != null && elem.equals(actual.getDato()));

            if (esIgual) {
                return i;
            }
            actual = actual.getSiguiente();
            i++;
        }

        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        NodoDoble<T> actual = this.primero;

        while (actual != null) {
            if (criterio.test(actual.getDato())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaDoblementeEnlazada<T> nuevaLista = new ListaDoblementeEnlazada<>();
        NodoDoble<T> actual = this.primero;

        while (actual != null) {
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
        this.ultimo = null;
        this.tamaño = 0;
    }
}
