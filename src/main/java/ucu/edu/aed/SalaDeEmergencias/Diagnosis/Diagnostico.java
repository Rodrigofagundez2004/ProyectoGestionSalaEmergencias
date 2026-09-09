package ucu.edu.aed.SalaDeEmergencias.Diagnosis;


public class Diagnostico {
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean confirmado;

    
    public Diagnostico(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.confirmado = false;
    }

    public Diagnostico(String codigo, String nombre, String descripcion, boolean confirmado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.confirmado = confirmado;
    }

    
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    
    @Override
    public String toString() {
        return codigo + " - " + nombre + (confirmado ? " (CONFIRMADO)" : " (PRESUNTIVO)");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Diagnostico that = (Diagnostico) obj;
        return codigo != null && codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}