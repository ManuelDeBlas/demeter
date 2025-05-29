package es.mde.config;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.mde.entidades.Coste;
import es.mde.repositorios.CosteDAO;

@Component
public class CostesInitializer implements CommandLineRunner {

    private final CosteDAO costeDAO;
    private final ObjectMapper objectMapper;

    public CostesInitializer(CosteDAO costeDAO, ObjectMapper objectMapper) {
        this.costeDAO = costeDAO;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (costeDAO.count() == 0) {
            InputStream inputStream = getClass().getResourceAsStream("/config/costes.json");
            List<Coste> costes = Arrays.asList(objectMapper.readValue(inputStream, Coste[].class));
            costeDAO.saveAll(costes);
            System.out.println("Costes iniciales cargados desde JSON");
        }
    }
}
