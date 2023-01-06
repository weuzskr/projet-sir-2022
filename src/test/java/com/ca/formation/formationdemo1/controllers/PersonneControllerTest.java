package com.ca.formation.formationdemo1.controllers;

import com.ca.formation.formationdemo1.dto.UtilisateurDto;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.services.PersonneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonneControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  @MockBean
  PersonneService personneService;

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  private String getRootUrl() {
    return "http://localhost:" + port + "/api/v2";
  }

  private String tokenRequest;

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void helloTest() {

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/api/v2/personnes/bye",
            HttpMethod.GET, entity, String.class);

    assertNotNull(response.getBody(), "Bye bye");
  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void getPersonnes() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void getPersonnesPasAutoriser() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());

  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void getPersonnesVrai() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/v2/personnes",
            HttpMethod.GET, entity, String.class);

    assertNotNull(response);

  }

  @Test
  public void getPersonne_() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<Personne> responseEntity = restTemplate.exchange(getRootUrl() + "/personnes/3", HttpMethod.GET,
            entity, Personne.class);

    assertNotNull(responseEntity);
    // assertEquals(personne.getNom(), "Abdel");

  }

  @Test
  public void ajouterPersonne_() throws Exception {
    Personne personne = new Personne("tonux", "samb", 40);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<Personne> entity = new HttpEntity<Personne>(null, headers);
    ResponseEntity<Personne> responseEntity = restTemplate
            .exchange(getRootUrl() + "/personnes", HttpMethod.POST, entity, Personne.class, personne);
    assertNotNull(responseEntity);
  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void getPersonne() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/2")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);
    assertNotNull(contentAsString);

  }

  @Before
  public void login() throws Exception {
    String body = "{\n" +
            "    \"username\": \"clara@formation.ca\",\n" +
            "    \"password\": \"Passer@123\"\n" +
            "}";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v2/auth/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String token = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    tokenRequest = token;
  }


  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"ADMIN"})
  public void registration() throws Exception {
    String body = "{\n" +
            "    \"username\": \"Tal@formation.ca\",\n" +
            "    \"password\": \"Passer@123\"\n" +
            "    \"name\": \"Tal\"\n" +
            "    \"authoritie\": \"READ\"\n" +
            "}";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v2/auth/registration")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = null;

    mvcResult = mockMvc.perform(requestBuilder).andReturn();

    String token = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    tokenRequest = token;
    System.out.println(body);
    boolean c = true;
    assertTrue(c); // Fest assertio
  }










  @Test
  public void ajouterPerson_() throws Exception {
    Personne personne = new Personne("tonux", "samb", 40);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
    HttpEntity<Personne> entity = new HttpEntity<Personne>(null, headers);
    ResponseEntity<Personne> responseEntity = restTemplate
            .exchange("http://localhost:" + port + "/ajouterPersonne", HttpMethod.POST, entity, Personne.class, personne);
    assertNotNull(responseEntity);
  }


  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"ADMIN"})
  public void samaTest() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/bye")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @ParameterizedTest
  @CsvSource({"personnes", "personnes/hello"})
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  void samaTestBye(String input) throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/" + input)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }


  @ParameterizedTest
  @CsvSource({"age?nom=40", "bbb?nom=Abdel&prenom=Moussa", "ccc?nom=Abdel&prenom=Moussa", "aaa?nom=Abdel&prenom=Moussa", "search?nom=Abdel", "2"})
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  void ageGreaterThan(String input) throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/" + input)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);
    assertNotNull(contentAsString);

  }



  /*@Test
  @WithMockUser(username = "clara@formation.ca", password = "Passer@123", authorities = {"ADMIN"})
  public void createUtilisateurAPI() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v2/auth/registration")
                    .content(asJsonString(new UtilisateurDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  } */

 /* @Test
  @WithMockUser(username = "clara@formation.ca", password = "Passer@123", authorities = {"ADMIN"})
  public void addPersonneAPI() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v2/personnes")
                    .content(asJsonString(new Personne()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }*/




  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void getPersonneNomAndPrenom() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/aaa?nom=Abdel&prenom=Moussa")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);
    assertNotNull(contentAsString);

  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void getPersonneNomAndPrenom2() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/bbb?nom=Abdel&prenom=Moussa")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);
    assertNotNull(contentAsString);

  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void getPersonneParNomAndPrenom() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/ccc?nom=Abdel&prenom=Moussa")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);
    assertNotNull(contentAsString);

  }

  @Test
  @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = {"READ"})
  public void ageGreaterThan() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v2/personnes/age?nom=40")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);
    assertNotNull(contentAsString);

  }
}