package org.example;

import java.util.Date;

import junit.framework.TestCase;
import ucu.edu.aed.SalaDeEmergencias.Causa;
import ucu.edu.aed.SalaDeEmergencias.Registro.ConsultaInicial;
import ucu.edu.aed.SalaDeEmergencias.EpisodioClinico;
import ucu.edu.aed.SalaDeEmergencias.NivelPrioridad;
import ucu.edu.aed.SalaDeEmergencias.Paciente;

public class PacienteTest extends TestCase {
    private static final int CEDULA = 54825523;
    private static final String NOMBRE = "Rodrigo";
    private static final String APELLIDO = "Garcia";
    private static final byte EDAD = 22;
    private Date fechaIngreso;
    private Paciente paciente;

    @Override
    protected void setUp() {
        fechaIngreso = new Date();
        paciente = crearPaciente(CEDULA);
    }

    private Paciente crearPaciente(int cedula) {
        return new Paciente(cedula, NOMBRE, APELLIDO, EDAD, fechaIngreso, NivelPrioridad.MEDIA);
    }

    public void testCrearPacienteInicializaSusDatos() {
        assertEquals(CEDULA, paciente.getCedula());
        assertEquals(NOMBRE, paciente.getNombre());
        assertEquals(APELLIDO, paciente.getApellido());
        assertEquals(EDAD, paciente.getEdad());
        assertSame(fechaIngreso, paciente.getFechaIngreso());
        assertEquals(NivelPrioridad.MEDIA, paciente.getPrioridad());
        assertEquals(0, paciente.getTiempoEsperando());
    }

    public void testCrearPacienteInicializaLaListaDeEpisodiosVacia() {
        assertNotNull(paciente.getEpisodiosClinicos());
        assertTrue(paciente.getEpisodiosClinicos().esVacio());
    }

    public void testSetPrioridadActualizaLaPrioridad() {
        paciente.setPrioridad(NivelPrioridad.URGENTE);
        assertEquals(NivelPrioridad.URGENTE, paciente.getPrioridad());
    }

    public void testSetTiempoEsperandoActualizaElTiempo() {
        paciente.setTiempoEsperando(7);
        assertEquals(7, paciente.getTiempoEsperando());
    }

    public void testAgregarEpisodioClinicoLoAgregaALaLista() {
        EpisodioClinico episodio = new EpisodioClinico(paciente, new ConsultaInicial("Consulta inicial", Causa.ACCIDENTE));
        paciente.agregarEpisodioClinico(episodio);
        assertEquals(1, paciente.getEpisodiosClinicos().tamaño());
        assertSame(episodio, paciente.getEpisodiosClinicos().obtener(0));
    }

    public void testAgregarVariosEpisodiosConservaElOrden() {
        EpisodioClinico primero = new EpisodioClinico(paciente, new ConsultaInicial("Primera", Causa.ACCIDENTE));
        EpisodioClinico segundo = new EpisodioClinico(paciente, new ConsultaInicial("Segunda", Causa.CONTROL));
        paciente.agregarEpisodioClinico(primero);
        paciente.agregarEpisodioClinico(segundo);
        assertEquals(2, paciente.getEpisodiosClinicos().tamaño());
        assertSame(primero, paciente.getEpisodiosClinicos().obtener(0));
        assertSame(segundo, paciente.getEpisodiosClinicos().obtener(1));
    }

    public void testPacientesConLaMismaCedulaSonIguales() {
        Paciente otro = crearPaciente(CEDULA);
        assertEquals(paciente, otro);
        assertEquals(paciente.hashCode(), otro.hashCode());
    }

    public void testPacientesConDistintaCedulaNoSonIguales() {
        assertFalse(paciente.equals(crearPaciente(CEDULA + 1)));
        assertFalse(paciente.equals((Object) null));
        assertFalse(paciente.equals("Paciente"));
    }

    public void testPacienteEsIgualASiMismo() {
        assertTrue(paciente.equals(paciente));
    }

    public void testCompareToOrdenaPorCedula() {
        Paciente menor = crearPaciente(CEDULA - 1);
        Paciente mayor = crearPaciente(CEDULA + 1);
        assertTrue(menor.compareTo(paciente) < 0);
        assertEquals(0, paciente.compareTo(crearPaciente(CEDULA)));
        assertTrue(mayor.compareTo(paciente) > 0);
    }
}