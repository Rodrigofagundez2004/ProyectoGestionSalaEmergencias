package ucu.edu.aed.SalaDeEmergencias.Diagnosis;

public interface INodoCatalogo {
    String getNombre();
    boolean aceptaHijo(INodoCatalogo nodoCatalogo);
    
}
