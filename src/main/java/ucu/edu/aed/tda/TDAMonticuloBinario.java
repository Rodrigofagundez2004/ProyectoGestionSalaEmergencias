package ucu.edu.aed.tda;

/**
 * Interfaz que define las operaciones que debe cumplir un montículo binario (cola de prioridad).
 */
public interface TDAMonticuloBinario<T extends Comparable<T>> {

    /**
     * Inserta un elemento en el montículo.
     * 
     * @param elemento El elemento a insertar.
     */
    void insertar(T elemento);

    /**
     * Elimina y retorna el elemento de mayor o menor prioridad (dependiendo del tipo de montículo).
     * 
     * @return El elemento eliminado.
     * @throws IllegalStateException Si el montículo está vacío.
     */
    T eliminar();

    /**
     * Retorna el elemento de mayor o menor prioridad (dependiendo del tipo de montículo) sin eliminarlo.
     * 
     * @return El elemento con mayor o menor prioridad.
     * @throws IllegalStateException Si el montículo está vacío.
     */
    T obtener();

    /**
     * Verifica si el montículo está vacío.
     * 
     * @return true si el montículo está vacío, false en caso contrario.
     */
    boolean estaVacio();

    /**
     * Retorna el tamaño actual del montículo.
     * 
     * @return El número de elementos en el montículo.
     */
    int tamano();
}