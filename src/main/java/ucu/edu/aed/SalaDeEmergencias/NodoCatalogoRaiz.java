package ucu.edu.aed.SalaDeEmergencias;

public class NodoCatalogoRaiz extends NodoCatalogoBase {
    public NodoCatalogoRaiz(String nombre) {
        super(nombre);
    }

    // La raiz solo va a aceptar que sus hijos sean capitulos
    @Override
    public boolean aceptaHijo(iNodoCatalogo nodo) {
        return nodo instanceof Capitulo;
    }
}
