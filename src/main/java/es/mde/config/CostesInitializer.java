package es.mde.config;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.mde.entidades.CostePorDia;
import es.mde.repositorios.CostePorDiaDAO;

@Component
public class CostesInitializer implements CommandLineRunner {

  private final CostePorDiaDAO costePorDiaDAO;
  private final ObjectMapper objectMapper;

  @Value("${costes-iniciales-json}")
  private String costesIniciales;

  public CostesInitializer(CostePorDiaDAO costePorDiaDAO, ObjectMapper objectMapper) {
    this.costePorDiaDAO = costePorDiaDAO;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (costePorDiaDAO.count() == 0) {
      InputStream inputStream = getClass().getResourceAsStream(costesIniciales);
      List<CostePorDia> costes =
          Arrays.asList(objectMapper.readValue(inputStream, CostePorDia[].class));
      costePorDiaDAO.saveAll(costes);
      System.out.println("Costes iniciales cargados desde JSON");
    }
  }

}
