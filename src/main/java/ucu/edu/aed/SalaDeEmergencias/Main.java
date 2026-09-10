package ucu.edu.aed.SalaDeEmergencias;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Capitulo;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Codigo;
import ucu.edu.aed.SalaDeEmergencias.Diagnosis.Grupo;
import ucu.edu.aed.SalaDeEmergencias.Registro.Complicacion;
import ucu.edu.aed.SalaDeEmergencias.Registro.ConsultaInterna;
import ucu.edu.aed.SalaDeEmergencias.Registro.Estudio;
import ucu.edu.aed.SalaDeEmergencias.Registro.Procedimiento;
import ucu.edu.aed.SalaDeEmergencias.Registro.TipoEstudio;
import ucu.edu.aed.SalaDeEmergencias.Registro.TipoProcedimiento;

public class Main {
    public static void main(String[] args) throws ParseException {

        SystemFacade facade = new SystemFacade();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        Date fechaIngreso = formato.parse("23/08/2026");
        Paciente paciente1 = new Paciente(54278247, "Francisco", "Lopez", (byte) 16, fechaIngreso, NivelPrioridad.MEDIA);
        Paciente paciente2 = new Paciente(34562479, "Mauro", "Martinez", (byte) 47, fechaIngreso, NivelPrioridad.BAJA);
        Paciente paciente3 = new Paciente(54825523, "Rodrigo", "Perez", (byte) 22, fechaIngreso, NivelPrioridad.URGENTE);
        Paciente paciente4 = new Paciente(25671821, "Natalia", "Rodriguez", (byte) 50, fechaIngreso, NivelPrioridad.ALTA);
        facade.registrarPaciente(paciente1);
        facade.registrarPaciente(paciente2);
        facade.registrarPaciente(paciente3);
        facade.registrarPaciente(paciente4);

        Capitulo capInfecciosas = new Capitulo("Ciertas enfermedades infecciosas y parasitarias", "A00", "B99");
        facade.agregarCapituloAlCatalogo(capInfecciosas);
        Grupo grupoIntestinales = new Grupo("Enfermedades infecciosas intestinales", "A00", "A09");
        facade.agregarGrupoAlCatalogo(capInfecciosas, grupoIntestinales);
        facade.agregarCodigoAlCatalogo(grupoIntestinales, new Codigo("Fiebres tifoidea y paratifoidea", "A01"));
        facade.agregarCodigoAlCatalogo(grupoIntestinales, new Codigo("Diarrea y gastroenteritis de presunto origen infeccioso", "A09"));
        Grupo grupoHepatitis = new Grupo("Hepatitis viral", "B15", "B19");
        facade.agregarGrupoAlCatalogo(capInfecciosas, grupoHepatitis);
        facade.agregarCodigoAlCatalogo(grupoHepatitis, new Codigo("Hepatitis aguda A", "B15"));
        facade.agregarCodigoAlCatalogo(grupoHepatitis, new Codigo("Hepatitis aguda B", "B16"));

        Capitulo capCirculatorio = new Capitulo("Enfermedades del sistema circulatorio", "I00", "I99");
        facade.agregarCapituloAlCatalogo(capCirculatorio);
        Grupo grupoHipertensivas = new Grupo("Enfermedades hipertensivas", "I10", "I15");
        facade.agregarGrupoAlCatalogo(capCirculatorio, grupoHipertensivas);
        facade.agregarCodigoAlCatalogo(grupoHipertensivas, new Codigo("Hipertensión esencial (primaria)", "I10"));
        facade.agregarCodigoAlCatalogo(grupoHipertensivas, new Codigo("Enfermedad cardíaca hipertensiva", "I11"));

        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n------ SISTEMA DE EMERGENCIAS ------");
            System.out.println("1: REGISTRAR NUEVO PACIENTE");
            System.out.println("2: BUSCAR INFORMACION DE PACIENTE");
            System.out.println("3: CONOCER PROXIMO PACIENTE PARA ATENDER");
            System.out.println("4: ATENDER PACIENTE (abre episodio con consulta inicial)");
            System.out.println("5: MODIFICAR URGENCIA DE PACIENTE");
            System.out.println("6: VER ARBOL DEL EPISODIO ABIERTO");
            System.out.println("7: AGREGAR ESTUDIO");
            System.out.println("8: AGREGAR CONSULTA INTERNA");
            System.out.println("9: AGREGAR PROCEDIMIENTO");
            System.out.println("10: AGREGAR COMPLICACION");
            System.out.println("11: ASIGNAR DIAGNOSTICO (por codigo del catalogo)");
            System.out.println("12: VER CATALOGO DE DIAGNOSTICOS");
            System.out.println("13: CERRAR UN NODO DEL EPISODIO");
            System.out.println("14: Salir");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\nRegistrando nuevo paciente... ");
                    System.out.println("Ingrese la cedula del paciente(sin puntos):");
                    int cedula = sc.nextInt();
                    System.out.println("Ingrese el nombre del paciente:");
                    String nombre = sc.next();
                    System.out.println("Ingrese el apellido del paciente:");
                    String apellido = sc.next();
                    System.out.println("Ingrese la edad del paciente:");
                    byte edad = sc.nextByte();
                    System.out.println("Ingrese la fecha de ingreso del paciente (dd/mm/aaaa):");
                    String fechaIngresoString = sc.next();

                    try {
                        fechaIngreso = formato.parse(fechaIngresoString);

                        System.out.println("Ingrese la prioridad del paciente:");
                        NivelPrioridad[] prioridades = NivelPrioridad.values();
                        for (int i = 0; i < prioridades.length; i++) {
                            System.out.println((i + 1) + ": " + prioridades[i]);
                        }
                        int opcionPrioridad = sc.nextInt();
                        NivelPrioridad prioridad = prioridades[opcionPrioridad - 1];

                        Paciente pacienteARegistrar = new Paciente(cedula, nombre, apellido, edad, fechaIngreso, prioridad);

                        if (facade.registrarPaciente(pacienteARegistrar)) {
                            System.out.println("Paciente registrado exitosamente.");
                        } else {
                            System.out.println("Error al registrar el paciente.");
                        }
                    } catch (ParseException e) {
                        System.out.println("La fecha ingresada no tiene un formato válido.");
                    }
                    break;

                case 2:
                    System.out.println("Ingrese la cedula del paciente:");
                    System.out.println(facade.consultarSobrePaciente(sc.nextInt()));
                    break;

                case 3:
                    Paciente pacienteParaAtender = facade.obtenerProximoPaciente();
                    System.out.println(pacienteParaAtender != null ? pacienteParaAtender.toString() : "No hay pacientes en espera.");
                    break;

                case 4: {
                    System.out.println("Ingrese la causa de la consulta:");
                    Causa[] causas = Causa.values();
                    for (int i = 0; i < causas.length; i++) {
                        System.out.println((i + 1) + ": " + causas[i]);
                    }
                    Causa causa = causas[sc.nextInt() - 1];

                    if (facade.atenderPaciente(causa)) {
                        System.out.println("Paciente atendido. Episodio abierto con consulta inicial.");
                    } else {
                        System.out.println("Error al atender el paciente (no hay nadie en espera).");
                    }
                    break;
                }

                case 5:
                    System.out.println("Ingrese la cedula del paciente:");
                    int cedulaModificar = sc.nextInt();
                    System.out.println("Ingrese la urgencia del paciente:");
                    NivelPrioridad[] niveles = NivelPrioridad.values();
                    for (int i = 0; i < niveles.length; i++) {
                        System.out.println((i + 1) + ": " + niveles[i]);
                    }
                    NivelPrioridad nivel = niveles[sc.nextInt() - 1];

                    if (facade.modificarUrgencia(cedulaModificar, nivel)) {
                        System.out.println("Urgencia modificada exitosamente.");
                    } else {
                        System.out.println("Error al modificar la urgencia.");
                    }
                    break;

                case 6:
                    System.out.println("Ingrese la cedula del paciente:");
                    System.out.println(facade.obtenerArbolEpisodio(sc.nextInt()));
                    break;

                case 7: {
                    System.out.println("Ingrese la cedula del paciente:");
                    int ced = sc.nextInt();
                    System.out.println(facade.obtenerArbolEpisodio(ced));
                    System.out.println("Ingrese el id del nodo padre:");
                    int idPadre = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Descripcion del estudio:");
                    String desc = sc.nextLine();
                    TipoEstudio[] tipos = TipoEstudio.values();
                    for (int i = 0; i < tipos.length; i++) {
                        System.out.println((i + 1) + ": " + tipos[i]);
                    }
                    TipoEstudio tipo = tipos[sc.nextInt() - 1];
                    boolean ok = facade.agregarEstudio(ced, idPadre, new Estudio(desc, tipo));
                    System.out.println(ok ? "Estudio agregado." : "Error al agregar estudio (paciente/episodio/id de padre invalido).");
                    break;
                }

                case 8: {
                    System.out.println("Ingrese la cedula del paciente:");
                    int ced = sc.nextInt();
                    System.out.println(facade.obtenerArbolEpisodio(ced));
                    System.out.println("Ingrese el id del nodo padre:");
                    int idPadre = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Descripcion de la consulta interna:");
                    String desc = sc.nextLine();
                    boolean ok = facade.agregarConsultaInterna(ced, idPadre, new ConsultaInterna(desc));
                    System.out.println(ok ? "Consulta interna agregada." : "Error al agregar consulta interna (paciente/episodio/id de padre invalido).");
                    break;
                }

                case 9: {
                    System.out.println("Ingrese la cedula del paciente:");
                    int ced = sc.nextInt();
                    System.out.println(facade.obtenerArbolEpisodio(ced));
                    System.out.println("Ingrese el id del nodo padre:");
                    int idPadre = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Descripcion del procedimiento:");
                    String desc = sc.nextLine();
                    TipoProcedimiento[] tipos = TipoProcedimiento.values();
                    for (int i = 0; i < tipos.length; i++) {
                        System.out.println((i + 1) + ": " + tipos[i]);
                    }
                    TipoProcedimiento tipo = tipos[sc.nextInt() - 1];
                    boolean ok = facade.agregarProcedimiento(ced, idPadre, new Procedimiento(desc, tipo));
                    System.out.println(ok ? "Procedimiento agregado." : "Error al agregar procedimiento (paciente/episodio/id de padre invalido).");
                    break;
                }

                case 10: {
                    System.out.println("Ingrese la cedula del paciente:");
                    int ced = sc.nextInt();
                    System.out.println(facade.obtenerArbolEpisodio(ced));
                    System.out.println("Ingrese el id del nodo padre (el que se complico):");
                    int idPadre = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Descripcion de la complicacion:");
                    String desc = sc.nextLine();
                    boolean ok = facade.agregarComplicacion(ced, idPadre, new Complicacion(desc));
                    System.out.println(ok ? "Complicacion agregada." : "Error al agregar complicacion (paciente/episodio/id de padre invalido).");
                    break;
                }

                case 11: {
                    System.out.println("Ingrese la cedula del paciente:");
                    int cedDiag = sc.nextInt();
                    System.out.println("Ingrese el codigo del catalogo (ej. A09):");
                    String codigoId = sc.next();
                    System.out.println("¿Confirmado? (true/false):");
                    boolean confirmado = sc.nextBoolean();
                    String resultado = facade.asignarDiagnostico(cedDiag, codigoId, confirmado);
                    System.out.println(resultado);
                    break;
                }

                case 12:
                    System.out.println(facade.verCatalogo());
                    break;

                case 13: {
                    System.out.println("Ingrese la cedula del paciente:");
                    int ced = sc.nextInt();
                    System.out.println(facade.obtenerArbolEpisodio(ced));
                    System.out.println("Ingrese el id del nodo a cerrar:");
                    int idNodo = sc.nextInt();
                    boolean ok = facade.cerrarNodo(ced, idNodo);
                    System.out.println(ok ? "Nodo cerrado." : "Error: paciente, episodio o id de nodo invalido.");
                    break;
                }

                case 14:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }
}