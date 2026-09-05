package ucu.edu.aed.SalaDeEmergencias;

public class Insumo extends RegistroClinico{
    String Nombre;
    int Cantidad;

    public void Insumo (String nombre, int cantidad) {
        this.Nombre = nombre;
        this.Cantidad = cantidad;
    }

    public void setCantidad(int cantidad) {
        this.Cantidad = cantidad;
    }

    public int getCantidad(){
        return Cantidad;
    }

    public String getNombre(){
        return Nombre;
    }
}
