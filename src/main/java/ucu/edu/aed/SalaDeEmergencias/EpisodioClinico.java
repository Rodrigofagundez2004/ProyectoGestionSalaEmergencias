
package ucu.edu.aed.SalaDeEmergencias;

import java.util.Date;

import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Diagnostico;
import ucu.edu.aed.SalaDeEmergencias.Registro.ConsultaInicial;
import ucu.edu.aed.SalaDeEmergencias.Registro.IRegistroClinico;
import ucu.edu.aed.implementaciones.jerarquicas.generico.ArbolGenerico;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

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
    public boolean agregarRegistro(IRegistroClinico nodoAInsertar, int idPadre) {
        return registroClinico.insertar(nodo -> nodo.getId() == idPadre, nodoAInsertar);
    }

    /** Lista, en texto, los nodos abiertos que impiden cerrar el episodio ahora mismo. */
    public String obtenerMotivosDeNoCierre() {
        StringBuilder sb = new StringBuilder();
        registroClinico.preOrder(nodo -> {
            if (!nodo.isCerrado()) {
                sb.append("  - [").append(nodo.getId()).append("] ").append(nodo.getDescripcion()).append(" sigue ABIERTO\n");
            }
        });
        return (sb.length() == 0) ? "Nada impide el cierre." : sb.toString();
    }
 
    /** Cierra un nodo puntual del registro clinico (no el episodio completo). */
    public boolean cerrarNodo(int idNodo) {
        IRegistroClinico nodo = registroClinico.buscar(n -> n.getId() == idNodo);
        if (nodo == null) {
            return false;
        }
        nodo.cerrar();
        return true;
    }


    public boolean puedeCerrarse() {
        if (cerrado) return false;
        boolean[] todosCerrados = {true};
        registroClinico.postOrder(nodo -> { if (!nodo.isCerrado()) todosCerrados[0] = false; });
        return todosCerrados[0];
    }

    public boolean cerrar() {
        if (!puedeCerrarse()) {
            return false;
        }
        this.cerrado = true;
        this.fechaCierre = new Date();
        return true;
    }

    /** Insumos consumidos en todo el episodio (o en cualquier subárbol, si se llama sobre un nodo). */
    public ListaDoblementeEnlazada<Insumo> obtenerInsumosTotales() {
        ListaDoblementeEnlazada<Insumo> total = new ListaDoblementeEnlazada<>();
        registroClinico.preOrder(nodo -> {
            for (Insumo insumo : nodo.getInsumos()) total.agregar(insumo);
        });
        return total;
    }

    public int obtenerDuracionTotal() {
        int[] total = {0};
        registroClinico.preOrder(nodo -> total[0] += nodo.getDuracion());
        return total[0];
    }

    public String obtenerArbolComoString() {
        if (raizRegistro == null) {
            return "Árbol vacío";
        }
        return arbolToString(raizRegistro, 0);
    }

    private String arbolToString(IRegistroClinico nodo, int nivel) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(nivel)).append("├─ [").append(nodo.getId()).append("] ")
                .append(nodo.getDescripcion()).append(" [").append(nodo.isCerrado() ? "CERRADO" : "ABIERTO").append("]\n");
        for (IRegistroClinico hijo : registroClinico.obtenerHijos(n -> n.getId() == nodo.getId())) {
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