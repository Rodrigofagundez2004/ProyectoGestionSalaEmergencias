package ucu.edu.aed.implementaciones.jerarquicas.generico;

import ucu.edu.aed.tda.jerarquico.TDAArbolGenerico;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Queue;
import java.util.LinkedList;

public class ArbolGenerico<T> implements TDAArbolGenerico<T> {

    private NodoGenerico<T> raiz;

    public ArbolGenerico(T datoRaiz) {
        this.raiz = (datoRaiz != null) ? new NodoGenerico<>(datoRaiz) : null;
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
    public boolean insertar(Predicate<T> criterioPadre, T datoHijo) {
        if (raiz == null || criterioPadre == null || datoHijo == null) {
            return false;
        }

        NodoGenerico<T> nodoPadre = raiz.buscar(criterioPadre);
        if (nodoPadre != null) {
            return nodoPadre.insertarHijo(datoHijo);
        }
        return false;
    }

    @Override
    public T buscar(Predicate<T> criterioBusqueda) {
        if (raiz == null || criterioBusqueda == null) {
            return null;
        }

        NodoGenerico<T> nodo = raiz.buscar(criterioBusqueda);
        return (nodo != null) ? nodo.getDato() : null;
    }

    @Override
    public boolean contiene(Predicate<T> criterioBusqueda) {
        return (raiz != null) && raiz.contiene(criterioBusqueda);
    }

    @Override
    public boolean eliminar(Predicate<T> criterioBusqueda) {
        if (raiz == null || criterioBusqueda == null) {
            return false;
        }

        // Si el nodo raíz es el que debe eliminarse: el primer hijo pasa a ser
        // la nueva raíz. Sus hermanos (los demás hijos de la raíz vieja) quedan
        // colgando de él vía hermanoDerecho, tal como ya estaban encadenados.
        if (criterioBusqueda.test(raiz.getDato())) {
            this.raiz = raiz.getPrimerHijo(); // null si la raíz no tenía hijos -> árbol vacío
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
    public int obtenerNivel(Predicate<T> criterioBusqueda) {
        if (raiz == null || criterioBusqueda == null) {
            return -1;
        }
        return raiz.obtenerNivel(criterioBusqueda);
    }

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public void vaciar() {
        this.raiz = null;
    }
}