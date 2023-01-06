package com.ca.formation.formationdemo1.services;

import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.models.Utilisateur;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import com.ca.formation.formationdemo1.repositories.UtilisateurRepository;
import com.ca.formation.formationdemo1.services.impl.PersonneServiceImpl;
import com.ca.formation.formationdemo1.services.impl.UtilisateurServiceImpl;
import io.micrometer.core.instrument.config.validate.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PersonneServiceImplTest {

    @Mock
    PersonneRepository personneRepository;
    Personne personne;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @InjectMocks
    private PersonneServiceImpl personneServiceImpl;
    @Mock
    UtilisateurRepository utilisateurRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    // Test de la méthode login

    @Test
    public void testLogin() {
        // Préparer les données de test
        String username = "username";
        String password = "password";
        Utilisateur utilisateurRequest = new Utilisateur(username, password);

        // Mocker le comportement du repository
        Utilisateur utilisateurMock = new Utilisateur(username, password);
        when(utilisateurRepository.findByUsername(username)).thenReturn(Optional.of(utilisateurMock));

        // Mocker le comportement de l'authentication manager
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(utilisateurMock);

        // Appeler la méthode à tester

        Utilisateur utilisateurResponse = utilisateurServiceImpl.login(utilisateurRequest);

        // Vérifier que la méthode retourne bien l'utilisateur attendu
        assertEquals(utilisateurMock, utilisateurResponse);
    }

    // Test de la méthode registration
    @Test
    public void testRegistration() throws ValidationException, javax.xml.bind.ValidationException {
        // Préparer les données de test
        String username = "username";
        String password = "password";
        Utilisateur utilisateurRequest = new Utilisateur(username, password);

        // Mocker le comportement du repository
        Utilisateur utilisateurMock = new Utilisateur(username, password);
        when(utilisateurRepository.save(utilisateurRequest)).thenReturn(utilisateurMock);

        // Mocker le comportement du password encoder
        String encodedPassword = "encoded_password";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Appeler la méthode à tester
        Utilisateur utilisateurResponse = utilisateurServiceImpl.registration(utilisateurRequest);

        // Vérifier que la méthode retourne bien l'utilisateur attendu
        assertEquals(utilisateurMock, utilisateurResponse);
    }

    @Test
    public void ajouterPersonne() {
        Personne personne = new Personne("tonux", "samb", 50);
        personne.setId(1L);
        when(personneRepository.save(any())).thenReturn(personne);

        Personne personneResponse = personneServiceImpl.addPersonne(new Personne("tonux", "samb", 50));

        assertNotNull(personneResponse);

        verify(personneRepository, atLeastOnce()).save(any());
    }
    @Test
    public void addPersonne() {
        //Given
        Personne personneResponse = new Personne("tonux", "samb", 50);
        personneResponse.setId(1L);
        when(personneRepository.save(any())).thenReturn(personneResponse);

        //When
        Personne personAded =personneServiceImpl.addPersonne(personneResponse);

        //Then
        assertEquals("tonux",personAded.getNom());
        assertEquals(1,personAded.getId());
        verify(personneRepository,atLeastOnce()).save(any());
    }
    @Test
    public void deletePersonne(){
        Personne person = new Personne("tonux", "samb", 50);
        person.setId(1L);
        doNothing().when(personneRepository).delete(any());

        //When
        personneServiceImpl.deletePersonne(person.getId());

        //Then
        verify(personneRepository, atLeastOnce()).deleteById(1L);
        verifyNoMoreInteractions(personneRepository);
    }
    @Test
    public void getPersonneParNom(){
        List<Personne> list = new ArrayList<Personne>();
        list.add(new Personne("toloi", "samb", 50));
        list.add(new Personne("daichi", "kamada", 50));
        list.add(new Personne("wataru", "endo", 50));
        when(personneRepository.findByNom("daichi")).thenReturn(list);
        //When
        List<Personne> personList = personneServiceImpl.getPersonneParNom("daichi");
        //Then
        assertEquals(3, personList.size());
        assertEquals("kamada",
                personList.get(1).getPrenom());

    }



    //Test de la méthode getPersonne
    @Test
    public void getPersonne() throws ResourceNotFoundException {
        Personne personne = new Personne("tonux", "samb",50);
        when(personneRepository.findById(1L)).thenReturn(Optional.of(personne));

        Personne personneResponse = personneServiceImpl.getPersonne(1L);

        assertNotNull(personneResponse);
        assertEquals(personne.getNom(), personneResponse.getNom());
        assertEquals(personne.getPrenom(), personneResponse.getPrenom());
        assertEquals(personne.getAge(), personneResponse.getAge());
    }
    //Test de la méthode updatepersone
    @Test
    public void updatePersonne() throws ResourceNotFoundException {
        Personne personne = new Personne("tonux", "samb", 50);
        personne.setId(1L);
        when(personneRepository.findById(anyLong())).thenReturn(Optional.of(personne));
        when(personneRepository.save(any())).thenReturn(personne);

        Personne personneResponse = personneServiceImpl.updatePersonne(1L, new Personne("tonux", "samb", 55));

        assertNotNull(personneResponse);
        assertEquals(1L, personneResponse.getId().longValue());
        assertEquals(55, personneResponse.getAge());

        verify(personneRepository, atLeastOnce()).findById(anyLong());
        verify(personneRepository, atLeastOnce()).save(any());
    }

    //Test de la méthode getPersonnes

    @Test
    public void getAllPersonnes() {
        // Création de la liste de personnes attendue
        List<Personne> personnes = Arrays.asList(
                new Personne("tonux", "samb", 50),
                new Personne("nagatomo", "yuto", 13),
                new Personne("sakai", "gotuku", 13),
                new Personne("asano", "asano", 16),
                new Personne("denis", "zakaria", 14)
        );

        // Configuration du comportement du mock du repository de personne
        when(personneRepository.findAll()).thenReturn(personnes);

// Appel de la méthode à tester
        List<Personne> personnesResponse = personneServiceImpl.getPersonnes();

        // Vérification du résultat
        assertNotNull(personnesResponse);
        assertEquals(personnes, personnesResponse);
    }






    // Test de la méthode registration
    @Before
    public void setUp() throws Exception {
        personne = new Personne("tonux", "samb", 50);
        personne.setId(1L);
        when(personneRepository.save(any())).thenReturn(personne);

    }


    // TODO: ajouter les autres tests sur methodes



    @Test
    public void deletePerson() {
        when(personneRepository.findById(personne.getId())).thenReturn(Optional.of(personne));

        personneServiceImpl.deletePersonne(personne.getId());
        verify(personneRepository).deleteById(personne.getId());
    }
    @Test
    public void getPerson() throws ResourceNotFoundException {

        when(personneRepository.findById(personne.getId())).thenReturn(Optional.of(personne));

        Personne expected = personneServiceImpl.getPersonne(personne.getId());


        assertEquals("tonux", expected.getNom());
        assertEquals(1, expected.getId());
        assertThat(expected).isSameAs(personne);
        verify(personneRepository).findById(personne.getId());
    }
    @Test
    public void getAllPersons() {
        //Given
        List<Personne> list = new ArrayList<Personne>();
        list.add(new Personne("tonux","samb",50));
        list.add(new Personne("dendryk","boyata",13));
        list.add(new Personne("naser","chadli",14));
        when(personneRepository.findAll()).thenReturn(list);
        //When
        List<Personne> personList = personneServiceImpl.getPersonnes();
        //Then
        assertEquals(3, personList.size());
        verify(personneRepository, atLeastOnce()).findAll();
    }



    @Test
    public void getPersonneParNomAndPrenom() {
        String nom="sankhare";
        String prenom="ousmane";
        //Given
        List<Personne> list = new ArrayList<Personne>();
        list.add(new Personne("tonux","samb",50));
        list.add(new Personne("robin","gosens",13));
        list.add(new Personne("boubacar","soumare",14));
        when(personneRepository.findAll()).thenReturn(list);
        //When
        List<Personne> personList = personneServiceImpl.getPersonneParNomAndPrenom("tonux","samb");
        //Then
        assertEquals(0, personList.size());
        verify(personneRepository, atLeastOnce()).findByNomAndPrenom("tonux","samb");
    }

    @Test
    public void getPersonneNomAndPrenom() {
        String nom="ousmane";
        String prenom="sankhare";
        //Given
        List<Personne> list = new ArrayList<Personne>();
        list.add(new Personne("tonux","samb",50));
        list.add(new Personne("wilfried","ndidi",23));
        list.add(new Personne("sankhare","ousmane",14));
        when(personneRepository.findAll()).thenReturn(list);
        //When
        List<Personne> personList = personneServiceImpl.getPersonneNomAndPrenom("tonux","samb");
        //Then
        assertEquals(0, personList.size());
        verify(personneRepository, atLeastOnce()).findByNomAndPrenom("tonux","samb");
    }

    @Test
    public void getPersonneNomAndPrenom2() {
        String nom="diagne";
        String prenom="massaer";
        //Given
        List<Personne> list = new ArrayList<Personne>();
        list.add(new Personne("tonux","samb",50));
        list.add(new Personne("wilfried","ndidi",13));
        list.add(new Personne("diagne","massear",14));
        when(personneRepository.findAll()).thenReturn(list);
        //When
        List<Personne> personList = personneServiceImpl.getPersonneNomAndPrenom2("tonux","samb");
        //Then
        assertEquals(0, personList.size());
        verify(personneRepository, atLeastOnce()).findNomPrenom2("tonux","samb");
    }

    @Test
    public void ageGreaterThan() {
        //Given
        List<Personne> list = new ArrayList<Personne>();
        list.add(new Personne("tonux","samb",50));
        list.add(new Personne("wilfried","ndidi",13));
        list.add(new Personne("earling","halland",14));
        when(personneRepository.findAll()).thenReturn(list);
        //When
        List<Personne> personList = personneServiceImpl.ageGreaterThan(40);
        //Then
        assertEquals(0, personList.size());
        verify(personneRepository, atLeastOnce()).ageGreaterThan(40);
    }


}