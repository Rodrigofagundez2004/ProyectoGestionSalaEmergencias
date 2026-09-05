package ucu.edu.aed.implementaciones;

import ucu.edu.aed.tda.TDAArbolGenerico;
import java.util.function.Consumer;
import java.util.Queue;
import java.util.LinkedList;

public class ArbolGenerico<T> implements TDAArbolGenerico<T> {

    private NodoGenerico<T> raiz;

    public ArbolGenerico(T datoRaiz) {
        if (datoRaiz != null) {
            this.raiz = new NodoGenerico<>(datoRaiz);
        } else {
            this.raiz = null;
        }
    }

    @Override
    public T obtenerRaiz() {
        return (raiz != null) ? raiz.getDato() : null;
    }

    @Override
    public boolean insertarRaiz(T dato) {
        if (raiz == null && dato != null) {
            raiz = new NodoGenerico<>(dato);
            return true;
        }
        return false; // No se puede insertar raíz en un árbol con una raíz ya existente.
    }

    @Override
    public boolean insertar(Comparable<T> criterioPadre, T datoHijo) {
        if (raiz == null || criterioPadre == null || datoHijo == null) {
            return false;
        }

        // Buscar el nodo padre según el criterio
        NodoGenerico<T> nodoPadre = raiz.buscar(criterioPadre);
        if (nodoPadre != null) {
            return nodoPadre.insertarHijo(datoHijo); // Insertar hijo en el nodo padre encontrado
        }
        return false;
    }

    @Override
    public T buscar(Comparable<T> criterioBusqueda) {
        if (raiz == null || criterioBusqueda == null) {
            return null;
        }

        NodoGenerico<T> nodo = raiz.buscar(criterioBusqueda);
        return (nodo != null) ? nodo.getDato() : null;
    }

    @Override
    public boolean contiene(Comparable<T> criterioBusqueda) {
        return (raiz != null) && raiz.contiene(criterioBusqueda);
    }

    @Override
    public boolean eliminar(Comparable<T> criterioBusqueda) {
        if (raiz == null || criterioBusqueda == null) {
            return false;
        }

        // Si el nodo raíz es el que debe eliminarse
        if (criterioBusqueda.compareTo(raiz.getDato()) == 0) {
            if (raiz.getPrimerHijo() != null) {
                NodoGenerico<T> nuevoRaiz = raiz.getPrimerHijo();
                NodoGenerico<T> resto = nuevoRaiz.getHermanoDerecho();
                nuevoRaiz.setHermanoDerecho(null);
                this.raiz = nuevoRaiz;

                NodoGenerico<T> ultimoHijo = nuevoRaiz;
                while (ultimoHijo.getHermanoDerecho() != null) {
                    ultimoHijo = ultimoHijo.getHermanoDerecho();
                }

                ultimoHijo.setHermanoDerecho(resto);
                return true;
            }
            this.raiz = null; // Si la raíz no tiene hijos, vaciamos el árbol
            return true;
        }

        return raiz.eliminar(criterioBusqueda) != null;
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.preOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.postOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public void porNiveles(Consumer<T> consumidor) {
        if (raiz == null || consumidor == null) {
            return;
        }

        Queue<NodoGenerico<T>> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            NodoGenerico<T> nodoActual = cola.poll();
            consumidor.accept(nodoActual.getDato());

            NodoGenerico<T> hijo = nodoActual.getPrimerHijo();
            while (hijo != null) {
                cola.add(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    @Override
    public int cantidadNodos() {
        return (raiz == null) ? 0 : raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas() {
        return (raiz == null) ? 0 : raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos() {
        return (raiz == null) ? 0 : raiz.cantidadNodosInternos();
    }

    @Override
    public int altura() {
        return (raiz == null) ? 0 : raiz.altura();
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        if (raiz == null || criterioBusqueda == null) {
            return -1; // Si el árbol está vacío o no se requiere buscar
        }
        return raiz.obtenerNivel(criterioBusqueda);
    }

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public void vaciar() {
        this.raiz = null; // Asigno null a la raíz para dejar el árbol completamente vacío
    }
}