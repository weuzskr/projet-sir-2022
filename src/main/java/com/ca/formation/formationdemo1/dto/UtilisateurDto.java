package com.ca.formation.formationdemo1.dto;



public class UtilisateurDto  {



    private boolean enabled = true;
    private String username;
    private String password;
    private String name;



    public UtilisateurDto() {
    }

    public UtilisateurDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;

    }

    public UtilisateurDto(String username, String password) {

            this.username = username;
            this.password = password;

    }



    public boolean isEnabled() {

        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
