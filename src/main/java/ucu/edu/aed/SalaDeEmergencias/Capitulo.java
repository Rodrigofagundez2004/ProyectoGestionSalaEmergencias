package ucu.edu.aed.SalaDeEmergencias;

public class Capitulo extends NodoCatalogoBase {
    private String codigoInicio;
    private String codigoFin;

    public Capitulo(String nombre) {
        super(nombre);
    }

    @Override 
    public boolean aceptaHijo(iNodoCatalogo nodo) {
        if (!(nodo instanceof Grupo)) {
            return false;
        }
        Grupo grupo = (Grupo) nodo;
        return grupo.getCodigoInicio().compareTo(this.codigoInicio) >= 0 && grupo.getCodigoFin().compareTo(this.codigoFin) <= 0;
    }

}
