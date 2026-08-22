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
        Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2004");
        SalaDeEspera porAtender = new SalaDeEspera();
        Paciente paciente = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaNacimiento, Causa.TAQUICARDIA);

        paciente.setPrioridad(NivelPrioridad.BAJA);
        paciente.setTiempoEsperando(1);

        porAtender.ponerPacienteEnEspera(paciente);
        porAtender.incrementarEspera();

        assertEquals(2, paciente.getTiempoEsperando());
    }

    void incrementarEsperaActualizaPrioridadDeBajaAMedia() throws ParseException {
        SalaDeEspera porAtender = new SalaDeEspera();
        Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2004");
        Paciente paciente = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaNacimiento, Causa.LESION_RODILLA);

        paciente.setPrioridad(NivelPrioridad.BAJA);
        paciente.setTiempoEsperando(LIMITE_ESPERA_URGENCIA_BAJA - 1);

        porAtender.ponerPacienteEnEspera(paciente);
        porAtender.incrementarEspera();

        assertEquals(NivelPrioridad.MEDIA, paciente.getPrioridad());
        assertEquals(0, paciente.getTiempoEsperando());

    }

    void incrementarEsperaReordenaColaCuandoCambiaPrioridad() throws ParseException {
        SalaDeEspera porAtender = new SalaDeEspera();
        Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2004");
        Paciente pacienteBajaPrioridad = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaNacimiento, Causa.LESION_RODILLA);

        pacienteBajaPrioridad.setPrioridad(NivelPrioridad.BAJA);
        pacienteBajaPrioridad.setTiempoEsperando(LIMITE_ESPERA_URGENCIA_BAJA - 1);

        fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2020");
        Paciente pacienteMediaPrioridad = new Paciente(12345678, "Franco", "Martinez", (byte) 6, fechaNacimiento, Causa.TRAUMATISMO_CRANEAL);

        pacienteMediaPrioridad.setPrioridad(NivelPrioridad.MEDIA);
        pacienteMediaPrioridad.setTiempoEsperando(1);

        porAtender.ponerPacienteEnEspera(pacienteBajaPrioridad);

        porAtender.ponerPacienteEnEspera(pacienteMediaPrioridad);

        porAtender.incrementarEspera();

        assertEquals(NivelPrioridad.MEDIA, porAtender.getPorAtender().frente().getPrioridad());

    }
}
