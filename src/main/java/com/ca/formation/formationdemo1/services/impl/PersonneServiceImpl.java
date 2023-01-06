package com.ca.formation.formationdemo1.services.impl;


import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import com.ca.formation.formationdemo1.services.PersonneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class PersonneServiceImpl implements PersonneService {


    private final PersonneRepository personneRepository;

    public PersonneServiceImpl( PersonneRepository personneRepository) {

        this.personneRepository = personneRepository;
    }

    @Override
    public List getPersonnes() {
        return (List) personneRepository.findAll();
    }

    @Override
    public Personne getPersonne(Long id) throws ResourceNotFoundException {
        return personneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Personne non trouvé "));
    }

    @Override
    public Personne updatePersonne(Long id, Personne personneRequest) throws ResourceNotFoundException  {
        if (personneRequest == null) {
            throw new IllegalArgumentException("Personne request cannot be null");
        }
        Personne personne = personneRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Personne not found with id: " + id));

        if (!id.equals(personne.getId())) {
            throw new ResourceNotFoundException("Id in path and object do not match");
        }



        personne.setAge(personneRequest.getAge());
        personne.setPrenom(personneRequest.getPrenom());
        personne.setNom(personneRequest.getNom());
        return personneRepository.save(personne);
    }


        @Override
        public Personne addPersonne(Personne personne) {
            return personneRepository.save(personne);
        }


    @Override
    public void deletePersonne(Long id) {
            personneRepository.deleteById(id);
    }

    @Override
    public List<Personne> getPersonneParNom(String nom) {
        return personneRepository.findByNom(nom);
    }
    @Override
    public List<Personne> getPersonneNomAndPrenom2(String nom, String prenom) {
        return  personneRepository.findNomPrenom2(nom,prenom);
    }
    @Override
    public List<Personne> getPersonneParNomAndPrenom(String nom,String prenom) {
        return  personneRepository.findByNomAndPrenom(nom,prenom);
    }
    @Override
    public List<Personne> ageGreaterThan(int age) {
        return personneRepository.ageGreaterThan(age);
    }

    public List<Personne> getPersonneNomAndPrenom(String nom,String prenom) {
        return  personneRepository.findByNomAndPrenom(nom,prenom);
    }
}
