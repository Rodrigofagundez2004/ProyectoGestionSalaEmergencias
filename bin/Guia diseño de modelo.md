# Proyecto Integrador — Sala de Emergencias

## SE NECESITA:

- Registrar paciente
- Gestionar espera
- Gestionar prioridades
- Registrar pacientes atendidos
- Consultar información


## Clases

### Paciente

- Atributos:
  - Cedula (int)
  - Nombre (String)
  - Apellido (String)
  - Edad (byte)
  - FechaIngreso (Date)
  - Motivo (String)
  - NivelPrioridad (NivelPrioridad)
  - Espera (int)
  - ProcedimientosRealizados (ListaDoblementeEnlazada<Procedimiento>)

- Métodos:
  - Constructor
  - Getters
  - toString (para conocer la información del paciente)
  - hashCode
  - equals

    

### Causa es una clase ENUM

Cada Causa tiene asignado un procedimiento

- TAQUICARDIA(Procedimiento.ELECTROCARDIOGRAMA)
- LESION_RODILLA(Procedimiento.TOMOGRAFIA)
- TRAUMATISMO_CRANEAL(Procedimiento.RESONANCIA)
- FRACTURA(Procedimiento.RADIOGRAFIA)
- APENDICITIS(Procedimiento.CIRUGIA)

y esta clase tiene un metodo getProcedimiento para facilitar asi la asignación de un procedimiento a la hora de atender un paciente en base al personal de salud que lo atiende.

### Procedimiento es una clase ENUM

- ELECTROCARDIOGRAMA
- RADIOGRAFIA
- RESONANCIA
- CIRUGIA
- TOMOGRAFIA


### NivelPrioridad es una clase ENUM

- BAJA
- MEDIA
- ALTA
- URGENTE


### SalaEspera

- Atributos:
  - PorAtender (ColaPrioridad<Paciente>)

- Métodos:
  - void incrementarEspera()
  - boolean actualizarUrgencia(Paciente paciente)

- incrementarEspera():
  - Incrementa el tiempo de espera de los pacientes que permanecen en la sala de espera.

- actualizarUrgencia(Paciente paciente):
  - Verifica si el paciente superó el tiempo de espera establecido para aumentar su nivel de prioridad.
  - Si corresponde, modifica su nivel de urgencia y actualiza su posición dentro de la ColaPrioridad.
  - Devuelve true si la prioridad pudo actualizarse y false si no se pudo.


### SystemFacade

- Atributos:
  - Pacientes (ListaDoblementeEnlazada<Paciente>)
  - PorAtender (SalaEspera)
  - Atendidos (ListaDoblementeEnlazada<Paciente>)

- Métodos:

  - boolean registrarPaciente(Paciente pacienteARegistrar):
    - Agrega el paciente a la lista Pacientes y a la cola PorAtender.
    - Devuelve true si se pudo registrar correctamente y false si no se pudo.


  - boolean atenderPaciente(PersonalDeSalud personal):
    - Toma de PorAtender al paciente que corresponda según el criterio de prioridad.
    - Lo elimina de la cola.
    - Determina el procedimiento que corresponde según el rol del personal de salud.
    - Agrega dicho procedimiento al historial de procedimientos del paciente.
    - Finalmente agrega el paciente a la lista Atendidos.
    - Devuelve true si se pudo realizar la atención y false si no se pudo.


  - boolean estaAtendido(Paciente paciente):
    - Busca al paciente en la lista Atendidos.
    - Devuelve true si el paciente fue atendido y false si no se encuentra.


  - String consultarSobrePaciente(Paciente paciente):
    - Busca al paciente en la lista general Pacientes.
    - Si lo encuentra, devuelve un String con todos sus datos, incluyendo sus procedimientos realizados.
    - Si no lo encuentra, devuelve un mensaje indicando que el paciente no fue encontrado.


  - boolean modificarUrgencia(Paciente paciente, NivelPrioridad nuevoNivel):
    - Busca al paciente en la cola PorAtender y modifica su nivel de prioridad.
    - La modificación debe verse reflejada en su posición dentro de la cola.
    - Devuelve true si se pudo modificar y false si no se pudo.


  - Paciente obtenerProximoPaciente():
    - Devuelve el paciente que debe ser atendido a continuación según su nivel de prioridad, sin atenderlo ni eliminarlo de la cola.

## CRITERIO DE PRIORIDAD

- URGENTE = mayor prioridad
- ALTA
- MEDIA
- BAJA = menor prioridad

Si dos o más pacientes tienen el mismo nivel de prioridad, se respeta el orden de llegada (FIFO).

Ejemplo:

Paciente A → ALTA → llega primero
Paciente B → ALTA → llega después

Orden de atención:

1. Paciente A
2. Paciente B


## AUMENTO DE PRIORIDAD POR TIEMPO DE ESPERA

El tiempo de espera de un paciente puede provocar que su nivel de prioridad aumente.

Ejemplo conceptual:

BAJA
  ↓ después de determinado tiempo
MEDIA
  ↓ después de determinado tiempo
ALTA
  ↓ después de determinado tiempo
URGENTE

Los tiempos concretos necesarios para cada aumento de prioridad deberán definirse posteriormente.

La finalidad es evitar que un paciente permanezca indefinidamente esperando solamente porque inicialmente tenía una prioridad baja.





