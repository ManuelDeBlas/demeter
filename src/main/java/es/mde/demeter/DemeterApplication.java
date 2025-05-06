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
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.entidades.ReservistaConId;
import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.ExpedienteDAO;
import es.mde.repositorios.FormacionContinuadaDAO;
import es.mde.repositorios.PocDAO;
import es.mde.repositorios.PrestacionServiciosUnidadDAO;
import es.mde.repositorios.ReservistaDAO;

@SpringBootApplication
public class DemeterApplication {

  private static final Logger log = LoggerFactory.getLogger(DemeterApplication.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(DemeterApplication.class, args);

    log.debug("La aplicación está funcionando");
  }

}
