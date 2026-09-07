
package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.implementaciones.ArbolGenerico;
import ucu.edu.aed.implementaciones.ListaDoblementeEnlazada;

import java.util.Date;

public class EpisodioClinico {

    private final Paciente paciente;
    private final Date fechaApertura;
    private Date fechaCierre;
    private boolean cerrado;

    private final ListaDoblementeEnlazada<Diagnostico> diagnosticos;

    private final ArbolGenerico<IRegistroClinico> registroClinico;
    private final IRegistroClinico raizRegistro;

    public EpisodioClinico(Paciente paciente, ConsultaInicial consultaInicial) {
        this.paciente = paciente;
        this.fechaApertura = new Date();
        this.fechaCierre = null;
        this.cerrado = false;
        this.diagnosticos = new ListaDoblementeEnlazada<>();

        // El árbol comienza con la consulta inicial como raíz
        this.raizRegistro = consultaInicial;
        this.registroClinico = new ArbolGenerico<>(consultaInicial);
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public ListaDoblementeEnlazada<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public ArbolGenerico<IRegistroClinico> getRegistroClinico() {
        return registroClinico;
    }

    public IRegistroClinico getRaizRegistro() {
        return raizRegistro;
    }

    public void agregarDiagnostico(Diagnostico diagnostico) {
        if (diagnostico != null) {
            diagnosticos.agregar(diagnostico);
        }
    }

    /**
     * Asocia un nuevo registro (estudio, interconsulta, procedimiento, complicación...)
     * como hijo del registro que lo originó.
     */
    public boolean agregarRegistro(IRegistroClinico padre, IRegistroClinico hijo) {
        if (padre == null || hijo == null) {
            return false;
        }

        IRegistroClinico encontrado = registroClinico.buscar(nodo -> nodo == padre);
        if (encontrado == null) {
            return false;
        }

        encontrado.agregarHijo(hijo);
        return true;
    }

    private boolean verificarNodosCerrados(IRegistroClinico nodo) {
        if (nodo == null) {
            return true;
        }
        // Postorden: primero los hijos, después el nodo actual
        for (IRegistroClinico hijo : nodo.getHijos()) {
            if (!verificarNodosCerrados(hijo)) {
                return false;
            }
        }
        return nodo.isCerrado();
    }

    /**
     * Un episodio sólo puede cerrarse cuando todo lo que se desprendió de él
     * (todo el árbol) está cerrado.
     */
    public boolean puedeCerrarse() {
        if (cerrado) {
            return false;
        }
        return verificarNodosCerrados(raizRegistro);
    }

    public boolean cerrar() {
        if (!puedeCerrarse()) {
            return false;
        }
        this.cerrado = true;
        this.fechaCierre = new Date();
        return true;
    }

    private void recolectarInsumos(IRegistroClinico nodo, ListaDoblementeEnlazada<Insumo> acumulador) {
        if (nodo == null) {
            return;
        }
        for (Insumo insumo : nodo.getInsumos()) {
            acumulador.agregar(insumo);
        }
        for (IRegistroClinico hijo : nodo.getHijos()) {
            recolectarInsumos(hijo, acumulador);
        }
    }

    /** Insumos consumidos en todo el episodio (o en cualquier subárbol, si se llama sobre un nodo). */
    public ListaDoblementeEnlazada<Insumo> obtenerInsumosTotales() {
        ListaDoblementeEnlazada<Insumo> total = new ListaDoblementeEnlazada<>();
        recolectarInsumos(raizRegistro, total);
        return total;
    }

    private int sumarDuraciones(IRegistroClinico nodo) {
        if (nodo == null) {
            return 0;
        }
        int total = nodo.getDuracion();
        for (IRegistroClinico hijo : nodo.getHijos()) {
            total += sumarDuraciones(hijo);
        }
        return total;
    }

    public int obtenerDuracionTotal() {
        return sumarDuraciones(raizRegistro);
    }

    public String obtenerArbolComoString() {
        if (raizRegistro == null) {
            return "Árbol vacío";
        }
        return arbolToString(raizRegistro, 0);
    }

    private String arbolToString(IRegistroClinico nodo, int nivel) {
        if (nodo == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
            String indent = "";
            for (int i = 0; i < nivel; i++) {
            indent += "  ";
            }

        sb.append(indent).append("├─ ").append(nodo.getDescripcion());
        sb.append(" [").append(nodo.isCerrado() ? "CERRADO" : "ABIERTO").append("]");
        sb.append("\n");
        for (IRegistroClinico hijo : nodo.getHijos()) {
            sb.append(arbolToString(hijo, nivel + 1));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EpisodioClinico{" +
                "paciente=" + paciente.getNombre() + " " + paciente.getApellido() +
                ", cedula=" + paciente.getCedula() +
                ", fechaApertura=" + fechaApertura +
                ", fechaCierre=" + (fechaCierre != null ? fechaCierre : "ABIERTO") +
                ", cerrado=" + cerrado +
                ", diagnosticos=" + diagnosticos.tamaño() +
                ", duracionTotal=" + obtenerDuracionTotal() +
                '}';
    }
}