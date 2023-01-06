package com.ca.formation.formationdemo1.repositories;

import com.ca.formation.formationdemo1.models.Personne;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface PersonneRepository extends CrudRepository<Personne, Long> {

    List<Personne> findByNom(String nom);

    public List<Personne> findByNomAndPrenom(String nom, String prenom);

    @Query(value = "SELECT * FROM personne WHERE lastname= :nom AND firstname= :prenom ", nativeQuery = true)
    List<Personne>  findNomPrenom(String nom, String prenom);

    @Query(value = "SELECT p.prenom FROM Personne p WHERE p.nom= :nom AND p.prenom= :prenom ")
    List<Personne>  findNomPrenom2(String nom, String prenom);

    List<Personne> ageGreaterThan(int age);


}
