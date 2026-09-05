package ucu.edu.aed.implementaciones;


public class ArbolBinarioBalanceado <T> extends ArbolBinarioBusquedad<T>{
    @Override
    public boolean insertar(Comparable<T> dato){
        if (esVacio()){
            raiz = new ElementoABB<>((T) dato);
            return true;
        }
        else {
            raiz = insertarBalanceado (raiz, dato);
            return true;
        }
        }
    private ElementoABB<T> insertarBalanceado(ElementoABB<T> nodo, Comparable<T> dato){
        if (nodo == null){
            return new ElementoABB<>((T) dato) ;
        }

        if (dato.compareTo(nodo.getDato())<0){
            nodo.setHijoIzquierdo(insertarBalanceado((ElementoABB<T>) nodo.getHijoIzquierdo(), dato));           
        }

        else if (dato.compareTo(nodo.getDato())>0){
            nodo.setHijoDerecho(insertarBalanceado((ElementoABB<T>) nodo.getHijoDerecho(), dato));
        }
      
        return balancear(nodo);
        }
        
    private int factorBalance(ElementoABB<T> nodo){
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
    
    private ElementoABB<T> balancear (ElementoABB<T> nodo){
        int factorBalance  = factorBalance(nodo);
        if (factorBalance > 1){
            ElementoABB<T> hijoIzquierdo = (ElementoABB<T>) nodo.getHijoIzquierdo();

            if (factorBalance(hijoIzquierdo) < 0){
                nodo.setHijoIzquierdo(rotacionIzquierda(hijoIzquierdo));
            }
            return rotacionDerecha(nodo);
        }

        if (factorBalance < -1 ){
            ElementoABB<T> hijoDerecho = (ElementoABB<T>) nodo.getHijoDerecho();

            if (factorBalance(hijoDerecho) > 0){
                nodo.setHijoDerecho(rotacionDerecha(hijoDerecho));
            }

            return rotacionIzquierda(nodo);
        }

        return nodo;
    }
    
    private ElementoABB<T> rotacionDerecha(ElementoABB<T> nodo){
        ElementoABB<T> hijo = (ElementoABB<T>) nodo.getHijoIzquierdo();
        nodo.setHijoIzquierdo(hijo.getHijoDerecho());
        hijo.setHijoDerecho(nodo);
        return hijo;
    }

    private ElementoABB<T> rotacionIzquierda(ElementoABB<T> nodo){
        ElementoABB<T> hijo = (ElementoABB<T>) nodo.getHijoDerecho();
        nodo.setHijoDerecho(hijo.getHijoIzquierdo());
        hijo.setHijoIzquierdo(nodo);
        return hijo;
    }

    @Override
    public boolean eliminar (Comparable<T> criterio){
        if (esVacio()){
            return false;
        }

        raiz = eliminarBalanceado(raiz, criterio);
        return true;

    }

    private ElementoABB<T> eliminarBalanceado(ElementoABB<T> nodo, Comparable<T> criterio){
        if (nodo == null){
            return null;
        }

        if (criterio.compareTo(nodo.getDato()) < 0){
            nodo.setHijoIzquierdo(eliminarBalanceado((ElementoABB<T>) nodo.getHijoIzquierdo(), criterio));
        }

        else if (criterio.compareTo(nodo.getDato()) > 0){
            nodo.setHijoDerecho(eliminarBalanceado((ElementoABB<T>) nodo.getHijoDerecho(), criterio));
        }
        else{
            ElementoABB<T> reemplazo = (ElementoABB<T>) nodo.quitarNodo();
            if (reemplazo != null ){
                return balancear(reemplazo);
            }
            return null;
        }

        return balancear(nodo);

    }
    }





   
    




    

