package ucu.edu.aed.implementaciones;

public class Nodo<T> {
    
    private T dato;
    private Nodo<T> siguiente;

    public Nodo(){

    }

    public Nodo(T dato, Nodo<T> siguiente){
        this.dato = dato;
        this.siguiente = siguiente;
    }

    public T getDato(){
        return this.dato;
    }

    public Nodo<T> getSiguiente(){
        return this.siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente){
        this.siguiente = siguiente;
    }
}