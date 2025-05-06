package es.mde.demeter;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("${es.mde.demeter.repositorios}")
@ComponentScan("${es.mde.demeter.rest}")
@PropertySource({ "classpath:config/rest.properties" })
public class ConfiguracionPorJava {

    @Value("${es.mde.demeter.entidades}")
    private String entidades;
    
    @Value("${es.mde.demeter.jpa-resources}")
    private String[] xmlsJpa;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env,
            JpaVendorAdapter vendorAdapter) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan(entidades);
        em.setMappingResources(xmlsJpa);

        Properties jpaProperties = new Properties();
        Arrays.asList("dialect", "show_sql", "hbm2ddl.auto", "enable_lazy_load_no_trans")
                .stream().map(s -> "hibernate." + s)
                .map(p -> new AbstractMap.SimpleEntry<String, String>(p, env.getProperty(p)))
                .filter(e -> e.getValue() != null).forEach(e -> jpaProperties.put(e.getKey(), e.getValue()));
        em.setJpaProperties(jpaProperties);

        return em;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory emf) {
        System.err.println("--- LAS ENTIDADES MAPEADAS SON ---");
        emf.getMetamodel().getEntities().forEach(System.err::println);
        System.err.println("----------------------------------");

        return emf.createEntityManager();
    }

}

