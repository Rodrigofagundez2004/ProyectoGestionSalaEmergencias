package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import ucu.edu.aed.SalaDeEmergencias.Causa;
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

    public void testIncrementarEsperaIncrementaTiempo() throws ParseException {
        Date fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
        SalaDeEspera porAtender = new SalaDeEspera();
        Paciente paciente = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaIngreso, Causa.TAQUICARDIA, NivelPrioridad.MEDIA);

        paciente.setTiempoEsperando(1);

        porAtender.ponerPacienteEnEspera(paciente);
        porAtender.incrementarEspera();

        assertEquals(2, paciente.getTiempoEsperando());
    }

    void incrementarEsperaActualizaPrioridadDeBajaAMedia() throws ParseException {
        SalaDeEspera porAtender = new SalaDeEspera();
        Date fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2025");
        Paciente paciente = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaIngreso, Causa.LESION_RODILLA, NivelPrioridad.BAJA);

        paciente.setTiempoEsperando(LIMITE_ESPERA_URGENCIA_BAJA - 1);

        porAtender.ponerPacienteEnEspera(paciente);
        porAtender.incrementarEspera();

        assertEquals(NivelPrioridad.MEDIA, paciente.getPrioridad());
        assertEquals(0, paciente.getTiempoEsperando());

    }

    void incrementarEsperaReordenaColaCuandoCambiaPrioridad() throws ParseException {
        SalaDeEspera porAtender = new SalaDeEspera();
        Date fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2026");
        Paciente pacienteBajaPrioridad = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaIngreso, Causa.LESION_RODILLA, NivelPrioridad.BAJA);
        pacienteBajaPrioridad.setTiempoEsperando(LIMITE_ESPERA_URGENCIA_BAJA - 1);

        Paciente pacienteMediaPrioridad = new Paciente(12345678, "Franco", "Martinez", (byte) 6, fechaIngreso, Causa.TRAUMATISMO_CRANEAL, NivelPrioridad.MEDIA);
        pacienteMediaPrioridad.setTiempoEsperando(1);

        porAtender.ponerPacienteEnEspera(pacienteBajaPrioridad);
        porAtender.ponerPacienteEnEspera(pacienteMediaPrioridad);
        porAtender.incrementarEspera();

        assertEquals(NivelPrioridad.MEDIA, porAtender.getPorAtender().frente().getPrioridad());

    }
}
