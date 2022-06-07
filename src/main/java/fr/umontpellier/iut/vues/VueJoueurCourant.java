package fr.umontpellier.iut.vues;

import java.io.FileInputStream;

import fr.umontpellier.iut.IJoueur;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends Pane {

    private IJoueur joueurCourant;

    private Text pseudoJoueur;
    private Text score;
    private Text nbWagons;
    private Text nbGares;

    private ImageView avatarJoueur;
    private ImageView gares;
    private ImageView wagons;

    private FileInputStream lienAvatar;
    private FileInputStream lienGare;
    private FileInputStream lienWagon;

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
        //Destinations joueur
        destinationsJoueur = new VBox();
        destinationsJoueur.setLayoutX(14);
        destinationsJoueur.setLayoutY(81);
        for(int i=0; i<joueur.getDestinations().size(); i++){
            Text destination = new Text();
            destination.setFont(fontPseudo);
            destination.setText(joueur.getDestinations().get(i).toString());
            destinationsJoueur.getChildren().add(destination);
        }
        //Pseudo
        pseudoJoueur = new Text(joueurCourant.getNom());
        pseudoJoueur.setLayoutX(65);
        pseudoJoueur.setLayoutY(25);
        pseudoJoueur.setFont(fontPseudo);
        //Score
        score = new Text(String.valueOf(joueurCourant.getScore()));
        score.setFont(fontPseudo);
        score.setLayoutX(251);
        score.setLayoutY(25);
        //File Input
        try {
            lienAvatar = new FileInputStream("ressources/images/images/avatar-" + joueur.getCouleur().toString().toUpperCase() + ".png");
            lienWagon = new FileInputStream("ressources/images/images/image-wagon-" + joueur.getCouleur().toString().toUpperCase() + ".png");
            lienGare = new FileInputStream("ressources/images/images/gare-" + joueur.getCouleur().toString().toUpperCase() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Avatar
        avatarJoueur = new ImageView(new Image(lienAvatar));
        avatarJoueur.setFitWidth(42);
        avatarJoueur.setFitHeight(57);
        avatarJoueur.setLayoutX(14);
        avatarJoueur.setLayoutY(13);
        //Wagons
        wagons = new ImageView(new Image(lienWagon));
        wagons.setFitHeight(13);
        wagons.setFitWidth(14);
        wagons.setLayoutX(61);
        wagons.setLayoutY(56);
        //NbWagons
        nbWagons = new Text(String.valueOf(joueurCourant.getNbWagons()));
        nbWagons.setFont(fontPseudo);
        nbWagons.setLayoutX(83);
        nbWagons.setLayoutY(67);
        //Gare
        gares = new ImageView(new Image(lienGare));
        gares.setFitHeight(14);
        gares.setFitWidth(10);
        gares.setLayoutX(64);
        gares.setLayoutY(34);
        //NbGares
        nbGares = new Text(String.valueOf(joueurCourant.getNbGares()));
        nbGares.setFont(fontPseudo);
        nbGares.setLayoutX(83);
        nbGares.setLayoutY(45);
        //Shadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        this.setEffect(dropShadow);
        //This
        this.setStyle("-fx-background-color: #" + joueurCourant.convertirCouleurJoueur());
        this.setPrefSize(250, 182);
        getChildren().addAll(pseudoJoueur, avatarJoueur, gares, wagons, nbGares, nbWagons, score, cartesJoueur, destinationsJoueur);
    }

}
