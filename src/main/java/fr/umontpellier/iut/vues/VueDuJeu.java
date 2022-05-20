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
        vuePlateau = new VuePlateau();
        getChildren().add(vuePlateau);
    }

        public IJeu getJeu () {
            return jeu;
        }

        public void creerBindings () {}

    }