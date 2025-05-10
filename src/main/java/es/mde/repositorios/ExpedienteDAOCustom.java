package es.mde.repositorios;

import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.SolicitudConId;

public interface ExpedienteDAOCustom {

  void asignarSolicitudAExpediente(ExpedienteConId expediente, SolicitudConId solicitud);
  
  void eliminarSolicitudDeExpediente(ExpedienteConId expediente, SolicitudConId solicitud);
}
