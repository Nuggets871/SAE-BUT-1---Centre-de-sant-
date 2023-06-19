/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;


/**
 *
 * @author chris
 */
import java.util.ArrayList;
import java.util.List;

public class Sommet {
    private final String nom;         // Nom du sommet
    private final String type;        // Type du sommet
    private final List<Lien> liens;   // Liste des liens associés au sommet

    public Sommet(String nom, String type) {
        this.nom = nom;
        this.type = type;
        this.liens = new ArrayList<>();  // Initialisation de la liste des liens
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public void ajouterLien(Lien lien) {
        liens.add(lien);  // Ajout d'un lien à la liste des liens
    }

    public List<Lien> getLiens() {
        return liens;  // Renvoie la liste des liens
    }
}
