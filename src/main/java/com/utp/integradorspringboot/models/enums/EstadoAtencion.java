package com.utp.integradorspringboot.models.enums;

public enum EstadoAtencion {
    PENDIENTE, // pendiente de asignación
    MECANICO_ASIGNADO_DIAGNOSTICO,
    EN_REVISION,
    DIAGNOSTICO_REALIZADO,
    DIAGNOSTICO_APROBADO,
    FINALIZADA_SIN_ATENCION,
    MECANICO_ASIGNADO_REPARACION,
    EN_ATENCION_REPARACION,
    ATENCION_REALIZADA
}
