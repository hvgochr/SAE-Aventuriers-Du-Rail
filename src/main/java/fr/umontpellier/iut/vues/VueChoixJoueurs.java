package fr.umontpellier.iut.vues;

import javafx.beans.value.ChangeListener;
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

    private int nbJoueurs = 2;

    private Font fontTradeWindsTitre1;
    private Font fontTradeWindsTitre2;
    private Font fontTradeWindsBoutons;

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
    private Button jouer;
    
    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();
        //Pane
        pane = new Pane();
        pane.setStyle("-fx-background-color: #F2EDBF");
        pane.setPrefSize(920, 720);
        //Fonts
        fontTradeWindsTitre1 = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 26);
        fontTradeWindsTitre2 = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 18);
        fontTradeWindsBoutons = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 28);
        //Titre 1 "Aventuriers Du Rail - Version Europe"
        titre1 = new Text("Les Aventuriers Du Rail - Version Europe");
        titre1.setFont(fontTradeWindsTitre1);
        //A voir si ombre ou non
        //titre1.setEffect(new DropShadow(1, 1, 1, Color.BLACK));
        titre1.setLayoutX(191);
        titre1.setLayoutY(41);
        //Titre 2 "Veuillez choisir un nombre de joueurs et renseigner leurs noms."
        titre2 = new Text("Veuillez choisir un nombre de joueurs et renseigner leurs noms.");
        titre2.setFont(fontTradeWindsTitre2);
        //A voir si ombre ou non
        //titre2.setEffect(new DropShadow(1, 1, 1, Color.BLACK)); 
        titre2.setLayoutX(174);
        titre2.setLayoutY(140);
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
            if(nbJoueurs<5){
                nbJoueurs++; 
                nbJoueursTexte.setText(String.valueOf(nbJoueurs));
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
            if(nbJoueurs>2){
                nbJoueurs--; 
                nbJoueursTexte.setText(String.valueOf(nbJoueurs));
            }
        });
        //Texte boutons
        plus = new Text("+");
        plus.setFont(fontTradeWindsBoutons);
        plus.setLayoutX(214);
        plus.setLayoutY(40);
        plus.setOnMouseClicked(e -> {
            if(nbJoueurs<5){
                nbJoueurs++; 
                nbJoueursTexte.setText(String.valueOf(nbJoueurs));
            }
        });
        moins = new Text("-");
        moins.setFont(fontTradeWindsBoutons);
        moins.setLayoutX(25);
        moins.setLayoutY(40);
        moins.setOnMouseClicked(e -> {
            if(nbJoueurs>2){
                nbJoueurs--; 
                nbJoueursTexte.setText(String.valueOf(nbJoueurs));
            }
        });
        //Texte nbJoueurs
        nbJoueursTexte = new Text(String.valueOf(nbJoueurs));
        nbJoueursTexte.setFont(fontTradeWindsTitre2);
        nbJoueursTexte.setLayoutX(123);
        nbJoueursTexte.setLayoutY(36);
        //Pane de boutons
        paneBoutons = new Pane();
        paneBoutons.setPrefSize(256, 61);
        paneBoutons.setLayoutX(334);
        paneBoutons.setLayoutY(219);
        paneBoutons.setStyle("-fx-background-color: WHITE");
        paneBoutons.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        paneBoutons.getChildren().addAll(boutonSupprimer, moins, nbJoueursTexte, boutonAjouter, plus);
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
        pseudoJoueurVert.setLayoutX(96);
        pseudoJoueurVert.setLayoutY(509);
        pseudoJoueurVert.setMaxSize(100, 20);
        //Textfield bleu
        pseudoJoueurBleu = new TextField();
        pseudoJoueurBleu.setLayoutX(253);
        pseudoJoueurBleu.setLayoutY(509);
        pseudoJoueurBleu.setMaxSize(100, 20);
        //Textfield rose
        pseudoJoueurRose = new TextField();
        pseudoJoueurRose.setLayoutX(413);
        pseudoJoueurRose.setLayoutY(509);
        pseudoJoueurRose.setMaxSize(100, 20);
        //Textfield rouge
        pseudoJoueurRouge = new TextField();
        pseudoJoueurRouge.setLayoutX(573);
        pseudoJoueurRouge.setLayoutY(509);
        pseudoJoueurRouge.setMaxSize(100, 20);
        //Textfield jaune
        pseudoJoueurJaune = new TextField();
        pseudoJoueurJaune.setLayoutX(733);
        pseudoJoueurJaune.setLayoutY(509);
        pseudoJoueurJaune.setMaxSize(100, 20);
        //Bouton jouer
        jouer = new Button("Jouer");
        jouer.setFont(fontTradeWindsTitre2);
        jouer.setLayoutY(590);
        jouer.setLayoutX(375);
        jouer.setPrefSize(173, 42);
        jouer.setStyle("-fx-background-color: WHITE");
        jouer.setEffect(new DropShadow(5, 2, 2, Color.BLACK));
        //AddAll children
        pane.getChildren().addAll(titre1, titre2, paneBoutons, joueurVert, pseudoJoueurVert, joueurBleu, pseudoJoueurBleu, joueurJaune, pseudoJoueurJaune, joueurRouge, pseudoJoueurRouge, joueurRose, pseudoJoueurRose, jouer);
        //Scene
        scene = new Scene(pane);
        //Stage
        setTitle("Choix des joueurs");
        setWidth(920);
        setHeight(720);
        setScene(scene);
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {}

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {}

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

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return this.nomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return this.nomsJoueurs.get(playerNumber);
    }

}
