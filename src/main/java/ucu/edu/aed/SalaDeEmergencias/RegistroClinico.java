package ucu.edu.aed.SalaDeEmergencias;

import ucu.edu.aed.implementaciones.ListaDoblementeEnlazada;

public abstract class RegistroClinico {

    ListaDoblementeEnlazada<Insumo> insumos;
    int insumosUsados;

    public void setInsumos(Insumo insumo) {
        this.insumos.agregar(insumo);
        insumosUsados += 1;
    }

    public ListaDoblementeEnlazada<Insumo> getInsumos() {
        return insumos;
    }
}
