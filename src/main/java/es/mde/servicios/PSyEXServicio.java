package es.mde.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.entidades.ReservistaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;
import jakarta.persistence.EntityManager;

@Service
public class PSyEXServicio {

  private static final Logger log = LoggerFactory.getLogger(PSyEXServicio.class);

  private final SolicitudDAO solicitudDAO;
  private final CostePorDiaDAO costePorDiaDAO;
  private final EntityManager entityManager;

  public PSyEXServicio(SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO, EntityManager entityManager) {
    this.solicitudDAO = solicitudDAO;
    this.costePorDiaDAO = costePorDiaDAO;
    this.entityManager = entityManager;
  }
  

  public PrestacionServiciosUnidadConId crearPS(PrestacionServiciosUnidadConId prestacionServiciosUnidad) {
    ReservistaConId ref = entityManager.getReference(ReservistaConId.class, prestacionServiciosUnidad.getReservista().getId());
    prestacionServiciosUnidad.setReservista(ref);
    prestacionServiciosUnidad.setCosteCentimos(calcularCosteCentimos(prestacionServiciosUnidad));

    return solicitudDAO.save(prestacionServiciosUnidad);
  }

  public ActivacionAmpliadaConId crearEX(ActivacionAmpliadaConId activacionAmpliadaConId) {
    ReservistaConId ref = entityManager.getReference(ReservistaConId.class, activacionAmpliadaConId.getReservista().getId());
    activacionAmpliadaConId.setReservista(ref);
    activacionAmpliadaConId.setCosteCentimos(calcularCosteCentimos(activacionAmpliadaConId));

    return solicitudDAO.save(activacionAmpliadaConId);
  }

  private int calcularCosteCentimos(SolicitudConId solicitud) {
    int costeCentimos = 0;
    int costeDiaCentimos = costePorDiaDAO.findByEmpleo(solicitud.getReservista().getEmpleo()).getCentimos();
    int duracion = solicitud.getDiasDuracion();
    costeCentimos = Math.toIntExact(duracion * costeDiaCentimos);
    log.warn(String.valueOf(costeCentimos));

    return costeCentimos;
  }
}
