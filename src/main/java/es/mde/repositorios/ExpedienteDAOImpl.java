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

  @Autowired
  private SolicitudDAO solicitudDAO;
  @Autowired
  private ExpedienteDAO expedienteDAO;

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public void asignarSolicitudAExpediente(Long expedienteId, Long solicitudId) {
    Optional<ExpedienteConId> expedienteOpt = expedienteDAO.findById(expedienteId);
    Optional<SolicitudConId> solicitudOpt = solicitudDAO.findById(solicitudId);

    if (expedienteOpt.isEmpty() || solicitudOpt.isEmpty()) {
      throw new IllegalArgumentException("Expediente o solicitud no encontrado");
    }
    ExpedienteConId expediente = expedienteOpt.get();
    SolicitudConId solicitud = solicitudOpt.get();
    try {
      expediente.addSolicitud(solicitud);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(e.getMessage());
    }
    expedienteDAO.save(expediente);
    log.info("Solicitud {} asignada correctamente al expediente {}", solicitudId, expedienteId);

  }

}
