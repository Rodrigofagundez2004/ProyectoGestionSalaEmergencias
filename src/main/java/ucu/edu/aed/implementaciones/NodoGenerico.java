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
        if (datoHijo == null) {
            return false;
        }

        NodoGenerico<T> nodoAInsertar = new NodoGenerico<T>(datoHijo);

        if (this.primerHijo == null) {
            this.primerHijo = nodoAInsertar;
            return true;
        }

        NodoGenerico<T> actual = this.primerHijo;
        while (actual.getHermanoDerecho() != null) {
            actual = actual.getHermanoDerecho();
        }
        actual.setHermanoDerecho(nodoAInsertar);
        return true;
    }
    
    
    public boolean insertarHijoExistente(NodoGenerico<T> hijo){
        if (hijo == null) {
            return false;
        }

        if (this.primerHijo == null) {
            this.primerHijo = hijo;
            return true;
        }

        NodoGenerico<T> actual = this.primerHijo;
        while (actual.getHermanoDerecho() != null) {
            actual = actual.getHermanoDerecho();
        }
        actual.setHermanoDerecho(hijo);
        return true;
    }
    
    
    public NodoGenerico<T> buscar(Comparable<T> criterio) {
        if (criterio == null) {
            return null;
        }

        if (criterio.compareTo(this.dato) == 0) {
            return this;
        }

        if (this.primerHijo != null) {
            return this.primerHijo.buscar(criterio);
        }

        if (this.hermanoDerecho != null) {
            return this.hermanoDerecho.buscar(criterio);
        }

        return null;
    }

    
    public boolean contiene(Comparable<T> criterio) {
        if (this.buscar(criterio) != null) {
            return true;
        } else {
            return false;
        }
    }

    
    public NodoGenerico<T> eliminar(Comparable<T> criterio) {
        if (criterio == null) {
            return null; // Si no hay criterio, no hay nada que eliminar
        }

        if (criterio.compareTo(this.dato) == 0) {
            return this; // Retornar el nodo para que se maneje en el nivel del padre
        }


        if (this.primerHijo != null) {

            // Verificar si el primer hijo debe ser eliminado
            if (criterio.compareTo(this.primerHijo.dato) == 0) {
                NodoGenerico<T> nodoEliminado = this.primerHijo;

                // Si el nodo eliminado tiene hijos, los reconectamos al árbol
                if (nodoEliminado.primerHijo != null) {
                    NodoGenerico<T> hijoDelEliminado = nodoEliminado.primerHijo;

                    // Reconectar los hijos del nodo eliminado al lugar del primer hijo
                    hijoDelEliminado.hermanoDerecho = nodoEliminado.hermanoDerecho;
                    this.primerHijo = hijoDelEliminado;
                } else {
                    // Si no tiene hijos, simplemente saltamos al siguiente hermano
                    this.primerHijo = nodoEliminado.hermanoDerecho;
                }

                return nodoEliminado; // Retornar el nodo eliminado
            } else {
                // Llamada recursiva para eliminar en el subárbol del primer hijo
                NodoGenerico<T> eliminadoEnHijo = this.primerHijo.eliminar(criterio);
                if (eliminadoEnHijo != null) {
                    return eliminadoEnHijo;
                }
            }
        }


        if (this.hermanoDerecho != null) {
            // Verificar si el hermano debe ser eliminado
            if (criterio.compareTo(this.hermanoDerecho.dato) == 0) {
                NodoGenerico<T> nodoEliminado = this.hermanoDerecho;

                // Si el nodo eliminado tiene hijos, reconectamos el árbol
                if (nodoEliminado.primerHijo != null) {
                    NodoGenerico<T> hijoEliminado = nodoEliminado.primerHijo;

                    // Reconectar los hijos al lugar del hermano derecho
                    hijoEliminado.hermanoDerecho = nodoEliminado.hermanoDerecho;
                    this.hermanoDerecho = hijoEliminado;
                } else {
                    // Si no tiene hijos, simplemente saltamos al siguiente hermano
                    this.hermanoDerecho = nodoEliminado.hermanoDerecho;
                }

                return nodoEliminado; // Retornar el nodo eliminado
            } else {
                // Llamada recursiva para eliminar en el subárbol de los hermanos
                NodoGenerico<T> eliminadoEnHermano = this.hermanoDerecho.eliminar(criterio);
                if (eliminadoEnHermano != null) {
                    return eliminadoEnHermano;
                }
            }
        }

        // Si no se encuentra el nodo, retornar null
        return null;
    }

    
    public void preOrder(Consumer<NodoGenerico<T>> consumidor) {
        if (consumidor == null) {
            return;
        }

        consumidor.accept(this);

        if (this.primerHijo != null) {
            this.primerHijo.preOrder(consumidor);
        }

        if (this.hermanoDerecho != null) {
            this.hermanoDerecho.preOrder(consumidor);
        }
    }

    
    public void postOrder(Consumer<NodoGenerico<T>> consumidor) {
        if (this.primerHijo != null) {
            this.primerHijo.postOrder(consumidor);
        }

        if (this.hermanoDerecho != null) {
            this.hermanoDerecho.postOrder(consumidor);
        }

        consumidor.accept(this);
    }

    public void inOrder(Consumer<NodoGenerico<T>> consumidor) {
        if (this.primerHijo != null) {
            this.primerHijo.preOrder(consumidor);
        }

        consumidor.accept(this);

        if (this.hermanoDerecho != null) {
            this.hermanoDerecho.preOrder(consumidor);
        }
    }

    
    public boolean esHoja() {
        if (this.primerHijo == null) {
            return true;
        }
        return false;
    }

    
    public int cantidadNodos() {

        int contador = 1;

        if (this.primerHijo != null) {
            contador += this.primerHijo.cantidadNodos();
        }

        if (this.hermanoDerecho != null) {
            contador += this.hermanoDerecho.cantidadNodos();
        }

        return contador;
    }

    
    public int cantidadHojas() {

         int contador = 0;

        if (this.esHoja()) {
            contador =+ 1;
        }

        if (this.primerHijo != null) {
            contador += this.primerHijo.cantidadHojas();
        }

        if (this.hermanoDerecho != null) {
            contador += this.hermanoDerecho.cantidadHojas();
        }


        return contador;
    }

    
    public int cantidadNodosInternos() {
        int contador = 0;

        if (!this.esHoja()) {
            contador += 1;
        }

        if (this.primerHijo != null) {
            contador += this.primerHijo.cantidadNodosInternos();
        }

        if (this.hermanoDerecho != null) {
            contador += this.hermanoDerecho.cantidadNodosInternos();
        }
        return contador;
    }

    
    public int altura() {
        // Caso base: si no tiene hijos, la altura es 0
        if (this.primerHijo == null) {
            return 0;
        }

        int alturaMaximaHijos = 0;

        // Recorremos todos los hijos, acumulando la altura máxima
        NodoGenerico<T> hijoActual = this.primerHijo;
        while (hijoActual != null) {
            alturaMaximaHijos = Math.max(alturaMaximaHijos, hijoActual.altura());
            hijoActual = hijoActual.getHermanoDerecho();
        }

        // Retornamos la altura total del árbol: 1 + altura máxima de los subárboles
        return 1 + alturaMaximaHijos;
    }


    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        // Caso base: si el nodo coincide con el criterio, el nivel actual es 0.
        if (criterioBusqueda.compareTo(this.dato) == 0) {
            return 0; // Raíz o nodo encontrado, nivel es 0 desde la perspectiva del nodo actual.
        }

        // Buscar en los hijos
        if (this.primerHijo != null) {
            NodoGenerico<T> hijoActual = this.primerHijo;
            while (hijoActual != null) {
                int nivelHijo = hijoActual.obtenerNivel(criterioBusqueda); // Llamada recursiva a los hijos
                if (nivelHijo != -1) {
                    return nivelHijo + 1; // Si se encuentra, sumar uno al nivel del hijo.
                }
                hijoActual = hijoActual.getHermanoDerecho(); // Avanzar al siguiente hermano
            }
        }

        // Si no se encuentra en los hijos ni en esta rama
        return -1;
    }
}