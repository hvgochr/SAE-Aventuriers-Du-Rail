package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * 
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends Pane {

    private IJeu jeu;

    private DropShadow dropShadow;

    public VueAutresJoueurs(IJeu jeu){
        dropShadow = new DropShadow(20, Color.BLACK);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        this.setEffect(dropShadow);
        this.setPrefSize(329, 78);
        this.setStyle("-fx-background-color: #E4C6FF");
        this.setEffect(dropShadow);
    }

}
