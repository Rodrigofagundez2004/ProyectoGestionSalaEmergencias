package ucu.edu.aed.SalaDeEmergencias.Diagnosis;

public abstract class NodoCatalogoBase implements INodoCatalogo {
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

    public boolean aceptaHijo(INodoCatalogo nodoCatalogo) {
        return false;
    }
}
