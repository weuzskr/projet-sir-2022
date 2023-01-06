package com.ca.formation.formationdemo1.services;

import com.ca.formation.formationdemo1.models.Utilisateur;

import javax.xml.bind.ValidationException;

public interface UtilisateurService {

    Utilisateur registration(Utilisateur utilisateurRequest) throws ValidationException;

    Utilisateur login(Utilisateur utilisateurRequest);

}
