package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.ExpedienteConId;

@RepositoryRestResource(path = "expedientes", itemResourceRel = "expediente", collectionResourceRel = "expedientes")
public interface ExpedienteDAO  extends JpaRepository<ExpedienteConId, Long>{

}
