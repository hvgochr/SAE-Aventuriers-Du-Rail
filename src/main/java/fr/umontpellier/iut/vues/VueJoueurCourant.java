package fr.umontpellier.iut.vues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private Font fontDestinations;

    private VBox destinationsJoueur;

    private GridPane cartesJoueur;

    private IntegerProperty nbOrange = new SimpleIntegerProperty(0);
    private IntegerProperty nbBlanc = new SimpleIntegerProperty(0);
    private IntegerProperty nbVert = new SimpleIntegerProperty(0);
    private IntegerProperty nbRouge = new SimpleIntegerProperty(0);
    private IntegerProperty nbBleu = new SimpleIntegerProperty(0);
    private IntegerProperty nbNoir = new SimpleIntegerProperty(0);
    private IntegerProperty nbJaune = new SimpleIntegerProperty(0);
    private IntegerProperty nbRose = new SimpleIntegerProperty(0);
    private IntegerProperty nbLocomotive = new SimpleIntegerProperty(0);

    private DropShadow dropShadow;

    public VueJoueurCourant(IJoueur joueur){
        this.joueurCourant = joueur;
        this.creerBindings();
        //Font
        fontPseudo = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 10);
        fontDestinations = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 8);
        //Cartes joueur
        cartesJoueur = new GridPane();
        this.initIndicesNbCartes();
        //Cartes
        cartesJoueur.setPrefSize(233, 112);
        cartesJoueur.setHgap(6);
        cartesJoueur.setVgap(5);
        cartesJoueur.add(this.carteWagonPlusIndice("blanc"), 1, 0);
        cartesJoueur.add(this.carteWagonPlusIndice("bleu"), 0, 1);
        cartesJoueur.add(this.carteWagonPlusIndice("jaune"), 2, 1);
        cartesJoueur.add(this.carteWagonPlusIndice("locomotive"), 1, 2);
        cartesJoueur.add(this.carteWagonPlusIndice("noir"), 1, 1);
        cartesJoueur.add(this.carteWagonPlusIndice("orange"), 0, 0);
        cartesJoueur.add(this.carteWagonPlusIndice("rose"), 3, 1);
        cartesJoueur.add(this.carteWagonPlusIndice("rouge"), 3, 0);
        cartesJoueur.add(this.carteWagonPlusIndice("vert"), 2, 0);
        GridPane.setMargin(this.getNodeByRowColumnIndex(2, 1), new Insets(0, 0, 0, 29));
        GridPane.setMargin(this.getNodeByRowColumnIndex(1, 2), new Insets(0, 0, 0, -29));
        GridPane.setMargin(this.getNodeByRowColumnIndex(0, 2), new Insets(0, 0, 0, -29));
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
        this.setPrefSize(250, 240);
        this.setMaxSize(250, 240);
        VueJoueurCourant.setMargin(destinationsJoueur, new Insets(0, 0, 0, 5));
        VueJoueurCourant.setMargin(cartesJoueur, new Insets(0, 0, 5, 5));
        getChildren().addAll(vueJoueur, destinationsJoueur, cartesJoueur);
    }

    public void creerBindings(){
        this.joueurCourant.cartesWagonProperty().addListener(listeCarte);
    }

    private final ListChangeListener<ICouleurWagon> listeCarte = e -> {
        Platform.runLater(() -> {
            while(e.next()){
                if(e.wasAdded()){
                    for(ICouleurWagon c : e.getAddedSubList()){
                        if(c.toString().toUpperCase().equals("BLANC")){
                            nbBlanc.setValue(nbBlanc.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("ORANGE")){
                            nbOrange.setValue(nbOrange.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("VERT")){
                            nbVert.setValue(nbVert.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("ROUGE")){
                            nbRouge.setValue(nbRouge.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("BLEU")){
                            nbBleu.setValue(nbBleu.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("NOIR")){
                            nbNoir.setValue(nbNoir.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("JAUNE")){
                            nbJaune.setValue(nbJaune.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("ROSE")){
                            nbRose.setValue(nbRose.getValue()+1);
                        }else if(c.toString().toUpperCase().equals("LOCOMOTIVE")){
                            nbLocomotive.setValue(nbLocomotive.getValue()+1);
                        }
                    }
                }else if(e.wasRemoved()){
                    for(ICouleurWagon c : e.getRemoved()){
                        if(c.toString().toUpperCase().equals("BLANC")){
                            nbBlanc.setValue(nbBlanc.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("ORANGE")){
                            nbOrange.setValue(nbOrange.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("VERT")){
                            nbVert.setValue(nbVert.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("ROUGE")){
                            nbRouge.setValue(nbRouge.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("BLEU")){
                            nbBleu.setValue(nbBleu.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("NOIR")){
                            nbNoir.setValue(nbNoir.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("JAUNE")){
                            nbJaune.setValue(nbJaune.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("ROSE")){
                            nbRose.setValue(nbRose.getValue()-1);
                        }else if(c.toString().toUpperCase().equals("LOCOMOTIVE")){
                            nbLocomotive.setValue(nbLocomotive.getValue()-1);
                        }
                    }
                }
            }
        });
    };

    public Node getNodeByRowColumnIndex (int row, int column) {
        ObservableList<Node> children = this.cartesJoueur.getChildren();
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public void initIndicesNbCartes(){
        for(int i=0; i<this.joueurCourant.getCartesWagon().size(); i++){
            if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("BLANC")){
                nbBlanc.setValue(nbBlanc.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("ORANGE")){
                nbOrange.setValue(nbOrange.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("VERT")){
                nbVert.setValue(nbVert.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("ROUGE")){
                nbRouge.setValue(nbRouge.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("BLEU")){
                nbBleu.setValue(nbBleu.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("NOIR")){
                nbNoir.setValue(nbNoir.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("JAUNE")){
                nbJaune.setValue(nbJaune.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("ROSE")){
                nbRose.setValue(nbRose.getValue()+1);
            }else if(this.joueurCourant.getCartesWagon().get(i).toString().toUpperCase().equals("LOCOMOTIVE")){
                nbLocomotive.setValue(nbLocomotive.getValue()+1);
            }
        }
    }

    private Pane carteWagonPlusIndice(String couleur){
        Pane res = new Pane();
        res.setPrefSize(56, 37);
        ImageView carte = new ImageView(new Image("images/cartesWagons/carte-wagon-" + couleur.toString().toUpperCase() + ".png"));
        Circle cercle = new Circle(5);
        StringProperty stringIndice = new SimpleStringProperty();
        Text indice = new Text();
        if(couleur.toUpperCase().equals("BLANC")){
            stringIndice.set(String.valueOf(nbBlanc.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("ORANGE")){
            stringIndice.set(String.valueOf(nbOrange.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("VERT")){
            stringIndice.set(String.valueOf(nbVert.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("ROUGE")){
            stringIndice.set(String.valueOf(nbRouge.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("BLEU")){
            stringIndice.set(String.valueOf(nbBleu.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("NOIR")){
            stringIndice.set(String.valueOf(nbNoir.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("JAUNE")){
            stringIndice.set(String.valueOf(nbJaune.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("ROSE")){
            stringIndice.set(String.valueOf(nbRose.getValue()));
            indice.setText(stringIndice.get());
        }else if(couleur.toUpperCase().equals("LOCOMOTIVE")){
            stringIndice.set(String.valueOf(nbLocomotive.getValue()));
            indice.setText(stringIndice.get());
        }
        carte.setLayoutX(0);
        carte.setLayoutY(0);
        cercle.setLayoutX(50);
        cercle.setLayoutY(32);
        cercle.setFill(Color.valueOf("#D9D9D9"));
        cercle.setStroke(Color.BLACK);
        cercle.setStrokeWidth(0.3);
        indice.setFont(Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 6));
        indice.setLayoutX(48);
        indice.setLayoutY(34);
        carte.setFitHeight(33);
        carte.setFitWidth(52);
        res.getChildren().addAll(carte, cercle, indice);
        return res;
    }

}
