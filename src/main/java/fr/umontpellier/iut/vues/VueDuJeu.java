package fr.umontpellier.iut.vues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private VBox carteWagonVisibles;

    private ImageView piocheWagon;
    private ImageView piocheDestination;

    private HBox boxChoix;
    private HBox top;
    private VBox left;

    private Button passer;

    private VueJoueurCourant vueJoueurCourant;

    private VueAutresJoueurs vueAutreJoueur1;
    private VueAutresJoueurs vueAutreJoueur2;
    private VueAutresJoueurs vueAutreJoueur3;
    private VueAutresJoueurs vueAutreJoueur4;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        this.setPrefSize(1280, 720);
        //BoxCarteWagon
        carteWagonVisibles = new VBox();
        carteWagonVisibles.setSpacing(6);
        //BoxJoueurs
        boxJoueurs = new VBox();
        boxJoueurs.setPrefSize(250, 469);
        boxJoueurs.setSpacing(9);
        //Choix
        choix = new BorderPane();
        //BoxChoix
        boxChoix = new HBox();
        boxChoix.setSpacing(10);
        boxChoix.setPrefSize(798, 33);
        choix.setLeft(boxChoix);
        //DropShadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        //Font
        fontTitre = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 30);
        fontPasser = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 11);
        //BoxTop
        top = new HBox();
        top.setSpacing(100);
        top.setPrefHeight(80);
        //Pioches
        try {
            piocheWagon = new ImageView(new Image(new FileInputStream("ressources/images/images/carte-wagon.png")));
            piocheDestination = new ImageView(new Image(new FileInputStream("ressources/images/images/eu_TicketBack.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        piocheDestination.setFitHeight(61);
        piocheDestination.setFitWidth(99);
        piocheDestination.setEffect(dropShadow);
        piocheDestination.setOnMouseClicked(e -> {
            this.getJeu().uneDestinationAEtePiochee();
        });
        piocheWagon.setFitHeight(61);
        piocheWagon.setFitWidth(99);
        piocheWagon.setEffect(dropShadow);
        piocheWagon.setOnMouseClicked(e -> {
            this.getJeu().uneCarteWagonAEtePiochee();
        });
        //BoxLeft
        left = new VBox();
        left.setSpacing(20);
        left.getChildren().addAll(carteWagonVisibles, piocheWagon, piocheDestination);
        //Titre
        titre = new Text("Les Aventuriers Du Rail - Version Europe");
        titre.setFont(fontTitre);
        top.getChildren().add(titre);
        top.setAlignment(Pos.CENTER);
        //Plateau
        vuePlateau = new VuePlateau();
        //Passer
        passer = new Button("Passer");
        passer.setPrefSize(108, 33);
        passer.setStyle("-fx-background-color: #FBF8DC;");
        passer.setFont(fontPasser);
        passer.setEffect(dropShadow);
        choix.setRight(passer);
        //Bot
        bot = new VBox();
        bot.setSpacing(15);
        bot.getChildren().addAll(choix);
        //This
        this.setTop(top);
        this.setLeft(left);
        this.setCenter(vuePlateau);
        this.setRight(boxJoueurs);
        this.setBottom(bot);
        VueDuJeu.setMargin(boxJoueurs, new Insets(0, 58, 0, 0));
        VueDuJeu.setMargin(left, new Insets(0, 0, 0, 58));
        VueDuJeu.setMargin(vuePlateau, new Insets(-140, 0, 0, 0));
        this.setStyle("-fx-background-color: #F2EDBF");
    }

    public IJeu getJeu () {
        return jeu;
    }

    public void creerBindings () {
        this.getJeu().destinationsInitialesProperty().addListener(destinationsSontPiocheesListener);
        this.getJeu().cartesWagonVisiblesProperty().addListener(listeCarte);
        this.getJeu().joueurCourantProperty().addListener(joueurChangeListener);
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
                        ImageView carte = new ImageView();
                        try {
                            carte.setImage(new Image(new FileInputStream("ressources/images/images/carte-wagon-" + couleurWagon.toString().toUpperCase() + ".png")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        carte.setFitHeight(61);
                        carte.setFitWidth(99);
                        carte.setEffect(dropShadow);
                        carteWagonVisibles.getChildren().add(carte);
                        carte.setOnMouseClicked(e -> {
                            carteWagonVisibles.getChildren().remove(carte);
                            jeu.uneCarteWagonAEteChoisie(couleurWagon);
                        });
                    }
                }else if (action.wasRemoved()) {
                    for (ICouleurWagon couleurWagon : action.getRemoved()) {
                        carteWagonVisibles.getChildren().remove(trouveCarte(couleurWagon));
                    }
                }
            }
        }
    );

    private final ChangeListener<IJoueur> joueurChangeListener = (observableValue, ancienJoueur, nouveauJoueur) -> {
        Platform.runLater(() -> {
            this.vueJoueurCourant = new VueJoueurCourant(nouveauJoueur);
            boxJoueurs.getChildren().clear();
            changementOrdreDesJoueurs(nouveauJoueur.getOrdreJoueur());
        });
    };

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
        for (Node n : carteWagonVisibles.getChildren()) {
            ImageView c = (ImageView) n;
            try {
                if(c.getImage().equals(new Image(new FileInputStream("ressources/images/images/carte-wagon-" + i.toString().toUpperCase() + ".png")))){
                    return c;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void changementOrdreDesJoueurs(int numJoueurCourant){
        //NbJoueurs = 2
        if (jeu.getJoueurs().size() == 2) {
            if(numJoueurCourant==0){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1);
            }else if(numJoueurCourant==1){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant);
            }
        }
        //NbJoueurs = 3
        else if (jeu.getJoueurs().size() == 3) {
            if(numJoueurCourant==0){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                //Test hover autre joueur
                /** 
                vueAutreJoueur1.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                    if (newValue) {
                        vueAutreJoueur1.setPrefHeight(200);
                    } else {
                        vueAutreJoueur1.setPrefHeight(59);
                    }
                });
                vueAutreJoueur2.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                    if (newValue) {
                        vueAutreJoueur2.setPrefHeight(200);
                    } else {
                        vueAutreJoueur2.setPrefHeight(78);
                    }
                });
                **/
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2);
            }else if(numJoueurCourant==1){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant, vueAutreJoueur2);
            }else if(numJoueurCourant==2){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueJoueurCourant);
            }
        }
        //NbJoueurs = 4
        else if (jeu.getJoueurs().size() == 4) {
            if(numJoueurCourant==0){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3);
            }else if(numJoueurCourant==1){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant, vueAutreJoueur2, vueAutreJoueur3);
            }else if(numJoueurCourant==2){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueJoueurCourant, vueAutreJoueur3);
            }
            else if(numJoueurCourant==3){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueJoueurCourant);
            }
        }
        //NbJoueurs = 5
        else if (jeu.getJoueurs().size() == 5) {
            if(numJoueurCourant==0){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueAutreJoueur4);
            }else if(numJoueurCourant==1){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant, vueAutreJoueur2, vueAutreJoueur3, vueAutreJoueur4);
            }else if(numJoueurCourant==2){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueJoueurCourant, vueAutreJoueur3, vueAutreJoueur4);
            }else if(numJoueurCourant==3){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueJoueurCourant, vueAutreJoueur4);
            }else if(numJoueurCourant==4){
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueAutreJoueur4, vueJoueurCourant);
            }
        }
    }

}
