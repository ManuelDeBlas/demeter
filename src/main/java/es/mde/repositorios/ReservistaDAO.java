package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.ReservistaConId;

@RepositoryRestResource(path = "reservistas", itemResourceRel = "reservista", collectionResourceRel = "reservistas")
public interface ReservistaDAO extends JpaRepository<ReservistaConId, Long> {

}
