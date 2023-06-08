/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

/**
 *
 * @author chris
 */
class Lien {
    private Sommet sommet1;

    public void setSommet1(Sommet sommet1) {
        this.sommet1 = sommet1;
    }

    public void setSommet2(Sommet sommet2) {
        this.sommet2 = sommet2;
    }

    public void setFiabilite(double fiabilite) {
        this.fiabilite = fiabilite;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDureeMoyenne(double dureeMoyenne) {
        this.dureeMoyenne = dureeMoyenne;
    }
    private Sommet sommet2;
    private double fiabilite;
    private double distance;
    private double dureeMoyenne;

    public Lien(Sommet sommet1, Sommet sommet2, double fiabilite, double distance, double dureeMoyenne) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;
        this.fiabilite = fiabilite;
        this.distance = distance;
        this.dureeMoyenne = dureeMoyenne;
    }

    public Sommet getSommet1() {
        return sommet1;
    }

    public Sommet getSommet2() {
        return sommet2;
    }

    public double getFiabilite() {
        return fiabilite;
    }

    public double getDistance() {
        return distance;
    }

    public double getDureeMoyenne() {
        return dureeMoyenne;
    }
}