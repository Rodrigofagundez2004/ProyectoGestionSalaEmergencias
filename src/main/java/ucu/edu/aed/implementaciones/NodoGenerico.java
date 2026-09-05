package ucu.edu.aed.implementaciones;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class NodoGenerico<T> {

    private T dato;
    private NodoGenerico<T> hermanoDerecho;
    private NodoGenerico<T> primerHijo;

    public NodoGenerico(T dato) {
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

        NodoGenerico<T> nodoAInsertar = new NodoGenerico<>(datoHijo);

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

    public boolean insertarHijoExistente(NodoGenerico<T> hijo) {
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

    public NodoGenerico<T> buscar(Predicate<T> criterio) {
        if (criterio == null) {
            return null;
        }
        if (criterio.test(this.dato)) {
            return this;
        }

        if (this.primerHijo != null) {
            NodoGenerico<T> encontrado = this.primerHijo.buscar(criterio);
            if (encontrado != null) {
                return encontrado;
            }
        }

        if (this.hermanoDerecho != null) {
            return this.hermanoDerecho.buscar(criterio);
        }

        return null;
    }

    public boolean contiene(Predicate<T> criterio) {
        return this.buscar(criterio) != null;
    }

    public NodoGenerico<T> eliminar(Predicate<T> criterio) {
        if (criterio == null) {
            return null;
        }

        if (criterio.test(this.dato)) {
            return this; // Se maneja la reconexión en el nivel del padre
        }

        if (this.primerHijo != null) {
            if (criterio.test(this.primerHijo.dato)) {
                NodoGenerico<T> nodoEliminado = this.primerHijo;

                if (nodoEliminado.primerHijo != null) {
                    NodoGenerico<T> hijoDelEliminado = nodoEliminado.primerHijo;
                    hijoDelEliminado.hermanoDerecho = nodoEliminado.hermanoDerecho;
                    this.primerHijo = hijoDelEliminado;
                } else {
                    this.primerHijo = nodoEliminado.hermanoDerecho;
                }

                return nodoEliminado;
            } else {
                NodoGenerico<T> eliminadoEnHijo = this.primerHijo.eliminar(criterio);
                if (eliminadoEnHijo != null) {
                    return eliminadoEnHijo;
                }
            }
        }

        if (this.hermanoDerecho != null) {
            if (criterio.test(this.hermanoDerecho.dato)) {
                NodoGenerico<T> nodoEliminado = this.hermanoDerecho;

                if (nodoEliminado.primerHijo != null) {
                    NodoGenerico<T> hijoEliminado = nodoEliminado.primerHijo;
                    hijoEliminado.hermanoDerecho = nodoEliminado.hermanoDerecho;
                    this.hermanoDerecho = hijoEliminado;
                } else {
                    this.hermanoDerecho = nodoEliminado.hermanoDerecho;
                }

                return nodoEliminado;
            } else {
                NodoGenerico<T> eliminadoEnHermano = this.hermanoDerecho.eliminar(criterio);
                if (eliminadoEnHermano != null) {
                    return eliminadoEnHermano;
                }
            }
        }

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
        return this.primerHijo == null;
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
            contador += 1;
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
        if (this.primerHijo == null) {
            return 0;
        }

        int alturaMaximaHijos = 0;
        NodoGenerico<T> hijoActual = this.primerHijo;
        while (hijoActual != null) {
            alturaMaximaHijos = Math.max(alturaMaximaHijos, hijoActual.altura());
            hijoActual = hijoActual.getHermanoDerecho();
        }

        return 1 + alturaMaximaHijos;
    }

    public int obtenerNivel(Predicate<T> criterio) {
        if (criterio.test(this.dato)) {
            return 0;
        }

        if (this.primerHijo != null) {
            NodoGenerico<T> hijoActual = this.primerHijo;
            while (hijoActual != null) {
                int nivelHijo = hijoActual.obtenerNivel(criterio);
                if (nivelHijo != -1) {
                    return nivelHijo + 1;
                }
                hijoActual = hijoActual.getHermanoDerecho();
            }
        }

        return -1;
    }
}