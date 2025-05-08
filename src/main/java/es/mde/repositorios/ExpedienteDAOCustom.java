package es.mde.repositorios;

public interface ExpedienteDAOCustom {

  void asignarSolicitudAExpediente(Long expedienteId, Long solicitudId);
  
  void eliminarSolicitudDeExpediente(Long expedienteId, Long solicitudId);
}
