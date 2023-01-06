package com.ca.formation.formationdemo1.services;


import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;

import java.util.List;

public interface PersonneService {

    List<Personne> getPersonnes();

    Personne getPersonne(Long id) throws ResourceNotFoundException;

    Personne updatePersonne(Long id, Personne personne) throws ResourceNotFoundException ;

    Personne addPersonne(Personne personne);

    void deletePersonne(Long id);

    List<Personne> getPersonneParNom(String nom);
    List<Personne> getPersonneNomAndPrenom2(String nom,String prenom);

    List<Personne> ageGreaterThan(int age);
    List<Personne> getPersonneParNomAndPrenom(String nom,String prenom);
}
