package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mde.entidades.PresupuestoConId;

@RepositoryRestResource(path = "presupuestos-secres", itemResourceRel = "presupuesto-secres", collectionResourceRel = "presupuestos-secres")
public interface PresupuestoDAO extends JpaRepository<PresupuestoConId, Long> {

  PresupuestoConId getByAnho(int anho);

}
