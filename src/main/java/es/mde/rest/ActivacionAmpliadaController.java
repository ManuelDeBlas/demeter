package es.mde.rest;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import es.mde.dtos.SolicitudConLinks;
import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.servicios.PSyEXServicio;
import es.mde.util.CargadorLinks;

@RepositoryRestController
public class ActivacionAmpliadaController {
  private static final Logger log = LoggerFactory.getLogger(ActivacionAmpliadaController.class);

  private final PSyEXServicio pSyEXServicio;

  public ActivacionAmpliadaController(PSyEXServicio pSyEXServicio) {
    this.pSyEXServicio = pSyEXServicio;
  }

  @PostMapping("/activaciones-ampliadas")
  public ResponseEntity<?> crearActivacionAmpliada(
      @RequestBody ActivacionAmpliadaConId activacionAmpliadaConId) {
    ResponseEntity<?> respuesta = null;
    try {
      ActivacionAmpliadaConId creada = pSyEXServicio.crearEX(activacionAmpliadaConId);
      SolicitudConLinks dto = CargadorLinks.cargarLinks(creada);
      URI selfUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(creada.getId()).toUri();
      respuesta = ResponseEntity.created(selfUri).body(dto);
    } catch (Exception e) {
      respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    return respuesta;
  }


}
