package ucu.edu.aed.SalaDeEmergencias;

public class Capitulo extends NodoCatalogoBase {
    private String codigoInicio;
    private String codigoFin;

    public Capitulo(String nombre, String codigoInicio, String codigoFin) {
        super(nombre);
        this.codigoInicio = codigoInicio;
        this.codigoFin = codigoFin;
    }

    public String getCodigoInicio () {
        return codigoInicio;
    }

    public String getCodigoFin () {
        return codigoFin;
    }

    @Override 
    public boolean aceptaHijo(INodoCatalogo nodo) {
        if (!(nodo instanceof Grupo)) {
            return false;
        }
        Grupo grupo = (Grupo) nodo;
        return grupo.getCodigoInicio().compareTo(this.codigoInicio) >= 0 && grupo.getCodigoFin().compareTo(this.codigoFin) <= 0;
    }

}
