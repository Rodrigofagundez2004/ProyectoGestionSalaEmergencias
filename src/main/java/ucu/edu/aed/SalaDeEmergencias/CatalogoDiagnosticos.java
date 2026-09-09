package ucu.edu.aed.SalaDeEmergencias;
import ucu.edu.aed.implementaciones.ArbolGenerico;
import ucu.edu.aed.tda.TDALista;

public class CatalogoDiagnosticos {
    private NodoCatalogoRaiz raiz;
    private ArbolGenerico<INodoCatalogo> arbol;

    public CatalogoDiagnosticos() {
        this.raiz = new NodoCatalogoRaiz("Catalogo");
        this.arbol = new ArbolGenerico<>(raiz);
    }

    /*
        Genera un nuevo Capitulo, los capitulos son hijos directos del Catalogo.
        Dentro de los capitulos van los Grupos.
    */ 
    public boolean agregarCapitulo(Capitulo capitulo) {
        if (capitulo == null || !raiz.aceptaHijo(capitulo)) {
            return false;
        }
        return arbol.insertar(nodo -> nodo == raiz, capitulo);
    }

    /*
        Genera un nuevo Grupo, los grupos son hijos directos de los distintos Capitulos.
        Dentro de los Grupos van los codigos, los codigos son las hojas.
    */ 
    public boolean agregarGrupo(Capitulo capituloPadre, Grupo grupo) {
        if (capituloPadre == null || grupo == null || !capituloPadre.aceptaHijo(grupo)) {
            return false;
        }
        return arbol.insertar(nodo -> nodo == capituloPadre, grupo);
    }

    /*
        Genera un nuevo Codigo, los Codigos son hojas del Catalogo entero.
        Dentro de los codigos, se define el detalle de los diagnosticos.
    */ 
    public boolean agregarCodigo(Grupo grupoPadre, Codigo codigo) {
        if (grupoPadre == null || codigo == null || !grupoPadre.aceptaHijo(codigo)) {
            return false;
        }
        return arbol.insertar(nodo -> nodo == grupoPadre, codigo);
    }

    /*
        Busca un codigo en base a su ID, ejemplo: A01, B19
    */
    public Codigo buscarCodigo(String codigoDiagnostico) {
        if (codigoDiagnostico == null) {
            return null;
        }

        INodoCatalogo encontrado = arbol.buscar(nodo -> nodo instanceof Codigo c && c.getId().equals(codigoDiagnostico));
        if (encontrado instanceof Codigo codigo) {
            return codigo;
        } else {
            return null;
        }
    }

    /*
        Recorre el capitulo ingresado por parametro y devuelve una lista con todos los codigos del capitulo.
    */
    public TDALista<Codigo> obtenerCodigosDeCapitulo(String nombreCapitulo) {
        return null;
    }

    
}
