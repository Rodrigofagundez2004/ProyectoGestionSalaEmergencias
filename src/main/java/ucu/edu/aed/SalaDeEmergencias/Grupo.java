package ucu.edu.aed.SalaDeEmergencias;

public class Grupo extends NodoCatalogoBase {
    // Los codigos de inicio y fin, controlan que no se agreguen codigos incorrectos en x grupo.
    private String codigoInicio;
    private String codigoFin;

    public Grupo(String nombre, String codigoInicio, String codigoFin) {
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
        if (!(nodo instanceof Codigo)) {
            return false;
        } 
        Codigo codigo = (Codigo) nodo;
        return codigo.getId().compareTo(codigoInicio) >= 0 && codigo.getId().compareTo(codigoFin) <= 0;
    }
}
