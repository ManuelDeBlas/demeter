package es.mde.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.servicios.PSyEXServicio;

@RepositoryRestController
public class ActivacionAmpliadaController {
  private static final Logger log = LoggerFactory.getLogger(ActivacionAmpliadaController.class);

  private final PSyEXServicio pSyEXServicio;

  public ActivacionAmpliadaController(PSyEXServicio pSyEXServicio) {
    this.pSyEXServicio = pSyEXServicio;
  }

  @PostMapping("/activaciones-ampliadas")
  public ResponseEntity<ActivacionAmpliadaConId> crearActivacionAmpliada(
      @RequestBody ActivacionAmpliadaConId activacionAmpliadaConId) {
    ActivacionAmpliadaConId creado = pSyEXServicio.crearEX(activacionAmpliadaConId);
    URI uri = URI.create("/solicitudes/" + creado.getId());

    return ResponseEntity.created(uri).body(creado);
  }
}
