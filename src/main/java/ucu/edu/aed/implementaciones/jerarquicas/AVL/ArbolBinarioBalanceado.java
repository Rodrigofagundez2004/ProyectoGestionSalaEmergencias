package ucu.edu.aed.implementaciones.jerarquicas.AVL;

import ucu.edu.aed.implementaciones.jerarquicas.ABB.ArbolBinarioBusquedad;

public class ArbolBinarioBalanceado <T> extends ArbolBinarioBusquedad<T> {
    
    @Override
    public boolean insertar(Comparable<T> dato){
        if (esVacio()){
            raiz = new ElementoAVL<>((T) dato);
            return true;
        }
        else {
            raiz = insertarBalanceado ((ElementoAVL<T>)raiz, dato);
            return true;
        }
        }
    private ElementoAVL<T> insertarBalanceado(ElementoAVL<T> nodo, Comparable<T> dato){
        if (nodo == null){
            return new ElementoAVL<>((T) dato) ;
        }

        if (dato.compareTo(nodo.getDato())<0){
            nodo.setHijoIzquierdo(insertarBalanceado((ElementoAVL<T>) nodo.getHijoIzquierdo(), dato));           
        }

        else if (dato.compareTo(nodo.getDato())>0){
            nodo.setHijoDerecho(insertarBalanceado((ElementoAVL<T>) nodo.getHijoDerecho(), dato));
        }
        nodo.actualizarAltura();
        return balancear(nodo);
        }
        
    private int factorBalance(ElementoAVL<T> nodo){
        int alturaIzquierda;
        if (nodo.getHijoIzquierdo() != null ){
            alturaIzquierda = nodo.getHijoIzquierdo().altura();
        }

        else {
            alturaIzquierda = -1;
        }

        int alturaDerecha;
        if (nodo.getHijoDerecho() != null){
            alturaDerecha = nodo.getHijoDerecho().altura();
        }
        else {
            alturaDerecha = -1;
        }

        return alturaIzquierda - alturaDerecha;
    } 
    
    private ElementoAVL<T> balancear (ElementoAVL<T> nodo){
        int factorBalance  = factorBalance(nodo);
        if (factorBalance > 1){
            ElementoAVL<T> hijoIzquierdo = (ElementoAVL<T>) nodo.getHijoIzquierdo();
            if (factorBalance(hijoIzquierdo) >= 0){
                return rotacionDerecha(nodo);
            }
            else{
                nodo.setHijoIzquierdo(rotacionIzquierda(hijoIzquierdo));
                return rotacionDerecha(nodo);
            }
        }

        if (factorBalance < -1  ){
            ElementoAVL<T> hijoDerecho = (ElementoAVL<T>) nodo.getHijoDerecho();
            if (factorBalance(hijoDerecho) <= 0){
                return rotacionIzquierda(nodo);
            } 
            else {
                nodo.setHijoDerecho(rotacionDerecha(hijoDerecho));
                return rotacionIzquierda(nodo);
            }
        }

        return nodo;
    }
    
    private ElementoAVL<T> rotacionDerecha(ElementoAVL<T> nodo){
        ElementoAVL<T> hijo = (ElementoAVL<T>) nodo.getHijoIzquierdo();
        nodo.setHijoIzquierdo(hijo.getHijoDerecho());
        hijo.setHijoDerecho(nodo);
        nodo.actualizarAltura();
        hijo.actualizarAltura();
        return hijo;
    }

    private ElementoAVL<T> rotacionIzquierda(ElementoAVL<T> nodo){
        ElementoAVL<T> hijo = (ElementoAVL<T>) nodo.getHijoDerecho();
        nodo.setHijoDerecho(hijo.getHijoIzquierdo());
        hijo.setHijoIzquierdo(nodo);
        nodo.actualizarAltura();
        hijo.actualizarAltura();
        return hijo;
    }

    @Override
    public boolean eliminar (Comparable<T> criterio){
        if (esVacio()){
            return false;
        }

        raiz = eliminarBalanceado((ElementoAVL<T>)raiz, criterio);
        return true;

    }

    private ElementoAVL<T> eliminarBalanceado(ElementoAVL<T> nodo, Comparable<T> criterio) {
        if (nodo == null) {
            return null;
        }
        if (criterio.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzquierdo(eliminarBalanceado((ElementoAVL<T>) nodo.getHijoIzquierdo(), criterio));
        } else if (criterio.compareTo(nodo.getDato()) > 0) {
            nodo.setHijoDerecho(eliminarBalanceado((ElementoAVL<T>) nodo.getHijoDerecho(), criterio));
        } else {
            if (nodo.getHijoIzquierdo() == null) {
                return (ElementoAVL<T>) nodo.getHijoDerecho();
            }
            if (nodo.getHijoDerecho() == null) {
                return (ElementoAVL<T>) nodo.getHijoIzquierdo();
            }

            ElementoAVL<T> predecesor = (ElementoAVL<T>) nodo.getHijoIzquierdo();
            while (predecesor.getHijoDerecho() != null) {
                predecesor = (ElementoAVL<T>) predecesor.getHijoDerecho();
            }

            T datoPredecesor = predecesor.getDato();
            nodo.setDato(datoPredecesor);

            nodo.setHijoIzquierdo(eliminarBalanceado((ElementoAVL<T>) nodo.getHijoIzquierdo(),
            elem -> ((Comparable<T>) datoPredecesor).compareTo(elem)));
        }
        nodo.actualizarAltura();
        return balancear(nodo);
    }
}