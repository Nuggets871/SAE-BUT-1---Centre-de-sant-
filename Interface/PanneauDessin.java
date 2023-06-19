/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sae.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import graphe.Cercle;
import java.awt.Dimension;
import modele.Graphe;
import modele.Lien;
import modele.Sommet;

/**
 *
 * @author chris
 */
public class PanneauDessin extends javax.swing.JPanel {
    private static  List<Cercle> lesCercles;
    private final Graphe graphe;
    private List<Sommet> cheminPlusFiable; //Permettra de coloré le chemin le plus fiable

    /**
     * Creates new form PanneauDessin
     */
    public PanneauDessin(Graphe graphe) {
        this.graphe = graphe;
        lesCercles = new ArrayList<>();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                genererCerclesAleatoires();
            }
        });

        initComponents();
    }

    public void genererCerclesAleatoires() {
    lesCercles.clear(); // Effacer la liste des cercles existants

    Random random = new Random(); // Créer une instance de Random pour générer des coordonnées aléatoires

    int width = getWidth(); // Largeur du panneau de dessin
    int height = getHeight(); // Hauteur du panneau de dessin
    int rayon = 25; // Rayon fixe pour tous les cercles

    for (Sommet sommet : graphe.getSommets().values()) {
        Color color = TypeToColor.getColorByType(sommet.getType()); // Obtenir la couleur en fonction du type du sommet

        int x, y;
        boolean intersection;

        do {
            intersection = false;
            // Générer des coordonnées aléatoires pour le cercle dans les limites du panneau de dessin
            x = random.nextInt(width - 2 * rayon) + rayon;
            y = random.nextInt(height - 2 * rayon) + rayon;
            // Vérifier si les coordonnées se trouvent dans la bande supérieure
            if (y - rayon < topBandHeight) { 
                intersection = true; // Les coordonnées se trouvent dans la bande supérieure, réessayer avec de nouvelles coordonnées
                continue;
            }
            // Vérifier s'il y a une intersection avec un cercle existant
            for (Cercle cercle : lesCercles) {
                double distance = Math.sqrt(Math.pow(cercle.getX() - x, 2) + Math.pow(cercle.getY() - y, 2));// calcul la distance entre deux point utilisant la formule de la distance euclidienne racine((x2-x1)²+(y2-y1)²)
                if (distance < cercle.getRadius() + rayon) { // si cette distance est inférieur à 2 fois la rayon d'un cercle (donc le diametre du cercle #genius)ca veut dire que les deux cercles sont superposé 
                    intersection = true; // Il y a une intersection, réessayer avec de nouvelles coordonnées
                    break;
                }
            }
        } while (intersection);

        // Créer un nouveau cercle avec les coordonnées aléatoires, le rayon fixe, la couleur et le nom du sommet
        Cercle cercle = new Cercle(x, y, rayon, color, sommet.getNom());
        lesCercles.add(cercle); // Ajouter le cercle à la liste des cercles
    }

    repaint(); // Redessiner le panneau de dessin avec les nouveaux cercles
}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        Font font = new Font("Arial", Font.BOLD, 14);
        g.setFont(font);
        
        g2d.setColor(new Color(249, 237, 105)); //Légende Maternité
        g2d.fillOval(15,15,25,25); 
        g2d.drawString(": Maternité", 45,35);
        
        g2d.setColor(new Color(240, 138, 93)); //Légende Opératoire
        g2d.fillOval(130,15,25,25);
        g2d.drawString(": Opératoire", 160, 35);
        
        g2d.setColor(new Color(184, 59, 94)); //Légende Nutritionnel
        g2d.fillOval(270, 15, 25, 25);
        g2d.drawString(": Nutritionnel", 300,35);
        
        g2d.setColor(new Color(106, 44, 112));
        for (Lien lien : graphe.getLiens()) {
            Sommet sommet1 = lien.getSommet1();
            Sommet sommet2 = lien.getSommet2();

            Cercle cercle1 = getCercleBySommet(sommet1);
            Cercle cercle2 = getCercleBySommet(sommet2);

            if (cercle1 != null && cercle2 != null) {
                g2d.drawLine(cercle1.getX(), cercle1.getY(), cercle2.getX(), cercle2.getY());
            }
        }
        for (Cercle circle : lesCercles) {
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(),
                    circle.getRadius() * 2, circle.getRadius() * 2);

            // Dessiner le texte au centre du cercle
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(circle.getText());
            int textHeight = fm.getHeight();
            int textX = circle.getX() - (textWidth / 2);
            int textY = circle.getY() + (textHeight / 2);
            g2d.drawString(circle.getText(), textX, textY);
        }

        
    }
    public  Cercle getCercleBySommet(Sommet sommet) {
        for (Cercle cercle : lesCercles) {
            if (cercle.getText().equals(sommet.getNom())) {
                return cercle;
            }
        }
        return null;
    }
    
    

    public class TypeToColor {
        public static Color getColorByType(String type) {
            switch (type) {
                case "M":
                    return new Color(249, 237, 105); // Jaune
                case "O":
                    return new Color(240, 138, 93); // Orange
                case "N":
                    return new Color(184, 59, 94); // Rouge
                default:
                    return Color.RED; // Couleur par défaut
            }
        }
    }

    
    public void afficherMaternite(){
        for (Sommet sommet : graphe.getSommets().values()){
            if (sommet.getType().equals("M")){
                Cercle cercle = getCercleBySommet(sommet);
                cercle.setColor(Color.RED); // Modifier la couleur du cercle en vert
                repaint(); // Redessiner le panneau de dessin avec la nouvelle couleur du cercle

            }
        }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 973, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        for (Cercle circle : lesCercles) {
                    if (circle.isDragging()) {
                        circle.move(evt.getX() - circle.getOffsetX(), evt.getY() - circle.getOffsetY());
                    }
        }
        repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        for (Cercle circle : lesCercles) {
                    if (circle.contains(evt.getPoint())) {
                        circle.setDragging(true);
                        circle.setOffset(evt.getX() - circle.getX(), evt.getY() - circle.getY());
                    }
                }
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        for (Cercle circle : lesCercles) {
                    circle.setDragging(false);
        }
    }//GEN-LAST:event_formMouseReleased
    private final int topBandHeight = 50; //Définition 

    @Override
    public Dimension getPreferredSize() { //Zone de légende
        Dimension preferredSize = super.getPreferredSize();
        int width = preferredSize.width;
        int height = preferredSize.height + topBandHeight;
        return new Dimension(width, height);
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}



