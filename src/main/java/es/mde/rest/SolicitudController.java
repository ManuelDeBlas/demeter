package es.mde.rest;

import java.net.URI;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.SolicitudConId;
import es.mde.servicios.SolicitudSercivio;

@RepositoryRestController
public class SolicitudController {

  private final SolicitudSercivio solicitudSercivio;

  public SolicitudController(SolicitudSercivio solicitudSercivio) {
    this.solicitudSercivio = solicitudSercivio;
  }

  @PostMapping
  public ResponseEntity<SolicitudConId> crearProducto(@RequestBody SolicitudConId solicitud) {
    SolicitudConId creado = solicitudSercivio.crearSolicitud(solicitud);
    URI uri = URI.create("/solicitudes/" + creado.getId());

    return ResponseEntity.created(uri).body(creado);
  }

}
