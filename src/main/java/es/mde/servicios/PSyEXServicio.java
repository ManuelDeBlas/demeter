package es.mde.servicios;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PSyEXServicio extends AbstractSolicitudServicio<SolicitudConId> {

  private static final Logger log = LoggerFactory.getLogger(PSyEXServicio.class);

  private final CostePorDiaDAO costePorDiaDAO;

  public PSyEXServicio(EntityManager entityManager, SolicitudDAO solicitudDAO,
      CostePorDiaDAO costePorDiaDAO) {
    super(entityManager, solicitudDAO);
    this.costePorDiaDAO = costePorDiaDAO;
  }

  public PrestacionServiciosUnidadConId crearPS(
      PrestacionServiciosUnidadConId prestacionServiciosUnidad) {
    comprobarViabilidadSolicitud(prestacionServiciosUnidad);
    prestacionServiciosUnidad.setCosteCentimos(calcularCosteCentimos(prestacionServiciosUnidad));
    PrestacionServiciosUnidadConId guardado = solicitudDAO.save(prestacionServiciosUnidad);

    return guardado;
  }

  public ActivacionAmpliadaConId crearEX(ActivacionAmpliadaConId activacionAmpliadaConId) {
    comprobarViabilidadSolicitud(activacionAmpliadaConId);
    activacionAmpliadaConId.setCosteCentimos(calcularCosteCentimos(activacionAmpliadaConId));
    ActivacionAmpliadaConId guardado = solicitudDAO.save(activacionAmpliadaConId);

    return guardado;
  }

  protected int calcularCosteCentimos(SolicitudConId solicitud) {
    int costeCentimos = 0;
    int costeDiaCentimos =
        costePorDiaDAO.findByEmpleo(solicitud.getReservista().getEmpleo()).getCentimos();
    int duracion = solicitud.getDiasDuracion();
    costeCentimos = Math.toIntExact(duracion * costeDiaCentimos);
    log.warn(String.valueOf(costeCentimos));

    return costeCentimos;
  }
}
