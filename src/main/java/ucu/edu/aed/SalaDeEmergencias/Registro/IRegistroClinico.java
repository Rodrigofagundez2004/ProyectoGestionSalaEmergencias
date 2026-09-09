package ucu.edu.aed.SalaDeEmergencias.Registro;

import ucu.edu.aed.SalaDeEmergencias.Insumo;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

public interface IRegistroClinico {
    String getDescripcion();
    ListaDoblementeEnlazada<Insumo> getInsumos();
    void agregarInsumo(Insumo insumo);
    boolean isCerrado();
    void cerrar();
    int getDuracion();
    int getId();
}