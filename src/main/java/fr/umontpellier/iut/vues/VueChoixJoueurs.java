package fr.umontpellier.iut.vues;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
    private Font fontTradeWindsTitre1;
    private Font fontTradeWindsTitre2;
    private Font fontTradeWindsBoutons;
    private HBox boiteHorizontale;
    private Button boutonAjouter;
    private Button boutonSupprimer;
    
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
        pane.setPrefWidth(920);
        pane.setPrefHeight(720);
        //Fonts
        fontTradeWindsTitre1 = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 26);
        fontTradeWindsTitre2 = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 18);
        fontTradeWindsBoutons = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 28);
        //Titre 1 "Aventuriers Du Rail - Version Europe"
        titre1 = new Text("Les Aventuriers Du Rail - Version Europe");
        titre1.setFont(fontTradeWindsTitre1);
        //titre1.setEffect(new DropShadow(1, 1, 1, Color.BLACK));
        titre1.setLayoutX(191);
        titre1.setLayoutY(41);
        //A voir si ombre ou non
        //Titre 2 "Veuillez choisir un nombre de joueurs et renseigner leurs noms."
        titre2 = new Text("Veuillez choisir un nombre de joueurs et renseigner leurs noms.");
        titre2.setFont(fontTradeWindsTitre2);
        //A voir si ombre ou non
        //titre2.setEffect(new DropShadow(1, 1, 1, Color.BLACK)); 
        titre2.setLayoutX(174);
        titre2.setLayoutY(140);
        //Bouton ajouter joueur
        boutonAjouter = new Button("+");
        boutonAjouter.setFont(fontTradeWindsBoutons);
        //Bouton supprimer joueur
        boutonSupprimer = new Button("-");
        boutonSupprimer.setFont(fontTradeWindsBoutons);
        //HBox
        boiteHorizontale = new HBox();
        boiteHorizontale.setPrefWidth(256);
        boiteHorizontale.setPrefHeight(61);
        boiteHorizontale.setLayoutX(334);
        boiteHorizontale.setLayoutY(219);
        boiteHorizontale.setStyle("-fx-background-color: WHITE");
        boiteHorizontale.setEffect(new DropShadow(10, 3, 3, Color.BLACK));
        boiteHorizontale.getChildren().addAll(boutonAjouter, boutonSupprimer);
        //AddAll children
        pane.getChildren().addAll(titre1, titre2, boiteHorizontale);
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
