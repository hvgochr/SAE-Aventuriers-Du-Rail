package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Pane {

    private ICouleurWagon couleurWagon;

    public VueCarteWagon(ICouleurWagon couleurWagon) {

        this.couleurWagon = couleurWagon;

            ChangeListener l = (observableValue, ancienneVal, nouvelleVal) -> {








         };

    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }



}
