package ucu.edu.aed.SalaDeEmergencias;

public class ConsultaInterna extends RegistroClinico {
    String descripcionConsulta;

    public void ConsultaInterna(String registroConsulta) {
        this.descripcionConsulta = registroConsulta;
    }

    public String getDescripcionConsulta() {
        return descripcionConsulta;
    }
}
