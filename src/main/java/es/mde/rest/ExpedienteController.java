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

  @PatchMapping("/expedientes/{expedienteId}/asignar-solicitud")
  public ResponseEntity<String> asignarSolicitud(@PathVariable Long expedienteId,
      @RequestParam Long solicitudId) {
    try {
      expedienteDAO.asignarSolicitudAExpediente(expedienteId, solicitudId);
      return ResponseEntity
          .ok("Solicitud " + solicitudId + " asignada correctamente al expediente " + expedienteId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("No se encontró el expediente o la solicitud");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error interno al asignar la solicitud: " + e.getMessage());
    }
  }

}

