package es.mde.rest;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.servicios.FCServicio;

@RepositoryRestController
public class FormacionContinuadaController
    extends AbstractSolicitudController<FormacionContinuadaConId> {

  private final FCServicio fCServicio;

  public FormacionContinuadaController(FCServicio fCServicio) {
    this.fCServicio = fCServicio;
  }

  @PostMapping("/formaciones-continuadas")
  public ResponseEntity<?> crearFormacionContinuada(
      @RequestBody FormacionContinuadaConId formacionContinuadaConId) {

    return crearSolicitud(formacionContinuadaConId, fCServicio::crearFC);
  }

}
