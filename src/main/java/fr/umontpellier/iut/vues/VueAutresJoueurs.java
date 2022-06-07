package fr.umontpellier.iut.vues;

import java.io.FileInputStream;

import fr.umontpellier.iut.IJoueur;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * 
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends HBox {

    private IJoueur joueur;

    private VBox vBoxInfos;

    private HBox hBoxGares;
    private HBox hBoxWagons;

    private Pane vide;

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

    private DropShadow dropShadow;

    public VueAutresJoueurs(IJoueur joueur){
        this.joueur = joueur;
        //Font
        fontPseudo = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 11);
        //Shadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        //VBox infos
        vBoxInfos = new VBox();
        vBoxInfos.setPrefWidth(65);
        hBoxGares = new HBox();
        hBoxGares.setSpacing(10);
        hBoxWagons = new HBox();
        hBoxWagons.setSpacing(5);
        vide = new Pane();
        vide.setPrefSize(55, 59);
        //Pseudo
        pseudoJoueur = new Text(joueur.getNom());
        pseudoJoueur.setFont(fontPseudo);
        //Score
        score = new Text("Score: " + String.valueOf(joueur.getScore()));
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
        avatarJoueur.setFitWidth(34);
        avatarJoueur.setFitHeight(43);
        //Wagons
        wagons = new ImageView(new Image(lienWagon));
        wagons.setFitHeight(13);
        wagons.setFitWidth(14);
        //NbWagons
        nbWagons = new Text(String.valueOf(joueur.getNbWagons()));
        nbWagons.setFont(fontPseudo);
        hBoxWagons.getChildren().addAll(wagons, nbWagons);
        //Gare
        gares = new ImageView(new Image(lienGare));
        gares.setFitHeight(14);
        gares.setFitWidth(10);
        //NbGares
        nbGares = new Text(String.valueOf(joueur.getNbGares()));
        nbGares.setFont(fontPseudo);
        hBoxGares.getChildren().addAll(gares, nbGares);
        vBoxInfos.getChildren().addAll(pseudoJoueur, hBoxGares, hBoxWagons);
        //Shadow
        dropShadow = new DropShadow(20, Color.BLACK);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        //This
        this.setEffect(dropShadow);
        this.setPrefSize(250, 59);
        this.setStyle("-fx-background-color: #" + this.joueur.convertirCouleurJoueur());
        this.setSpacing(10);
        VueAutresJoueurs.setMargin(vBoxInfos, new Insets(5, 0, 0, 0));
        VueAutresJoueurs.setMargin(avatarJoueur, new Insets(5, 0, 0, 10));
        VueAutresJoueurs.setMargin(score, new Insets(5, 0, 0, 0));
        getChildren().addAll(avatarJoueur, vBoxInfos, vide, score);
    }

}
