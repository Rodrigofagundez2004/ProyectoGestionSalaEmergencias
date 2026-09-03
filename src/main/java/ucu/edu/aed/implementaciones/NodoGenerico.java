package ucu.edu.aed.implementaciones;

import java.util.function.Consumer;

public class NodoGenerico<T> {

    private T dato;
    private NodoGenerico<T> hermanoDerecho;
    private NodoGenerico<T> primerHijo;

    public NodoGenerico(T dato){
        this.dato = dato;
        this.primerHijo = null;
        this.hermanoDerecho = null;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    
    public T getDato() {
        return this.dato;
    }

    
    public void setPrimerHijo(NodoGenerico<T> primerHijo) {
        this.primerHijo = primerHijo;
    }
    
    public NodoGenerico<T> getPrimerHijo() {
        return this.primerHijo;
    }
    
    public void setHermanoDerecho(NodoGenerico<T> hermanoDerecho) {
        this.hermanoDerecho = hermanoDerecho;
    }
    
    public NodoGenerico<T> getHermanoDerecho() {
        return this.hermanoDerecho;
    }
    
    public boolean insertarHijo(T datoHijo) {
        NodoGenerico<T> nodoAInsertar = new NodoGenerico<T>(datoHijo);
        NodoGenerico<T> actual = this;
        if (actual == null) {
            return false;
        }
        if (actual.primerHijo == null) {
            actual.primerHijo = nodoAInsertar;
            return true;
        } else {
            NodoGenerico<T> hijo = actual.primerHijo;
            while (hijo.getHermanoDerecho() != null) {
                hijo = hijo.getHermanoDerecho();
            }
            hijo.setHermanoDerecho(nodoAInsertar);
            return true;
        }
    }
    
    
    public boolean insertarHijoExistente(NodoGenerico<T> hijo){
        NodoGenerico<T> actual = this;
        if (actual == null) {
            return false;
        }
        if (actual.primerHijo == null) {
            actual.primerHijo = hijo;
            return true;
        } else {
            NodoGenerico<T> hijoExistente = actual.primerHijo;
            while (hijoExistente.getHermanoDerecho() != null) {
                hijoExistente = hijoExistente.getHermanoDerecho();
            }
            hijoExistente.setHermanoDerecho(hijo);
            return true;
        }
    }
    
    
    public NodoGenerico<T> buscar(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return null;
        }

        if (criterioBusqueda.compareTo(this.dato) == 0) {
            return this;
        }

        if (this.primerHijo != null) {
            NodoGenerico<T> encontradoEnHijos = this.primerHijo.buscar(criterioBusqueda);

            if (encontradoEnHijos != null) {
                return encontradoEnHijos;
            }
        }

        if (this.hermanoDerecho != null) {
            return this.hermanoDerecho.buscar(criterioBusqueda);
        }

        return null;
    }

    
    public boolean contiene(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return false;
        }

        if (criterioBusqueda.compareTo(this.dato) == 0) {
            return true;
        }

        if (this.primerHijo != null) {
            return this.primerHijo.contiene(criterioBusqueda);
        }

        if (this.hermanoDerecho != null) {
            return this.hermanoDerecho.contiene(criterioBusqueda);
        }
        return false;
    }

    
    public NodoGenerico<T> eliminar(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda == null) {
            return null;
        }

        if(this.primerHijo != null) {
            if (criterioBusqueda.compareTo(this.dato) == 0) {
                NodoGenerico<T> nodoEliminado = this.primerHijo;
                this.primerHijo = this.primerHijo.getHermanoDerecho();
                nodoEliminado.setHermanoDerecho(null);
                return nodoEliminado;
            }

            NodoGenerico<T> actual = this.primerHijo;
            while (actual.getHermanoDerecho() != null) {
                if (criterioBusqueda.compareTo(actual.getHermanoDerecho().getDato()) == 0) {
                    NodoGenerico<T> nodoEliminado = actual.getHermanoDerecho();
                    actual.setHermanoDerecho(nodoEliminado.getHermanoDerecho());
                    nodoEliminado.setHermanoDerecho(null);
                    return nodoEliminado;
                }

                actual = actual.getHermanoDerecho();
            }

            actual = this.primerHijo;

            while (actual.getHermanoDerecho() != null) {
                NodoGenerico<T> eliminado = actual.eliminar(criterioBusqueda);

                if (eliminado != null) {
                    return eliminado;
                }

                actual = actual.getHermanoDerecho();
            }
        }

        return null;
    }

    
    public void preOrder(Consumer<NodoGenerico<T>> consumidor) {

    }

    
    public void postOrder(Consumer<NodoGenerico<T>> consumidor) {

    }

    
    public boolean esHoja() {
        return false;
    }

    
    public int cantidadNodos() {
        return 0;
    }

    
    public int cantidadHojas() {
        return 0;
    }

    
    public int cantidadNodosInternos() {
        return 0;
    }

    
    public int altura() {
        return 0;
    }

    
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        return 0;
    }
}
