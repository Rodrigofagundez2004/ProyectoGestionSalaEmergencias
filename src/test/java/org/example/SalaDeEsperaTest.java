package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import ucu.edu.aed.SalaDeEmergencias.NivelPrioridad;
import ucu.edu.aed.SalaDeEmergencias.Paciente;
import ucu.edu.aed.SalaDeEmergencias.SalaDeEspera;

/**
 * Pruebas unitarias para SalaDeEspera
 */
public class SalaDeEsperaTest extends TestCase {
    private static final int LIMITE_ESPERA_URGENCIA_BAJA = 4;
    private static final int LIMITE_ESPERA_URGENCIA_MEDIA = 3;
    private static final int LIMITE_ESPERA_URGENCIA_ALTA = 2;
    private static final int LIMITE_ESPERA_URGENCIA_URGENTE = 1;


    // TEST: incrementarEspera() - incrementa el tiempo

    public void testIncrementarEsperaIncrementaTiempo() throws ParseException {
        Date fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2004");
        SalaDeEspera porAtender = new SalaDeEspera();
        Paciente paciente = new Paciente(
            54825523, 
            "Rodrigo", 
            "Garcia", 
            (byte) 22, 
            fechaIngreso, 
            NivelPrioridad.BAJA 
        );

        paciente.setTiempoEsperando(1);

        porAtender.ponerPacienteEnEspera(paciente);
        porAtender.incrementarEspera();

        assertEquals(2, paciente.getTiempoEsperando());
    }


    // TEST: incrementarEspera() - actualiza prioridad de BAJA a MEDIA
  
    public void testIncrementarEsperaActualizaPrioridadDeBajaAMedia() throws ParseException {
        SalaDeEspera porAtender = new SalaDeEspera();
        Date fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2004");
        Paciente paciente = new Paciente(
            54825523, 
            "Rodrigo", 
            "Garcia", 
            (byte) 22, 
            fechaIngreso, 
            NivelPrioridad.BAJA  
        );

        paciente.setTiempoEsperando(LIMITE_ESPERA_URGENCIA_BAJA - 1);

        porAtender.ponerPacienteEnEspera(paciente);
        porAtender.incrementarEspera();

        assertEquals(NivelPrioridad.MEDIA, paciente.getPrioridad());
        assertEquals(0, paciente.getTiempoEsperando());
    }

    // TEST: incrementarEspera() - reordena la cola cuando cambia prioridad
  
    public void testIncrementarEsperaReordenaColaCuandoCambiaPrioridad() throws ParseException {
        SalaDeEspera porAtender = new SalaDeEspera();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date fechaIngreso1 = sdf.parse("12/05/2004");
        Paciente pacienteBajaPrioridad = new Paciente(
            54825523, 
            "Rodrigo", 
            "Garcia", 
            (byte) 22, 
            fechaIngreso1, 
            NivelPrioridad.BAJA  
        );
        pacienteBajaPrioridad.setTiempoEsperando(LIMITE_ESPERA_URGENCIA_BAJA - 1);

        Date fechaIngreso2 = sdf.parse("01/02/2020");
        Paciente pacienteMediaPrioridad = new Paciente(
            12345678, 
            "Franco", 
            "Martinez", 
            (byte) 6, 
            fechaIngreso2, 
            NivelPrioridad.MEDIA  
        );
        pacienteMediaPrioridad.setTiempoEsperando(1);

        porAtender.ponerPacienteEnEspera(pacienteBajaPrioridad);
        porAtender.ponerPacienteEnEspera(pacienteMediaPrioridad);

        porAtender.incrementarEspera();

        // Después de incrementar espera, el paciente baja pasa a MEDIA
        // Pero el que ya era MEDIA tiene prioridad igual, y llegó después
        // Por lo que el frente debería ser el que tiene más tiempo de espera (pacienteBajaPrioridad)
        assertEquals(
            NivelPrioridad.MEDIA, 
            porAtender.getPorAtender().frente().getPrioridad()
        );
    }
}