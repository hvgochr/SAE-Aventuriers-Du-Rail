package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;

import javafx.scene.effect.*;
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

    private VueAutresJoueurs vueAutreJoueur;

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
        vueJoueurCourant = new VueJoueurCourant(jeu.getJoueurs().get(3));
        vueJoueurCourant.setLayoutX(1044);
        vueJoueurCourant.setLayoutY(377);
        //Autre joueur
        vueAutreJoueur = new VueAutresJoueurs(jeu.getJoueurs().get(2));
        vueAutreJoueur.setLayoutX(1044);
        vueAutreJoueur.setLayoutY(287);
        //This
        this.setStyle("-fx-background-color: #F2EDBF");
        getChildren().addAll(vuePlateau, vueAutreJoueur, vueJoueurCourant, titre);
    }

    public IJeu getJeu () {
        return jeu;
    }

    public void creerBindings () {

    }

}