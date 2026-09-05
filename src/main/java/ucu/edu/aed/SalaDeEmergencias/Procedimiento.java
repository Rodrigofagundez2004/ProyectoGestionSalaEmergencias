package ucu.edu.aed.SalaDeEmergencias;

public class Procedimiento extends RegistroClinico {
    TipoProcedimiento tipoProcedimiento;

    public void Procedimiento(TipoProcedimiento procedimiento) {
        this.tipoProcedimiento = procedimiento;
    }

    public TipoProcedimiento getProcedimiento() {
        return this.tipoProcedimiento;
    }

}
