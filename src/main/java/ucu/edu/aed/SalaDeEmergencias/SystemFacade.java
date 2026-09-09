package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.SalaDeEmergencias.Diagnosis.CatalogoDiagnosticos;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Codigo;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Diagnostico;
import ucu.edu.aed.SalaDeEmergencias.Registro.ConsultaInicial;
import ucu.edu.aed.implementaciones.jerarquicas.AVL.ArbolBinarioBalanceado;
import ucu.edu.aed.implementaciones.lineales.colas.ColaConPrioridad;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

public class SystemFacade {
    private ArbolBinarioBalanceado<Paciente> pacientes;
    private SalaDeEspera pacientesEnEspera;
    private ListaDoblementeEnlazada<Paciente> atendidos;
    private CatalogoDiagnosticos catalogoDiagnosticos;

    public SystemFacade(){
        pacientes = new ArbolBinarioBalanceado<>();
        pacientesEnEspera = new SalaDeEspera();
        atendidos = new ListaDoblementeEnlazada<>();
        catalogoDiagnosticos = new CatalogoDiagnosticos();
    }

    public  boolean registrarPaciente(Paciente pacienteARegistrar){
        if (pacienteARegistrar == null || pacientes.buscar(pacienteARegistrar) != null)
        {
            return false;
        }
        // agregar a la lista general
        pacientes.insertar(pacienteARegistrar);
        // Agregar a la cola de espera con su prioridad inicial.
        pacientesEnEspera.ponerPacienteEnEspera(pacienteARegistrar);
        return true;
    }

    public boolean atenderPaciente(Causa causa){
        /*
        Toma de PorAtender al paciente que corresponda según el criterio
        de prioridad. Determina el procedimiento que corresponde según la causa.
        Agrega dicho procedimiento al historial de procedimientos del paciente.
        Finalmente agrega el paciente a la lista Atendidos.
        Devuelve true si se pudo realizar la atención y false si no se pudo.
         */
        if (pacientesEnEspera.getPorAtender().esVacio()) {
            return false;
        }
        Paciente paciente = pacientesEnEspera.getPorAtender().quitaDeCola();

        ConsultaInicial consultaInicial = new ConsultaInicial("Consulta inicial", causa);
        EpisodioClinico episodio = new EpisodioClinico(paciente, consultaInicial);
        paciente.agregarEpisodioClinico(episodio);

        atendidos.agregar(paciente);
        return true;
    }

    
    /*
        Se pasa cedula, el id del codigo y si esta confirmado, para asignar un diagnostico.
        Se va a buscar que episodio clinico tiene el pasiente aun sin cerrar (es decir esta en curso)
        para asignarselo a ese.
    */
    public boolean asignarDiagnostico(int cedula, String codigoId, boolean confirmado) {
        EpisodioClinico episodio = obtenerEpisodioAbierto(cedula);
        if (episodio == null) {
            return false; // no tiene episodio abierto al cual asignarle el diagnostico
        }

        Codigo codigo = catalogoDiagnosticos.buscarCodigo(codigoId);
        if (codigo == null) {
            return false; // el codigo no existe en el catalogo institucional
        }

        episodio.agregarDiagnostico(new Diagnostico(codigo, confirmado));
        return true;
    }

    public boolean estaAtendido(Paciente paciente){
        /*
        Determina si el paciente está en la lista Atendidos.
        Devuelve true si está y false si no lo está.
         */
        if (atendidos.contiene(paciente)){
            return true;
        }
        return false;
    }

