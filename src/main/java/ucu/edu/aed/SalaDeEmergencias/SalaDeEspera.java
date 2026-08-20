package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.implementaciones.ColaConPrioridad;
import java.util.Comparator;

public class SalaDeEspera {
    private ColaConPrioridad<Paciente> porAtender;
    
    public SalaDeEspera(){
        porAtender = new ColaConPrioridad<Paciente>(new ComparadorPacientes());
    }

    public void incrementarEspera(){
        /*
        Incrementa el tiempo de espera de los pacientes que permanecen 
        en la sala de espera.
         */
    }
    
    public boolean actualizarUrgencia(){
        /*
        Verifica si el paciente superó el tiempo de espera establecido para 
        aumentar su nivel de prioridad. Si corresponde, modifica su nivel de 
        urgencia y actualiza su posición dentro de la ColaPrioridad.
        Devuelve true si la prioridad pudo actualizarse y false si no se pudo.
         */
        return false;
    }

}
