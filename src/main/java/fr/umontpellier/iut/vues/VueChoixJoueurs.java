package fr.umontpellier.iut.vues;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;

    private Scene scene;

    private Pane pane;

    private Text titre1;
    private Text titre2;
    private Text nbJoueursTexte;
    private Text plus;
    private Text moins;

    private IntegerProperty nbJoueurs;

    private Font fontTradeWindsTitre1;
    private Font fontTradeWindsTitre2;
    private Font fontTradeWindsBoutons;
    private Font fontPseudos;

    private FileInputStream avatarVert;
    private FileInputStream avatarBleu;
    private FileInputStream avatarRose;
    private FileInputStream avatarRouge; 
    private FileInputStream avatarJaune;

    private ImageView joueurVert;
    private ImageView joueurBleu;
    private ImageView joueurRose;
    private ImageView joueurRouge;
    private ImageView joueurJaune;

    private TextField pseudoJoueurVert;
    private TextField pseudoJoueurBleu;
    private TextField pseudoJoueurRose;
    private TextField pseudoJoueurRouge;
    private TextField pseudoJoueurJaune;

    private Pane paneBoutons;

    private Button boutonAjouter;
    private Button boutonSupprimer;
    public Button jouer;

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        nbJoueurs = new SimpleIntegerProperty(5);
        nomsJoueurs = FXCollections.observableArrayList();
        //Pane
        pane = new Pane();
        pane.setStyle("-fx-background-color: #F2EDBF");
        pane.setPrefSize(920, 720);
        //Fonts
        fontTradeWindsTitre1 = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 26);
        fontTradeWindsTitre2 = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 18);
        fontTradeWindsBoutons = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 28);
        fontPseudos = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 12);
        //Titre 1 "Aventuriers Du Rail - Version Europe"
        titre1 = new Text("Les Aventuriers Du Rail - Version Europe");
        titre1.setFont(fontTradeWindsTitre1);
        titre1.setLayoutX(191);
        titre1.setLayoutY(41);
        //Titre 2 "Veuillez choisir un nombre de joueurs et renseigner leurs noms."
        titre2 = new Text("Veuillez choisir un nombre de joueurs et renseigner leurs noms.");
        titre2.setFont(fontTradeWindsTitre2);
        titre2.setLayoutX(174);
        titre2.setLayoutY(140);
        //Texte nbJoueurs
        nbJoueursTexte = new Text(String.valueOf(nbJoueurs.getValue()));
        nbJoueursTexte.setFont(fontTradeWindsTitre2);
        nbJoueursTexte.setLayoutX(123);
        nbJoueursTexte.setLayoutY(36);
        //Pane de boutons
        paneBoutons = new Pane();
        paneBoutons.setPrefSize(256, 61);
        paneBoutons.setLayoutX(334);
        paneBoutons.setLayoutY(219);
        paneBoutons.setStyle("-fx-background-radius: 10 10 10 10;" + "-fx-background-color: WHITE;");
        paneBoutons.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        //Input avatars
        try {
            avatarVert = new FileInputStream("ressources/images/images/avatar-VERT.png");
            avatarBleu = new FileInputStream("ressources/images/images/avatar-BLEU.png");
            avatarJaune = new FileInputStream("ressources/images/images/avatar-JAUNE.png");
            avatarRouge = new FileInputStream("ressources/images/images/avatar-ROUGE.png");
            avatarRose = new FileInputStream("ressources/images/images/avatar-ROSE.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Joueur vert
        joueurVert = new ImageView(new Image(avatarVert));
        joueurVert.setFitHeight(136);
        joueurVert.setFitWidth(108);
        joueurVert.setLayoutX(91);
        joueurVert.setLayoutY(337);
        //Joueur bleu
        joueurBleu = new ImageView(new Image(avatarBleu));
        joueurBleu.setFitHeight(136);
        joueurBleu.setFitWidth(108);
        joueurBleu.setLayoutX(250);
        joueurBleu.setLayoutY(337);
        //Joueur jaune
        joueurJaune = new ImageView(new Image(avatarJaune));
        joueurJaune.setFitHeight(136);
        joueurJaune.setFitWidth(108);
        joueurJaune.setLayoutX(408);
        joueurJaune.setLayoutY(337);
        //Joueur rouge
        joueurRouge = new ImageView(new Image(avatarRouge));
        joueurRouge.setFitHeight(136);
        joueurRouge.setFitWidth(108);
        joueurRouge.setLayoutX(567);
        joueurRouge.setLayoutY(337);
        //Joueur rouge
        joueurRose = new ImageView(new Image(avatarRose));
        joueurRose.setFitHeight(136);
        joueurRose.setFitWidth(108);
        joueurRose.setLayoutX(726);
        joueurRose.setLayoutY(337);
        //Textfield vert
        pseudoJoueurVert = new TextField();
        pseudoJoueurVert.setPromptText("Saisir un nom:");
        pseudoJoueurVert.setLayoutX(96);
        pseudoJoueurVert.setLayoutY(509);
        pseudoJoueurVert.setMaxSize(100, 20);
        pseudoJoueurVert.setStyle("-fx-background-radius: 0 0 0 0;" + "-fx-background-color: WHITE;");
        pseudoJoueurVert.setEffect(new DropShadow(5, 1, 1, Color.BLACK));
        pseudoJoueurVert.setFont(fontPseudos);
        //Textfield bleu
        pseudoJoueurBleu = new TextField();
        pseudoJoueurBleu.setPromptText("Saisir un nom:");
        pseudoJoueurBleu.setLayoutX(253);
        pseudoJoueurBleu.setLayoutY(509);
        pseudoJoueurBleu.setMaxSize(100, 20);
        pseudoJoueurBleu.setStyle("-fx-background-radius: 0 0 0 0;" + "-fx-background-color: WHITE;");
        pseudoJoueurBleu.setEffect(new DropShadow(5, 1, 1, Color.BLACK));
        pseudoJoueurBleu.setFont(fontPseudos);
        //Textfield rose
        pseudoJoueurRose = new TextField();
        pseudoJoueurRose.setPromptText("Saisir un nom:");
        pseudoJoueurRose.setLayoutX(733);
        pseudoJoueurRose.setLayoutY(509);
        pseudoJoueurRose.setMaxSize(100, 20);
        pseudoJoueurRose.setStyle("-fx-background-radius: 0 0 0 0;" + "-fx-background-color: WHITE;");
        pseudoJoueurRose.setEffect(new DropShadow(5, 1, 1, Color.BLACK));
        pseudoJoueurRose.setFont(fontPseudos);
        //Textfield rouge
        pseudoJoueurRouge = new TextField();
        pseudoJoueurRouge.setPromptText("Saisir un nom:");
        pseudoJoueurRouge.setLayoutX(573);
        pseudoJoueurRouge.setLayoutY(509);
        pseudoJoueurRouge.setMaxSize(100, 20);
        pseudoJoueurRouge.setStyle("-fx-background-radius: 0 0 0 0;" + "-fx-background-color: WHITE;");
        pseudoJoueurRouge.setEffect(new DropShadow(5, 1, 1, Color.BLACK));
        pseudoJoueurRouge.setFont(fontPseudos);
        //Textfield jaune
        pseudoJoueurJaune = new TextField();
        pseudoJoueurJaune.setPromptText("Saisir un nom:");
        pseudoJoueurJaune.setLayoutX(413);
        pseudoJoueurJaune.setLayoutY(509);
        pseudoJoueurJaune.setMaxSize(100, 20);
        pseudoJoueurJaune.setStyle("-fx-background-radius: 0 0 0 0;" + "-fx-background-color: WHITE;");
        pseudoJoueurJaune.setEffect(new DropShadow(5, 1, 1, Color.BLACK));
        pseudoJoueurJaune.setFont(fontPseudos);
        //Add avatars et pseudos
        pane.getChildren().addAll(joueurBleu, pseudoJoueurBleu, joueurVert, pseudoJoueurVert, joueurJaune, pseudoJoueurJaune, joueurRouge, pseudoJoueurRouge, joueurRose, pseudoJoueurRose);
        //Bouton ajouter joueur
        boutonAjouter = new Button();
        boutonAjouter.setStyle(
            "-fx-background-radius: 12em; " +
            "-fx-min-width: 40px; " +
            "-fx-min-height: 40px; " +
            "-fx-max-width: 40px; " +
            "-fx-max-height: 40px; " +
            "-fx-background-color: GREEN;");
        boutonAjouter.setLayoutX(202);
        boutonAjouter.setLayoutY(10);
        boutonAjouter.setOnAction(e -> {
            if(nbJoueurs.getValue()<5){
                nbJoueurs.setValue(nbJoueurs.getValue()+1);
                nbJoueursTexte.setText(String.valueOf(nbJoueurs.getValue()));
                changementNbJoueurs(nbJoueurs.getValue());
            }
        });
        //Bouton supprimer joueur
        boutonSupprimer = new Button();
        boutonSupprimer.setStyle(
            "-fx-background-radius: 12em; " +
            "-fx-min-width: 40px; " +
            "-fx-min-height: 40px; " +
            "-fx-max-width: 40px; " +
            "-fx-max-height: 40px; " +
            "-fx-background-color: RED;");
        boutonSupprimer.setLayoutX(12);
        boutonSupprimer.setLayoutY(10);
        boutonSupprimer.setOnAction(e -> {
            if(nbJoueurs.getValue()>2){
                nbJoueurs.setValue(nbJoueurs.getValue()-1);
                nbJoueursTexte.setText(String.valueOf(nbJoueurs.getValue()));
                changementNbJoueurs(nbJoueurs.getValue());
            }
        });
        //Texte boutons
        plus = new Text("+");
        plus.setFont(fontTradeWindsBoutons);
        plus.setLayoutX(214);
        plus.setLayoutY(40);
        plus.setOnMouseClicked(e -> {
            if(nbJoueurs.getValue()<5){
                nbJoueurs.setValue(nbJoueurs.getValue()+1);
                nbJoueursTexte.setText(String.valueOf(nbJoueurs.getValue()));
                changementNbJoueurs(nbJoueurs.getValue());
            }
        });
        moins = new Text("-");
        moins.setFont(fontTradeWindsBoutons);
        moins.setLayoutX(25);
        moins.setLayoutY(40);
        moins.setOnMouseClicked(e -> {
            if(nbJoueurs.getValue()>2){
                nbJoueurs.setValue(nbJoueurs.getValue()-1);
                nbJoueursTexte.setText(String.valueOf(nbJoueurs.getValue()));
                changementNbJoueurs(nbJoueurs.getValue());
            }
        });
        paneBoutons.getChildren().addAll(boutonSupprimer, moins, nbJoueursTexte, boutonAjouter, plus);
        //Bouton jouer
        jouer = new Button("Jouer");
        jouer.setFont(fontTradeWindsTitre2);
        jouer.setLayoutY(590);
        jouer.setLayoutX(375);
        jouer.setPrefSize(173, 42);
        jouer.setStyle("-fx-background-radius: 10 10 10 10;" + "-fx-background-color: WHITE;");
        jouer.setEffect(new DropShadow(5, 2, 2, Color.BLACK));
        //AddAll children
        pane.getChildren().addAll(titre1, titre2, paneBoutons, jouer);
        //Scene
        scene = new Scene(pane);
        //Stage
        setTitle("Choix des joueurs");
        setWidth(920);
        setHeight(720);
        setScene(scene);
    }

    public void changementNbJoueurs(int nbJoueurs){
        if(nbJoueurs==2){
            if(!pane.getChildren().contains(joueurBleu)){
                pane.getChildren().addAll(joueurBleu, pseudoJoueurBleu);
            }
            joueurBleu.setLayoutX(494);
            joueurBleu.setLayoutY(337);
            pseudoJoueurBleu.setLayoutX(510);
            pseudoJoueurBleu.setLayoutY(509);
            if(!pane.getChildren().contains(joueurVert)){
                pane.getChildren().addAll(joueurVert, pseudoJoueurVert);
            }
            joueurVert.setLayoutX(321);
            joueurVert.setLayoutY(337);
            pseudoJoueurVert.setLayoutX(334);
            pseudoJoueurVert.setLayoutY(509);
            if(pane.getChildren().contains(joueurJaune)){
                pane.getChildren().removeAll(joueurJaune, pseudoJoueurJaune);
            }
            if(pane.getChildren().contains(joueurRose)){
                pane.getChildren().removeAll(joueurRose, pseudoJoueurRose);
            }
            if(pane.getChildren().contains(joueurRouge)){
                pane.getChildren().removeAll(joueurRouge, pseudoJoueurRouge);
            }
        }else if(nbJoueurs==3){
            if(!pane.getChildren().contains(joueurBleu)){
                pane.getChildren().addAll(joueurBleu, pseudoJoueurBleu);
            }
            joueurBleu.setLayoutX(408);
            joueurBleu.setLayoutY(337);
            pseudoJoueurBleu.setLayoutX(421);
            pseudoJoueurBleu.setLayoutY(509);
            if(!pane.getChildren().contains(joueurVert)){
                pane.getChildren().addAll(joueurVert, pseudoJoueurVert);
            }
            joueurVert.setLayoutX(217);
            joueurVert.setLayoutY(337);
            pseudoJoueurVert.setLayoutX(230);
            pseudoJoueurVert.setLayoutY(509);
            if(!pane.getChildren().contains(joueurJaune)){
                pane.getChildren().addAll(joueurJaune, pseudoJoueurJaune);
            }
            joueurJaune.setLayoutX(599);
            joueurJaune.setLayoutY(337);
            pseudoJoueurJaune.setLayoutX(615);
            pseudoJoueurJaune.setLayoutY(509);
            if(pane.getChildren().contains(joueurRose)){
                pane.getChildren().removeAll(joueurRose, pseudoJoueurRose);
            }
            if(pane.getChildren().contains(joueurRouge)){
                pane.getChildren().removeAll(joueurRouge, pseudoJoueurRouge);
            }
        }else if(nbJoueurs==4){
            if(!pane.getChildren().contains(joueurBleu)){
                pane.getChildren().addAll(joueurBleu, pseudoJoueurBleu);
            }
            joueurBleu.setLayoutX(307);
            joueurBleu.setLayoutY(337);
            pseudoJoueurBleu.setLayoutX(320);
            pseudoJoueurBleu.setLayoutY(509);
            if(!pane.getChildren().contains(joueurVert)){
                pane.getChildren().addAll(joueurVert, pseudoJoueurVert);
            }
            joueurVert.setLayoutX(107);
            joueurVert.setLayoutY(337);
            pseudoJoueurVert.setLayoutX(120);
            pseudoJoueurVert.setLayoutY(509);
            if(!pane.getChildren().contains(joueurJaune)){
                pane.getChildren().addAll(joueurJaune, pseudoJoueurJaune);
            }
            joueurJaune.setLayoutX(507);
            joueurJaune.setLayoutY(337);
            pseudoJoueurJaune.setLayoutX(523);
            pseudoJoueurJaune.setLayoutY(509);
            if(!pane.getChildren().contains(joueurRouge)){
                pane.getChildren().addAll(joueurRouge, pseudoJoueurRouge);
            }
            joueurRouge.setLayoutX(707);
            joueurRouge.setLayoutY(337);
            pseudoJoueurRouge.setLayoutX(723);
            pseudoJoueurRouge.setLayoutY(509);
            if(pane.getChildren().contains(joueurRose)){
                pane.getChildren().removeAll(joueurRose, pseudoJoueurRose);
            }
        }else if(nbJoueurs==5){
            if(!pane.getChildren().contains(joueurBleu)){
                pane.getChildren().addAll(joueurBleu, pseudoJoueurBleu);
            }
            joueurBleu.setLayoutX(250);
            joueurBleu.setLayoutY(337);
            pseudoJoueurBleu.setLayoutX(253);
            pseudoJoueurBleu.setLayoutY(509);
            if(!pane.getChildren().contains(joueurVert)){
                pane.getChildren().addAll(joueurVert, pseudoJoueurVert);
            }
            joueurVert.setLayoutX(91);
            joueurVert.setLayoutY(337);
            pseudoJoueurVert.setLayoutX(96);
            pseudoJoueurVert.setLayoutY(509);
            if(!pane.getChildren().contains(joueurJaune)){
                pane.getChildren().addAll(joueurJaune, pseudoJoueurJaune);
            }
            joueurJaune.setLayoutX(408);
            joueurJaune.setLayoutY(337);
            pseudoJoueurJaune.setLayoutX(413);
            pseudoJoueurJaune.setLayoutY(509);
            if(!pane.getChildren().contains(joueurRose)){
                pane.getChildren().addAll(joueurRose, pseudoJoueurRose);
            }
            joueurRose.setLayoutX(726);
            joueurRose.setLayoutY(337);
            pseudoJoueurRose.setLayoutX(733);
            pseudoJoueurRose.setLayoutY(509);
            if(!pane.getChildren().contains(joueurRouge)){
                pane.getChildren().addAll(joueurRouge, pseudoJoueurRouge);
            }
            joueurRouge.setLayoutX(567);
            joueurRouge.setLayoutY(337);
            pseudoJoueurRouge.setLayoutX(573);
            pseudoJoueurRouge.setLayoutY(509);
        }
    }

    public void setNomDesJoueurs(){
        if(nbJoueurs.getValue() == 2){
            nomsJoueurs.addAll(pseudoJoueurBleu.getText(), pseudoJoueurVert.getText());
        }else if(nbJoueurs.getValue() == 3){
            nomsJoueurs.addAll(pseudoJoueurBleu.getText(), pseudoJoueurVert.getText(), pseudoJoueurJaune.getText());
        }else if(nbJoueurs.getValue() == 4){
            nomsJoueurs.addAll(pseudoJoueurBleu.getText(), pseudoJoueurVert.getText(), pseudoJoueurJaune.getText(), pseudoJoueurRouge.getText());
        }else{
            nomsJoueurs.addAll(pseudoJoueurBleu.getText(), pseudoJoueurVert.getText(), pseudoJoueurJaune.getText(), pseudoJoueurRouge.getText(), pseudoJoueurRose.getText());
        }
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis){

    }
    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return this.nbJoueurs.getValue();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return this.nomsJoueurs.get(playerNumber);
    }

}
