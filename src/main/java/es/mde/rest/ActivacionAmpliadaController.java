package es.mde.rest;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.servicios.EXServicio;

@RepositoryRestController
public class ActivacionAmpliadaController extends AbstractSolicitudController<ActivacionAmpliadaConId> {

  private final EXServicio eXServicio;

  public ActivacionAmpliadaController(EXServicio eXServicio) {
    this.eXServicio = eXServicio;
  }

  @PostMapping("/activaciones-ampliadas")
  public ResponseEntity<?> crear(@RequestBody ActivacionAmpliadaConId activacionAmpliadaConId) {

    return crearSolicitud(activacionAmpliadaConId, eXServicio::crearSolicitud, "activaciones-ampliadas");
  }

  @PutMapping("/activaciones-ampliadas/{id}")
  public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ActivacionAmpliadaConId solicitud) {

    return actualizarSolicitud(id, solicitud, eXServicio::actualizarSolicitud, "activaciones-ampliadas");
  }

}
