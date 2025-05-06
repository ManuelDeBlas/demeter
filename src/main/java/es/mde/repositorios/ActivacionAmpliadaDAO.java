package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.ActivacionAmpliadaConId;

@RepositoryRestResource(path = "activaciones-ampliadas", itemResourceRel = "activacion-ampliada",
    collectionResourceRel = "activaciones-ampliadas")
public interface ActivacionAmpliadaDAO extends JpaRepository<ActivacionAmpliadaConId, Long> {

}
