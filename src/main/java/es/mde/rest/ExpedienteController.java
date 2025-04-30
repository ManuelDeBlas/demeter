package es.mde.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import es.mde.repositorios.ExpedienteDAO;

@RepositoryRestController
@Configuration
public class ExpedienteController {

  private ExpedienteDAO expedienteDAO;
  
  public ExpedienteController(ExpedienteDAO expedienteDAO) {
    this.expedienteDAO = expedienteDAO;
  }

}