   public String consultarSobrePaciente(int cedulaPaciente){   
        Paciente encontrado = pacientes.buscar(otro -> Integer.compare(cedulaPaciente, otro.getCedula()));
        if (encontrado == null) {
            return "Paciente no encontrado";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("===DATOS DEL PACIENTE===\n");
        sb.append("Cédula: ").append(encontrado.getCedula()).append("\n");
        sb.append("Nombre: ").append(encontrado.getNombre()).append("\n");
        sb.append("Apellido: ").append(encontrado.getApellido()).append("\n");
        sb.append("Edad: ").append(encontrado.getEdad()).append("\n");
        sb.append("Fecha de ingreso: ").append(encontrado.getFechaIngreso()).append("\n");
        sb.append("Prioridad: ").append(encontrado.getPrioridad()).append("\n");
        sb.append("Tiempo en espera: ").append(encontrado.getTiempoEsperando()).append("\n");
        sb.append("Procedimientos realizados: ");
        ListaDoblementeEnlazada<EpisodioClinico> procedimientos = encontrado.getEpisodiosClinicos();

        if (procedimientos == null || procedimientos.esVacio()) 
        {
            sb.append("Ninguno");

        } else {
            for (int j = 0; j < procedimientos.tamaño(); j++)
            {
                sb.append(procedimientos.obtener(j));
                if (j < procedimientos.tamaño() - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append("\n");
        sb.append("=========================\n");
        return sb.toString();
        
    }

    public boolean modificarUrgencia(Paciente paciente, NivelPrioridad nuevoNivel){
        if (paciente == null || nuevoNivel == null)
        {
            return false;
        }
        ColaConPrioridad<Paciente> cola = pacientesEnEspera.getPorAtender();
        if (cola == null || cola.esVacio())
        {
            return false;
        }
        //buscar pacientes en la cola
        // como no podemos buscar directamente  necesitamos recorrerla
        // y usar una cola temporal para mantener los elemen tos
        ColaConPrioridad<Paciente> temp = new ColaConPrioridad<>(new ComparadorPacientes());
        Paciente encontrado  = null;
        while (!cola.esVacio())
        {
            Paciente actual = cola.quitaDeCola();
            if (paciente.getCedula() == actual.getCedula())
            {
                encontrado = actual;
            }
            else {
                temp.poneEnCola(actual);
            }

        }
        //si no se encontro restaurar la cola original  y retornar false
        if (encontrado == null)
        {
            while (!temp.esVacio())
            {
                cola.poneEnCola(temp.quitaDeCola());
            }
            return false;
        }
        //modificamos la prioridad
        encontrado.setPrioridad(nuevoNivel);
        // volve ra insertar el paciente modificado 
        pacientesEnEspera.ponerPacienteEnEspera(encontrado);
        while (!temp.esVacio())
        {
            cola.poneEnCola(temp.quitaDeCola());
        }
        return true;
    }

    public boolean modificarUrgencia(int cedula, NivelPrioridad nuevoNivel) {
    // Buscar al paciente por cédula
        Paciente encontrado = pacientes.buscar(otro -> Integer.compare(cedula, otro.getCedula()));
        if (encontrado == null) {
            return false;
        }
        return modificarUrgencia(encontrado, nuevoNivel);
    }

    public Paciente obtenerProximoPaciente() {
        // Obtener la cola de espera
        ColaConPrioridad<Paciente> cola = pacientesEnEspera.getPorAtender();
        
        // Verificar si la cola está vacía
        if (cola == null || cola.esVacio()) {
            return null;
        }
        
        // Devolver el frente sin eliminarlo
        return cola.frente();
    }

    /*
        REVISAR REVISAR REVISAR REVISAR REVISAR
    
    public boolean agregarEstudio(int cedula, IRegistroClinico padre, Estudio estudio) {
        EpisodioClinico episodio = episodioAbierto(cedula);
        if (episodio == null) return false;
        return episodio.agregarRegistro(padre, estudio);
    }

    public boolean agregarConsultaInterna(int cedula, IRegistroClinico padre, ConsultaInterna consulta) {
        EpisodioClinico episodio = episodioAbierto(cedula);
        if (episodio == null) return false;
        return episodio.agregarRegistro(padre, consulta);
    }

    public boolean agregarProcedimiento(int cedula, IRegistroClinico padre, Procedimiento procedimiento) {
        EpisodioClinico episodio = episodioAbierto(cedula);
        if (episodio == null) return false;
        return episodio.agregarRegistro(padre, procedimiento);
    }

    public boolean agregarComplicacion(int cedula, IRegistroClinico padre, Complicacion complicacion) {
        EpisodioClinico episodio = episodioAbierto(cedula);
        if (episodio == null) return false;
        return episodio.agregarRegistro(padre, complicacion);
    }
    */

    /*
        En base a una cedula, devuelve el episodio abierto del paciente
    */
    private EpisodioClinico obtenerEpisodioAbierto(int cedula) {
        Paciente paciente = pacientes.buscar(otro -> Integer.compare(cedula, otro.getCedula()));
        if (paciente == null) {
            return null;
        }
        ListaDoblementeEnlazada<EpisodioClinico> episodios = paciente.getEpisodiosClinicos();
        for (int i = 0; i < episodios.tamaño(); i++) {
            EpisodioClinico episodio = episodios.obtener(i);
            if (!episodio.isCerrado()) {
                return episodio;
            }
        }
        return null;
    }
}
