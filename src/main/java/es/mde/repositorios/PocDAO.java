package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.PocConId;

@RepositoryRestResource(path = "pocs", itemResourceRel = "poc", collectionResourceRel = "pocs")
public interface PocDAO extends JpaRepository<PocConId, Long> {

}
