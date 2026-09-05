package ucu.edu.aed.SalaDeEmergencias;

public enum Causa {
    CORTE(TipoProcedimiento.SUTURA),
    ABSCESO(TipoProcedimiento.DRENAJE),
    INSUFICIENCIA_RESPIRATORIA(TipoProcedimiento.INTUBACION),
    FRACTURA_PERONE(TipoProcedimiento.COLOCACION_DE_YESO),
    APENDICITIS(TipoProcedimiento.CIRUGIA_GENERAL);

    private final TipoProcedimiento procedimiento;

    Causa(TipoProcedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public TipoProcedimiento getProcedimiento() {
        return procedimiento;
    }
}
