package ucu.edu.aed.implementaciones;
import java.util.function.Consumer;

import ucu.edu.aed.tda.TDAArbolBinario;
import ucu.edu.aed.tda.TDAElemento;



public class ArbolBinarioBusquedad <T> implements TDAArbolBinario<T>{
    private ElementoABB <T> raiz;

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
    public boolean insertar(Comparable<T> dato){
        if (esVacio()){
            raiz = new ElementoABB<>((T)dato);
        } else {
            raiz.insertar(dato);
        }
        return true;
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
    public void inOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.inOrder(elemento -> consumidor.accept(elemento.getDato()));
        }
    }

    @Override
    public boolean esVacio(){
        return raiz == null;
    } 
}