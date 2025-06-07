package es.mde.repositorios;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;
import es.mde.secres.Solicitud;
import es.mde.servicios.ExpedienteServicio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Implementación personalizada del repositorio para gestionar los expedientes.
 * Proporciona métodos específicos para asignar y desasignar solicitudes a
 * expedientes.
 * 
 * Esta clase implementa la interfaz {@link ExpedienteDAOCustom}.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
//@Transactional(readOnly = true)
public class ExpedienteDAOImpl implements ExpedienteDAOCustom {

  private static final Logger log = LoggerFactory.getLogger(ExpedienteDAOImpl.class);

  /**
   * Repositorio JPA para gestionar los expedientes.
   */
  @Autowired
  private ExpedienteDAO expedienteDAO;

  /**
   * EntityManager para realizar operaciones personalizadas en la base de datos.
   */
  @PersistenceContext
  EntityManager entityManager;

  @Override
  public int getCosteCentimosExpedienteByNumeroExpediente(String numeroExpediente) {
    int costeCentimos = 0;
    ExpedienteConId expediente = expedienteDAO.findByNumeroExpediente(numeroExpediente)
        .orElseThrow(() -> new IllegalArgumentException("Expediente no encontrado: " + numeroExpediente));
    for (Solicitud solicitud : expediente.getSolicitudes()) {
      costeCentimos += solicitud.getCosteCentimos();
    }

    return costeCentimos;
  }

}
