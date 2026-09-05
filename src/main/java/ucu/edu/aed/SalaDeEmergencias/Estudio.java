package ucu.edu.aed.SalaDeEmergencias;

public class Estudio extends RegistroClinico{

    TipoEstudio estudio;

    public void Estudio(TipoEstudio estudio){
        this.estudio = estudio;
    }

    public TipoEstudio getEstudio(){
        return estudio;
    }
}
