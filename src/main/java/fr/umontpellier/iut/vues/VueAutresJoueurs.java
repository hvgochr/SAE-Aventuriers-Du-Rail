package fr.umontpellier.iut.vues;

import java.io.FileInputStream;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * 
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {

    private IJoueur joueur;

    private VBox vBoxInfos;

    private HBox vue;
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
        vue = new HBox();
        vue.setPrefSize(250, 59);
        vue.setSpacing(10);
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
        this.setStyle("-fx-background-color: #" + this.joueur.convertirCouleurJoueur());
        HBox.setMargin(vBoxInfos, new Insets(5, 0, 0, 0));
        HBox.setMargin(avatarJoueur, new Insets(5, 0, 0, 10));
        HBox.setMargin(score, new Insets(5, 0, 0, 0));
        vue.getChildren().addAll(avatarJoueur, vBoxInfos, vide, score);
        this.setPrefSize(250, 59);
        this.getChildren().add(vue);
    }

    private Pane carteWagonPlusIndice(String couleur){
        Pane res = new Pane();
        res.setPrefSize(56, 37);
        ImageView carte = new ImageView(new Image("images/cartesWagons/carte-wagon-" + couleur.toString().toUpperCase() + ".png"));
        Circle cercle = new Circle(5);
        StringProperty stringIndice = new SimpleStringProperty();
        Text indice = new Text();
        if(couleur.toUpperCase().equals("BLANC")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.BLANC)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("ORANGE")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.ORANGE)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("VERT")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.VERT)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("ROUGE")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.ROUGE)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("BLEU")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.BLEU)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("NOIR")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.NOIR)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("JAUNE")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.JAUNE)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("ROSE")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.ROSE)));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("LOCOMOTIVE")){
            stringIndice.set(String.valueOf(this.joueur.getNbCarteWagons(CouleurWagon.LOCOMOTIVE)));
            indice.setText(stringIndice.get());
        }
        carte.setLayoutX(0);
        carte.setLayoutY(0);
        cercle.setLayoutX(50);
        cercle.setLayoutY(32);
        cercle.setFill(Color.valueOf("#D9D9D9"));
        cercle.setStroke(Color.BLACK);
        cercle.setStrokeWidth(0.3);
        indice.setFont(Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 7));
        indice.setLayoutX(48);
        indice.setLayoutY(34);
        carte.setFitHeight(33);
        carte.setFitWidth(52);
        res.getChildren().addAll(carte, cercle, indice);
        return res;
    }

    public Node getNodeByRowColumnIndex (int row, int column, GridPane grid) {
        ObservableList<Node> children = grid.getChildren();
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public GridPane gridCarteWagons(){
        GridPane res = new GridPane();
        res.setPrefSize(233, 112);
        res.setHgap(6);
        res.setVgap(5);
        res.add(this.carteWagonPlusIndice("blanc"), 1, 0);
        res.add(this.carteWagonPlusIndice("bleu"), 0, 1);
        res.add(this.carteWagonPlusIndice("jaune"), 2, 1);
        res.add(this.carteWagonPlusIndice("locomotive"), 1, 2);
        res.add(this.carteWagonPlusIndice("noir"), 1, 1);
        res.add(this.carteWagonPlusIndice("orange"), 0, 0);
        res.add(this.carteWagonPlusIndice("rose"), 3, 1);
        res.add(this.carteWagonPlusIndice("rouge"), 3, 0);
        res.add(this.carteWagonPlusIndice("vert"), 2, 0);
        GridPane.setMargin(this.getNodeByRowColumnIndex(2, 1, res), new Insets(0, 0, 0, 29));
        GridPane.setMargin(this.getNodeByRowColumnIndex(1, 2, res), new Insets(0, 0, 0, -29));
        GridPane.setMargin(this.getNodeByRowColumnIndex(0, 2, res), new Insets(0, 0, 0, -29));
        return res;
    }

    public VBox destinationsJoueur(){
        VBox res = new VBox();
        for(int i=0; i<joueur.getDestinations().size(); i++){
            Text destination = new Text();
            destination.setFont(fontPseudo);
            destination.setText(joueur.getDestinations().get(i).toString());
            res.getChildren().add(destination);
        }
        return res;
    }

    public void expand() {
        this.setPrefSize(250, 240);
        VBox destinations = this.destinationsJoueur();
        GridPane cartes = this.gridCarteWagons();
        VueAutresJoueurs.setMargin(destinations, new Insets(0, 0, 0, 5));
        VueAutresJoueurs.setMargin(cartes, new Insets(0, 0, 5, 5));
        this.getChildren().addAll(destinations, cartes);
    }

    public void shrink() {
        this.setPrefSize(250, 59);
        this.getChildren().clear();
        this.getChildren().add(vue);
    }

}
