package ucu.edu.aed.SalaDeEmergencias.Diagnosis;

public class Codigo extends NodoCatalogoBase {
    private String id;

    public Codigo(String nombre, String id) {
        super(nombre);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Codigo that = (Codigo) obj;
        return id != null && id.equals(that.id);
    }

    @Override 
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
