package ucu.edu.aed.implementaciones;

import java.util.Comparator;
import java.util.NoSuchElementException;

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
        return QuitarNodo();
    }
    private TDAElemento<T> QuitarNodo()
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
