package ucu.edu.aed.implementaciones.jerarquicas.ABB;
import java.util.function.Consumer;

import ucu.edu.aed.tda.jerarquico.TDAArbolBinario;
import ucu.edu.aed.tda.jerarquico.TDAElemento;



public class ArbolBinarioBusquedad <T> implements TDAArbolBinario<T>{
    protected ElementoABB<T> raiz;

    @Override
    public T buscar(Comparable<T> criterio){
        if (raiz != null){
            TDAElemento<T> resultado = raiz.buscar(criterio);
            if (resultado == null) return null;
            return resultado.getDato();
        }
        return null;
    }

    @Override
    public TDAElemento<T> obtenerRaiz() {
        return this.raiz;
    }

    @Override
    public boolean eliminar(Comparable<T> criterio) {
        if (this.raiz != null) {
            this.raiz = (ElementoABB<T>) this.raiz.eliminar(criterio);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertar(Comparable<T> dato){
        if (esVacio()){
            raiz = new ElementoABB<>((T)dato);
        } else {
            raiz.insertar(dato);
        }
        return true;
    }

    @Override
    public void inOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.inOrder(elemento -> consumidor.accept(elemento.getDato()));
        }
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.preOrder(elemento -> consumidor.accept(elemento.getDato()));
        }
    }

    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.postOrder(elemento -> consumidor.accept(elemento.getDato()));
        }
    }

    @Override
    public boolean esVacio(){
        return raiz == null;
    } 

    @Override
    public int cantidadNodos() {
        if (esVacio()) {
            return 0;
        }
        return raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas() {
        if (esVacio()) {
            return 0;
        }
        return raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos() {
        if (esVacio()) {
            return 0;
        }
        return raiz.cantidadNodosInternos();
    }
}