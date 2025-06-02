package es.mde.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;

@Service
public class FCServicio {

  private static final Logger log = LoggerFactory.getLogger(FCServicio.class);

  private final SolicitudDAO solicitudDAO;
  private final CosteFormacionContinuadaDAO costeFormacionContinuadaDAO;

  public FCServicio(SolicitudDAO solicitudDAO, CosteFormacionContinuadaDAO costeFormacionContinuadaDAO) {
    this.solicitudDAO = solicitudDAO;
    this.costeFormacionContinuadaDAO = costeFormacionContinuadaDAO;
  }

  public FormacionContinuadaConId crearSolicitud(FormacionContinuadaConId formacionContinuada) {
    formacionContinuada.setCosteCentimos(calcularCosteCentimos(formacionContinuada));

    return solicitudDAO.save(formacionContinuada);
  }

  private int calcularCosteCentimos(FormacionContinuadaConId formacionContinuada) {
    int costeCentimos = 0;
    int smi = costeFormacionContinuadaDAO.findByClave("smi-centimos").getValor();
    float cantidadSmi = costeFormacionContinuadaDAO.findByClave(formacionContinuada.getEscala()).getValor();
    float duracion = formacionContinuada.getDuracionMeses();
    costeCentimos = (int) (smi * cantidadSmi * duracion);

    return costeCentimos;
  }
}
