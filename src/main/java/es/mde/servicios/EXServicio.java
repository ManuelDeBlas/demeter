package es.mde.servicios;

import org.springframework.stereotype.Service;

import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import jakarta.persistence.EntityManager;

@Service
public class EXServicio extends AbstractSolicitudServicio<ActivacionAmpliadaConId> {

  public EXServicio(EntityManager entityManager, SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO) {
    super(entityManager, solicitudDAO, costePorDiaDAO);
  }

}
