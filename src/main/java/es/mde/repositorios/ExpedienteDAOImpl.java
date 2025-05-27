package es.mde.repositorios;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;
import es.mde.servicios.ExpedienteServicio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Implementación personalizada del repositorio para gestionar los expedientes.
 * Proporciona métodos específicos para asignar y desasignar solicitudes a expedientes.
 * 
 * Esta clase implementa la interfaz {@link ExpedienteDAOCustom}.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
public class ExpedienteDAOImpl implements ExpedienteDAOCustom {

  private static final Logger log = LoggerFactory.getLogger(ExpedienteDAOImpl.class);

  /**
   * Repositorio JPA para gestionar los expedientes.
   */
  @Autowired
  private ExpedienteDAO expedienteDAO;
  
  @Autowired
//  private ExpedienteServicio expedienteServicio;

  /**
   * EntityManager para realizar operaciones personalizadas en la base de datos.
   */
  @PersistenceContext
  EntityManager entityManager;

//  /**
//   * Asigna una solicitud a un expediente.
//   * 
//   * Este método agrega la solicitud al expediente, guarda los cambios en la base de datos
//   * y registra la operación en los logs.
//   * 
//   * @param expediente El expediente al que se asignará la solicitud.
//   * @param solicitud La solicitud que se asignará al expediente.
//   */
//  @Override
//  public void asignarSolicitudAExpediente(Long expedienteId, Long solicitudId) {
//    expedienteServicio.asignarSolicitudConNotificacion(expedienteId, solicitudId);
////    expedienteDAO.save(expediente);
//    log.info("Solicitud {} asignada correctamente al expediente {}", solicitudId, expedienteId);
//  }
//
//  /**
//   * Elimina una solicitud de un expediente.
//   * 
//   * Este método elimina la solicitud del expediente, guarda los cambios en la base de datos
//   * y registra la operación en los logs.
//   * 
//   * @param expediente El expediente del que se eliminará la solicitud.
//   * @param solicitud La solicitud que se eliminará del expediente.
//   */
//  @Override
//  public void eliminarSolicitudDeExpediente(Long expedienteId, Long solicitudId) {
//    expedienteServicio.desasignarSolicitudConNotificacion(expedienteId, solicitudId);
////    expedienteDAO.save(expediente);
//    log.info("Solicitud {} desasignada correctamente al expediente {}", solicitudId, expedienteId);
//  }

}
