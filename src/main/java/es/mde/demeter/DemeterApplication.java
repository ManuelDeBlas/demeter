package es.mde.demeter;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import es.mde.entidades.ExpedienteConId;
import es.mde.entidades.FormacionContinuadaConId;
import es.mde.entidades.PocConId;
import es.mde.entidades.ReservistaConId;
import es.mde.repositorios.ExpedienteDAO;
import es.mde.repositorios.PocDAO;
import es.mde.repositorios.ReservistaDAO;
import es.mde.repositorios.SolicitudDAO;

@SpringBootApplication
public class DemeterApplication {

  private static final Logger log = LoggerFactory.getLogger(DemeterApplication.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(DemeterApplication.class, args);

    log.debug("La aplicación está funcionando");

    // TODO eliminar. Añadido para pruebas
    ExpedienteConId expedienteConId = new ExpedienteConId("T64PSAENE251");
    PocConId pocConId = 
        new PocConId("Pepe", "Perez", "Lopez", "Cap", "RAAA71", "600001110", "pp@gmail.com");
    ReservistaConId reservistaConId = new ReservistaConId(null, null, null, null);
    ExpedienteDAO expedienteDAO = context.getBean(ExpedienteDAO.class);
    expedienteDAO.save(expedienteConId);

    PocDAO pocDAO = context.getBean(PocDAO.class);
    pocDAO.save(pocConId);
    
    ReservistaDAO reservistaDAO = context.getBean(ReservistaDAO.class);
    reservistaDAO.save(reservistaConId);

    SolicitudDAO solicitudDAO = context.getBean(SolicitudDAO.class);
    solicitudDAO.save(new FormacionContinuadaConId("RAAA71", "5743895", "Pendiente evaluación",
       reservistaConId , LocalDate.now(), LocalDate.now(), expedienteConId, 100, "Escalaaa"));
  }

}
