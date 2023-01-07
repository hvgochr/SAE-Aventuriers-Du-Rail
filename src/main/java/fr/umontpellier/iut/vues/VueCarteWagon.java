package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Label {

    private ICouleurWagon couleurWagon;
    private ImageView imageCarteWagon;
    private DropShadow dropShadow;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        imageCarteWagon = new ImageView(new Image("images/cartesWagons/carte-wagon-" + couleurWagon.toString().toUpperCase() + ".png"));
        imageCarteWagon.setFitHeight(61);
        imageCarteWagon.setPreserveRatio(true);
        this.setEffect(dropShadow);
        setGraphic(imageCarteWagon);
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

}
