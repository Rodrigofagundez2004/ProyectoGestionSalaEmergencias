package org.example;

import junit.framework.TestCase;

import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Diagnostico;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Codigo;
import ucu.edu.aed.SalaDeEmergencias.Registro.ConsultaInicial;
import ucu.edu.aed.SalaDeEmergencias.Registro.Estudio;
import ucu.edu.aed.SalaDeEmergencias.Registro.Procedimiento;
import ucu.edu.aed.SalaDeEmergencias.Registro.Complicacion;
import ucu.edu.aed.SalaDeEmergencias.Registro.TipoEstudio;
import ucu.edu.aed.SalaDeEmergencias.Registro.TipoProcedimiento;
import ucu.edu.aed.SalaDeEmergencias.Paciente;
import ucu.edu.aed.SalaDeEmergencias.EpisodioClinico;
import ucu.edu.aed.SalaDeEmergencias.Causa;
import ucu.edu.aed.SalaDeEmergencias.NivelPrioridad;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EpisodioClinicoTest extends TestCase {

    private Paciente paciente;
    private ConsultaInicial consulta;
    private EpisodioClinico episodio;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaIngreso = sdf.parse("12/05/2004");

        paciente = new Paciente(54825523, "Rodrigo", "Garcia", (byte) 22, fechaIngreso, NivelPrioridad.BAJA);
        consulta = new ConsultaInicial("Dolor en el pecho", Causa.ENFERMEDAD);
        episodio = new EpisodioClinico(paciente, consulta);
    }

    public void testCrearEpisodio() {
        assertNotNull(episodio);
        assertNotNull(episodio.getPaciente());
        assertNotNull(episodio.getFechaApertura());
        assertFalse(episodio.isCerrado());
        assertEquals(0, episodio.getDiagnosticos().tamaño());
        assertNotNull(episodio.getRegistroClinico());
        assertNotNull(episodio.getRaizRegistro());
    }

    public void testAgregarDiagnostico() {
        Codigo c1 = new Codigo("Fiebre", "R50");
        Codigo c2 = new Codigo("Infección", "J06");
        episodio.agregarDiagnostico(new Diagnostico(c1, true));
        episodio.agregarDiagnostico(new Diagnostico(c2, false));
        assertEquals(2, episodio.getDiagnosticos().tamaño());
    }

    public void testAgregarRegistro() {
        Estudio estudio = new Estudio("Ecografía", TipoEstudio.ECOGRAFIA);
        Procedimiento proc = new Procedimiento("Apéndice", TipoProcedimiento.CIRUGIA_GENERAL);
        proc.setDuracion(30);
        Complicacion comp = new Complicacion("Infección postoperatoria");

        assertTrue(episodio.agregarRegistro(estudio, consulta.getId()));
        assertTrue(episodio.agregarRegistro(proc, estudio.getId()));
        assertTrue(episodio.agregarRegistro(comp, proc.getId()));

        assertNotNull(episodio.getRegistroClinico());
    }

    public void testCierreExitoso() {
        consulta.cerrar();
        assertTrue(episodio.puedeCerrarse());
        assertTrue(episodio.cerrar());
        assertTrue(episodio.isCerrado());
        assertNotNull(episodio.getFechaCierre());
    }

    public void testCierreFallidoPorNodoAbierto() {
        Procedimiento proc = new Procedimiento("Angioplastia", TipoProcedimiento.CIRUGIA_GENERAL);
        proc.setDuracion(45);
        episodio.agregarRegistro(proc, consulta.getId());
        consulta.cerrar();
        assertFalse(episodio.puedeCerrarse());
        assertFalse(episodio.cerrar());
        assertFalse(episodio.isCerrado());
    }
}