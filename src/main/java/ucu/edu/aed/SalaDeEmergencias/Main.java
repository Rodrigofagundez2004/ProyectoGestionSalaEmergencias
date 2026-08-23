package ucu.edu.aed.SalaDeEmergencias;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SystemFacade facade = new SystemFacade();

        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n------ SISTEMA DE EMERGENCIAS ------");
            System.out.println("Seleccione la operacion a realizar:");
            System.out.println("1: REGISTRAR NUEVO PACIENTE");
            System.out.println("2: BUSCAR INFORMACION DE PACIENTE");
            System.out.println("3: CONOCER PROXIMO PACIENTE PARA ATENDER");
            System.out.println("4: ATENDER PACIENTE");
            System.out.println("5: MODIFICAR URGENCUIA DE PACIENTE");
            System.out.println("7: Salir");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\nRegistrando nuevo paciente... ");

                    System.out.println("Ingrese la cedula del paciente:");
                    int cedula = sc.nextInt();

                    System.out.println("Ingrese el nombre del paciente:");
                    String nombre = sc.next();

                    System.out.println("Ingrese el apellido del paciente:");
                    String apellido = sc.next();

                    System.out.println("Ingrese la edad del paciente:");
                    byte edad = sc.nextByte();

                    System.out.println("Ingrese la fecha de nacimiento del paciente (dd/mm/aaaa):");
                    String fechaNacimientoString = sc.next();

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");


                    try {
                        Date fechaNacimiento = formato.parse(fechaNacimientoString);


                        System.out.println("Ingrese la causa del paciente:");
                        Causa[] causas = Causa.values();

                        for (int i = 0; i < causas.length; i++) {
                            System.out.println((i + 1) + ": " + causas[i]);
                        }

                        int opcionCausa = sc.nextInt();

                        Causa causa = causas[opcionCausa - 1];


                        Paciente pacienteARegistrar = new Paciente(
                                cedula,
                                nombre,
                                apellido,
                                edad,
                                fechaNacimiento,
                                causa
                        );

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
                    System.out.println("Buscando paciente...");
                    System.out.println("Ingrese la cedula del paciente:");
                    int cedulaBuscar = sc.nextInt();
                    String mensaje = facade.consultarSobrePaciente(cedulaBuscar);
                    System.out.println(mensaje);
                    break;


                case 3:
                    System.out.println("Buscando proximo paciente...");
                    Paciente pacienteParaAtender = facade.obtenerProximoPaciente();
                    String datosPaciente = pacienteParaAtender.toString();
                    System.out.println(datosPaciente);
                    break;

                case 4:

                    try {
                        System.out.println("Atendiendo paciente...");
                        if (facade.atenderPaciente()) {
                            System.out.println("Paciente atendido exitosamente.");
                        } else {
                            System.out.println("Error al atender el paciente.");
                        }

                    } catch (Exception NullPointerException) {
                        System.out.println("Error al atender el paciente.");
                    }
                    break;

                case 5:

                    System.out.println("Modificando urgencia...");

                    System.out.println("Ingrese la cedula del paciente:");
                    int cedulaModificar = sc.nextInt();

                    System.out.println("Ingrese la urgencia del paciente:");
                    NivelPrioridad[] niveles = NivelPrioridad.values();

                    for (int i = 0; i < niveles.length; i++) {
                        System.out.println((i + 1) + ": " + niveles[i]);
                    }

                    int opcionNivel = sc.nextInt();

                    NivelPrioridad nivel = niveles[opcionNivel - 1];


                    if(facade.modificarUrgencia(cedulaModificar, nivel)){
                        System.out.println("Urgencia modificada exitosamente.");
                    } else {
                        System.out.println("Error al modificar la urgencia.");
                    }
                    break;
            }

        }

    }
}
