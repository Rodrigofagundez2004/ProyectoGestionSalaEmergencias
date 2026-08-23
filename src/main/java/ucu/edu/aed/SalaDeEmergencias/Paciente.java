package ucu.edu.aed.SalaDeEmergencias;

import java.util.Date;

import ucu.edu.aed.implementaciones.ListaDoblementeEnlazada;

public class Paciente {

    private int cedula;
    private String nombre;
    private String apellido;
    private byte edad;
    private Date fechaNacimiento;
    private final Causa causa;
    private NivelPrioridad prioridad;
    private int tiempoEsperando;
    private ListaDoblementeEnlazada<Procedimiento> procedimientosRealizados;

    public Paciente(int cedula, String nombre, String apellido, byte edad, Date fechaNacimiento, Causa causa) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.causa = causa;
        this.procedimientosRealizados = new ListaDoblementeEnlazada<>();
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public byte getEdad() {
        return edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Causa getCausa() {
        return causa;
    }

    public ListaDoblementeEnlazada<Procedimiento> getProcedimientos() {
        return procedimientosRealizados;
    }

    public void agregarProcedimiento(Procedimiento procedimiento) {
        this.procedimientosRealizados.agregar(procedimiento);
    }

    public NivelPrioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(NivelPrioridad prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoEsperando() {
        return tiempoEsperando;
    }
    public ListaDoblementeEnlazada<Procedimiento> getProcedimientosRealizados()
    {
        return procedimientosRealizados;
    }
    public void setTiempoEsperando(int tiempoEsperando) {
        this.tiempoEsperando = tiempoEsperando;
    }

    public String toString() {
        return "Paciente: " + nombre + " " + apellido + " (" + cedula + ") \n Fecha de nacimiento: " + fechaNacimiento +
                "\n Fecha de ingreso: " + new Date() + "\n Motivo de la consulta: " + causa + "\n Nivel de prioridad: " + prioridad +
                "\n Tiempo de espera: " + tiempoEsperando + "\n Procedimientos realizados: ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        Paciente paciente = (Paciente) o;
        return this.cedula == paciente.cedula;
    }

    @Override
    public int hashCode() {
        return cedula;
    }
}
