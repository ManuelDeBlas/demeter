package es.mde.demeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemeterApplication {

  private static final Logger log = LoggerFactory.getLogger(DemeterApplication.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(DemeterApplication.class, args);

    log.debug("La aplicación está funcionando");
  }

}
