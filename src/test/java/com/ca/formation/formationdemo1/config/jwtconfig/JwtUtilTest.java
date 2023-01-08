package com.ca.formation.formationdemo1.config.jwtconfig;

import com.ca.formation.formationdemo1.models.*;
import com.ca.formation.formationdemo1.models.Utilisateur;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Test JwtUtil")
class JwtUtilTest {

    @Autowired
    public JwtUtil jwtUtil;

    @Test
    @DisplayName("Should return true when the token is valid")
    void validateWhenTokenIsValidThenReturnTrue() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername("test");
        utilisateur.setPassword("test");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        utilisateur.setAuthoritie(roles);
        String token = jwtUtil.generateAccesToken(utilisateur);

        boolean result = jwtUtil.validate(token);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when the token is invalid")
    void validateWhenTokenIsInvalidThenReturnFalse() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaXNzIjoiZm9ybWF0aW9uLmNhIiwiaWF0IjoxNTg1MjYwMjQ2LCJleHAiOjE1ODUzNDY2NDZ9.q-_7-8Q6_5-3_4-2_1-0_9-8_7-6_5-4_3-2_1-0";
        assertFalse(jwtUtil.validate(token));
    }

    @Test
    @DisplayName("Should return the claims when the token is valid")
    void getClaimsWhenTokenIsValid() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin", "admin", new HashSet<>());
        String token = jwtUtil.generateAccesToken(utilisateur);
        assertNotNull(jwtUtil.getClaims(token));
    }

    @Test
    @DisplayName("Should throw an exception when the token is invalid")
    void getClaimsWhenTokenIsInvalidThenThrowException() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6ImZvcm1hdGlvbi5jYSIsImlhdCI6MTU4NjQwMzY2NiwiZXhwIjoxNTg2NDkxMDY2fQ.X-_q-7_8-9_0-a_b-c_d-e_f-g_h-i_j-k_l-m_n-o_p-q_r-s_t-u_v-w_x-y_z";
        assertThrows(SignatureException.class, () -> jwtUtil.getClaims(token));
    }

    @Test
    @DisplayName("Should return a token with the expiration date set to 7 days")
    void refreshAccesTokenShouldReturnATokenWithTheExpirationDateSetTo7Days() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin", "admin", new HashSet<>());
        String token = jwtUtil.refreshAccesToken(utilisateur);
        assertNotNull(token);
        assertTrue(jwtUtil.validate(token));
    }


    @Test
    @DisplayName("Should return the username when the token is valid")
    void getUsernameWhenTokenIsValid() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        Utilisateur utilisateur = new Utilisateur("admin", "admin", "admin", roles);
        String token = jwtUtil.generateAccesToken(utilisateur);
        String username = jwtUtil.getUsername(token);
        assertEquals("admin", username);
    }

    @Test
    @DisplayName("Should throw an exception when the token is invalid")
    void getUsernameWhenTokenIsInvalidThenThrowException() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaXNzIjoiZm9ybWF0aW9uLmNhIiwiaWF0IjoxNTg1MjYwMjQ2LCJleHAiOjE1ODUzNDY2NDZ9.q-_7-8Q6_5-3-4-5-6-7-8-9-0";
        assertThrows(SignatureException.class, () -> jwtUtil.getUsername(token));
    }


    @Test
    @DisplayName("Should return false when the token is expired")
    void validateWhenTokenIsExpiredThenReturnFalse() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImZvcm1hdGlvbi5jYSIsImlhdCI6MTU4NjY0MjQwMCwiZXhwIjoxNTg2NjQ2MDAwfQ.X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3";
        assertFalse(jwtUtil.validate(token));
    }


    @Test
    @DisplayName("Should throw an exception when the user is invalid")
    void generateAccesTokenWhenUserIsInvalidThenThrowException() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTg2MjYwNjE5LCJleHAiOjE1ODYzNDcwMTl9.q-_7-8Q6_3-XqyvZK8k5Y3x7m6LH4n_2QrZKvRqXsQt0uWYy7D5k6-9g3BHp8l2e_4nPfTmCxrLcRtXoZKPWcA";
        assertThrows(SignatureException.class, () -> jwtUtil.getUsername(token));
    }

    @Test
    @DisplayName("Should return a token when the user is valid")
    void generateAccesTokenWhenUserIsValidThenReturnAToken() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername("test");
        utilisateur.setPassword("test");
        utilisateur.setName("test");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("READ"));
        utilisateur.setAuthoritie(roles);

        String token = jwtUtil.generateAccesToken(utilisateur);

        assertNotNull(token);
    }
}