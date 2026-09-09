package ucu.edu.aed.SalaDeEmergencias;

import java.util.Date;

import ucu.edu.aed.SalaDeEmergencias.Registro.Procedimiento;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

public class Paciente implements Comparable<Paciente> {

    private int cedula;
    private String nombre;
    private String apellido;
    private byte edad;
    private Date fechaIngreso;
    private NivelPrioridad prioridad;
    private int tiempoEsperando;
    private ListaDoblementeEnlazada<EpisodioClinico> episodiosClinicos;

    public Paciente(int cedula, String nombre, String apellido, byte edad, Date fechaIngreso, NivelPrioridad prioridad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaIngreso = fechaIngreso;
        this.prioridad = prioridad;
        this.episodiosClinicos = new ListaDoblementeEnlazada<>();
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public ListaDoblementeEnlazada<EpisodioClinico> getEpisodiosClinicos() {
        return episodiosClinicos;
    }

    public void agregarEpisodioClinico(EpisodioClinico episodio) {
        this.episodiosClinicos.agregar(episodio);
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

    public void setTiempoEsperando(int tiempoEsperando) {
        this.tiempoEsperando = tiempoEsperando;
    }

    public String toString() {
        return "Paciente: " + nombre + " " + apellido + " (" + cedula + ") \n Fecha de ingreso: " + fechaIngreso +
                "\n Fecha de ingreso: " + new Date() + "\n Nivel de prioridad: " + prioridad +
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

    @Override
    public int compareTo(Paciente otroPaciente) {
        return Integer.compare(this.cedula, otroPaciente.cedula);
    }
}