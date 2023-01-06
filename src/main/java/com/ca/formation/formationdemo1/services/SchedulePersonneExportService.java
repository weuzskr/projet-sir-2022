package com.ca.formation.formationdemo1.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
@Component
@EnableAsync
public class SchedulePersonneExportService {

    //@Async


    //@Scheduled(fixedDelay = 10000)
    public void envoyerListePersonnes(){
        Logger logger = LoggerFactory.getLogger(SchedulePersonneExportService.class);

        logger.info(" Envoyer la liste des personne - temps écouler: {} secondes",+System.currentTimeMillis()/1000);
    }
}
