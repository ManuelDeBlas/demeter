package es.mde.servicios;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;

@Service
public class SolicitudSercivio {

  private final SolicitudDAO solicitudDAO;
  private final CosteFormacionContinuadaDAO costeFormacionContinuadaDAO;
  private final CostePorDiaDAO costePorDiaDAO;

  public SolicitudSercivio(SolicitudDAO solicitudDAO, CosteFormacionContinuadaDAO costeFormacionContinuadaDAO,
      CostePorDiaDAO costePorDiaDAO) {
    this.solicitudDAO = solicitudDAO;
    this.costeFormacionContinuadaDAO = costeFormacionContinuadaDAO;
    this.costePorDiaDAO = costePorDiaDAO;
  }

  public SolicitudConId crearSolicitud(SolicitudConId solicitud) {
    solicitud.setCosteCentimos(calcularCosteCentimos(solicitud));

    return solicitudDAO.save(solicitud);
  }

  private int calcularCosteCentimos(Solicitud solicitud) {
    int costeCentimos = 0;
    // TODO no funciona bien
    if (solicitud.getTipoSolicitud().equals("FC")) {
      FormacionContinuadaConId formacionContinuada = (FormacionContinuadaConId) solicitud;
      int smi = costeFormacionContinuadaDAO.findValorByClave("smi-centimos");
      float cantidadSmi = costeFormacionContinuadaDAO.findValorByClave(formacionContinuada.getEscala());
      float duracion = formacionContinuada.getDuracionMeses();
      costeCentimos = (int) (smi * cantidadSmi * duracion);
    } else {
      int costeDiaCentimos = costePorDiaDAO.findCentimosByEmpleo(solicitud.getReservista().getEmpleo());
      int duracion = solicitud.getDiasDuracion();
      costeCentimos = Math.toIntExact(duracion * costeDiaCentimos);
    }

    return costeCentimos;
  }
}
