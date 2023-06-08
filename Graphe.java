/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae; 

/**
 *
 * @author chris
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Graphe {
    private HashMap<String, Sommet> sommets; 
    private List<Lien> liens;

    public Graphe() {
        sommets = new HashMap<>();
        liens = new ArrayList<>();
    }
    public HashMap<String, Sommet> getSommets() {
        return sommets;
    }

    public List<Lien> getLiens() {
        return liens;
    }
    public void chargerGraphe(String nomFichier){           // Permet de charger le graphe en mémoire ainsi que leur caractéristiques 
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = br.readLine()) != null) { // récupère les lignes du fichier csv      -----    1ere partie - ajout des centres(nom,type)
                String[] parts = line.split(";"); // découpe les lignes en morceaux à chaque point virgule
                String nom = parts[0]; // récupère le nom du centre
                String type = parts[1]; // récupère le type du centre
                ajoutCentre(nom, type); // ajoute le centre

                for (int i = 2; i < parts.length; i++) { // 2eme partie - ajout des liens entre les centres ainsi que leur caractéristiques
                    if (!parts[i].equals("0")) { // regarde si ce n'est pas un 0
                        String val1 = parts[i]; // récupère la valeur de la case afin de la comparer par la suite
                        String[] valeurs = parts[i].split(","); // segmente en trois parties afin de récupéré les cara du liens
                        double fiabilite = Double.parseDouble(valeurs[0]); // la fiabilité est la première valeur 
                        double distance = Double.parseDouble(valeurs[1]); // la distance la 2eme
                        double dureeMoyenne = Double.parseDouble(valeurs[2]); // et enfin la durée moyenne

                        try(BufferedReader br2 = new BufferedReader(new FileReader(nomFichier))){ // on parcourt une deuxieme fois le fichier csv afin de trouver avec qui le sommet précédent est lié
                            String line2;
                            while ((line2=br2.readLine()) != null){
                                String[] parts2 = line2.split(";");
                                if (!parts2[i].equals("0")){
                                    String val2 = parts2[i]; // on regarde directement sur la même colonne 

                                    String nom2 = parts2[0]; // on verifie que ce n'est pas un 0
                                    if (val1.equals(val2)&& !nom.equals(nom2)){ // on verifie si les deux valeurs sont les mêmes, et qu'il s'agit bien de 2 centres different

                                        ajouterLien(nom, nom2, fiabilite, distance, dureeMoyenne); // on peut alors les ajouter
                                    }   
                                }
                                
                            }  
                        }                            
                    }                    
                }                                      
            }
        } catch (IOException e) {
        }
        
        
    }

    public void ajoutCentre(String nom, String type) { // permet d'ajouter un centre
        Sommet sommet = new Sommet(nom, type);
        sommets.put(nom, sommet);
    }

    public void ajouterLien(String nomSommet1, String nomSommet2, double fiabilite, double distance, double dureeMoyenne) { // permet de lier deux centre ainsi que leur caractéristiques
        Sommet sommet1 = sommets.get(nomSommet1);
        Sommet sommet2 = sommets.get(nomSommet2);

        if (sommet1 != null && sommet2 != null) {
            Lien lien = new Lien(sommet1, sommet2, fiabilite, distance, dureeMoyenne);
            liens.add(lien);
        }
    }

    public int getNombreCentres() {  // renvoie le nombre de centre dans le graphe
        return sommets.size();
    }
    
    public int getNombreLiens() { // renvoie le nombre de lien total, on ajoute un 1 car l'indice d'une liste commence à 0, or on cherche le nombre d'éléments
        return liens.size()+1;
    }

    public void afficherInformationsCentres() { // affiche les informations de tous les centres du graphe, il est possible que l'ordre soit inhabituel car on utilise une HashMap et non une LinkedHashMap 
        for (Sommet sommet : sommets.values()) {
            System.out.println(sommet.getNom() + " : " + sommet.getType());
        }
    }


    public void afficherInformationsLiens() {
    if (liens.isEmpty()) {
        System.out.println("Aucun lien n'a été trouvé.");
    } else {
        for (Lien lien : liens) {
            System.out.println("Lien entre " + lien.getSommet1().getNom() + " et " + lien.getSommet2().getNom());
            System.out.println("Fiabilité : " + lien.getFiabilite());
            System.out.println("Distance : " + lien.getDistance() + " km");
            System.out.println("Durée moyenne : " + lien.getDureeMoyenne() + " minutes");
            System.out.println();
        }
    }
}



    public void afficherInformationsCentrePrecis(String nomCentre) { // affiche les information d'un centre mis en paramètre
        Sommet sommet = sommets.get(nomCentre);
        if (sommet != null) {
            System.out.println("Informations pour le centre " + sommet.getNom() + " :");
            System.out.println("Type : " + sommet.getType());
        } else {
            System.out.println("Le centre " + nomCentre + " n'existe pas dans le graphe.");
        }
    }
    
    public boolean estLie(String nomCentre1, String nomCentre2) {
    Sommet sommet1 = sommets.get(nomCentre1);
    Sommet sommet2 = sommets.get(nomCentre2);
    
    if (sommet1 == null || sommet2 == null) {
        return false; // Les centres n'existent pas dans le graphe
    }
    for (Lien lien : liens) {
        if ((lien.getSommet1().getNom().equals(sommet1.getNom()) && lien.getSommet2().getNom().equals(sommet2.getNom()))
            || (lien.getSommet2().getNom().equals(sommet1.getNom()) && lien.getSommet1().getNom().equals(sommet2.getNom()))) {
            return true; // Les centres sont reliés par ce lien
        }
    }
    
    return false; // Les centres ne sont pas reliés
    }

    
    public boolean existeCentre(String nomCentre) {
        return sommets.containsKey(nomCentre);
    }
    public Lien getLienEntreCentres(String nomCentre1, String nomCentre2) {
    Sommet sommet1 = sommets.get(nomCentre1);
    Sommet sommet2 = sommets.get(nomCentre2);

    if (sommet1 == null || sommet2 == null) {
        return null; // L'un ou les deux centres n'existent pas dans le graphe
    }

    for (Lien lien : liens) {
        if ((lien.getSommet1().getNom().equals(sommet1.getNom()) && lien.getSommet2().getNom().equals(sommet2.getNom()))
                || (lien.getSommet1().getNom().equals(sommet2.getNom()) && lien.getSommet2().getNom().equals(sommet1.getNom()))) {
            return lien; // Retourne l'objet Lien correspondant
        }
    }

    return null; // Les centres ne sont pas reliés
    }
    
    public void afficherSommetParType(String type) {
    int count = 0;
    
    for (Sommet sommet : sommets.values()) {
        if (sommet.getType().equalsIgnoreCase(type)) {
            System.out.println(sommet.getNom());
            count++;
        }
    }
    
    System.out.println("Nombre de sommets de type " + type + " : " + count);
    }
    
    public List<Sommet> getVoisinDirect(String nomSommet) {
    List<Sommet> voisins = new ArrayList<>();
    
    Sommet sommet = sommets.get(nomSommet);
    if (sommet != null) {
        for (Lien lien : liens) {
            if (lien.getSommet1().equals(sommet)) {
                voisins.add(lien.getSommet2());
            } else if (lien.getSommet2().equals(sommet)) {
                voisins.add(lien.getSommet1());
            }
        }
    }
    
    return voisins;
}

    public void modifierLien(String nomCentre1, String nomCentre2, int fiabilite, int distance, int duree) {
    Sommet sommet1 = sommets.get(nomCentre1);
    Sommet sommet2 = sommets.get(nomCentre2);

    if (sommet1 == null || sommet2 == null) {
        System.out.println("Les centres spécifiés n'existent pas.");
        return;
    }

    boolean lienTrouve = false;

    for (Lien lien : liens) {
        if ((lien.getSommet1().getNom().equals(sommet1.getNom()) && lien.getSommet2().getNom().equals(sommet2.getNom()))
                || (lien.getSommet1().getNom().equals(sommet2.getNom()) && lien.getSommet2().getNom().equals(sommet1.getNom()))) {

            lien.setFiabilite(fiabilite);
            lien.setDistance(distance);
            lien.setDureeMoyenne(duree);

            System.out.println("Les informations du lien entre les centres " + nomCentre1 + " et " + nomCentre2 + " ont été modifiées.");
            lienTrouve = true;

            break;
        }
    }

    if (!lienTrouve) {
        System.out.println("Aucun lien trouvé entre les centres " + nomCentre1 + " et " + nomCentre2 + ".");
    }
}

    
    public List<Sommet> listerVoisinsDirectsDeType(String nomSommet, String typeVoisin) {
    Sommet sommet = sommets.get(nomSommet);

    if (sommet == null) {
        System.out.println("Le sommet spécifié n'existe pas.");
        return null;
    }

    List<Sommet> voisins = new ArrayList<>();

    for (Lien lien : liens) {
        if (lien.getSommet1().equals(sommet) && lien.getSommet2().getType().equals(typeVoisin)) {
            voisins.add(lien.getSommet2());
        } else if (lien.getSommet2().equals(sommet) && lien.getSommet1().getType().equals(typeVoisin)) {
            voisins.add(lien.getSommet1());
        }
    }

    return voisins;
}
    public boolean sontA2Distance(String nomSommet1, String nomSommet2) {
    Sommet sommet1 = sommets.get(nomSommet1);
    Sommet sommet2 = sommets.get(nomSommet2);

    if (sommet1 == null || sommet2 == null) {
        return false; // Les sommets n'existent pas dans le graphe
    }

    for (Lien lien : liens) {  // pour tous les liens
        if (lien.getSommet1().equals(sommet1)) { // si dans la liste de lien le premier sommet (un lien est constitué de 2 sommets, on regarde ici le premier) correspond au premier sommet donné par l'utilisateur
            Sommet voisin1 = lien.getSommet2(); // on récupère le centre avec lequel ce sommet est lié (donc le deuxieme sommet du lien) 
            if (sontVoisins(voisin1, sommet2)) { // si ce sommet est voisin du deuxième sommet donné par l'utilisateur, ils sont à 2 distances
                return true; // Ils sont à une distance de 2 
            }
        } 
    }

    return false; // Ils ne sont pas à une distance de 2
    }

    public boolean sontVoisins(Sommet sommet1, Sommet sommet2) {
        for (Lien lien : liens) {
            if ((lien.getSommet1().equals(sommet1) && lien.getSommet2().equals(sommet2))
                || (lien.getSommet1().equals(sommet2) && lien.getSommet2().equals(sommet1))) {
                return true; // Les sommets sont voisins
            }
        }
        return false; // Les sommets ne sont pas voisins
    }






}