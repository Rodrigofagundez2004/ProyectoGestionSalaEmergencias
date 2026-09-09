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
		return new Paciente(
				cedula,
				NOMBRE,
				APELLIDO,
				EDAD,
				fechaIngreso,
				NivelPrioridad.MEDIA);
	}

	/*
		TESTS CASES PARA LA CREACION DE PACIENTES
	*/

	// Se crea un paciente y se verifica que sus datos queden inicializados
	public void testCrearPacienteInicializaSusDatos() {
		assertEquals(CEDULA, paciente.getCedula());
		assertEquals(NOMBRE, paciente.getNombre());
		assertEquals(APELLIDO, paciente.getApellido());
		assertEquals(EDAD, paciente.getEdad());
		assertSame(fechaIngreso, paciente.getFechaIngreso());
		assertEquals(NivelPrioridad.MEDIA, paciente.getPrioridad());
		assertEquals(0, paciente.getTiempoEsperando());
	}

	// Se crea un paciente y se verifica que comience sin episodios clinicos
	public void testCrearPacienteInicializaLaListaDeEpisodiosVacia() {
		assertNotNull(paciente.getEpisodiosClinicos());
		assertTrue(paciente.getEpisodiosClinicos().esVacio());
		assertEquals(0, paciente.getEpisodiosClinicos().tamaño());
	}

	/*
		TESTS CASES PARA LA ACTUALIZACION DE PACIENTES
	*/

	// Se cambia la prioridad y se verifica que el paciente conserve el nuevo valor
	public void testSetPrioridadActualizaLaPrioridad() {
		paciente.setPrioridad(NivelPrioridad.URGENTE);

		assertEquals(NivelPrioridad.URGENTE, paciente.getPrioridad());
	}

	// Se cambia el tiempo de espera y se verifica que el paciente conserve el nuevo valor
	public void testSetTiempoEsperandoActualizaElTiempo() {
		paciente.setTiempoEsperando(7);

		assertEquals(7, paciente.getTiempoEsperando());
	}

	/*
		TESTS CASES PARA LOS EPISODIOS CLINICOS
	*/

	// Se agrega un episodio clinico y se verifica que quede asociado al paciente
	public void testAgregarEpisodioClinicoLoAgregaALaLista() {
		EpisodioClinico episodio = new EpisodioClinico(
				paciente,
				new ConsultaInicial("Consulta inicial", Causa.ACCIDENTE));

		paciente.agregarEpisodioClinico(episodio);

		assertEquals(1, paciente.getEpisodiosClinicos().tamaño());
		assertSame(episodio, paciente.getEpisodiosClinicos().obtener(0));
	}

	// Se agregan varios episodios y se verifica que se conserven en orden de insercion
	public void testAgregarVariosEpisodiosConservaElOrden() {
		EpisodioClinico primerEpisodio = new EpisodioClinico(
				paciente,
				new ConsultaInicial("Primera consulta", Causa.ACCIDENTE));
		EpisodioClinico segundoEpisodio = new EpisodioClinico(
				paciente,
				new ConsultaInicial("Segunda consulta", Causa.CONTROL));

		paciente.agregarEpisodioClinico(primerEpisodio);
		paciente.agregarEpisodioClinico(segundoEpisodio);

		assertEquals(2, paciente.getEpisodiosClinicos().tamaño());
		assertSame(primerEpisodio, paciente.getEpisodiosClinicos().obtener(0));
		assertSame(segundoEpisodio, paciente.getEpisodiosClinicos().obtener(1));
	}

	/*
		TESTS CASES PARA LA IGUALDAD Y EL ORDEN NATURAL
	*/

	// Se comparan dos pacientes con la misma cedula y se verifica que sean iguales
	public void testPacientesConLaMismaCedulaSonIguales() {
		Paciente otroPaciente = crearPaciente(CEDULA);

		assertEquals(paciente, otroPaciente);
		assertEquals(paciente.hashCode(), otroPaciente.hashCode());
	}

	// Se comparan dos pacientes con distinta cedula y se verifica que sean diferentes
	public void testPacientesConDistintaCedulaNoSonIguales() {
		Paciente otroPaciente = crearPaciente(CEDULA + 1);
		Object objetoDiferente = "Paciente";

		assertFalse(paciente.equals(otroPaciente));
		assertFalse(paciente.equals((Object) null));
		assertFalse(paciente.equals(objetoDiferente));
	}

	// Se compara un paciente consigo mismo y se verifica que sea igual a si mismo
	public void testPacienteEsIgualASiMismo() {
		assertTrue(paciente.equals(paciente));
	}

	// Se comparan pacientes por cedula y se verifica el orden natural ascendente
	public void testCompareToOrdenaPorCedula() {
		Paciente pacienteMenor = crearPaciente(CEDULA - 1);
		Paciente pacienteMayor = crearPaciente(CEDULA + 1);

		assertTrue(pacienteMenor.compareTo(paciente) < 0);
		assertEquals(0, paciente.compareTo(crearPaciente(CEDULA)));
		assertTrue(pacienteMayor.compareTo(paciente) > 0);
	}
}
