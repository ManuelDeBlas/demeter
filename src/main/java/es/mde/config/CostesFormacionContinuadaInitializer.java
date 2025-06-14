package es.mde.config;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.mde.entidades.CosteFormacionContinuada;
import es.mde.repositorios.CosteFormacionContinuadaDAO;

@Component
public class CostesFormacionContinuadaInitializer implements CommandLineRunner {
  
  private static final Logger log = LoggerFactory.getLogger(CostesPorDiaInitializer.class);

  private final CosteFormacionContinuadaDAO costeFormacionContinuadaDAO;
  private final ObjectMapper objectMapper;

  @Value("${costes-fc-iniciales-json}")
  private String costesIniciales;

  public CostesFormacionContinuadaInitializer(CosteFormacionContinuadaDAO costeFormacionContinuadaDAO, ObjectMapper objectMapper) {
    this.costeFormacionContinuadaDAO = costeFormacionContinuadaDAO;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (costeFormacionContinuadaDAO.count() == 0) {
      InputStream inputStream = getClass().getResourceAsStream(costesIniciales);
      List<CosteFormacionContinuada> costes =
          Arrays.asList(objectMapper.readValue(inputStream, CosteFormacionContinuada[].class));
      costeFormacionContinuadaDAO.saveAll(costes);
    }
  }

}
