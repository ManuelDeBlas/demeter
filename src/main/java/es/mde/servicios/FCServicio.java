package es.mde.servicios;

import org.springframework.stereotype.Service;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import jakarta.persistence.EntityManager;

@Service
public class FCServicio extends AbstractSolicitudServicio<FormacionContinuadaConId> {

  private final CosteFormacionContinuadaDAO costeFormacionContinuadaDAO;

  public FCServicio(EntityManager entityManager, SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO,
      CosteFormacionContinuadaDAO costeFormacionContinuadaDAO) {
    super(entityManager, solicitudDAO, costePorDiaDAO);
    this.costeFormacionContinuadaDAO = costeFormacionContinuadaDAO;
  }

  @Override
  protected int calcularCosteCentimos(FormacionContinuadaConId formacionContinuadaConId) {
    int smi = costeFormacionContinuadaDAO.findByClave("smi-centimos").getValor();
    float cantidad = costeFormacionContinuadaDAO.findByClave(formacionContinuadaConId.getEscala()).getValor();
    return (int) (smi * cantidad * formacionContinuadaConId.getDuracionMeses());
  }

}
