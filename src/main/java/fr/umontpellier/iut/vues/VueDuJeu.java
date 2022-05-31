package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends Pane {

    private Text titre;

    private Font fontTitre;

    private IJeu jeu;

    private VuePlateau vuePlateau;

    private VueJoueurCourant vueJoueurCourant;

    private VueAutresJoueurs vueAutreJoueur1;
    private VueAutresJoueurs vueAutreJoueur2;
    private VueAutresJoueurs vueAutreJoueur3;
    private VueAutresJoueurs vueAutreJoueur4;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        this.setPrefSize(1440, 1024);
        //Font 
        fontTitre = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 40);
        //Titre
        titre = new Text("Les Aventuriers Du Rail - Version Europe");
        titre.setFont(fontTitre);
        titre.setLayoutX(285);
        titre.setLayoutY(58);
        //Plateau
        vuePlateau = new VuePlateau();
        vuePlateau.setLayoutX(66);
        vuePlateau.setLayoutY(105);
        vuePlateau.setPrefSize(957, 616);
        //Joueur Courant

        vueJoueurCourant = new VueJoueurCourant(jeu.getJoueurs().get(0));
        vueJoueurCourant.setLayoutX(1044);
        vueJoueurCourant.setLayoutY(106);
        //Autre joueur
        if (jeu.getJoueurs().size() == 2) {
            vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
            vueAutreJoueur1.setLayoutX(1044);
            vueAutreJoueur1.setLayoutY(358);
        }

        //Autre Joueur

        if (jeu.getJoueurs().size() == 3) {
            vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
            vueAutreJoueur2.setLayoutX(1044);
            vueAutreJoueur2.setLayoutY(449);
            getChildren().addAll(vuePlateau, titre,vueAutreJoueur2, vueJoueurCourant);
        }
        //Autre Joueur
        else if (jeu.getJoueurs().size() == 4) {
            vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
            vueAutreJoueur3.setLayoutX(1044);
            vueAutreJoueur3.setLayoutY(538);
            getChildren().addAll(vuePlateau, titre,vueAutreJoueur3, vueJoueurCourant);
        }
        //Autre Joueur
        else if (jeu.getJoueurs().size() == 5) {
            vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
            vueAutreJoueur4.setLayoutX(1044);
            vueAutreJoueur4.setLayoutY(629);
            getChildren().addAll(vuePlateau, titre,vueAutreJoueur4, vueJoueurCourant);
        }
        //This
        this.setStyle("-fx-background-color: #F2EDBF");
    }

    public IJeu getJeu () {
        return jeu;
    }

    public void creerBindings () {

    }

}