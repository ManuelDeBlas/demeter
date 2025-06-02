package es.mde.util;

import java.time.LocalDate;

import es.mde.secres.Solicitud;

public class SolicitudUtil {

  public static void comprobarViabilidadSolicitud(Solicitud solicitud, int maximoDias) {
    boolean resultado = true;
    if (solicitud.getDiasDuracion() + solicitud.getReservista().getDiasConsumidos() > maximoDias) {
      throw new IllegalArgumentException(
          "El reservista cuenta con " + String.valueOf(maximoDias - solicitud.getReservista().getDiasConsumidos())
              + " dias disponibles y la activación dura " + solicitud.getDiasDuracion() + " dias");
    }
    LocalDate fechaFinCompromiso = solicitud.getReservista().getFechaFinCompromiso();
    if ((fechaFinCompromiso.isEqual(solicitud.getFechaInicio())
        || fechaFinCompromiso.isAfter(solicitud.getFechaInicio()))
        && (fechaFinCompromiso.isEqual(solicitud.getFechaFin())
            || fechaFinCompromiso.isBefore(solicitud.getFechaFin()))) {
      throw new IllegalArgumentException(
          "La fecha de fin de compromiso del reservista (" + fechaFinCompromiso + ") transcurre durante la activación");
    }
    LocalDate fechaCaducidadReconocmientoMedico = solicitud.getReservista().getFechaCaducidadReconocimientoMedico();
    if ((fechaCaducidadReconocmientoMedico.isEqual(solicitud.getFechaInicio())
        || fechaCaducidadReconocmientoMedico.isAfter(solicitud.getFechaInicio()))
        && (fechaCaducidadReconocmientoMedico.isEqual(solicitud.getFechaFin())
            || fechaCaducidadReconocmientoMedico.isBefore(solicitud.getFechaFin()))) {
      throw new IllegalArgumentException("La fecha de caducidad del reconocimiento medico del reservista ("
          + fechaCaducidadReconocmientoMedico + ") transcurre durante la activación");
    }
  }
}
