package es.mde.rest;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.servicios.PSyEXServicio;

@RepositoryRestController
public class ActivacionAmpliadaController
    extends AbstractSolicitudController<ActivacionAmpliadaConId> {

  private final PSyEXServicio pSyEXServicioservicio;

  public ActivacionAmpliadaController(PSyEXServicio pSyEXServicioservicio) {
    this.pSyEXServicioservicio = pSyEXServicioservicio;
  }

  @PostMapping("/activaciones-ampliadas")
  public ResponseEntity<?> crear(@RequestBody ActivacionAmpliadaConId activacionAmpliadaConId) {

    return crearSolicitud(activacionAmpliadaConId, pSyEXServicioservicio::crearEX);
  }
}

