package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** 
 * Cette classe présente les éléments appartenant au joueur courant.
 * 
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */ 

public class VueJoueurCourant extends VBox {

    private IJoueur joueurCourant;

    private Font fontPseudo;

    private VBox cartesJoueur;
    private VBox destinationsJoueur;

    private DropShadow dropShadow;

    public VueJoueurCourant(IJoueur joueur){
        this.joueurCourant = joueur;
        //Font
        fontPseudo = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 10);
        //Cartes joueur
        cartesJoueur = new VBox();
        //A changer
        for(int i=0; i<joueur.getCartesWagon().size(); i++){
            Text couleur = new Text();
            couleur.setFont(fontPseudo);
            couleur.setText(joueur.getCartesWagon().get(i).name());
            cartesJoueur.getChildren().add(couleur);
        }
        //Destinations joueur
        destinationsJoueur = new VBox();
        for(int i=0; i<joueur.getDestinations().size(); i++){
            Text destination = new Text();
            destination.setFont(fontPseudo);
            destination.setText(joueur.getDestinations().get(i).toString());
            destinationsJoueur.getChildren().add(destination);
        }
        //Vue
        VueAutresJoueurs vueJoueur = new VueAutresJoueurs(joueur);
        vueJoueur.setEffect(null);
        //Shadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        this.setEffect(dropShadow);
        //This
        this.setStyle("-fx-background-color: #" + joueurCourant.convertirCouleurJoueur());
        this.setPrefSize(250, 182);
        VueJoueurCourant.setMargin(destinationsJoueur, new Insets(-8, 0, 0, 5));
        VueJoueurCourant.setMargin(cartesJoueur, new Insets(0, 0, 0, 5));
        getChildren().addAll(vueJoueur, destinationsJoueur, cartesJoueur);
    }

}
