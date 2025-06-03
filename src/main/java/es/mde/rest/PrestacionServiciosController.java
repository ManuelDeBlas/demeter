package es.mde.rest;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.servicios.FCServicio;
import es.mde.servicios.PSyEXServicio;

@RepositoryRestController
public class PrestacionServiciosController {

  private static final Logger log = LoggerFactory.getLogger(PrestacionServiciosController.class);

  private final PSyEXServicio pSyEXServicio;

  public PrestacionServiciosController(PSyEXServicio pSyEXServicio) {
    this.pSyEXServicio = pSyEXServicio;
  }

  @PostMapping("/prestaciones-servicios-unidad")
  public ResponseEntity<?> crearPrestacionServiciosUnidad(
      @RequestBody PrestacionServiciosUnidadConId prestacionServiciosUnidad) {
    try {
      PrestacionServiciosUnidadConId creadas =
          pSyEXServicio.crearPS(prestacionServiciosUnidad);
      return ResponseEntity.status(HttpStatus.CREATED).body(creadas);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }


}
