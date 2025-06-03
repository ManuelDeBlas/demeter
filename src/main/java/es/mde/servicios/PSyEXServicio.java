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
public class PSyEXServicio {

  private static final Logger log = LoggerFactory.getLogger(PSyEXServicio.class);

  private final SolicitudDAO solicitudDAO;
  private final CostePorDiaDAO costePorDiaDAO;
  private final SolicitudServicio solicitudServicio;
  private final ObjectMapper objectMapper;



  public PSyEXServicio(SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO,
      SolicitudServicio solicitudServicio, ObjectMapper objetMapper) {
    this.solicitudDAO = solicitudDAO;
    this.costePorDiaDAO = costePorDiaDAO;
    this.solicitudServicio = solicitudServicio;
    this.objectMapper = objetMapper;
  }


  public List<PrestacionServiciosUnidadConId> crearPS(
      PrestacionServiciosUnidadConId prestacionServiciosUnidad) {
    solicitudServicio.comprobarViabilidadSolicitud(prestacionServiciosUnidad);
    int anhoInicio = prestacionServiciosUnidad.getFechaInicio().getYear();
    int anhoFin = prestacionServiciosUnidad.getFechaFin().getYear();
    List<PrestacionServiciosUnidadConId> respuesta = null;
    if (anhoInicio != anhoFin) {
      try {
        PrestacionServiciosUnidadConId solicitudAnhoInicio =
            objectMapper.readValue(objectMapper.writeValueAsString(prestacionServiciosUnidad),
                PrestacionServiciosUnidadConId.class);
        solicitudAnhoInicio.setFechaFin(LocalDate.of(anhoInicio, 12, 31));
        solicitudAnhoInicio.setCosteCentimos(calcularCosteCentimos(solicitudAnhoInicio));
        solicitudDAO.save(solicitudAnhoInicio);

        PrestacionServiciosUnidadConId solicitudAnhoFin =
            objectMapper.readValue(objectMapper.writeValueAsString(prestacionServiciosUnidad),
                PrestacionServiciosUnidadConId.class);
        solicitudAnhoFin.setFechaInicio(LocalDate.of(anhoFin, 1, 1));
        solicitudAnhoFin.setCosteCentimos(calcularCosteCentimos(solicitudAnhoFin));
        solicitudDAO.save(solicitudAnhoFin);

        respuesta = List.of(solicitudAnhoInicio, solicitudAnhoFin);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Error al clonar la prestación", e);
      }
    } else {
      prestacionServiciosUnidad.setCosteCentimos(calcularCosteCentimos(prestacionServiciosUnidad));
      PrestacionServiciosUnidadConId guardado = solicitudDAO.save(prestacionServiciosUnidad);
      respuesta = List.of(guardado);
    }

    return respuesta;
  }

  public List<ActivacionAmpliadaConId> crearEX(ActivacionAmpliadaConId activacionAmpliadaConId) {
    solicitudServicio.comprobarViabilidadSolicitud(activacionAmpliadaConId);

    int anhoInicio = activacionAmpliadaConId.getFechaInicio().getYear();
    int anhoFin = activacionAmpliadaConId.getFechaFin().getYear();

    List<ActivacionAmpliadaConId> resultado;

    if (anhoInicio != anhoFin) {
      try {
        ActivacionAmpliadaConId solicitudAnhoInicio =
            objectMapper.readValue(objectMapper.writeValueAsString(activacionAmpliadaConId),
                ActivacionAmpliadaConId.class);
        solicitudAnhoInicio.setFechaFin(LocalDate.of(anhoInicio, 12, 31));
        solicitudAnhoInicio.setCosteCentimos(calcularCosteCentimos(solicitudAnhoInicio));
        solicitudDAO.save(solicitudAnhoInicio);

        ActivacionAmpliadaConId solicitudAnhoFin =
            objectMapper.readValue(objectMapper.writeValueAsString(activacionAmpliadaConId),
                ActivacionAmpliadaConId.class);
        solicitudAnhoFin.setFechaInicio(LocalDate.of(anhoFin, 1, 1));
        solicitudAnhoFin.setCosteCentimos(calcularCosteCentimos(solicitudAnhoFin));
        solicitudDAO.save(solicitudAnhoFin);

        resultado = List.of(solicitudAnhoInicio, solicitudAnhoFin);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Error al clonar la activación", e);
      }
    } else {
      activacionAmpliadaConId.setCosteCentimos(calcularCosteCentimos(activacionAmpliadaConId));
      ActivacionAmpliadaConId guardado = solicitudDAO.save(activacionAmpliadaConId);
      resultado = List.of(guardado);
    }

    return resultado;
  }


  private int calcularCosteCentimos(SolicitudConId solicitud) {
    int costeCentimos = 0;
    int costeDiaCentimos =
        costePorDiaDAO.findByEmpleo(solicitud.getReservista().getEmpleo()).getCentimos();
    int duracion = solicitud.getDiasDuracion();
    costeCentimos = Math.toIntExact(duracion * costeDiaCentimos);
    log.warn(String.valueOf(costeCentimos));

    return costeCentimos;
  }
}
