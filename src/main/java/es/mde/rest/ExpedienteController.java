package es.mde.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import es.mde.repositorios.ExpedienteDAO;

@RepositoryRestController
public class ExpedienteController {

  private ExpedienteDAO expedienteDAO;

  @Autowired
  public ExpedienteController(ExpedienteDAO expedienteDAODAO) {
    this.expedienteDAO = expedienteDAODAO;
  }

  @PatchMapping("/expedientes/{expedienteId}/asignar-solicitud/{solicitudId}")
  public ResponseEntity<String> asignarSolicitud(@PathVariable Long expedienteId, @PathVariable Long solicitudId) {
    ResponseEntity<String> respuesta = null;
    try {
      expedienteDAO.asignarSolicitudAExpediente(expedienteId, solicitudId);
      respuesta = ResponseEntity
          .ok("Solicitud " + solicitudId + " asignada correctamente al expediente " + expedienteId);
    } catch (Exception e) {
      respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    return respuesta;
  }

  @PatchMapping("/expedientes/{expedienteId}/desasignar-solicitud/{solicitudId}")
  public ResponseEntity<String> desasignarSolicitud(@PathVariable Long expedienteId, @PathVariable Long solicitudId) {
    ResponseEntity<String> respuesta = null;
    try {
      expedienteDAO.eliminarSolicitudDeExpediente(expedienteId, solicitudId);
      respuesta = ResponseEntity
          .ok("Solicitud " + solicitudId + " desasignada correctamente del expediente " + expedienteId);
    } catch (Exception e) {
      respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    return respuesta;
  }

}
