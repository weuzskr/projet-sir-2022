package com.ca.formation.formationdemo1.controllers.api;


import com.ca.formation.formationdemo1.config.jwtconfig.JwtUtil;
import com.ca.formation.formationdemo1.dto.UtilisateurDto;
import com.ca.formation.formationdemo1.models.Utilisateur;
import com.ca.formation.formationdemo1.services.UtilisateurService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UtilisateurControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UtilisateurController utilisateurController;

    @MockBean
    UtilisateurService utillisateurService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/v2";
    }

    private String tokenRequest;


    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "ADMIN" })
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
        assertTrue(c);

    }
    @Test
    @DisplayName("Should return a status code of 200 when the user is created")
    public void registrationWhenUserIsCreatedThenReturnStatusCode200() throws javax.xml.bind.ValidationException {
        UtilisateurDto utilisateur = new UtilisateurDto();
        utilisateur.setUsername("test");
        utilisateur.setPassword("test");

        // when(utilisateurService.    registration(any(Utilisateur.class))).thenReturn(utilisateur);

        ResponseEntity<Utilisateur> responseEntity =
                utilisateurController.registration(utilisateur);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    @DisplayName("Should return 401 when the credentials are incorrect")
    public  void loginWhenCredentialsAreIncorrectThenReturn401() {
        UtilisateurDto utilisateurRequest = new UtilisateurDto("admin", "admin");
        when(utilisateurService.login(any(Utilisateur.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<Utilisateur> responseEntity =
                utilisateurController.login(utilisateurRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should return 200 when the credentials are correct")
    public  void loginWhenCredentialsAreCorrectThenReturn200() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin");
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setUsername(utilisateur.getUsername());
        utilisateurDto.setPassword(utilisateur.getPassword());
        utilisateurDto.setName(utilisateur.getName());

        when(utilisateurService.login(any(Utilisateur.class))).thenReturn(utilisateur);
        when(jwtUtil.generateAccesToken(any(Utilisateur.class))).thenReturn("token");
        when(jwtUtil.refreshAccesToken(any(Utilisateur.class))).thenReturn("refresh_token");


        ResponseEntity<Utilisateur> responseEntity = utilisateurController.login(utilisateurDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION));
        assertNotNull(responseEntity.getHeaders().get("refresh_token"));
    }


}