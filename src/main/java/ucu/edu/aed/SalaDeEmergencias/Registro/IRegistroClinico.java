package ucu.edu.aed.SalaDeEmergencias.Registro;

import ucu.edu.aed.SalaDeEmergencias.Insumo;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

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