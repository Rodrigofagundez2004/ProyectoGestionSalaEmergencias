package ucu.edu.aed.implementaciones;

public class Item{

    private String texto;
    private int numero;

    public Item(String unString,int unInt){
        this.texto = unString;
        this.numero = unInt;
    }

    public String getString(){
        return texto;
    }

    public int getInt(){
        return numero;
    }

    public String toString(){
        return texto + " " + numero;
    }

}