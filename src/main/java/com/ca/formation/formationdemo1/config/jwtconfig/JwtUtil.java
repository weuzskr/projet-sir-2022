package com.ca.formation.formationdemo1.config.jwtconfig;


import com.ca.formation.formationdemo1.models.Utilisateur;
import io.jsonwebtoken.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private    String jwtSecret;
    Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // generer JWT

    public String generateAccesToken(Utilisateur utilisateur){
        Claims claims = Jwts.claims().setSubject(utilisateur.getUsername());
        claims.put("scopes", utilisateur.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(utilisateur.getName()+","+utilisateur.getUsername())
                .setIssuer("formation.ca")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 *1000))// 1 jour
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String refreshAccesToken(Utilisateur utilisateur){
        return Jwts.builder()
                .setSubject(utilisateur.getName()+","+utilisateur.getUsername())
                .setIssuer("formation.ca")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 *1000))// 1 semaine
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    // Recuperer le username
    public String getUsername(String token){
        Claims claims = getClaims(token);
       return claims.getSubject().split(",")[1];
    }

    // Recuperer les claims
    Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Recuperer la Date d'expiration
    public Date getExpirationDate(String token){
       return getClaims(token).getExpiration();
    }


    // Verifier la validité du token
    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
           String mes=String.format("Invalide Signature Jwt - %s",ex.getMessage());
           logger.info(mes);
        } catch (ExpiredJwtException ex){
            String mes=String.format("Expiration du Jwt - %s",ex.getMessage());
            logger.info(mes);

        }catch (UnsupportedJwtException ex){
            String mes=String.format("Token jwt non supporté - %s",ex.getMessage());
            logger.info(mes);

        }catch (IllegalArgumentException ex){
            String mes=String.format("Invalide claims Jwt  - %s",ex.getMessage());
            logger.info(mes);

        }catch (MalformedJwtException ex){
            String mes=String.format("jwt mal formatter - %s",ex.getMessage());
            logger.info(mes);

        }

        return false;
    }
}
