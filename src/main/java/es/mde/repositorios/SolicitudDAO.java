package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.SolicitudConId;

@RepositoryRestResource(path = "solicitudes", itemResourceRel = "solicitud", collectionResourceRel = "solicitudes")
public interface SolicitudDAO extends JpaRepository<SolicitudConId, Long> {
}
