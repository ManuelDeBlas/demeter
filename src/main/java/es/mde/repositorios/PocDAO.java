package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.PocConId;

/**
 * Repositorio JPA para gestionar los POCs.
 * Proporciona métodos CRUD para la entidad {@link PocConId}.
 * 
 * Este repositorio está expuesto como un recurso REST con el path "pocs".
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@RepositoryRestResource(path = "pocs", itemResourceRel = "poc", collectionResourceRel = "pocs")
public interface PocDAO extends JpaRepository<PocConId, Long> {

}
