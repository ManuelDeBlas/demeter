package es.mde.rest;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.FormacionContinuadaConId;
import es.mde.servicios.FormacionContinuadaServicio;

@RepositoryRestController
public class FormacionContinuadaController extends AbstractSolicitudController<FormacionContinuadaConId> {

  private final FormacionContinuadaServicio formacionContinuadaServicio;

  public FormacionContinuadaController(FormacionContinuadaServicio formacionContinuadaServicio) {
    this.formacionContinuadaServicio = formacionContinuadaServicio;
  }

  @PostMapping("/formaciones-continuadas")
  public ResponseEntity<?> crearFormacionContinuada(@RequestBody FormacionContinuadaConId formacionContinuadaConId) {

    return crearSolicitud(formacionContinuadaConId, formacionContinuadaServicio::crearSolicitud, "formaciones-continuadas");
  }

  @PutMapping("/formaciones-continuadas/{id}")
  public ResponseEntity<?> actualizar(@PathVariable Long id,
      @RequestBody FormacionContinuadaConId formacionContinuadaConId) {
    return actualizarSolicitud(id, formacionContinuadaConId, formacionContinuadaServicio::actualizarSolicitud,
        "formaciones-continuadas");
  }

}
