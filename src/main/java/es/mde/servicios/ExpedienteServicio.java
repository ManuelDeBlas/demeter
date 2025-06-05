package es.mde.servicios;

import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import es.mde.entidades.CostePorDia;
import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.PresupuestoConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.ExpedienteDAO;
import es.mde.repositorios.PresupuestoDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;
import es.mde.secres.Solicitud.Estados;
import es.mde.util.StringUtils;

/**
 * Servicio para gestionar la lógica de negocio relacionada con los expedientes. Incluye operaciones
 * para asignar y desasignar solicitudes con notificaciones.
 * 
 * @version 1.0
 */
@Service
public class ExpedienteServicio {
  private record EntidadesAModificar(ExpedienteConId expediente, SolicitudConId solicitud,
      PresupuestoConId presupuesto) {
  }

  private final ExpedienteDAO expedienteDAO;
  private final SolicitudDAO solicitudDAO;
  private final PresupuestoDAO presupuestoDAO;
  private final CostePorDiaDAO costePorDiaDAO;
  private final EmailSenderServicio emailSenderServicio;

  @Value("${maximo-dias-activacion}")
  private int maximoDiasActivacion;

  /**
   * Constructor que inyecta los DAOs y el servicio de correo electrónico.
   * 
   * @param expedienteDAO DAO para gestionar expedientes.
   * @param solicitudDAO DAO para gestionar solicitudes.
   * @param emailSenderServicio Servicio para enviar correos electrónicos.
   */
  @Autowired
  public ExpedienteServicio(ExpedienteDAO expedienteDAO, SolicitudDAO solicitudDAO,
      PresupuestoDAO presupuestoDAO, CostePorDiaDAO costePorDiaDAO,
      EmailSenderServicio emailSenderServicio) {
    this.expedienteDAO = expedienteDAO;
    this.solicitudDAO = solicitudDAO;
    this.presupuestoDAO = presupuestoDAO;
    this.costePorDiaDAO = costePorDiaDAO;
    this.emailSenderServicio = emailSenderServicio;
  }

  /**
   * Asigna una solicitud a un expediente y envía una notificación.
   * 
   * @param expedienteId ID del expediente.
   * @param solicitudId ID de la solicitud.
   * @return
   */
  public String asignarSolicitudAExpediente(Long expedienteId, Long solicitudId) {
    EntidadesAModificar entidadesAModificar = obtenerEntidades(expedienteId, solicitudId);
    ExpedienteConId expediente = entidadesAModificar.expediente();
    SolicitudConId solicitud = entidadesAModificar.solicitud();
    PresupuestoConId presupuesto = entidadesAModificar.presupuesto();
    if (expediente.getAnho() != solicitud.getFechaInicio().getYear()) {
      throw new IllegalArgumentException(
          "No se pudo agregar la solicitud al expediente ya que el año de la solicitud ("
              + solicitud.getFechaInicio().getYear() + ") no es igual al año del expediente ("
              + expediente.getAnho() + ")");
    }
    if (solicitud.getExpediente() != null) {
      throw new IllegalArgumentException("Esta solicitud ya está asignada al expediente "
          + solicitud.getExpediente().getNumeroExpediente()
          + ". Elimínela de su expediente antes de asignarla a otro.");
    }
    if (!Objects.equals(solicitud.getTipoSolicitud(), expediente.getTipoSolicitud())) {
      throw new IllegalArgumentException("El tipo de la solicitud (" + solicitud.getTipoSolicitud()
          + ") no coincide con el tipo del expediente (" + expediente.getTipoSolicitud() + ")");
    }
    if (solicitud.getDiasDuracion() + solicitud.getReservista()
        .getDiasConsumidos(solicitud.getFechaInicio().getYear()) > maximoDiasActivacion) {
      throw new IllegalArgumentException("El reservista cuenta con "
          + String.valueOf(maximoDiasActivacion
              - solicitud.getReservista().getDiasConsumidos(solicitud.getFechaInicio().getYear()))
          + " dias disponibles y la activación dura " + solicitud.getDiasDuracion() + " dias");
    }
    int costeCentimosSolicitud = solicitud.getCosteCentimos();
    if (solicitud.isPagaSecres() && presupuesto.getCantidadCentimos() < costeCentimosSolicitud) {
      throw new IllegalArgumentException("La SECRES no dispone de suficiente presupuesto en el año "
          + expediente.getAnho() + ". Aumente el presupuesto disponible hasta "
          + StringUtils.centimosToEurosString(costeCentimosSolicitud)
          + " € o cambie al pagador de la solicitud para poder asignarla a un expediente.");
    }
    expediente.addSolicitudConId(solicitud);
    solicitud.setExpediente(expediente);
    if (solicitud.isPagaSecres()) {
      presupuesto.setCantidadCentimos(presupuesto.getCantidadCentimos() - costeCentimosSolicitud);
    }
    solicitud.setEstado(Estados.ACEPTADA_PENDIENTE_PUBLICACION);
    guardarCambios(expediente, solicitud, presupuesto);
    String respuesta = "Solicitud del reservista " + solicitud.getReservista().getDni()
        + " asignada al expediente " + expediente.getNumeroExpediente();
    emailSenderServicio.enviarEmail(solicitud.getEmailPoc(), "Solicitud asiganada al expediente",
        respuesta);

    return respuesta + " correctamente. El presupuesto restante para el año "
        + presupuesto.getAnho() + " es de "
        + StringUtils.centimosToEurosString(presupuesto.getCantidadCentimos()) + " €.";
  }

