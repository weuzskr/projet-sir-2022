package com.ca.formation.formationdemo1.config;

import com.ca.formation.formationdemo1.config.jwtconfig.JwtFilter;
import com.ca.formation.formationdemo1.repositories.UtilisateurRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtFilter jwtFilter;

    @Value("${springdoc.api-docs.path}")
    private String apiDocPath;
    @Value("${springdoc.swagger-ui.path}")
    private String swaggerPath;
    //Logger logger= LoggerFactory.getLogger( SecurityConfig.class);

    public SecurityConfig(UtilisateurRepository utilisateurRepository, JwtFilter jwtFilter) {

        this.utilisateurRepository = utilisateurRepository;
        this.jwtFilter = jwtFilter;
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(username -> utilisateurRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("utilisateur: %s,  pas trouvé", username)
                        )
                ));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // activer les cors et desactiver les CSRF
        http = http.cors().and().csrf().disable();

        // http.csrf().disable(); // Complian

        // Mettre la getion de la session a un sans etat
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        ;

        // mettre pas autoriser si on a une exception
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> {
                           // logger.info("Demande pas autoriser -");
                            //logger.info(authException.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                        })
                )
                .and();

        // mettre les permissions sur nos resources
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers(apiDocPath+"/**").permitAll()
                .antMatchers(swaggerPath+"/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/api/v2/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/documentation").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // mettre le type d'encodage du mot de passe
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Exposer le bean du authentication manager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}