package fr.umontpellier.iut.vues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class VueDuJeu extends BorderPane {

    private Text titre;

    private DropShadow dropShadow;

    private Font fontTitre;
    private Font fontPasser;

    private IJeu jeu;

    private VuePlateau vuePlateau;

    private BorderPane choix;

    private VBox boxJoueurs;
    private VBox bot;

    private HBox boxChoix;
    private HBox carteWagonPosee;
    private HBox top;

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
        carteWagonPosee.setSpacing(26);
        //BoxJoueurs
        boxJoueurs = new VBox();
        boxJoueurs.setPrefSize(329, 618);
        boxJoueurs.setSpacing(13);
        boxJoueurs.setStyle("-fx-background-color: #F2EDBF");
        //Choix
        choix = new BorderPane();
        //BoxChoix
        boxChoix = new HBox();
        boxChoix.setSpacing(10);
        boxChoix.setPrefSize(798, 33);
        boxChoix.setStyle("-fx-background-color: #F2EDBF");
        choix.setLeft(boxChoix);
        //DropShadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        //Font 
        fontTitre = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 40);
        fontPasser = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 15);
        //BoxTop
        top = new HBox();
        top.setSpacing(100);
        //Titre
        titre = new Text("Les Aventuriers Du Rail - Version Europe");
        titre.setFont(fontTitre);
        //Plateau
        vuePlateau = new VuePlateau();
        vuePlateau.setPrefSize(957, 616);
        //Passer
        passer = new Button("Passer");
        passer.setPrefSize(108, 33);
        passer.setStyle("-fx-background-color: #FBF8DC;");
        passer.setFont(fontPasser);
        passer.setEffect(dropShadow);
        choix.setRight(passer);
        //Button Règles
        regles = new Button("Règles");
        regles.setPrefSize(108, 33);
        regles.setOnAction(e -> {
           new RailsIHM().openRules();
        });
        top.getChildren().addAll(regles, titre);
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
        //Bot
        bot = new VBox();
        bot.setSpacing(15);
        bot.getChildren().addAll(choix, carteWagonPosee);
        //This
        this.setTop(top);
        this.setCenter(vuePlateau);
        this.setRight(boxJoueurs);
        this.setBottom(bot);
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
                        boxChoix.getChildren().add(b);
                        b.setOnAction(e -> {
                            if(boxChoix.getChildren().size() > 2){
                                boxChoix.getChildren().remove(b);
                            }
                            jeu.uneDestinationAEteChoisie(d.getNom());
                        });
                    }
                }else if(change.wasRemoved()){
                    for(IDestination d : change.getRemoved()){
                        boxChoix.getChildren().remove(trouveButtonDestination(d));
                    }
                }
            }
        });
    };

    private final ListChangeListener<ICouleurWagon> listeCarte = action -> 
        Platform.runLater(() -> {
            while (action.next()) {
                if (action.wasAdded()) {
                    for (ICouleurWagon couleurWagon : action.getAddedSubList()) {
                        try {
                            ImageView carte = new ImageView();
                            carte.setImage(new Image(new FileInputStream("ressources/images/images/carte-wagon-" + couleurWagon.toString().toUpperCase() + ".png")));
                            carte.setFitHeight(90);
                            carte.setFitWidth(140);
                            carte.setEffect(dropShadow);
                            carteWagonPosee.getChildren().add(carte);
                            carte.setOnMouseClicked(e -> {
                                carteWagonPosee.getChildren().remove(carte);
                                jeu.uneCarteWagonAEteChoisie(couleurWagon);
                            });
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }else if (action.wasRemoved()) {
                    for (ICouleurWagon couleurWagon : action.getRemoved()) {
                        carteWagonPosee.getChildren().remove(trouveCarte(couleurWagon));
                    }
                }
            }
        }
    );

    private Button trouveButtonDestination(IDestination d){
        for(Node n : boxChoix.getChildren()){
            Button buttonDestination = (Button) n;
            if(buttonDestination.getText().equals(d.getNom())){
                return buttonDestination;
            }
        }
        return null;
    }

    private ImageView trouveCarte(ICouleurWagon i) {
        for (Node n : carteWagonPosee.getChildren()) {
            ImageView c = (ImageView) n;
            try {
                if (c.getImage().equals(new Image(new FileInputStream("ressources/images/images/carte-wagon-" + i.toString().toUpperCase() + ".png")))){
                    return c;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
