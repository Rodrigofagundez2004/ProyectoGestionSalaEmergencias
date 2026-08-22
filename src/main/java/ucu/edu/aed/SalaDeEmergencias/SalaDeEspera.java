package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.implementaciones.ColaConPrioridad;
import java.util.Comparator;

public class SalaDeEspera {
    private static final int LIMITE_ESPERA_URGENCIA_BAJA = 4;
    private static final int LIMITE_ESPERA_URGENCIA_MEDIA = 3;
    private static final int LIMITE_ESPERA_URGENCIA_ALTA = 2;
    private static final int LIMITE_ESPERA_URGENCIA_URGENTE = 1;

    private ColaConPrioridad<Paciente> porAtender;
    
    public SalaDeEspera(){
        porAtender = new ColaConPrioridad<Paciente>(new ComparadorPacientes());
    }

    public void incrementarEspera(){
        /*
        Incrementa el tiempo de espera de los pacientes que permanecen 
        en la sala de espera.
         */
        if (porAtender == null) {
            return;
        }
        boolean prioridadActualizada = false;

        for (int i = 0; i < porAtender.tamaño(); i++) {
            Paciente paciente = porAtender.obtener(i);
            paciente.setTiempoEsperando(paciente.getTiempoEsperando()+1);
            prioridadActualizada = prioridadActualizada || actualizarUrgencia(paciente);
        }
        if (prioridadActualizada) {
            
            porAtender = reordenar();
        }
    }

    public ColaConPrioridad<Paciente> getPorAtender() {
        return porAtender;
    }

    public boolean ponerPacienteEnEspera (Paciente paciente) {
        return porAtender.poneEnCola(paciente);
    }
    
    private boolean actualizarUrgencia(Paciente paciente){
        /*
        Verifica si el paciente superó el tiempo de espera establecido para 
        aumentar su nivel de prioridad. Si corresponde, modifica su nivel de 
        urgencia y reinicia su tiempo de espera.
        Devuelve valoresActualizados, si la prioridad pudo actualizarse la variable sera true y false si no se pudo.
         */
        boolean prioridadActualizada = false;

        if (paciente.getPrioridad() == NivelPrioridad.BAJA && paciente.getTiempoEsperando() == LIMITE_ESPERA_URGENCIA_BAJA) {
            paciente.setPrioridad(NivelPrioridad.MEDIA);
            paciente.setTiempoEsperando(0);
            prioridadActualizada = true;
        } else if (paciente.getPrioridad() == NivelPrioridad.MEDIA && paciente.getTiempoEsperando() == LIMITE_ESPERA_URGENCIA_MEDIA) {
            paciente.setPrioridad(NivelPrioridad.ALTA);
            paciente.setTiempoEsperando(0);
            prioridadActualizada = true;
        } else if (paciente.getPrioridad() == NivelPrioridad.ALTA && paciente.getTiempoEsperando() == LIMITE_ESPERA_URGENCIA_ALTA) {
            paciente.setPrioridad(NivelPrioridad.URGENTE);
            paciente.setTiempoEsperando(0);
            prioridadActualizada = true;
        }

        return prioridadActualizada;
    }

    private ColaConPrioridad<Paciente> reordenar() {
        ColaConPrioridad<Paciente> porAtenderOrdenado = new ColaConPrioridad<Paciente>(new ComparadorPacientes());

        for (int i = 0; i < porAtender.tamaño(); i++) {
            Paciente paciente  = porAtender.obtener(i);
            porAtenderOrdenado.poneEnCola(paciente);
        }
        return porAtenderOrdenado;
    }

}
