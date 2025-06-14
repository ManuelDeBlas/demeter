package es.mde.rest;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.servicios.PrestacionServiciosUnidadServicio;

@RepositoryRestController
public class PrestacionServiciosController
    extends AbstractSolicitudController<PrestacionServiciosUnidadConId> {

  private final PrestacionServiciosUnidadServicio prestacionServiciosUnidadServicio;

  public PrestacionServiciosController(PrestacionServiciosUnidadServicio prestacionServiciosUnidadServicio) {
    this.prestacionServiciosUnidadServicio = prestacionServiciosUnidadServicio;
  }

  @PostMapping("/prestaciones-servicios-unidad")
  public ResponseEntity<?> crearPrestacionServiciosUnidad(
      @RequestBody PrestacionServiciosUnidadConId prestacionServiciosUnidadConId) {

    return crearSolicitud(prestacionServiciosUnidadConId, prestacionServiciosUnidadServicio::crearSolicitud, "prestaciones-servicios-unidad");
  }

}
