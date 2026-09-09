package ucu.edu.aed.SalaDeEmergencias.Registro;

public class Estudio extends RegistroClinico {
    private final TipoEstudio tipoEstudio;

    public Estudio(String descripcion, TipoEstudio tipoEstudio) {
        super(descripcion);
        this.tipoEstudio = tipoEstudio;
    }

    public TipoEstudio getTipoEstudio() {
        return tipoEstudio;
    }
}