package ucu.edu.aed.SalaDeEmergencias;

public abstract class NodoCatalogoBase implements iNodoCatalogo {
    protected String nombre;

    public NodoCatalogoBase(String nombre) {
        this.nombre = nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean aceptaHijo(iNodoCatalogo nodoCatalogo) {
        return false;
    }
}
