package es.mde.servicios;

import java.time.LocalDate;

import es.mde.entidades.ReservistaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import jakarta.persistence.EntityManager;

public abstract class AbstractSolicitudServicio<T extends SolicitudConId> {

  private final EntityManager entityManager;
  protected final SolicitudDAO solicitudDAO;
  private final CostePorDiaDAO costePorDiaDAO;

  public AbstractSolicitudServicio(EntityManager entityManager, SolicitudDAO solicitudDAO,
      CostePorDiaDAO costePorDiaDAO) {
    this.entityManager = entityManager;
    this.solicitudDAO = solicitudDAO;
    this.costePorDiaDAO = costePorDiaDAO;
  }

  public T crearSolicitud(T solicitud) {
    return guardarYRecalcularCoste(solicitud);
  }

  public T actualizarSolicitud(Long id, T solicitud) {
    solicitud.setId(id);
    return guardarYRecalcularCoste(solicitud);
  }

  private T guardarYRecalcularCoste(T solicitud) {
    comprobarViabilidadSolicitud(solicitud);
    solicitud.setCosteCentimos(calcularCosteCentimos(solicitud));
    return solicitudDAO.save(solicitud);
  }

  protected int calcularCosteCentimos(T solicitud) {
    int costeCentimos = 0;
    int costeDiaCentimos = costePorDiaDAO.findByEmpleo(solicitud.getReservista().getEmpleo()).getCentimos();
    int duracion = solicitud.getDiasDuracion();
    costeCentimos = Math.toIntExact(duracion * costeDiaCentimos);

    return costeCentimos;
  }

  public void comprobarViabilidadSolicitud(SolicitudConId solicitud) {
    ReservistaConId reservista = entityManager.getReference(ReservistaConId.class, solicitud.getReservista().getId());

    LocalDate fechaFinCompromiso = reservista.getFechaFinCompromiso();
    if ((fechaFinCompromiso.isEqual(solicitud.getFechaInicio())
        || fechaFinCompromiso.isAfter(solicitud.getFechaInicio()))
        && (fechaFinCompromiso.isEqual(solicitud.getFechaFin())
            || fechaFinCompromiso.isBefore(solicitud.getFechaFin()))) {
      throw new IllegalArgumentException("ERROR: La fecha de fin de compromiso del reservista (" + fechaFinCompromiso
          + ") transcurre durante la activación");
    }
    LocalDate fechaCaducidadReconocmientoMedico = reservista.getFechaCaducidadReconocimientoMedico();
    if ((fechaCaducidadReconocmientoMedico.isEqual(solicitud.getFechaInicio())
        || fechaCaducidadReconocmientoMedico.isAfter(solicitud.getFechaInicio()))
        && (fechaCaducidadReconocmientoMedico.isEqual(solicitud.getFechaFin())
            || fechaCaducidadReconocmientoMedico.isBefore(solicitud.getFechaFin()))) {
      throw new IllegalArgumentException("ERROR: La fecha de caducidad del reconocimiento medico del reservista ("
          + fechaCaducidadReconocmientoMedico + ") transcurre durante la activación");
    }
    reservista.addSolicitudConId(solicitud);
  }

}
