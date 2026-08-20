package ucu.edu.aed.SalaDeEmergencias;

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
         /*
         Agrega el paciente a la lista Pacientes y a la cola PorAtender.
         Devuelve true si se pudo registrar correctamente y false si no se pudo.
          */
        return false;
    }

    boolean atenderPaciente(){
        /*
        Toma de PorAtender al paciente que corresponda según el criterio
        de prioridad. Determina el procedimiento que corresponde según la causa.
        Agrega dicho procedimiento al historial de procedimientos del paciente.
        Finalmente agrega el paciente a la lista Atendidos.
        Devuelve true si se pudo realizar la atención y false si no se pudo.
         */
        return false;
    }

    boolean estaAtendido(Paciente paciente){
        /*
        Determina si el paciente está en la lista Atendidos.
        Devuelve true si está y false si no lo está.
         */
        return false;
    }

    String consultarSobrePaciente(Paciente paciente){
        /*
         Busca al paciente en la lista general Pacientes. Si lo encuentra,
         devuelve un String con todos sus datos, incluyendo sus procedimientos
         realizados. Si no lo encuentra, devuelve un mensaje indicando que
         el paciente no fue encontrado.
         */
        return null;
    }

    boolean modificarUrgencia(Paciente paciente){
        /*
        Busca al paciente en la cola PorAtender y modifica su nivel de prioridad.
        La modificación debe verse reflejada en su posición dentro de la cola.
        Devuelve true si se pudo modificar y false si no se pudo.
         */
        return false;
    }

    Paciente obtenerProximoPaciente(){
        /*
        Devuelve el siguiente paciente que se encuentra en la cola PorAtender.
        */
        return null;
    }
}
