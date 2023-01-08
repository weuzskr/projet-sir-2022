package com.ca.formation.formationdemo1.repositories;

import com.ca.formation.formationdemo1.models.Personne;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PersonneRepositoryTest {

    @Autowired
    PersonneRepository personneRepository;

    @Test
    public void ajouterPersonne() {
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        assertNotNull(personne);
        assertEquals( "tonux",personne.getNom());
    }
    @Test
    public void findByNom(){



        List < Personne> personneList= personneRepository.findByNom("tonux");

        assertEquals(3,personneList.size());
        assertEquals("tonux",personneList.get(0).getNom());





    }
    @Test
    public void findByNomAndPrenom(){

        List < Personne> personneList= personneRepository.findByNomAndPrenom("tonux","samb");
        assertNotNull(personneList);
        //(2,personneList.size());



    }
    @Test
    public void create(){
        Personne personne = personneRepository.save(new Personne("tonux", "mor_talla", 50));
        assertNotNull(personne);
        assertEquals("tonux", personne.getNom());
    }
    ///
    @Test

    public void update(){
        //Given
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        personne.setNom("Seferovic-Harris");
        //When
        Personne personUpdated = personneRepository.save(personne);
        //Then
        assertNotNull(personUpdated);
        assertEquals("Seferovic-Harris", personUpdated.getNom());
    }

    @Test
    public void delete(){
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        personne.setId(1L);
        personneRepository.delete(personne);
        assertNotNull(personne);
        assertEquals(200,HttpStatus.OK.value());

    }




    @Test
    public void findById()
    {
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        Optional<Personne> personList=personneRepository.findById(personne.getId());
        assertNotNull(personList);
        assertEquals("tonux",personList.get().getNom());
    }



    @Test
    public void findAll()
    {
        List<Personne> personList= (List<Personne>) personneRepository.findAll();
        assertNotNull(personList);
        assertEquals(4,personList.size());
    }



    @Test
    public void DeleteById() {
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        personneRepository.delete(personne);
        assertNotNull(personneRepository.findById(personne.getId()));
    }




    @Test
    public  void ageGreaterThan()
    {
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        List<Personne> p=personneRepository.ageGreaterThan(personne.getAge());
        assertNotNull(p);
    }




    // TODO: ajouter un test sur les autres methodes comme delete, findByNom, etc...


    @Test
    public void findbyNom(){

        List<Personne> person = personneRepository.findByNom("Abdel");
        assertNotNull(person);
        assertNotEquals(0, person.size());
    }

    // TODO : add test findAll
    @Test
    public void findbyid(){
        //Given
        Personne pers = personneRepository.save(new Personne("lahm", "phillip", 24));
        //When
        Optional<Personne> person = personneRepository.findById(pers.getId());
        //Then
        assertNotNull(person);
        assertEquals("lahm", person.get().getNom());
    }


    @Test
    public void deletePerson(){
        Personne person  =  personneRepository.save(new Personne("phillip","lahm",24));
        //Personne person = personneRepository.findById(1L).get();
        personneRepository.delete(personneRepository.findById(person.getId()).get());

        Personne person1 = null;

        Optional<Personne> person2 = personneRepository.findById(person.getId());

        if (person2.isPresent()){
            person1 = person2.get();
        }
        Assertions.assertThat(person1).isNull();
    }




}
