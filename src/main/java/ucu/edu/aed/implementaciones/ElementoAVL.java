package ucu.edu.aed.implementaciones;

public class ElementoAVL<T> extends ElementoABB<T> {

    private int altura;
    
    public ElementoAVL(T dato) {
        super(dato);
        this.altura = 0;
    }

    @Override 
    public int altura(){
        return this.altura;
    }
    
    public void actualizarAltura() {
        int alturaIzq;
        int alturaDer;

        if (getHijoIzquierdo() != null){
            alturaIzq = getHijoIzquierdo().altura();
        }
        else {
            alturaIzq= -1;
        }

        if (getHijoDerecho() != null){
            alturaDer = getHijoDerecho().altura();
        }
        else {
            alturaDer = -1;
        }

        this.altura = 1 + Math.max(alturaIzq, alturaDer);
    }
}