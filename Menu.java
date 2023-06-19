/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;

/**
 *
 * @author chris
 */



import java.util.InputMismatchException; // programme de test des fonctionnalitées 
import java.util.List;
import java.util.Scanner;
public class Menu {
    private static Graphe graphe;
    
    public static void main(String[] args) {
        graphe = new Graphe();
        String nomFichier = "TestPerso.csv"; 
        graphe.chargerGraphe(nomFichier);

        
        afficherMenu();


    }
    
    public static void afficherMenu() {
        Scanner scanner = new Scanner(System.in);
        int choix=-1;
        
        while (choix != 0) {
            System.out.println("Menu");
            System.out.println("1. Afficher le nombre de centres");
            System.out.println("2. Afficher le nombre de liens");
            System.out.println("3. Afficher les informations de tous les centres");
            System.out.println("4. Afficher les informations de tous les liens");
            System.out.println("5. Afficher les informations d'un centre précis");
            System.out.println("6. Vérifier si deux centres sont reliés");
            System.out.println("7. Afficher les centres par type précis");
            System.out.println("8. Charger un nouveau graphe");
            System.out.println("9. Voisin direct");
            System.out.println("10. Modification des caractéristiques d'un lien");
            System.out.println("11. Voisin direct d'un type donné");
            System.out.println("12. Regarder si 2 centres sont à 2-distances");
            System.out.println("13 Afficher tous les centres à 2 distance");
            System.out.println("14 Afficher le chemin le plus court (distance) entre deux centres");
            System.out.println("15 Afficher le chemin le plus fiable entre deux centres");
            System.out.println("16 Afficher le chemin le plus court (duree) entre deux centres");
            System.out.print("Choix : ");
            
            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Vider le tampon de lecture
                
                switch (choix) {
                case 1:
                    int nbr = graphe.getNombreCentres();
                    System.out.println("Nombre de centres : " + nbr);
                    break;
                case 2:
                    int nbr2 = graphe.getNombreLiens();
                    System.out.println(nbr2);
                    break;
                case 3:
                    graphe.afficherInformationsCentres();
                    break;
                case 4:
                    graphe.afficherInformationsLiens();
                    break;
                case 5:
                    String nomCentreInfo;
                    do {
                        System.out.print("Entrez le nom du centre : ");
                        nomCentreInfo = scanner.nextLine();
                        graphe.afficherInformationsCentrePrecis(nomCentreInfo);
                    } while (!graphe.existeCentre(nomCentreInfo));
                    break;
                case 6:
                    String nomCentreL1;
                    String nomCentreL2;
                    System.out.print("Entrez le nom du premier centre : ");
                    nomCentreL1 = scanner.nextLine();
                    System.out.print("Entrez le nom du deuxième centre : ");
                    nomCentreL2 = scanner.nextLine();
                    boolean centresRelies = graphe.estLie(nomCentreL1, nomCentreL2);
                    if (centresRelies) {
                        System.out.println("Les centres sont liés. Caractéristiques du lien :");
                        Lien lien = graphe.getLienEntreCentres(nomCentreL1, nomCentreL2);
                        System.out.println("Fiabilité : " + lien.getFiabilite());
                        System.out.println("Distance : " + lien.getDistance());
                        System.out.println("Durée moyenne : " + lien.getDureeMoyenne());
                    } else {
                        System.out.println("Les centres ne sont pas liés ou l'un des centres n'existe pas dans le graphe.");
                    }

                    break;
                case 7:
                    String typeCentre;
                    System.out.print("Entrez le type de centre (M, N ou O) : ");
                    typeCentre = scanner.nextLine();                    
                    graphe.afficherSommetParType(typeCentre);
                    break;
                case 8:
                    //System.out.print("Entrez le nom du fichier CSV : ");
                    //scanner.nextLine();
                    //String nomFichier = scanner.nextLine();
                    //graphe = Graphe.chargerGraphe(nomFichier);
                break;
                case 9 :
                    System.out.print("Entrez le nom du sommet : ");
                    String nomSommet = scanner.nextLine();

                    List<Sommet> voisins = graphe.getVoisinDirect(nomSommet);
                    if (!voisins.isEmpty()) {
                        System.out.println("Voisins directs du sommet " + nomSommet + ":");
                        for (Sommet voisin : voisins) {
                            System.out.println("- " + voisin.getNom());
                        }
                    } else {
                        System.out.println("Aucun voisin direct trouvé pour le sommet " + nomSommet);
                    }
                     break;
                case 10:
                     System.out.print("Entrez le nom du premier centre : ");
                    String nomCentre1 = scanner.nextLine();
                    System.out.print("Entrez le nom du deuxième centre : ");
                    String nomCentre2 = scanner.nextLine();
                    System.out.print("Entrez la nouvelle fiabilité : ");
                    int nouvelleFiabilite = scanner.nextInt();
                    System.out.print("Entrez la nouvelle distance : ");
                    int nouvelleDistance = scanner.nextInt();
                    System.out.print("Entrez la nouvelle durée : ");
                    int nouvelleDuree = scanner.nextInt();
                    graphe.modifierLien(nomCentre1, nomCentre2, nouvelleFiabilite, nouvelleDistance, nouvelleDuree);
                    break;
                case 11:
                    System.out.print("Entrez le nom du sommet : ");
                    String nomSommetListe = scanner.nextLine();
                    System.out.print("Entrez le type de voisin : ");
                    String typeVoisin = scanner.nextLine();

                    List<Sommet> voisinsListe = graphe.listerVoisinsDirectsDeType(nomSommetListe, typeVoisin);

                    if (voisinsListe != null && !voisinsListe.isEmpty()) {
                        System.out.println("Voisins directs de type '" + typeVoisin + "' pour le sommet '" + nomSommetListe + "' :");
                        for (Sommet voisin : voisinsListe) {
                            System.out.println("- " + voisin.getNom());
                        }
                    } else {
                        System.out.println("Aucun voisin direct de type '" + typeVoisin + "' pour le sommet '" + nomSommetListe + "'.");
                    }
                    break;
                case 12:
                     System.out.print("Entrez le nom du premier centre : ");
                    String nomCentre1_2distance = scanner.nextLine();
                    System.out.print("Entrez le nom du deuxième centre : ");
                    String nomCentre2_2distance = scanner.nextLine();

                    if (graphe.sontA2Distance(nomCentre1_2distance, nomCentre2_2distance)||graphe.sontA2Distance(nomCentre2_2distance,nomCentre1_2distance)) {
                        System.out.println("Les centres sont à 2-distance.");
                    } else {
                        System.out.println("Les centres ne sont pas à 2-distance.");
                    }
                    break;
                case 13:
                    System.out.print("Entrez le nom du  centre : ");
                    String nomCentre1_2distancetous = scanner.nextLine();
                    List<Sommet> voisins3 = graphe.getVoisin2Distance(nomCentre1_2distancetous);
                    for (Sommet sommet : voisins3){
                        System.out.println(sommet.getNom());
                    }
                case 14:
                   System.out.print("Entrez le nom du premier centre : ");
                    String centreDepart = scanner.nextLine();
                    System.out.print("Entrez le nom du deuxième centre : ");
                    String centreArrivee = scanner.nextLine();
                    Sommet s1 = graphe.sommets.get(centreDepart);
                    Sommet s2 = graphe.sommets.get(centreArrivee);
                    List<Sommet> cheminPlusCourt = graphe.dijkstraDistance(s1, s2);

                    // Affichage du chemin
                    System.out.println("Chemin le plus court en distance : ");
                    for (Sommet centre : cheminPlusCourt) {
                        System.out.println(centre.getNom());
                    }
                    break; 
                case 15:
                    System.out.print("Entrez le nom du premier centre : ");
                    String centreDepart2 = scanner.nextLine();
                    System.out.print("Entrez le nom du deuxième centre : ");
                    String centreArrivee2 = scanner.nextLine();
                    Sommet s12 = graphe.sommets.get(centreDepart2);
                    Sommet s22 = graphe.sommets.get(centreArrivee2);
                    List<Sommet> cheminPlusCourt2 = graphe.dijkstraFiabilite(s12, s22);

                    // Affichage du chemin
                    System.out.println("Chemin le plus fiable : ");
                    for (Sommet centre : cheminPlusCourt2) {
                        System.out.println(centre.getNom());
                    }
                    break; 
                case 16 :
                    System.out.print("Entrez le nom du premier centre : ");
                    String centreDepart3 = scanner.nextLine();
                    System.out.print("Entrez le nom du deuxième centre : ");
                    String centreArrivee3 = scanner.nextLine();
                    Sommet s13 = graphe.sommets.get(centreDepart3);
                    Sommet s23 = graphe.sommets.get(centreArrivee3);
                    List<Sommet> cheminPlusCourt3 = graphe.dijkstraFiabilite(s13, s23);

                    // Affichage du chemin
                    System.out.println("Chemin le plus court en durée : ");
                    for (Sommet centre : cheminPlusCourt3) {
                        System.out.println(centre.getNom());
                    }
                    break; 
                case 0:
                    System.out.println("\nAu revoir !");
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez réessayer.");
                    break;
                }
                
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("Choix invalide. Veuillez réessayer.");
                scanner.nextLine(); // Vider le tampon de lecture
            }
        }
        
        scanner.close();
    }
    
   
}
 
