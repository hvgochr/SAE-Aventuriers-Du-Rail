package fr.umontpellier.iut.vues;

import java.io.FileInputStream;

import fr.umontpellier.iut.IJeu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends Pane {

    private Text pseudoJoueur;

    private ImageView joueur;

    private FileInputStream avatarJoueur;

    private Font fontPseudo;

    public VueJoueurCourant(IJeu jeu){
        //Font
        fontPseudo = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 14);
        //Pseudo
        pseudoJoueur = new Text(jeu.joueurCourantProperty().getName());
        pseudoJoueur.setLayoutX(65);
        pseudoJoueur.setLayoutY(12);
        pseudoJoueur.setFont(fontPseudo);
        //File Input Avatar
        try {
            avatarJoueur = new FileInputStream("ressources/images/images/avatar-VERT.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Avatar
        joueur = new ImageView(new Image(avatarJoueur));
        joueur.setFitWidth(42);
        joueur.setFitHeight(57);
        joueur.setLayoutX(14);
        joueur.setLayoutY(13);
        //This
        this.setStyle("-fx-background-color: #FFDF73");
        this.setPrefSize(329, 240);
        getChildren().addAll(pseudoJoueur, joueur);
    }

}
