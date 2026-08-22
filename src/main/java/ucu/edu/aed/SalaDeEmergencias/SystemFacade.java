package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.implementaciones.ColaConPrioridad;
import ucu.edu.aed.implementaciones.ListaDoblementeEnlazada;

public class SystemFacade {
    private ListaDoblementeEnlazada<Paciente> pacientes;
    private SalaDeEspera pacientesEnEspera;
    private ListaDoblementeEnlazada<Paciente> atendidos;

    public SystemFacade(){
        pacientes = new ListaDoblementeEnlazada<>();
        pacientesEnEspera = new SalaDeEspera();
        atendidos = new ListaDoblementeEnlazada<>();
    }

    boolean registrarPaciente(Paciente pacienteARegistrar){
        if (pacienteARegistrar == null)
        {
            return false;
        }
        //validar que no exista ya un paciente con la misma cedula
        for (int i = 0; i < pacientes.tamaño(); i++) {
            Paciente existente = pacientes.obtener(i);
            if (existente.getCedula() == pacienteARegistrar.getCedula()) {
            return false; // Ya existe
            }
        }
        // agregar a la lista general
        pacientes.agregar(pacienteARegistrar);
        // Agregar a la cola de espera con su prioridad inicial.
        pacientesEnEspera.ponerPacienteEnEspera(pacienteARegistrar);
        return true;

    }

    boolean atenderPaciente(){
        /*
        Toma de PorAtender al paciente que corresponda según el criterio
        de prioridad. Determina el procedimiento que corresponde según la causa.
        Agrega dicho procedimiento al historial de procedimientos del paciente.
        Finalmente agrega el paciente a la lista Atendidos.
        Devuelve true si se pudo realizar la atención y false si no se pudo.
         */
	if (pacientesEnEspera.getPorAtender().esVacio()){
		return false;
	}
	Paciente paciente = pacientesEnEspera.getPorAtender().quitaDeCola();
	Procedimiento procedimiento = paciente.getCausa().getProcedimiento();
	paciente.getProcedimientosRealizados().agregar(procedimiento);
	atendidos.agregar(paciente);
	
        return true;
    }

    boolean estaAtendido(Paciente paciente){
        /*
        Determina si el paciente está en la lista Atendidos.
        Devuelve true si está y false si no lo está.
         */
	if (atendidos.contiene(paciente)){
		return true;
	}
        return false;
    }

    String consultarSobrePaciente(Paciente paciente){

        if (paciente == null)
        {
            return "paciente no encontrado (null)";
        }

        for (int i = 0; i < pacientes.tamaño(); i++)
        {
            Paciente encontrado = pacientes.obtener(i);
            if (encontrado.getCedula() == paciente.getCedula())
            {
                StringBuilder sb = new StringBuilder();
                sb.append("===DATOS DEL PACIENTE===\n");
                sb.append("Cédula: ").append(encontrado.getCedula()).append("\n");
                sb.append("Nombre;").append(encontrado.getNombre()).append("\n");
                sb.append("Apellido:").append(encontrado.getApellido()).append("\n");
                sb.append("Edad:").append(encontrado.getEdad()).append("\n");
                sb.append("Fecha de ingreso:").append(encontrado.getFechaNacimiento()).append("\n");
                sb.append("Motivo").append(encontrado.getCausa()).append("\n");
                sb.append("Prioridad:").append(encontrado.getPrioridad()).append("\n");
                sb.append("Tiempo en espera :").append(encontrado.getTiempoEsperando()).append("\n");
                sb.append("Procedimientos realizados");
                ListaDoblementeEnlazada<Procedimiento> procedimientos = encontrado.getProcedimientosRealizados();


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
        }
        return "paciente no encontrado";
    }

    boolean modificarUrgencia(Paciente paciente, NivelPrioridad nuevoNivel){
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
        cola.poneEnCola(encontrado);
        while (!temp.esVacio())
        {
            cola.poneEnCola(temp.quitaDeCola());
        }
        return true;
        

    }

    Paciente obtenerProximoPaciente(){
        /*
        Devuelve el siguiente paciente que se encuentra en la cola PorAtender.
        */
        return null;
    }
}
