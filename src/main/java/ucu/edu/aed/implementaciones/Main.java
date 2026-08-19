package ucu.edu.aed.implementaciones;

import java.util.Comparator;

import ucu.edu.aed.tda.*;

public class Main {
    public static void main(String [] args){

        Item item1 = new Item("uno", 1);
        Item item2 = new Item("dos",2);
        Item item3 = new Item("tres", 3);
        TDALista<Item> array = new ListaArray<>();
        TDALista<Item> enlazada = new ListaEnlazada<>();
        TDALista<Item> doble = new ListaDoblementeEnlazada<>();
        TDALista<Item> circular = new ListaEnlazadaCircular<>();
        TDALista<Item> dobleCircular = new ListaCircularDoble<>();
        TDACola<Item> cola = new Cola<>(); 
        TDACola<Item> prioridad = new ColaConPrioridad<>((i1,i2) -> Integer.compare(i1.getInt(), i2.getInt()) ); 
        TDAPila<Item> pila = new Pila<>();
        array.agregar(item3); array.agregar(item2); array.agregar(0, item1);
        enlazada.agregar(item2); enlazada.agregar(item1); enlazada.agregar(item3);
        doble.agregar(item1); doble.agregar(item2); doble.agregar(item3);
        circular.agregar(item1); circular.agregar(item2); circular.agregar(item3);
        dobleCircular.agregar(item1); dobleCircular.agregar(item2); dobleCircular.agregar(item3);
        cola.agregar(item1); cola.agregar(item2); cola.agregar(item3);
        prioridad.agregar(item1); prioridad.agregar(item2); prioridad.agregar(item3);
        pila.agregar(item1); pila.agregar(item2); pila.agregar(item3);

        /* Array funciona
        System.out.println(array.toString());
        for(int i = 0; i < array.tamaño(); i++){
            System.out.println(array.obtener(i));
        }
        array.remover(item3);
        System.out.println(array.toString());
        for(int i = 0; i < array.tamaño(); i++){
            System.out.println(array.obtener(i));
        }
        */

        System.out.println(enlazada.toString());
        Item unItem = enlazada.buscar((item) -> item.getString().equals("dos"));
        System.out.println(unItem);








    }
}
