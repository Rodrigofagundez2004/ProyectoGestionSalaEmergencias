package ucu.edu.aed.SalaDeEmergencias.Registro;

import ucu.edu.aed.SalaDeEmergencias.Causa;

public class ConsultaInicial extends RegistroClinico {
    Causa causa;

    public ConsultaInicial(String descripcion, Causa causa){
        super(descripcion);
        this.causa = causa;
    }

    public Causa getCausa() {
        return causa;
    }
}
