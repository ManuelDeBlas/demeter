package es.mde.servicios;

import org.springframework.stereotype.Service;

import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import jakarta.persistence.EntityManager;

@Service
public class PrestacionServiciosUnidadServicio extends AbstractSolicitudServicio<PrestacionServiciosUnidadConId> {

  public PrestacionServiciosUnidadServicio(EntityManager entityManager, SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO) {
    super(entityManager, solicitudDAO, costePorDiaDAO);
  }

}
