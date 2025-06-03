package es.mde.servicios;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import es.mde.entidades.ReservistaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;
import jakarta.persistence.EntityManager;

@Service
public class SolicitudServicio {

  private final EntityManager entityManager;

  @Value("${maximo-dias-activacion}")
  private int maximoDiasActivacion;

  public SolicitudServicio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void comprobarViabilidadSolicitud(SolicitudConId solicitud) {
    ReservistaConId reservista =
        entityManager.getReference(ReservistaConId.class, solicitud.getReservista().getId());
    if (solicitud.getDiasDuracion() + reservista
        .getDiasConsumidos(solicitud.getFechaInicio().getYear()) > maximoDiasActivacion) {
      throw new IllegalArgumentException("El reservista cuenta con "
          + String.valueOf(maximoDiasActivacion
              - reservista.getDiasConsumidos(solicitud.getFechaInicio().getYear()))
          + " dias disponibles y la activación dura " + solicitud.getDiasDuracion() + " dias");
    }

    LocalDate fechaFinCompromiso = reservista.getFechaFinCompromiso();
    if ((fechaFinCompromiso.isEqual(solicitud.getFechaInicio())
        || fechaFinCompromiso.isAfter(solicitud.getFechaInicio()))
        && (fechaFinCompromiso.isEqual(solicitud.getFechaFin())
            || fechaFinCompromiso.isBefore(solicitud.getFechaFin()))) {
      throw new IllegalArgumentException("La fecha de fin de compromiso del reservista ("
          + fechaFinCompromiso + ") transcurre durante la activación");
    }
    LocalDate fechaCaducidadReconocmientoMedico =
        reservista.getFechaCaducidadReconocimientoMedico();
    if ((fechaCaducidadReconocmientoMedico.isEqual(solicitud.getFechaInicio())
        || fechaCaducidadReconocmientoMedico.isAfter(solicitud.getFechaInicio()))
        && (fechaCaducidadReconocmientoMedico.isEqual(solicitud.getFechaFin())
            || fechaCaducidadReconocmientoMedico.isBefore(solicitud.getFechaFin()))) {
      throw new IllegalArgumentException(
          "La fecha de caducidad del reconocimiento medico del reservista ("
              + fechaCaducidadReconocmientoMedico + ") transcurre durante la activación");
    }
    reservista.addSolicitudConId(solicitud);
  }

}