  /**
   * Desasigna una solicitud de un expediente y envía una notificación.
   * 
   * @param expedienteId ID del expediente.
   * @param solicitudId ID de la solicitud.
   * @return
   */
  public String desasignarSolicitudDeExpediente(Long expedienteId, Long solicitudId) {
    EntidadesAModificar entidadesAModificar = obtenerEntidades(expedienteId, solicitudId);
    ExpedienteConId expediente = entidadesAModificar.expediente();
    SolicitudConId solicitud = entidadesAModificar.solicitud();
    PresupuestoConId presupuesto = entidadesAModificar.presupuesto();
    if (expediente.getSolicitudes().contains(solicitud)) {
      expediente.removeSolicitud(solicitud);
      solicitud.setExpediente(null);
      solicitud.setEstado(Estados.PENDIENTE_EVALUACION);
      if (solicitud.isPagaSecres()) {
        int costeCentimosSolicitud = solicitud.getCosteCentimos();
        presupuesto.setCantidadCentimos(presupuesto.getCantidadCentimos() + costeCentimosSolicitud);
      }
    } else {
      throw new IllegalArgumentException("El expediente " + expediente.getNumeroExpediente()
          + " no contiene la solicitud que se quiere eliminar.");
    }
    guardarCambios(expediente, solicitud, presupuesto);
    String respuesta = "Solicitud del reservista " + solicitud.getReservista().getDni()
        + " desasignada del expediente " + expediente.getNumeroExpediente();
    emailSenderServicio.enviarEmail(solicitud.getEmailPoc(),
        "Solicitud desasiganada del expediente", respuesta);

    return respuesta + "correctamente. El presupuesto restante para el año " + presupuesto.getAnho()
        + " es de " + StringUtils.centimosToEurosString(presupuesto.getCantidadCentimos()) + " €.";
  }

  private EntidadesAModificar obtenerEntidades(Long expedienteId, Long solicitudId) {
    Optional<ExpedienteConId> expedienteOpt = expedienteDAO.findById(expedienteId);
    Optional<SolicitudConId> solicitudOpt = solicitudDAO.findById(solicitudId);
    if (solicitudOpt.isEmpty()) {
      throw new IllegalArgumentException("Solicitud no encontrada");
    }
    if (expedienteOpt.isEmpty()) {
      throw new IllegalArgumentException("Expediente no encontrado");
    }
    SolicitudConId solicitud = solicitudOpt.get();
    ExpedienteConId expediente = expedienteOpt.get();
    int anho = expediente.getAnho();
    PresupuestoConId presupuesto = presupuestoDAO.getByAnho(anho);
    if (presupuesto == null) {
      throw new IllegalArgumentException("No existe presupuesto para el año " + anho);
    }
    return new EntidadesAModificar(expediente, solicitud, presupuesto);
  }

  private void guardarCambios(ExpedienteConId expediente, SolicitudConId solicitud,
      PresupuestoConId presupuesto) {
    expedienteDAO.save(expediente);
    solicitudDAO.save(solicitud);
    presupuestoDAO.save(presupuesto);
  }
}
