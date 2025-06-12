package es.mde.servicios;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.mde.entidades.ActivacionAmpliadaConId;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.entidades.ReservistaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.CosteFormacionContinuadaDAO;
import es.mde.repositorios.CostePorDiaDAO;
import es.mde.repositorios.SolicitudDAO;
import es.mde.secres.Solicitud;
import jakarta.persistence.EntityManager;

@Service
public class PSServicio extends AbstractSolicitudServicio<PrestacionServiciosUnidadConId> {

  public PSServicio(EntityManager entityManager, SolicitudDAO solicitudDAO, CostePorDiaDAO costePorDiaDAO) {
    super(entityManager, solicitudDAO, costePorDiaDAO);
  }

  @Override
  public PrestacionServiciosUnidadConId crearSolicitud(PrestacionServiciosUnidadConId solicitud) {
    return super.crearSolicitud(solicitud);
  }

}
