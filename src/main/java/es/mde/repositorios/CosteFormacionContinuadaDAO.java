package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mde.entidades.CosteFormacionContinuada;

@RepositoryRestResource(path = "costes-fc", itemResourceRel = "coste-fc", collectionResourceRel = "costes-fc")
public interface CosteFormacionContinuadaDAO extends JpaRepository<CosteFormacionContinuada, Long> {

  CosteFormacionContinuada findByClave(@Param("clave") String clave);

}
