package ucu.edu.aed.SalaDeEmergencias;

public enum Causa {
    TAQUICARDIA(Procedimiento.ELECTROCARDIOGRAMA),
    LESION_RODILLA(Procedimiento.TOMOGRAFIA),
    TRAUMATISMO_CRANEAL(Procedimiento.RESONANCIA),
    FRACTURA_PERONE(Procedimiento.RADIOGRAFIA),
    APENDICITIS(Procedimiento.CIRUGIA_GENERAL);

    private final Procedimiento procedimiento;

    Causa(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Procedimiento getProcedimiento() {
        return procedimiento;
    }
}
