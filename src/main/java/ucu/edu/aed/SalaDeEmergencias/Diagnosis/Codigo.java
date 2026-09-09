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
}
