package es.mde.servicios;

import org.springframework.stereotype.Service;

import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import jakarta.persistence.EntityManager;

@Service
public class ActivacionAmpliadaServicio extends AbstractSolicitudServicio<ActivacionAmpliadaConId> {

  public ActivacionAmpliadaServicio(EntityManager entityManager, SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO) {
    super(entityManager, solicitudDAO, costePorDiaDAO);
  }

}
