package es.mde.servicios;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.ExpedienteDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;
import es.mde.secres.Solicitud.Estados;

/**
 * Servicio para gestionar la lógica de negocio relacionada con los expedientes.
 * Incluye operaciones para asignar y desasignar solicitudes con notificaciones.
 * 
 * @version 1.0
 */
@Service
public class ExpedienteServicio {
  private record ExpedienteSolicitud(ExpedienteConId expediente, SolicitudConId solicitud) {
  }

  private final ExpedienteDAO expedienteDAO;
  private SolicitudDAO solicitudDAO;
//  private final EmailSenderServicio emailSenderServicio;

  /**
   * Constructor que inyecta los DAOs y el servicio de correo electrónico.
   * 
   * @param expedienteDAO       DAO para gestionar expedientes.
   * @param solicitudDAO        DAO para gestionar solicitudes.
   * @param emailSenderServicio Servicio para enviar correos electrónicos.
   */
  @Autowired
  public ExpedienteServicio(ExpedienteDAO expedienteDAO, SolicitudDAO solicitudDAO
//      ,EmailSenderServicio emailSenderServicio
      ) {
    this.expedienteDAO = expedienteDAO;
    this.solicitudDAO = solicitudDAO;
//    this.emailSenderServicio = emailSenderServicio;
  }

  /**
   * Asigna una solicitud a un expediente y envía una notificación.
   * 
   * @param expedienteId ID del expediente.
   * @param solicitudId  ID de la solicitud.
   */
  public void asignarSolicitudConNotificacion(Long expedienteId, Long solicitudId) {
    ExpedienteSolicitud resultado = obtenerExpedienteYSolicitud(expedienteId, solicitudId);
    ExpedienteConId expediente = resultado.expediente();
    SolicitudConId solicitud = resultado.solicitud();
    if (solicitud.getExpediente() != null) {
      throw new IllegalArgumentException(
          "Esta solicitud ya está asignada al expediente " + solicitud.getExpediente().getNumeroExpediente()
              + ". Elimínela de su expediente antes de asignarla a otro.");
    }
    if (Objects.equals(solicitud.getTipoSolicitud(), expediente.getTipoSolicitud())) {
      expediente.addSolicitudConId(solicitud);
      solicitud.setExpediente(expediente);
      solicitud.setEstado(Estados.ACEPTADA_PENDIENTE_PUBLICACION);
    } else {
      throw new IllegalArgumentException("El tipo de la solicitud (" + solicitud.getTipoSolicitud()
          + ") no coincide con el tipo del expediente (" + expediente.getTipoSolicitud() + ")");
    }
    expedienteDAO.save(expediente);
    solicitudDAO.save(solicitud);
//    String asunto = "Solicitud asignada a expediente";
//    String mensaje = "La solicitud entre las fechas " + solicitud.getFechaInicio() + " y " + solicitud.getFechaFin()
//        + " para el reservista con DNI: " + solicitud.getReservista().getDni() + " ha sido asignada al expediente con número "
//        + expediente.getNumeroExpediente() + " y su estado ha cambiado a " + solicitud.getEstado() + ".";
//    emailSenderServicio.enviarEmail(solicitud.getPoc().getEmailCorporativo(), asunto, mensaje);
  }

  /**
   * Desasigna una solicitud de un expediente y envía una notificación.
   * 
   * @param expedienteId ID del expediente.
   * @param solicitudId  ID de la solicitud.
   */
  public void desasignarSolicitudConNotificacion(Long expedienteId, Long solicitudId) {
    ExpedienteSolicitud resultado = obtenerExpedienteYSolicitud(expedienteId, solicitudId);
    ExpedienteConId expediente = resultado.expediente();
    SolicitudConId solicitud = resultado.solicitud();
    if (expediente.getSolicitudes().contains(solicitud)) {
      expediente.removeSolicitud(solicitud);
      solicitud.setExpediente(null);
      solicitud.setEstado(Estados.PENDIENTE_EVALUACION);
    } else {
      throw new IllegalArgumentException(
          "El expediente " + expediente.getNumeroExpediente() + " no contiene la solicitud que se quiere eliminar.");
    }
    expedienteDAO.save(expediente);
    solicitudDAO.save(solicitud);
//    String asunto = "Solicitud desasignada del expediente";
//    String mensaje = "La solicitud entre las fechas " + solicitud.getFechaInicio() + " y " + solicitud.getFechaFin()
//    + " para el reservista con DNI: " + solicitud.getReservista().getDni() + " ha sido desasignada al expediente con número "
//    + expediente.getNumeroExpediente() + " y su estado ha cambiado a " + solicitud.getEstado() + ".";
//    emailSenderServicio.enviarEmail(solicitud.getPoc().getEmailCorporativo(), asunto, mensaje);
  }

  private ExpedienteSolicitud obtenerExpedienteYSolicitud(Long expedienteId, Long solicitudId) {
    Optional<ExpedienteConId> expedienteOpt = expedienteDAO.findById(expedienteId);
    Optional<SolicitudConId> solicitudOpt = solicitudDAO.findById(solicitudId);
    if (expedienteOpt.isEmpty() || solicitudOpt.isEmpty()) {
      throw new IllegalArgumentException("Expediente o solicitud no encontrado");
    }
    return new ExpedienteSolicitud(expedienteOpt.get(), solicitudOpt.get());
  }

}