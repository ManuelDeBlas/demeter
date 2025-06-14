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

import es.mde.entidades.CostePorDiaConId;
import es.mde.repositorios.CostePorDiaDAO;

@Component
public class CostesPorDiaInitializer implements CommandLineRunner {
  
  private static final Logger log = LoggerFactory.getLogger(CostesPorDiaInitializer.class);

  private final CostePorDiaDAO costePorDiaDAO;
  private final ObjectMapper objectMapper;

  @Value("${costes-dia-iniciales-json}")
  private String costesIniciales;

  public CostesPorDiaInitializer(CostePorDiaDAO costePorDiaDAO, ObjectMapper objectMapper) {
    this.costePorDiaDAO = costePorDiaDAO;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (costePorDiaDAO.count() == 0) {
      InputStream inputStream = getClass().getResourceAsStream(costesIniciales);
      List<CostePorDiaConId> costes =
          Arrays.asList(objectMapper.readValue(inputStream, CostePorDiaConId[].class));
      costePorDiaDAO.saveAll(costes);
    }
  }

}
