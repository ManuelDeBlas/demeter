package es.mde.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import es.mde.servicios.ExpedienteServicio;

/**
 * Controlador REST para gestionar los expedientes.
 * Proporciona endpoints para asignar y desasignar solicitudes.
 * 
 * Este controlador utiliza el servicio {@link ExpedienteServicio} para realizar
 * las operaciones relacionadas con los expedientes.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@RepositoryRestController
public class ExpedienteController {

  private ExpedienteServicio expedienteServicio;

  /**
   * Constructor que inyecta el servicio de expedientes.
   * 
   * @param expedienteServicio Servicio para la lógica de negocio de expedientes.
   */
  @Autowired
  public ExpedienteController(ExpedienteServicio expedienteServicio) {
    this.expedienteServicio = expedienteServicio;
  }

  /**
   * Asigna una solicitud a un expediente.
   * 
   * Este endpoint permite asignar una solicitud a un expediente específico.
   * Además, se envía una notificación tras completar la operación.
   * 
   * @param expedienteId ID del expediente al que se asignará la solicitud.
   * @param solicitudId ID de la solicitud que se asignará.
   * @return Respuesta HTTP con el resultado de la operación.
   */
  @PatchMapping("/expedientes/{expedienteId}/asignar-solicitud/{solicitudId}")
  public ResponseEntity<String> asignarSolicitud(@PathVariable Long expedienteId, @PathVariable Long solicitudId) {
    ResponseEntity<String> respuesta = null;
    try {
      expedienteServicio.asignarSolicitudConNotificacion(expedienteId, solicitudId);
      respuesta = ResponseEntity
          .ok("Solicitud " + solicitudId + " asignada correctamente al expediente " + expedienteId);
    } catch (Exception e) {
      respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    return respuesta;
  }

  /**
   * Desasigna una solicitud de un expediente.
   * 
   * Este endpoint permite desasignar una solicitud de un expediente específico.
   * Además, se envía una notificación tras completar la operación.
   * 
   * @param expedienteId ID del expediente del que se desasignará la solicitud.
   * @param solicitudId ID de la solicitud que se desasignará.
   * @return Respuesta HTTP con el resultado de la operación.
   */
  @PatchMapping("/expedientes/{expedienteId}/desasignar-solicitud/{solicitudId}")
  public ResponseEntity<String> desasignarSolicitud(@PathVariable Long expedienteId, @PathVariable Long solicitudId) {
    ResponseEntity<String> respuesta = null;
    try {
      expedienteServicio.desasignarSolicitudConNotificacion(expedienteId, solicitudId);
      respuesta = ResponseEntity
          .ok("Solicitud " + solicitudId + " desasignada correctamente del expediente " + expedienteId);
    } catch (Exception e) {
      respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    return respuesta;
  }

}
