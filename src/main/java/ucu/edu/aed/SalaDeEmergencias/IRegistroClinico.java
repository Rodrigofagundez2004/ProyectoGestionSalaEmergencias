package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.implementaciones.ListaDoblementeEnlazada;

public interface IRegistroClinico {
    String getDescripcion();
    ListaDoblementeEnlazada<Insumo> getInsumos();
    void agregarInsumo(Insumo insumo);
    boolean isCerrado();
    void cerrar();
    ListaDoblementeEnlazada<IRegistroClinico> getHijos();
    void agregarHijo(IRegistroClinico hijo);
    int getDuracion();
}