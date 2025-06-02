package es.mde.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.FormacionContinuadaConId;
import es.mde.servicios.FCServicio;

@RepositoryRestController
public class FormacionContinuadaController {

  private static final Logger log = LoggerFactory.getLogger(FormacionContinuadaController.class);

  private final FCServicio formacionContinuadaServicio;

  public FormacionContinuadaController(FCServicio formacionContinuadaServicio) {
    this.formacionContinuadaServicio = formacionContinuadaServicio;
  }

  @PostMapping("/formaciones-continuadas")
  public ResponseEntity<FormacionContinuadaConId> crearFormacionContinuada(
      @RequestBody FormacionContinuadaConId formacionContinuada) {
    log.warn("se recibe una solicitud");
    FormacionContinuadaConId creado = formacionContinuadaServicio.crearSolicitud(formacionContinuada);
    URI uri = URI.create("/solicitudes/" + creado.getId());

    return ResponseEntity.created(uri).body(creado);
  }
}
