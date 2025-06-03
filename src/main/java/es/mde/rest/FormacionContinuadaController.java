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
import es.mde.entidades.ActivacionAmpliadaConId;
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
  public ResponseEntity<?> crearFormacionContinuada(
      @RequestBody FormacionContinuadaConId formacionContinuada) {
    ResponseEntity<?> respuesta;
    try {
      FormacionContinuadaConId creadas =
          formacionContinuadaServicio.crearSolicitud(formacionContinuada);
//      for (FormacionContinuadaConId formacionContinuadaCreada : creadas) {
//        
//      }
      respuesta = ResponseEntity.status(HttpStatus.CREATED).body(creadas);
    } catch (Exception e) {
      respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    return respuesta;
  }

}
