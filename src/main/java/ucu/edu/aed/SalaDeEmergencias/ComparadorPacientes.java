package ucu.edu.aed.SalaDeEmergencias;

import java.util.Comparator;


public class ComparadorPacientes implements Comparator<Paciente> {

    @Override
    public int compare(Paciente p1, Paciente p2) {
        int comparacionPrioridad = p1.getPrioridad().compareTo(p2.getPrioridad());
        if (comparacionPrioridad != 0) {
            return -comparacionPrioridad;
        }
        return Integer.compare(p2.getTiempoEsperando(), p1.getTiempoEsperando());
    }
}

