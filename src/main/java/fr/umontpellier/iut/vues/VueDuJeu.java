package fr.umontpellier.iut.vues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;

import javafx.beans.binding.Bindings;
import javafx.css.Style;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


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

    private IJeu jeu;
    private Lighting lighting = new Lighting();
    private boolean clique = false;
    private DropShadow dropShadow = new DropShadow();
    private VuePlateau vuePlateau;
    private VueAutresJoueurs vueAutreJoueurs;

    private final EventHandler<MouseEvent> blanchir = actionEvent -> {
            if (!clique) {
                vuePlateau.setEffect(lighting);
                clique = true;
            }
            else {
                vuePlateau.setEffect(dropShadow);
                clique = false;
            }
    };

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        vueAutreJoueurs = new VueAutresJoueurs(jeu);
        getChildren().add(vueAutreJoueurs);
        vuePlateau = new VuePlateau();
        vuePlateau.setEffect(dropShadow);
        vuePlateau = new VuePlateau();
        getChildren().add(vuePlateau);
        Button coin = new Button();
        coin.setShape(new Circle(0));
        ImageView i1 = new ImageView();
        FileInputStream lienToggle = null;
        try {
            lienToggle = new FileInputStream("ressources/images/images/toggle-button.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        i1.setImage(new Image(lienToggle));

        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.BLACK);
        vuePlateau.setEffect(dropShadow);
        i1.setFitHeight(60);
        i1.setFitWidth(60);
        coin.setStyle("-fx-background-color: BLACK");
        coin.setGraphic(i1);

        lighting.setDiffuseConstant(100.0);
        lighting.setSpecularConstant(0.8);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(30, 30, Color.WHITE));
        getChildren().add(coin);
        coin.setOnMouseClicked(blanchir);
    }

        public IJeu getJeu () {
            return jeu;
        }

        public void creerBindings () {}

    }