package ucu.edu.aed.SalaDeEmergencias.Diagnosis;


public class Diagnostico {
    private Codigo codigo;
    private boolean confirmado;

    
    public Diagnostico(Codigo codigo, boolean confirmado) {
        this.codigo = codigo;
        this.confirmado = confirmado;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return codigo.getNombre();
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    @Override
    public String toString() {
        return codigo.getId() + " - " + codigo.getNombre() + (confirmado ? " (CONFIRMADO)" : " (PRESUNTIVO)");
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