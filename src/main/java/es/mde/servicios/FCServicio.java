package es.mde.servicios;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.ReservistaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;
import jakarta.persistence.EntityManager;

@Service
public class FCServicio {

  private static final Logger log = LoggerFactory.getLogger(FCServicio.class);

  private final SolicitudDAO solicitudDAO;
  private final CosteFormacionContinuadaDAO costeFormacionContinuadaDAO;
  private final SolicitudServicio solicitudServicio;
  private final ObjectMapper objectMapper;

  public FCServicio(SolicitudDAO solicitudDAO,
      CosteFormacionContinuadaDAO costeFormacionContinuadaDAO, SolicitudServicio solicitudServicio,
      ObjectMapper objectMapper) {
    this.solicitudDAO = solicitudDAO;
    this.costeFormacionContinuadaDAO = costeFormacionContinuadaDAO;
    this.solicitudServicio = solicitudServicio;
    this.objectMapper = objectMapper;
  }

  public FormacionContinuadaConId crearSolicitud(
      FormacionContinuadaConId formacionContinuada) {
    solicitudServicio.comprobarViabilidadSolicitud(formacionContinuada);
//    int anhoInicio = formacionContinuada.getFechaInicio().getYear();
//    int anhoFin = formacionContinuada.getFechaFin().getYear();
//    List<FormacionContinuadaConId> resultado;
//
//    if (anhoInicio != anhoFin) {
//      try {
//        FormacionContinuadaConId solicitudAnhoInicio = objectMapper.readValue(
//            objectMapper.writeValueAsString(formacionContinuada), FormacionContinuadaConId.class);
//        solicitudAnhoInicio.setFechaFin(LocalDate.of(anhoInicio, 12, 31));
//        solicitudAnhoInicio.setCosteCentimos(calcularCosteCentimos(solicitudAnhoInicio));
//        solicitudDAO.save(solicitudAnhoInicio);
//
//        FormacionContinuadaConId solicitudAnhoFin = objectMapper.readValue(
//            objectMapper.writeValueAsString(formacionContinuada), FormacionContinuadaConId.class);
//        solicitudAnhoFin.setFechaInicio(LocalDate.of(anhoFin, 1, 1));
//        solicitudAnhoFin.setCosteCentimos(calcularCosteCentimos(solicitudAnhoFin));
//        solicitudDAO.save(solicitudAnhoFin);
//
//        resultado = List.of(solicitudAnhoInicio, solicitudAnhoFin);
//      } catch (JsonProcessingException e) {
//        throw new RuntimeException("Error al clonar la activación", e);
//      }
//    } else {
      formacionContinuada.setCosteCentimos(calcularCosteCentimos(formacionContinuada));
      FormacionContinuadaConId guardado = solicitudDAO.save(formacionContinuada);
      
      return guardado;
//      resultado = List.of(guardado);
//    }
//
//    return resultado;
  }

  private int calcularCosteCentimos(FormacionContinuadaConId formacionContinuada) {
    int costeCentimos = 0;
    int smi = costeFormacionContinuadaDAO.findByClave("smi-centimos").getValor();
    float cantidadSmi =
        costeFormacionContinuadaDAO.findByClave(formacionContinuada.getEscala()).getValor();
    float duracion = formacionContinuada.getDuracionMeses();
    costeCentimos = (int) (smi * cantidadSmi * duracion);

    return costeCentimos;
  }
}
