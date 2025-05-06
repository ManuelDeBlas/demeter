package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.FormacionContinuadaConId;

@RepositoryRestResource(path = "formaciones-continuadas", itemResourceRel = "formacion-continuada",
collectionResourceRel = "formaciones-continuadas")
public interface FormacionContinuadaDAO extends JpaRepository<FormacionContinuadaConId, Long> {

}
