package es.mde.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.ExpedienteDAO;
import es.mde.repositorios.SolicitudDAO;

@Service
public class ExpedienteServicio {
  private record ExpedienteSolicitud(ExpedienteConId expediente, SolicitudConId solicitud) {
  }

  private final ExpedienteDAO expedienteDAO;
  private SolicitudDAO solicitudDAO;
  private final EmailSenderServicio emailSenderServicio;

  @Autowired
  public ExpedienteServicio(ExpedienteDAO expedienteDAO, SolicitudDAO solicitudDAO,
      EmailSenderServicio emailSenderServicio) {
    this.expedienteDAO = expedienteDAO;
    this.solicitudDAO = solicitudDAO;
    this.emailSenderServicio = emailSenderServicio;
  }

  public void asignarSolicitudConNotificacion(Long expedienteId, Long solicitudId) {
    ExpedienteSolicitud resultado = obtenerExpedienteYSolicitud(expedienteId, solicitudId);
    ExpedienteConId expediente = resultado.expediente();
    SolicitudConId solicitud = resultado.solicitud();
    expedienteDAO.asignarSolicitudAExpediente(expediente, solicitud);
    solicitudDAO.save(solicitud);
    String asunto = "Solicitud asignada a expediente";
    String mensaje = "La solicitud con ID " + solicitudId + " ha sido asignada al expediente con número "
        + expediente.getNumeroExpediente() + " y su estado ha cambiado a " + solicitud.getEstado() + ".";
    emailSenderServicio.enviarEmail(solicitud.getPoc().getEmailCorporativo(), asunto, mensaje);
  }

  public void desasignarSolicitudConNotificacion(Long expedienteId, Long solicitudId) {
    ExpedienteSolicitud resultado = obtenerExpedienteYSolicitud(expedienteId, solicitudId);
    ExpedienteConId expediente = resultado.expediente();
    SolicitudConId solicitud = resultado.solicitud();
    expedienteDAO.eliminarSolicitudDeExpediente(expediente, solicitud);
    solicitudDAO.save(solicitud);
    String asunto = "Solicitud desasignada del expediente";
    String mensaje = "La solicitud con ID " + solicitudId + " ha sido desasignada al expediente con número "
        + expediente.getNumeroExpediente() + " y su estado ha cambiado a " + solicitud.getEstado() + ".";
    emailSenderServicio.enviarEmail(solicitud.getPoc().getEmailCorporativo(), asunto, mensaje);
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