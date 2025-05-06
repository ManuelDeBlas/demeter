package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.mde.entidades.ReservistaConId;

@Repository
public interface ReservistaDAO extends JpaRepository<ReservistaConId, Long> {

}
