package ucu.edu.aed.implementaciones;

import ucu.edu.aed.tda.TDAArbolGenerico;

import java.util.function.Consumer;

public class ArbolGenerico<T> implements TDAArbolGenerico<T>{

    private NodoGenerico<T> raiz;



    @Override
    public T obtenerRaiz() {

        return null;
    }

    @Override
    public boolean insertarRaiz(T dato) {
        return false;
    }

    @Override
    public boolean insertar(Comparable<T> criterioPadre, T datoHijo) {
        return false;
    }

    @Override
    public T buscar(Comparable<T> criterioBusqueda) {
        return null;
    }

    @Override
    public boolean contiene(Comparable<T> criterioBusqueda) {
        return false;
    }

    @Override
    public boolean eliminar(Comparable<T> criterioBusqueda) {
        return false;
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {

    }

    @Override
    public void postOrder(Consumer<T> consumidor) {

    }

    @Override
    public void porNiveles(Consumer<T> consumidor) {

    }

    @Override
    public int cantidadNodos() {
        return 0;
    }

    @Override
    public int cantidadHojas() {
        return 0;
    }

    @Override
    public int cantidadNodosInternos() {
        return 0;
    }

    @Override
    public int altura() {
        return 0;
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        return 0;
    }

    @Override
    public boolean esVacio() {
        return false;
    }

    @Override
    public void vaciar() {

    }
}
