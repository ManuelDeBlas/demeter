package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.PrestacionServiciosUnidadConId;

@RepositoryRestResource(path = "prestaciones-servicios-unidad", itemResourceRel = "prestacion-servicios-unidad",
collectionResourceRel = "prestaciones-servicios-unidad")
public interface PrestacionServiciosUnidadDAO extends JpaRepository<PrestacionServiciosUnidadConId, Long> {

}
