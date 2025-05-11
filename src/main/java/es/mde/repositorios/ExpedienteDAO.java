package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.ExpedienteConId;

/**
 * Repositorio JPA para gestionar los expedientes.
 * Proporciona métodos CRUD para la entidad {@link ExpedienteConId}.
 * 
 * Este repositorio está expuesto como un recurso REST con el path "expedientes".
 * 
 * @author
 * Manuel de Blas Pino
 * @version 1.0
 */
@RepositoryRestResource(path = "expedientes", itemResourceRel = "expediente", collectionResourceRel = "expedientes")
public interface ExpedienteDAO extends JpaRepository<ExpedienteConId, Long>, ExpedienteDAOCustom {

}
