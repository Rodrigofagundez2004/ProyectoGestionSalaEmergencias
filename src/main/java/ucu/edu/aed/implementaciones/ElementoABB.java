package ucu.edu.aed.implementaciones;

import java.util.function.Consumer;

import ucu.edu.aed.tda.TDAElemento;

public class ElementoABB <T> implements TDAElemento <T> {
    private T dato;
    private TDAElemento <T> hijoIzq;
    private TDAElemento <T> hijoDer;

    public ElementoABB(T dato)
    {
        this.dato = dato;
        this.hijoIzq = null;
        this.hijoDer = null;
    }
    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo)
    {
        this.hijoIzq = hijoIzquierdo;
    }
    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho)
    {
        this.hijoDer = hijoDerecho;
    }
    @Override
    public TDAElemento<T> getHijoIzquierdo()
    {
        return this.hijoIzq;
    }
    @Override
    public TDAElemento<T> getHijoDerecho()
    {
        return this.hijoDer;
    }

    @Override
    public void setDato(T dato)
    {
        this.dato = dato;
    }
    @Override
    public T getDato()
    {
        return this.dato;
    }
    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda)
    {
        if (criterioBusqueda.compareTo(this.dato) == 0)
            return this;
        else if (criterioBusqueda.compareTo(this.dato) < 0 && this.hijoIzq != null)
            return this.hijoIzq.buscar(criterioBusqueda);
        else if (criterioBusqueda.compareTo(this.dato) > 0 && this.hijoDer != null)
            return this.hijoDer.buscar(criterioBusqueda);
        else
            return null;
    }
    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda)
    {
        if (criterioBusqueda.compareTo(this.dato)< 0)
        {
            if (this.hijoIzq != null)
                this.hijoIzq = this.hijoIzq.eliminar(criterioBusqueda);

            return this;
        }
        
        if (criterioBusqueda.compareTo(this.dato) > 0)
        {
            if (this.hijoDer != null)
                this.hijoDer = this.hijoDer.eliminar(criterioBusqueda);
            return this;
        }
        return quitarNodo();
    }

    @Override
    public void insertar(Comparable<T> nuevoDato) {
        if (nuevoDato.compareTo(this.dato) > 0) {
            if (hijoDer == null) {
                hijoDer = new ElementoABB<>((T)nuevoDato);
            }
            hijoDer.insertar(nuevoDato);
        } else if (nuevoDato.compareTo(this.dato) < 0) {
            if (hijoIzq == null) {
                hijoIzq = new ElementoABB<>((T)nuevoDato);
            }
            hijoIzq.insertar(nuevoDato);
        }
    }

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);
        if (this.hijoIzq != null) this.hijoIzq.preOrder(consumidor);
        if (this.hijoDer != null) this.hijoDer.preOrder(consumidor);
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        if (this.hijoIzq != null) this.hijoIzq.postOrder(consumidor);
        if (this.hijoDer != null) this.hijoDer.postOrder(consumidor);
        consumidor.accept(this);
    }

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        if (this.hijoIzq != null) this.hijoIzq.inOrder(consumidor);
        consumidor.accept(this);
        if (this.hijoDer != null) this.hijoDer.inOrder(consumidor);
    }

    @Override
    public int cantidadNodos() {
        int cantDer;
        int cantIzq;
        if (hijoIzq != null) {
            cantIzq = hijoIzq.cantidadNodos();
        } else {
            cantIzq = 0;
        }
        if (hijoDer != null) {
            cantDer = hijoDer.cantidadNodos();
        } else {
            cantDer = 0;
        }
        return cantIzq + cantDer + 1;
    }

    @Override
    public boolean esHoja() {
        if (hijoIzq != null) {
            return false;
        }
        if (hijoDer != null) {
            return false;
        }
        return true;
    }

    @Override
    public int cantidadHojas() {
        if (esHoja()) {
            return 1;
        }
        int hojasDer;
        int hojasIzq;
        if (hijoIzq != null) {
            hojasIzq = hijoIzq.cantidadHojas();
        } else {
            hojasIzq = 0;
        }
        if (hijoDer != null) {
            hojasDer = hijoDer.cantidadHojas();
        } else {
            hojasDer = 0;
        }
        return hojasIzq + hojasDer;
    }

    @Override
    public int cantidadNodosInternos() {
        return cantidadNodos() - cantidadHojas();
    }

    @Override
    public int altura() {
        int altDer = -1;
        int altIzq = -1;
        if (hijoIzq != null) {
            altIzq = hijoIzq.altura();
        }
        if (hijoDer != null) {
            altDer = hijoDer.altura();
        }
        return Math.max(altIzq, altDer) + 1;
    }

    @Override
    public int obtenerNivel(Comparable<T> criterio) {
        if (criterio.compareTo(this.dato) == 0) {
            return 0;
        }
        if (criterio.compareTo(this.dato) < 0 && this.hijoIzq != null) {
            int nivelEnIzq = this.hijoIzq.obtenerNivel(criterio);
            if (nivelEnIzq != -1) {
                return nivelEnIzq + 1;
            }
        } else if (criterio.compareTo(this.dato) > 0 && this.hijoDer != null) {
            int nivelEnDer = this.hijoDer.obtenerNivel(criterio);
            if (nivelEnDer != -1) {
                return nivelEnDer + 1;
            }
        }
        return -1;
    }

    protected TDAElemento<T> quitarNodo()
    {
       
        if(this.hijoIzq == null)
        {
            return hijoDer;
        }
        else if(this.hijoDer == null)
        {
            return hijoIzq;
        }
        TDAElemento<T> elHijo = this.hijoIzq;
        TDAElemento<T>elPadre = this;
        while(elHijo.getHijoDerecho() != null)
        {
            elPadre = elHijo;
            elHijo = elHijo.getHijoDerecho();
        }
        if(elPadre != this)
        {
            elPadre.setHijoDerecho(elHijo.getHijoIzquierdo());
            elHijo.setHijoIzquierdo(hijoIzq);
        }
        elHijo.setHijoDerecho(hijoDer);
        return elHijo;



    }

    
}
