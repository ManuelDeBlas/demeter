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
  private ExpedienteDAO expedienteDAO;

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public void asignarSolicitudAExpediente(ExpedienteConId expediente, SolicitudConId solicitud) {
    expediente.addSolicitud(solicitud);
    expedienteDAO.save(expediente);
    log.info("Solicitud {} asignada correctamente al expediente {}", solicitud.getId(), expediente.getNumeroExpediente());
  }

  @Override
  public void eliminarSolicitudDeExpediente(ExpedienteConId expediente, SolicitudConId solicitud) {
    expediente.removeSolicitud(solicitud);
    expedienteDAO.save(expediente);
    log.info("Solicitud {} desasignada correctamente al expediente {}", solicitud.getId(), expediente.getNumeroExpediente());
  }

}
