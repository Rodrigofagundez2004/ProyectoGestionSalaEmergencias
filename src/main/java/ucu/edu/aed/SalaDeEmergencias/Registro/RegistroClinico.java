package ucu.edu.aed.SalaDeEmergencias.Registro;

import ucu.edu.aed.SalaDeEmergencias.Insumo;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

public abstract class RegistroClinico implements IRegistroClinico {
    protected String descripcion;
    protected ListaDoblementeEnlazada<Insumo> insumos;
    protected boolean cerrado;
    protected int duracion;
    protected final int id;

    private static int contador = 0;


    public RegistroClinico(String descripcion) {
        this.descripcion = descripcion;
        this.insumos = new ListaDoblementeEnlazada<>();
        this.cerrado = false;
        this.duracion = 0;
        this.id = contador++;
    }

    @Override
    public String getDescripcion() { return descripcion; }
    @Override
    public ListaDoblementeEnlazada<Insumo> getInsumos() { return insumos; }
    @Override
    public void agregarInsumo(Insumo insumo) { if (insumo != null) insumos.agregar(insumo); }
    @Override
    public boolean isCerrado() { return cerrado; }
    @Override
    public void cerrar() { this.cerrado = true; }
    @Override
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public int getId() {
        return this.id;
    }
}