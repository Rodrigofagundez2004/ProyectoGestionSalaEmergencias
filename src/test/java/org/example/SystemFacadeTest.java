package org.example;
import ucu.edu.aed.SalaDeEmergencias.Paciente;
import ucu.edu.aed.SalaDeEmergencias.Causa;
import ucu.edu.aed.SalaDeEmergencias.NivelPrioridad;
import ucu.edu.aed.SalaDeEmergencias.SystemFacade;

import junit.framework.TestCase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemFacadeTest extends TestCase {

    private SystemFacade sistema;
    private Paciente paciente1;
    private Paciente paciente2;
    private Paciente paciente3;
    private Paciente paciente4;

    @Override
    protected void setUp() throws ParseException {
        sistema = new SystemFacade();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        paciente1 = new Paciente(1, "Juan", "Perez", (byte) 45, sdf.parse("15/03/1980"), Causa.FRACTURA_PERONE, NivelPrioridad.MEDIA);
        paciente2 = new Paciente(2, "Maria", "Gomez", (byte) 30, sdf.parse("20/07/1995"), Causa.APENDICITIS, NivelPrioridad.URGENTE);
        paciente3 = new Paciente(3, "Carlos", "Lopez", (byte) 60, sdf.parse("10/12/1965"), Causa.ABSCESO, NivelPrioridad.ALTA);
        paciente4 = new Paciente(4, "Ana", "Martinez", (byte) 25, sdf.parse("05/09/2000"), Causa.CORTE, NivelPrioridad.BAJA);
    }

    // TESTS: obtenerProximoPaciente()    // ============================================================
    public void testObtenerProximoPaciente_DeberiaDevolverElDeMayorPrioridad() {
        sistema.registrarPaciente(paciente1);
        sistema.registrarPaciente(paciente2);
        sistema.registrarPaciente(paciente3);

        Paciente proximo = sistema.obtenerProximoPaciente();

        assertNotNull(proximo);
        assertEquals(paciente2.getCedula(), proximo.getCedula());
        assertEquals(NivelPrioridad.URGENTE, proximo.getPrioridad());
    }

    public void testObtenerProximoPaciente_NoDeberiaEliminarDeLaCola() {
        sistema.registrarPaciente(paciente2);
        sistema.registrarPaciente(paciente3);

        Paciente proximo1 = sistema.obtenerProximoPaciente();
        Paciente proximo2 = sistema.obtenerProximoPaciente();

        assertNotNull(proximo1);
        assertNotNull(proximo2);
        assertEquals(proximo1.getCedula(), proximo2.getCedula());
        assertEquals(paciente2.getCedula(), proximo1.getCedula());
        assertEquals(paciente2.getCedula(), proximo2.getCedula());
    }

    public void testObtenerProximoPaciente_DeberiaRespetarFIFOEnEmpate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        Paciente p1 = new Paciente(10, "Primero", "A", (byte) 30, sdf.parse("01/01/1995"), Causa.FRACTURA_PERONE, NivelPrioridad.ALTA);
        Paciente p2 = new Paciente(11, "Segundo", "B", (byte) 40, sdf.parse("02/02/1985"), Causa.APENDICITIS, NivelPrioridad.ALTA);

        sistema.registrarPaciente(p1);
        sistema.registrarPaciente(p2);

        Paciente proximo = sistema.obtenerProximoPaciente();

        assertNotNull(proximo);
        assertEquals(p1.getCedula(), proximo.getCedula());
        assertEquals("Primero", proximo.getNombre());
    }

    public void testObtenerProximoPaciente_DeberiaDevolverNullSiColaVacia() {
        Paciente proximo = sistema.obtenerProximoPaciente();
        assertNull(proximo);
    }

    public void testObtenerProximoPaciente_DeberiaFuncionarConCambiosDePrioridad() {
        sistema.registrarPaciente(paciente1);
        sistema.registrarPaciente(paciente2);
        sistema.registrarPaciente(paciente3);

        Paciente primero = sistema.obtenerProximoPaciente();
        assertEquals(paciente2.getCedula(), primero.getCedula());

        sistema.modificarUrgencia(paciente3, NivelPrioridad.URGENTE);

        Paciente nuevoPrimero = sistema.obtenerProximoPaciente();
        assertNotNull(nuevoPrimero);
        assertEquals(paciente3.getCedula(), nuevoPrimero.getCedula());
    }

    public void testObtenerProximoPaciente_DeberiaMantenerOrdenEnCola() {
        sistema.registrarPaciente(paciente4);
        sistema.registrarPaciente(paciente1);
        sistema.registrarPaciente(paciente3);
        sistema.registrarPaciente(paciente2);

        Paciente primero = sistema.obtenerProximoPaciente();

        assertNotNull(primero);
        assertEquals(paciente2.getCedula(), primero.getCedula());

        Paciente siguiente = sistema.obtenerProximoPaciente();
        assertEquals(primero.getCedula(), siguiente.getCedula());
    }


    // TESTS: atenderPaciente()

    public void testAtenderPaciente_DeberiaAtenderAlDeMayorPrioridad() {
        SystemFacade sistemaLocal = new SystemFacade();
        
        Paciente pacienteA = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.APENDICITIS, NivelPrioridad.URGENTE);
        Paciente pacienteB = new Paciente(98765432, "Jane", "Doe", (byte) 14, new Date(), Causa.CORTE, NivelPrioridad.BAJA);

        sistemaLocal.registrarPaciente(pacienteA);
        sistemaLocal.registrarPaciente(pacienteB);

        sistemaLocal.atenderPaciente();
        
        assertTrue(sistemaLocal.estaAtendido(pacienteA));
        assertFalse(sistemaLocal.estaAtendido(pacienteB));
        assertFalse(pacienteA.getProcedimientosRealizados().esVacio());
    }


    // TESTS: estaAtendido()

    public void testEstaAtendido_CuandoPacienteFueAtendido_DeberiaDevolverTrue() {
        SystemFacade sistemaLocal = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.ABSCESO, NivelPrioridad.URGENTE);
        
        sistemaLocal.registrarPaciente(paciente);
        sistemaLocal.atenderPaciente();
        
        assertTrue(sistemaLocal.estaAtendido(paciente));
    }

    public void testEstaAtendido_CuandoPacienteNoLlego_DeberiaDevolverFalse() {
        SystemFacade sistemaLocal = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.FRACTURA_PERONE, NivelPrioridad.MEDIA);
        
        assertFalse(sistemaLocal.estaAtendido(paciente));
    }

    public void testEstaAtendido_CuandoPacienteRegistradoPeroNoAtendido_DeberiaDevolverFalse() {
        SystemFacade sistemaLocal = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.CORTE, NivelPrioridad.MEDIA);
        
        sistemaLocal.registrarPaciente(paciente);
        
        assertFalse(sistemaLocal.estaAtendido(paciente));
    }


    // TESTS: modificarUrgencia()

    public void testModificarUrgencia_DeberiaCambiarLaPrioridad() {
        SystemFacade sistemaLocal = new SystemFacade();
        Paciente paciente = new Paciente(12345678, "Jhon", "Doe", (byte) 20, new Date(), Causa.INSUFICIENCIA_RESPIRATORIA, NivelPrioridad.BAJA);

        sistemaLocal.registrarPaciente(paciente);
        sistemaLocal.modificarUrgencia(paciente, NivelPrioridad.MEDIA);

        assertEquals(NivelPrioridad.MEDIA, paciente.getPrioridad());
    }

    public void testModificarUrgencia_CuandoPacienteNoExiste_DeberiaDevolverFalse() {
        SystemFacade sistemaLocal = new SystemFacade();
        Paciente paciente = new Paciente(99999999, "Inexistente", "Test", (byte) 30, new Date(), Causa.FRACTURA_PERONE, NivelPrioridad.BAJA);

        boolean resultado = sistemaLocal.modificarUrgencia(paciente, NivelPrioridad.ALTA);

        assertFalse(resultado);
    }
}