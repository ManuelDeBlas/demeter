package es.mde.repositorios;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;
import es.mde.rest.ExpedienteController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ExpedienteDAOImpl implements ExpedienteDAOCustom {
  private static final Logger log = LoggerFactory.getLogger(ExpedienteDAOImpl.class);

  private record ExpedienteSolicitud(ExpedienteConId expediente, SolicitudConId solicitud) {
  }

  @Autowired
  private SolicitudDAO solicitudDAO;
  @Autowired
  private ExpedienteDAO expedienteDAO;

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public void asignarSolicitudAExpediente(Long expedienteId, Long solicitudId) {
    ExpedienteSolicitud resultado = obtenerExpedienteYSolicitud(expedienteId, solicitudId);
    ExpedienteConId expediente = resultado.expediente();
    SolicitudConId solicitud = resultado.solicitud();
    expediente.addSolicitud(solicitud);
    expedienteDAO.save(expediente);
    solicitudDAO.save(solicitud);
    log.info("Solicitud {} asignada correctamente al expediente {}", solicitudId, expediente.getNumeroExpediente());
  }

  @Override
  public void eliminarSolicitudDeExpediente(Long expedienteId, Long solicitudId) {
    ExpedienteSolicitud resultado = obtenerExpedienteYSolicitud(expedienteId, solicitudId);
    ExpedienteConId expediente = resultado.expediente();
    SolicitudConId solicitud = resultado.solicitud();
    expediente.removeSolicitud(solicitud);
    expedienteDAO.save(expediente);
    solicitudDAO.save(solicitud);
    log.info("Solicitud {} asignada correctamente al expediente {}", solicitudId, expediente.getNumeroExpediente());
  }

  private ExpedienteSolicitud obtenerExpedienteYSolicitud(Long expedienteId, Long solicitudId) {
    Optional<ExpedienteConId> expedienteOpt = expedienteDAO.findById(expedienteId);
    Optional<SolicitudConId> solicitudOpt = solicitudDAO.findById(solicitudId);
    if (expedienteOpt.isEmpty() || solicitudOpt.isEmpty()) {
      throw new IllegalArgumentException("Expediente o solicitud no encontrado");
    }
    return new ExpedienteSolicitud(expedienteOpt.get(), solicitudOpt.get());
  }

}
