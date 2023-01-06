package com.ca.formation.formationdemo1;

import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;

@SpringBootApplication
public class ProjetSIRApplication {
  Logger logger = LoggerFactory.getLogger(ProjetSIRApplication.class);

  @Value("${mon.application.travail}")
  String monApplication;

  @Value("${mon.application.lieu}")
  String lieuFormation;

  @Autowired
  private Environment env;


  public static void main(String[] args) {
    SpringApplication.run(ProjetSIRApplication.class, args);
  }

  @Bean
  public void addBean() {
    logger.info(monApplication);
    logger.info(" Démarrage application Spring Boot");
  }

  @Bean
  public CommandLineRunner demo(PersonneRepository repository) {
    return (args -> {
      Personne personne1 = repository.save(new Personne("Lacroix", "Jean", 20));
      repository.save(new Personne("Beau", "Michel", 30));
      repository.save(new Personne("Abdel", "Moussa", 40));
      repository.delete(personne1);
      List<Personne> personneList = repository.findByNomAndPrenom("Abdel", "Moussa");
      personneList.stream().forEach(personne-> logger.info(String.valueOf(personne1)));
    });
  }

  @Bean
  public ClassLoaderTemplateResolver templateResolver() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates-new/");
    templateResolver.setSuffix(".html");
    templateResolver.setCheckExistence(true);
    return templateResolver;
  }

}
