package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.mde.entidades.SolicitudConId;

@Repository
public interface SolicitudDAO extends JpaRepository<SolicitudConId, Long> {

}
