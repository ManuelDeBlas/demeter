package es.mde.repositorios;

import es.mde.entidades.ExpedienteConId;

/**
 * Repositorio JPA personalizado para gestionar los expedientes. Proporciona
 * métodos CRUD para la entidad {@link ExpedienteConId}.
 * 
 * Este repositorio está expuesto como un recurso REST con el path
 * "expedientes".
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
public interface ExpedienteDAOCustom {

  int getCosteCentimosExpedienteByNumeroExpediente(String numeroExpediente);
}
