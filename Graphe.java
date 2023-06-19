/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele; 

/**
 *
 * @author chris
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graphe {
    public final LinkedHashMap<String, Sommet> sommets; // possibilité d'utilisé HashMap afin d'optimisé la mémoire mais pas la peine pour cette sae
    private final List<Lien> liens;



    public Graphe() {
        sommets = new LinkedHashMap<>();
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
    
    public int getNombreLiens() { // renvoie le nombre de lien total
        return liens.size();
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
    public boolean sontA2Distance(String sommet1, String sommet2) {
        List<Sommet> voisins1 = getVoisinDirect(sommet1);
        List<Sommet> voisins2 = getVoisinDirect(sommet2);

        for (Sommet voisin1 : voisins1) {
            List<Sommet> voisinsVoisin1 = getVoisinDirect(voisin1.getNom());
            if (voisinsVoisin1.contains(sommets.get(sommet2))) {
                return true;
            }
        }

        for (Sommet voisin2 : voisins2) {
            List<Sommet> voisinsVoisin2 = getVoisinDirect(voisin2.getNom());
            if (voisinsVoisin2.contains(sommets.get(sommet1))) {
                return true;
            }
        }

        return false;
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
    

    public List<Sommet> getVoisin2Distance(String nomSommet) { // retourne les voisins à 2 distances + ceux qui sont à 2 distance et en même temps à 1 distance
        List<Sommet> voisins2Distance = new ArrayList<>();

        Sommet sommet = sommets.get(nomSommet);
        if (sommet != null) {
            List<Sommet> voisinsDirects = getVoisinDirect(nomSommet);

            for (Sommet voisinDirect : voisinsDirects) {
                List<Sommet> voisinsVoisinDirect = getVoisinDirect(voisinDirect.getNom());
                for (Sommet voisinVoisinDirect : voisinsVoisinDirect) {
                    if (!voisinVoisinDirect.equals(sommet)
                            && !voisins2Distance.contains(voisinVoisinDirect)) {
                        voisins2Distance.add(voisinVoisinDirect);
                    }
                }
            }
        }

        return voisins2Distance;
    }
    
    public List<Sommet> getVoisin2Distance2(String nomSommet) { // retourne les voisins à 2 distances sans ceux qui sont aussi à une distance
        List<Sommet> voisins2Distance = new ArrayList<>();

        Sommet sommet = sommets.get(nomSommet);
        if (sommet != null) {
            List<Sommet> voisinsDirects = getVoisinDirect(nomSommet);

            for (Sommet voisinDirect : voisinsDirects) {
                List<Sommet> voisinsVoisinDirect = getVoisinDirect(voisinDirect.getNom());
                for (Sommet voisinVoisinDirect : voisinsVoisinDirect) {
                    if (!voisinVoisinDirect.equals(sommet) && !voisinsDirects.contains(voisinVoisinDirect)
                            && !voisins2Distance.contains(voisinVoisinDirect)) {
                        voisins2Distance.add(voisinVoisinDirect);
                    }
                }
            }
        }

        return voisins2Distance;
    }



    public List<Sommet> getCentreMax2Distance(String nomSommet){
        List<Sommet> centreMax2Distance = new ArrayList<>();
        Sommet sommet = sommets.get(nomSommet);
        if (sommet!=null){
            centreMax2Distance = getVoisinDirect(nomSommet);
            List<Sommet> voisin2D = getVoisin2Distance2(nomSommet);
            for (Sommet voisin : voisin2D){
                centreMax2Distance.add(voisin);
            }    

        }

        return centreMax2Distance;    
        }

    public List<Sommet> getMaterniteMax2Distance(List<Sommet> lMax2Distance){
        List<Sommet> materniteMax2Distance = new ArrayList<>();

        if (lMax2Distance!=null){
            for (Sommet sommet : lMax2Distance){
                if ("M".equals(sommet.getType())){
                    materniteMax2Distance.add(sommet);
                }
            }
        }
        return materniteMax2Distance;
        }
    public List<Sommet> getOperatoireMax2Distance(List<Sommet> lMax2Distance){
        List<Sommet> operatoireMax2Distance = new ArrayList<>();

        if (lMax2Distance!=null){
            for (Sommet sommet : lMax2Distance){
                if ("O".equals(sommet.getType())){
                    operatoireMax2Distance.add(sommet);
                }
            }
        }
        return operatoireMax2Distance;
        }
    public List<Sommet> getNutritionMax2Distance(List<Sommet> lMax2Distance){
        List<Sommet> nutritionMax2Distance = new ArrayList<>();

        if (lMax2Distance!=null){
            for (Sommet sommet : lMax2Distance){
                if ("N".equals(sommet.getType())){
                    nutritionMax2Distance.add(sommet);
                }
            }
        }
        return nutritionMax2Distance;
        }


    
    public int getNombreMaternite(){
            int cpt = 0;
            for (Sommet sommet : sommets.values()){
                if (sommet.getType().equals("M"))
                    cpt +=1;
            }
            return cpt;
        }
        public int getNombreOperatoire(){
            int cpt = 0;
            for (Sommet sommet : sommets.values()){
                if (sommet.getType().equals("O"))
                    cpt +=1;
            }
            return cpt;
        }
        public int getNombreNutrition(){
            int cpt = 0;
            for (Sommet sommet : sommets.values()){
                if (sommet.getType().equals("N"))
                    cpt +=1;
            }
            return cpt;
        }
    
    
    public List<Sommet> dijkstraDistance(Sommet depart, Sommet arrivee) {
        // Création des structures de données nécessaires
        Map<Sommet, Double> distances = new HashMap<>();  // Distances depuis le sommet de départ
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();  // Prédécesseur de chaque sommet
        Set<Sommet> nonVisites = new HashSet<>();  // Sommets non visités

        // Initialisation des distances à l'infini, sauf pour le sommet de départ
        for (Sommet sommet : sommets.values()) {
            distances.put(sommet, Double.POSITIVE_INFINITY);
            nonVisites.add(sommet);
        }
        distances.put(depart, 0.0);

        while (!nonVisites.isEmpty()) {
            // Recherche du sommet non visité avec la distance minimale
            Sommet sommetCourant = plusProcheVoisin(nonVisites, distances);
            if (sommetCourant==null){
                return null;
            }
            // Si le sommet courant est l'arrivée, on a trouvé le chemin le plus court
            if (sommetCourant.equals(arrivee)) {
                return construireChemin(predecesseurs, arrivee);
            }

            // Mise à jour des distances des voisins non visités
            for (Sommet voisin : getVoisinDirect(sommetCourant.getNom())) {
                if (nonVisites.contains(voisin)) {
                    double distanceVoisin = distances.get(sommetCourant) + getLienEntreCentres(sommetCourant.getNom(), voisin.getNom()).getDistance();
                    if (distanceVoisin < distances.get(voisin)) {
                        distances.put(voisin, distanceVoisin);
                        predecesseurs.put(voisin, sommetCourant);
                    }
                }
            }

            // Marquer le sommet courant comme visité
            nonVisites.remove(sommetCourant);
        }

        // Aucun chemin trouvé
        return null;
    }

    private Sommet plusProcheVoisin(Set<Sommet> sommets, Map<Sommet, Double> distances) {
        Sommet plusProche = null;
        double distanceMin = Double.POSITIVE_INFINITY;

        for (Sommet sommet : sommets) {
            double distance = distances.get(sommet);
            if (distance < distanceMin) {
                distanceMin = distance;
                plusProche = sommet;
            }
        }

        return plusProche;
    }

    private List<Sommet> construireChemin(Map<Sommet, Sommet> predecesseurs, Sommet arrivee) {
        List<Sommet> chemin = new ArrayList<>();
        Sommet sommet = arrivee;

        while (sommet != null) {
            chemin.add(0, sommet);
            sommet = predecesseurs.get(sommet);
        }

        return chemin;
    }

    public List<Sommet> dijkstraFiabilite(Sommet depart, Sommet arrivee) {
        // Création des structures de données nécessaires
        Map<Sommet, Double> fiabilite = new HashMap<>();  // Fiabilités depuis le sommet de départ
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();  // Prédécesseur de chaque sommet
        Set<Sommet> nonVisites = new HashSet<>();  // Sommets non visités

        // Initialisation des fiabilités à zéro, sauf pour le sommet de départ
        for (Sommet sommet : sommets.values()) {
            fiabilite.put(sommet, 0.0);
            nonVisites.add(sommet);
        }
        fiabilite.put(depart, 10.0);

        while (!nonVisites.isEmpty()) {
            // Recherche du sommet non visité avec la fiabilité maximale
            Sommet sommetCourant = plusFiableVoisin(nonVisites, fiabilite);
            if (sommetCourant==null){
                return null;
            }
            // Si le sommet courant est l'arrivée, on a trouvé le chemin le plus fiable
            if (sommetCourant.equals(arrivee)) {
                return construireChemin(predecesseurs, arrivee);
            }

            // Mise à jour des fiabilités des voisins non visités
            for (Sommet voisin : getVoisinDirect(sommetCourant.getNom())) {
                if (nonVisites.contains(voisin)) {
                    double fiabiliteVoisin = Math.min(fiabilite.get(sommetCourant), getLienEntreCentres(sommetCourant.getNom(), voisin.getNom()).getFiabilite());
                    if (fiabiliteVoisin > fiabilite.get(voisin)) {
                        fiabilite.put(voisin, fiabiliteVoisin);
                        predecesseurs.put(voisin, sommetCourant);
                    }
                }
            }

            // Marquer le sommet courant comme visité
            nonVisites.remove(sommetCourant);
        }

        // Aucun chemin trouvé
        return null;
    }

    private Sommet plusFiableVoisin(Set<Sommet> sommets, Map<Sommet, Double> fiabilites) {
        Sommet plusFiable = null;
        double fiabiliteMax = 0.0;

        for (Sommet sommet : sommets) {
            double fiabilite = fiabilites.get(sommet);
            if (fiabilite > fiabiliteMax) {
                fiabiliteMax = fiabilite;
                plusFiable = sommet;
            }
        }

        return plusFiable;
    }

    public List<Sommet> dijkstraDuree(Sommet depart, Sommet arrivee) {
        // Création des structures de données nécessaires
        Map<Sommet, Double> durees = new HashMap<>();  // Durées depuis le sommet de départ
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();  // Prédécesseur de chaque sommet
        Set<Sommet> nonVisites = new HashSet<>();  // Sommets non visités

        // Initialisation des durées à l'infini, sauf pour le sommet de départ
        for (Sommet sommet : sommets.values()) {
            durees.put(sommet, Double.POSITIVE_INFINITY);
            nonVisites.add(sommet);
        }
        durees.put(depart, 0.0);

        while (!nonVisites.isEmpty()) {
            // Recherche du sommet non visité avec la durée minimale
            Sommet sommetCourant = plusProcheVoisin(nonVisites, durees);
            if (sommetCourant==null){
                return null;
            }
            // Si le sommet courant est l'arrivée, on a trouvé le chemin le plus court en durée
            if (sommetCourant.equals(arrivee)) {
                return construireChemin(predecesseurs, arrivee);
            }

            // Mise à jour des durées des voisins non visités
            for (Sommet voisin : getVoisinDirect(sommetCourant.getNom())) {
                if (nonVisites.contains(voisin)) {
                    double dureeVoisin = durees.get(sommetCourant) + getLienEntreCentres(sommetCourant.getNom(), voisin.getNom()).getDureeMoyenne();
                    if (dureeVoisin < durees.get(voisin)) {
                        durees.put(voisin, dureeVoisin);
                        predecesseurs.put(voisin, sommetCourant);
                    }
                }
            }

            // Marquer le sommet courant comme visité
            nonVisites.remove(sommetCourant);
        }

        // Aucun chemin trouvé
        return null;
    }




}