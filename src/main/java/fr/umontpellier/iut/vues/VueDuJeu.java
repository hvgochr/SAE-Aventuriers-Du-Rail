package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    private DropShadow dropShadow;

    private Font fontTitre;
    private Font fontPasser;

    private IJeu jeu;

    private VuePlateau vuePlateau;

    private VBox boxJoueurs;

    private Button passer;

    private VueJoueurCourant vueJoueurCourant;

    private VueAutresJoueurs vueAutreJoueur1;
    private VueAutresJoueurs vueAutreJoueur2;
    private VueAutresJoueurs vueAutreJoueur3;
    private VueAutresJoueurs vueAutreJoueur4;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        boxJoueurs = new VBox();
        boxJoueurs.setPrefSize(329, 618);
        boxJoueurs.setSpacing(13);
        boxJoueurs.setStyle("-fx-background-color: #F2EDBF");
        boxJoueurs.setLayoutX(1044);
        boxJoueurs.setLayoutY(104);
        this.setPrefSize(1440, 1024);
        //DropShadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        //Font 
        fontTitre = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 40);
        fontPasser = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 15);
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
        //Passer
        passer = new Button("Passer");
        passer.setPrefSize(108, 33);
        passer.setLayoutX(899);
        passer.setLayoutY(742);
        passer.setStyle("-fx-background-color: #FBF8DC;");
        passer.setFont(fontPasser);
        passer.setEffect(dropShadow);
        //Autre joueur
        if (jeu.getJoueurs().size() == 2) {
            vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
            boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1);
        }
        //Autre Joueur
        if (jeu.getJoueurs().size() == 3) {
            vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
            vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
            boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2);
        }
        //Autre Joueur
        else if (jeu.getJoueurs().size() == 4) {
            vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
            vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
            vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
            boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3);
        }
        //Autre Joueur
        else if (jeu.getJoueurs().size() == 5) {
            vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
            vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
            vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
            vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
            boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueAutreJoueur4);
        }
        //This
        this.getChildren().addAll(vuePlateau, titre, boxJoueurs, passer);
        this.setStyle("-fx-background-color: #F2EDBF");
    }

    public IJeu getJeu () {
        return jeu;
    }

    public void creerBindings () {
        passer.setOnAction(e -> {
            jeu.passerAEteChoisi();
        });
    }

}