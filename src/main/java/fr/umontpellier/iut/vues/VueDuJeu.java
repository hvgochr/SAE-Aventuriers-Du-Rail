package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.RailsIHM;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private HBox boxChoix;
    private HBox carteWagonPosee;

    private Button passer;
    private Button regles;

    private VueJoueurCourant vueJoueurCourant;

    private VueAutresJoueurs vueAutreJoueur1;
    private VueAutresJoueurs vueAutreJoueur2;
    private VueAutresJoueurs vueAutreJoueur3;
    private VueAutresJoueurs vueAutreJoueur4;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        this.setPrefSize(1440, 1024);
        //BoxCarteWagon
        carteWagonPosee = new HBox();
        //BoxJoueurs
        boxJoueurs = new VBox();
        boxJoueurs.setPrefSize(329, 618);
        boxJoueurs.setSpacing(13);
        boxJoueurs.setStyle("-fx-background-color: #F2EDBF");
        boxJoueurs.setLayoutX(1044);
        boxJoueurs.setLayoutY(104);
        //BoxChoix
        boxChoix = new HBox();
        boxChoix.setSpacing(10);
        boxChoix.setPrefSize(798, 33);
        boxChoix.setLayoutX(66);
        boxChoix.setLayoutY(742);
        boxChoix.setStyle("-fx-background-color: #F2EDBF");
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
        //Passer
        passer = new Button("Passer");
        passer.setPrefSize(108, 33);
        passer.setLayoutX(899);
        passer.setLayoutY(742);
        passer.setStyle("-fx-background-color: #FBF8DC;");
        passer.setFont(fontPasser);
        passer.setEffect(dropShadow);
        //Button Règles
        regles = new Button("Règles");
        regles.setPrefSize(108, 33);
        regles.setLayoutX(110);
        regles.setLayoutY(36);
        regles.setOnAction(e -> {
           new RailsIHM().openRules();
        });
        //Joueur Courant
        vueJoueurCourant = new VueJoueurCourant(jeu.getJoueurs().get(0));
        //Autres joueur
        if (jeu.getJoueurs().size() == 2) {
            vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
            boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1);
        }
        //Autre Joueur
        else if (jeu.getJoueurs().size() == 3) {
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
        this.getChildren().addAll(vuePlateau, titre, boxJoueurs, passer, boxChoix, regles, carteWagonPosee);
        this.setStyle("-fx-background-color: #F2EDBF");
    }

    public IJeu getJeu () {
        return jeu;
    }

    public void creerBindings () {
        this.getJeu().destinationsInitialesProperty().addListener(destinationsSontPiocheesListener);
        this.getJeu().cartesWagonVisiblesProperty().addListener(listeCarte);
        passer.setOnAction(e -> {
            jeu.passerAEteChoisi();
        });
    }

    private final ListChangeListener<IDestination> destinationsSontPiocheesListener = change -> {
        Platform.runLater(() -> {
            while(change.next()){
                if(change.wasAdded()){
                    for(IDestination d : change.getAddedSubList()){
                        Button b = new Button(d.getNom());
                        b.setStyle("-fx-background-color: #FBF8DC");
                        b.setPrefHeight(33);
                        b.setFont(fontPasser);
                        b.setEffect(dropShadow);
                        b.setOnAction(e -> {
                            if(boxChoix.getChildren().size() > 2){
                                boxChoix.getChildren().remove(b);
                            }
                            jeu.uneDestinationAEteChoisie(d.getNom());
                        });
                        boxChoix.getChildren().add(b);
                    }
                }else if(change.wasRemoved()){
                    for(IDestination d : change.getRemoved()){
                        boxChoix.getChildren().remove(trouveButtonDestination(d));
                    }
                }
            }
        });
    };

    private final ListChangeListener<ICouleurWagon> listeCarte = action -> Platform.runLater(() -> {
        while (action.next()) {
            if (action.wasAdded()) {
                for (ICouleurWagon couleurWagon : action.getAddedSubList()) {
                    carteWagonPosee.getChildren().add(new Label(couleurWagon.toString()));
                }
            }
            else if (action.wasRemoved()) {
                for (ICouleurWagon couleurWagon : action.getRemoved()) {
                    carteWagonPosee.getChildren().remove(trouveCarte(couleurWagon));
                }
            }
        }
    });

    private Button trouveButtonDestination(IDestination d){
        for(Node n : boxChoix.getChildren()){
            Button buttonDestination = (Button) n;
            if(buttonDestination.getText().equals(d.getNom())){
                return buttonDestination;
            }
        }
        return null;
    }

    private Label trouveCarte(ICouleurWagon i) {
        Label res = null;
        for (Node n : carteWagonPosee.getChildren()) {
            Label l = (Label) n;
            if (l.getText().equals(i.toString())){
                res = l;
            }
        }
        return res;
    }

}
