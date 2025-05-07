package es.mde.rest;

import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.SolicitudDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RepositoryRestController
public class SolicitudController {

  @Autowired
  private SolicitudDAO solicitudDAO;

  @GetMapping("/solicitudes")
  public List<SolicitudConId> getSolicitudes() {
    return solicitudDAO.findAll();
  }

}
