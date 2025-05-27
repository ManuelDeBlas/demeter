package es.mde.repositorios;

import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;

/**
 * Repositorio JPA personalizado para gestionar los expedientes.
 * Proporciona métodos CRUD para la entidad {@link ExpedienteConId}.
 * 
 * Este repositorio está expuesto como un recurso REST con el path "expedientes".
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
public interface ExpedienteDAOCustom {

//  void asignarSolicitudAExpediente(ExpedienteConId expediente, SolicitudConId solicitud);
  
//  void eliminarSolicitudDeExpediente(ExpedienteConId expediente, SolicitudConId solicitud);

//  /**
//   * Asigna una solicitud a un expediente.
//   * 
//   * Este método agrega la solicitud al expediente, guarda los cambios en la base de datos
//   * y registra la operación en los logs.
//   * 
//   * @param expediente El expediente al que se asignará la solicitud.
//   * @param solicitud La solicitud que se asignará al expediente.
//   */
//  void asignarSolicitudAExpediente(Long expedienteId, Long solicitudId);
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
//  void eliminarSolicitudDeExpediente(Long expedienteId, Long solicitudId);
}
