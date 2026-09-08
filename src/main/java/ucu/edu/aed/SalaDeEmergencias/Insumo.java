
package ucu.edu.aed.SalaDeEmergencias;

public class Insumo {
    private final String nombre;
    private int cantidad;

    public Insumo(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}