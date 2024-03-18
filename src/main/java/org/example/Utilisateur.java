package org.example;

public class Utilisateur {
    String nom ;
    String prenom ;

    String email ;
    int id ;

    public Utilisateur(String nom, String prenom, String email, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }
}
