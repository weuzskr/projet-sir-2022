package com.ca.formation.formationdemo1.controllers;

import com.ca.formation.formationdemo1.dto.PersonneDto;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonneController {

    private final PersonneRepository repository;

    public PersonneController(PersonneRepository repository) {
        this.repository = repository;

    }

    @GetMapping
    public String getPersonnes(Model model){
        model.addAttribute("personnes", repository.findAll());
        return "index";
    }

    @GetMapping("/nouveau")
    public String nouveauPersonne(PersonneDto personne){

        return "nouveau";
    }

    @PostMapping("/ajouterPersonne")
    public String ajouterPersonne(PersonneDto personDTO, Model model){
        Personne person = new Personne();
        // Mapper les champs du DTO sur l'entité persistante
        person.setNom(personDTO.getNom());
        person.setAge(personDTO.getAge());
        person.setPrenom(personDTO.getPrenom());

        // Enregistrer l'entité en base de données
        repository.save(person);
        return "redirect:/";
    }



}
