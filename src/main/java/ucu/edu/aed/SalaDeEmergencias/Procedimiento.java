package ucu.edu.aed.SalaDeEmergencias;

public class Procedimiento extends RegistroClinico {
    private final TipoProcedimiento tipoProcedimiento;

    public Procedimiento(String descripcion, TipoProcedimiento tipoProcedimiento) {
        super(descripcion);
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public TipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }
}