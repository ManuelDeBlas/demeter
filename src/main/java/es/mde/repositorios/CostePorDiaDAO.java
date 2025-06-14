package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mde.entidades.CostePorDiaConId;

@RepositoryRestResource(path = "costes-por-dia", itemResourceRel = "coste-por-dia",
    collectionResourceRel = "costes-por-dia")
public interface CostePorDiaDAO extends JpaRepository<CostePorDiaConId, Long> {

  CostePorDiaConId findByEmpleo(@Param("empleo") String empleo);

}
