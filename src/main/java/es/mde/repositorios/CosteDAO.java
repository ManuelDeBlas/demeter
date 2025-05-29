package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.Coste;

@RepositoryRestResource(path = "costes", itemResourceRel = "coste", collectionResourceRel = "costes")
public interface CosteDAO extends JpaRepository<Coste, Long> {

  int findCentimosByClave(@Param("clave") String clave);

}
