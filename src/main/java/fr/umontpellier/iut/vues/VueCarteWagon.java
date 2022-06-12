package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Label {

    private ICouleurWagon couleurWagon;
    private ImageView imageCarteWagon;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
        imageCarteWagon = new ImageView(new Image("images/cartesWagons/carte-wagon-" + couleurWagon.toString().toUpperCase() + ".png"));
        imageCarteWagon.setFitHeight(61);
        imageCarteWagon.setPreserveRatio(true);
        setGraphic(imageCarteWagon);
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }




}
