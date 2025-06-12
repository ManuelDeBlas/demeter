package es.mde.rest;

import java.net.URI;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import es.mde.dtos.SolicitudConLinks;
import es.mde.entidades.SolicitudConId;
import es.mde.secres.Solicitud;
import es.mde.util.CargadorLinks;

public abstract class AbstractSolicitudController<T extends Solicitud> {

  <R extends SolicitudConId> ResponseEntity<?> crearSolicitud(T solicitud,
      Function<T, R> creador) {
    try {
      R creada = creador.apply(solicitud);
      SolicitudConLinks dto = CargadorLinks.cargarLinks(creada);
      URI selfUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(creada.getId()).toUri();

      return ResponseEntity.created(selfUri).body(dto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}

