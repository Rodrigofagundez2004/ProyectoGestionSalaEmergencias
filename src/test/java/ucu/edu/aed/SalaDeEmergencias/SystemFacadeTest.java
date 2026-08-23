package ucu.edu.aed.SalaDeEmergencias;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SystemFacadeTest extends TestCase {

    public SystemFacadeTest (String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(SystemFacadeTest.class);
    }

    public void testAtenderPaciente() {
        SystemFacade sistema = new SystemFacade();
        Paciente pacienteA = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.APENDICITIS);
        Paciente pacienteB = new Paciente(98765432, "Jane", "Doe", (byte) 14, new Date(), Causa.LESION_RODILLA);

        pacienteA.setPrioridad(NivelPrioridad.URGENTE);
        pacienteB.setPrioridad(NivelPrioridad.BAJA);

        sistema.registrarPaciente(pacienteA);
        sistema.registrarPaciente(pacienteB);

        sistema.atenderPaciente();
        
        assertTrue(sistema.estaAtendido(pacienteA));
        assertFalse(sistema.estaAtendido(pacienteB));
        assertFalse(pacienteA.getProcedimientosRealizados().esVacio());
    }


    public void testEstaAtendidoPacienteAtendido() {
        SystemFacade sistema = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.TAQUICARDIA);
        
        paciente.setPrioridad(NivelPrioridad.URGENTE);
        sistema.registrarPaciente(paciente);
        sistema.atenderPaciente();
        
        assertTrue(sistema.estaAtendido(paciente));
    }

    public void testEstaAtendidoPacienteNoLLego() {
        SystemFacade sistema = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.TAQUICARDIA);
        
        assertFalse(sistema.estaAtendido(paciente));
    }

    public void testModificarUrgencia() {
        SystemFacade sistema = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.LESION_RODILLA);

        paciente.setPrioridad(NivelPrioridad.BAJA);
        sistema.registrarPaciente(paciente);

        sistema.modificarUrgencia(paciente, NivelPrioridad.MEDIA);


        assertEquals( NivelPrioridad.MEDIA ,paciente.getPrioridad());


    }
}

