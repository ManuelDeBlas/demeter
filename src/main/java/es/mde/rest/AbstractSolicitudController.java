package es.mde.rest;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.mde.dtos.SolicitudConLinks;
import es.mde.entidades.SolicitudConId;
import es.mde.util.CargadorLinks;

public abstract class AbstractSolicitudController<T extends SolicitudConId> {

  ResponseEntity<?> crearSolicitud(T solicitud, Function<T, T> creador, String rutaApi) {
    try {
      T solcitudcreada = creador.apply(solicitud);
      SolicitudConLinks dto = CargadorLinks.cargarLinksGenerico(solcitudcreada, rutaApi);
      URI selfUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(solcitudcreada.getId())
          .toUri();
      return ResponseEntity.created(selfUri).body(dto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  protected ResponseEntity<?> actualizarSolicitud(Long id, T solicitud, BiFunction<Long, T, T> actualizador,
      String rutaApi) {
    try {
      T solicitudActualizada = actualizador.apply(id, solicitud);
      SolicitudConLinks dto = CargadorLinks.cargarLinksGenerico(solicitudActualizada, rutaApi);
      return ResponseEntity.ok(dto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
