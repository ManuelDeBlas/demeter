package es.mde.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
  private final EntityManager entityManager;

  // TODO incluir maximoDiasActivacion
  public FCServicio(SolicitudDAO solicitudDAO, CosteFormacionContinuadaDAO costeFormacionContinuadaDAO,
      EntityManager entityManager) {
    this.solicitudDAO = solicitudDAO;
    this.costeFormacionContinuadaDAO = costeFormacionContinuadaDAO;
    this.entityManager = entityManager;
  }

  public FormacionContinuadaConId crearSolicitud(FormacionContinuadaConId formacionContinuada) {
    ReservistaConId ref = entityManager.getReference(ReservistaConId.class,
        formacionContinuada.getReservista().getId());
    formacionContinuada.setReservista(ref);

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
