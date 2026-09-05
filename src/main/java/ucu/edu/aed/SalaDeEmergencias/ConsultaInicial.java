package ucu.edu.aed.SalaDeEmergencias;

public class ConsultaInicial extends RegistroClinico{
    String descripcionConsulta;
    Causa causa;

    public void ConsultaInicial(String descripcion, Causa causa){
        this.descripcionConsulta = descripcion;
        this.causa = causa;
    }

    public String getDescripcionConsulta(){
        return descripcionConsulta;
    }

    public Causa getCausa() {
        return causa;
    }
}
