package org.example;

import junit.framework.TestCase;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Capitulo;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.CatalogoDiagnosticos;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Codigo;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Grupo;

public class CatalogoDiagnosticosTest extends TestCase {
    private CatalogoDiagnosticos catalogo;
    private Capitulo capituloInfecciosas;
    private Grupo grupoIntestinales;

    protected void setUp() {
        catalogo = new CatalogoDiagnosticos();
        capituloInfecciosas = new Capitulo("Ciertas enfermedades infecciosas y parasitarias", "A00", "B99");
        grupoIntestinales = new Grupo("Enfermedades infecciosas intestinales", "A00", "A09");
    }

    /*
        TESTS CASES PARA CAPITULOS DEL CATALOGO
    */

    // Se agrega un nuevo Capitulo
    public void testAgregarCapituloValidoRetornaTrue() {
        assertTrue(catalogo.agregarCapitulo(capituloInfecciosas));
    }

    // Se intenta agregar un capitulo vacio
    public void testAgregarCapituloNuloRetornaFalse() {
        assertFalse(catalogo.agregarCapitulo(null));
    }

    /*
        TESTS CASES PARA GRUPOS DEL CATALOGO
    */

    // Se agrega un nuevo Grupo detro de un capitulo creado anteriormente
    public void testAgregarGrupoDentroDeRangoRetornaTrue() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        assertTrue(catalogo.agregarGrupo(capituloInfecciosas, grupoIntestinales));
    }

    // Se intenta agregar un grupo fuera del rango del capitulo pre creado. Devuelve false
    public void testAgregarGrupoFueraDeRangoRetornaFalse() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        Grupo grupoFueraDeRango = new Grupo("Enfermedades hipertensivas", "I10", "I15");
        assertFalse(catalogo.agregarGrupo(capituloInfecciosas, grupoFueraDeRango));
    }

    // Se intetna agregar un grupo con un Capitulo padre null
    public void testAgregarGrupoConPadreNuloRetornaFalse() {
        assertFalse(catalogo.agregarGrupo(null, grupoIntestinales));
    }

    // Se intenta agregar un grupo null
    public void testAgregarGrupoNuloRetornaFalse() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        assertFalse(catalogo.agregarGrupo(capituloInfecciosas, null));
    }

    /*
        TESTS CASES PARA CODIGOS DENTRO DEL CATALOGO
    */

    // Se agrega codigo dentro de un grupo correcto
    public void testAgregarCodigoDentroDeRangoRetornaTrue() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        catalogo.agregarGrupo(capituloInfecciosas, grupoIntestinales);

        Codigo codigoA01 = new Codigo("Fiebres tifoidea y paratifoidea", "A01");

        assertTrue(catalogo.agregarCodigo(grupoIntestinales, codigoA01));
    }

    // Se intenta agregar un codigo en un grupo con distinto rango aceptado
    public void testAgregarCodigoFueraDeRangoRetornaFalse() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        catalogo.agregarGrupo(capituloInfecciosas, grupoIntestinales);

        Codigo codigoFueraDeRango = new Codigo("B15", "Hepatitis aguda A");

        assertFalse(catalogo.agregarCodigo(grupoIntestinales, codigoFueraDeRango));
    }

    // Se intenta agregar un codigo a un grupo null
    public void testAgregarCodigoConPadreNuloRetornaFalse() {
        Codigo codigoA01 = new Codigo("A01", "Fiebres tifoidea y paratifoidea");
        assertFalse(catalogo.agregarCodigo(null, codigoA01));
    }

    /*
        TESTS CASES PARA BUSQUEDAD DENTRO DEL CATALOGO
    */

    // Se busca un codigo existente
    public void testBuscarCodigoExistenteLoEncuentra() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        catalogo.agregarGrupo(capituloInfecciosas, grupoIntestinales);

        Codigo codigoA01 = new Codigo("Fiebres tifoidea y paratifoidea", "A01");
        catalogo.agregarCodigo(grupoIntestinales, codigoA01);

        Codigo encontrado = catalogo.buscarCodigo("A01");
        assertNotNull(encontrado);
        assertEquals("A01", encontrado.getId());
    }

    // Se busca un codigo que no existe
    public void testBuscarCodigoInexistenteRetornaNull() {
        catalogo.agregarCapitulo(capituloInfecciosas);
        catalogo.agregarGrupo(capituloInfecciosas, grupoIntestinales);
        assertNull(catalogo.buscarCodigo("Z99"));
    }

    // Se intenta buscar un codigo con parametro null
    public void testBuscarCodigoConIdNuloRetornaNull() {
        assertNull(catalogo.buscarCodigo(null));
    }

    /*
        TESTS CASES PARA CASOS BORDES
    */

    // Un catalogo bacio
    public void testBuscarCodigoEnCatalogoVacioRetornaNull() {
        assertNull(catalogo.buscarCodigo("A01"));
    }

    // Se intenta agregar un Grupo a un Catalogo sin Capitulos
    public void testAgregarGrupoSinCapitulosCargadosRetornaFalse() {
        assertFalse(catalogo.agregarGrupo(capituloInfecciosas, grupoIntestinales));
    }
}
