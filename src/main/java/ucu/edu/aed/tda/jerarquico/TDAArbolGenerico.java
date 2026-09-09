package ucu.edu.aed.tda.jerarquico;
import ucu.edu.aed.implementaciones.lineales.listas.ListaDoblementeEnlazada;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Define un Tipo de Dato Abstracto (TDA) Árbol Genérico.
 *
 * <p>Un árbol genérico es una estructura jerárquica donde cada nodo puede tener
 * cero, uno o varios hijos.</p>
 *
 * @param <T> el tipo de dato almacenado en cada nodo del árbol
 */
public interface TDAArbolGenerico<T> {
    /**
     * Retorna el dato almacenado en la raíz del árbol.
     *
     * @return el dato de la raíz, o {@code null} si el árbol está vacío
     */
    T obtenerRaiz();

    /**
     * Inserta un dato como raíz del árbol.
     *
     * <p>Esta operación debería utilizarse solamente cuando el árbol está vacío.</p>
     *
     * @param dato el dato a insertar como raíz
     * @return {@code true} si se insertó correctamente;
     *         {@code false} si el árbol ya tenía raíz o si el dato es inválido
     */
    boolean insertarRaiz(T dato);

    /**
     * Inserta un nuevo dato como hijo del nodo que coincide con el criterio indicado.
     *
     * @param criterioPadre criterio para encontrar el nodo padre
     * @param datoHijo dato que se insertará como hijo del padre encontrado
     * @return {@code true} si el hijo fue insertado correctamente;
     *         {@code false} si no se encontró el padre o no se pudo insertar
     */
    boolean insertar(Predicate<T> criterioPadre, T datoHijo);

    /**
     * Busca y retorna el primer dato que coincide con el criterio indicado.
     *
     * @param criterioBusqueda criterio usado para buscar el dato
     * @return el dato encontrado, o {@code null} si no existe
     */
    T buscar(Predicate<T> criterioBusqueda);

    /**
     * Determina si el árbol contiene un dato que coincide con el criterio indicado.
     *
     * @param criterioBusqueda criterio usado para buscar el dato
     * @return {@code true} si el dato existe en el árbol;
     *         {@code false} en caso contrario
     */
    boolean contiene(Predicate<T> criterioBusqueda);

    /**
     * Elimina el nodo que coincide con el criterio indicado.
     *
     * <p>Dependiendo de la implementación, eliminar un nodo puede implicar eliminar
     * también todo su subárbol.</p>
     *
     * @param criterioBusqueda criterio usado para encontrar el nodo a eliminar
     * @return {@code true} si se eliminó algún nodo;
     *         {@code false} si no se encontró
     */
    boolean eliminar(Predicate<T> criterioBusqueda);

    /**
     * Recorre el árbol en preorden.
     *
     * <p>En preorden se procesa primero el nodo actual y luego sus hijos.</p>
     *
     * @param consumidor acción a ejecutar sobre cada dato del árbol
     */
    void preOrder(Consumer<T> consumidor);

    /**
     * Recorre el árbol en postorden.
     *
     * <p>En postorden se procesan primero los hijos y luego el nodo actual.</p>
     *
     * @param consumidor acción a ejecutar sobre cada dato del árbol
     */
    void postOrder(Consumer<T> consumidor);

    /**
     * Recorre el árbol por niveles.
     *
     * <p>Primero se visita la raíz, luego sus hijos, después los hijos de sus hijos,
     * y así sucesivamente.</p>
     *
     * @param consumidor acción a ejecutar sobre cada dato del árbol
     */
    void porNiveles(Consumer<T> consumidor);

    /**
     * Retorna la cantidad total de nodos del árbol.
     *
     * @return cantidad de nodos
     */
    int cantidadNodos();

    /**
     * Retorna la cantidad de hojas del árbol.
     *
     * <p>Una hoja es un nodo que no tiene hijos.</p>
     *
     * @return cantidad de hojas
     */
    int cantidadHojas();

    /**
     * Retorna la cantidad de nodos internos del árbol.
     *
     * <p>Un nodo interno es un nodo que tiene al menos un hijo.</p>
     *
     * @return cantidad de nodos internos
     */
    int cantidadNodosInternos();

    /**
     * Retorna la altura del árbol.
     *
     * <p>La altura puede definirse como la cantidad máxima de niveles desde la raíz
     * hasta una hoja. La implementación debe documentar si un árbol vacío tiene
     * altura 0 o -1.</p>
     *
     * @return altura del árbol
     */
    int altura();

    /**
     * Retorna el nivel del nodo que coincide con el criterio indicado.
     *
     * <p>Usualmente la raíz se considera en el nivel 0.</p>
     *
     * @param criterioBusqueda criterio usado para encontrar el nodo
     * @return el nivel del nodo encontrado, o {@code -1} si no existe
     */
    int obtenerNivel(Predicate<T> criterioBusqueda);

    /**
     * Determina si el árbol está vacío.
     *
     * @return {@code true} si no tiene nodos;
     *         {@code false} en caso contrario
     */
    boolean esVacio();

    /**
     * Elimina todos los nodos del árbol.
     */
    void vaciar();

    ListaDoblementeEnlazada<T> obtenerHijos(Predicate<T> criterio);
}