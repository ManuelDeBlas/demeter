package es.mde.servicios;

import org.springframework.stereotype.Service;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.SolicitudDAO;
import jakarta.persistence.EntityManager;

@Service
public class FCServicio extends AbstractSolicitudServicio<FormacionContinuadaConId> {

  private final CosteFormacionContinuadaDAO costeDAO;

  public FCServicio(EntityManager entityManager, SolicitudDAO solicitudDAO,
      CosteFormacionContinuadaDAO costeDAO) {
    super(entityManager, solicitudDAO);
    this.costeDAO = costeDAO;
  }
  
  crearFC()

  @Override
  protected int calcularCosteCentimos(FormacionContinuadaConId solicitud) {
    int smi = costeDAO.findByClave("smi-centimos").getValor();
    float cantidad = costeDAO.findByClave(solicitud.getEscala()).getValor();
    return (int) (smi * cantidad * solicitud.getDuracionMeses());
  }
}
