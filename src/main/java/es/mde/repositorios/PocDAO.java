package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.mde.entidades.PocConId;

@Repository
public interface PocDAO extends JpaRepository<PocConId, Long> {

}
