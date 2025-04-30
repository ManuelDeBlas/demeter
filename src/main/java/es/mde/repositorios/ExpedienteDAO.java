package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.mde.entidades.ExpedienteConId;

@Repository
public interface ExpedienteDAO  extends JpaRepository<ExpedienteConId, Long>{

}
