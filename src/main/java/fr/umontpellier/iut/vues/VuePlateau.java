package fr.umontpellier.iut.vues;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.Ville;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    private DropShadow dropShadow;
    private ColorAdjust lighting;

    private boolean listener = false;

    private Circle boutonToggle;

    private boolean clique = false;

    public VuePlateau() {
        this.setPrefSize(726, 468);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Lighting
        lighting = new ColorAdjust();
        lighting.setBrightness(0.7);
        //Bouton toggle
        boutonToggle = new Circle(10);
        boutonToggle.setLayoutX(13);
        boutonToggle.setLayoutY(13);
        try {
            boutonToggle.setFill(new ImagePattern(new Image(new FileInputStream("ressources/images/images/toggle-button.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boutonToggle.setOnMouseClicked(e -> {
            if(!clique){
                image.setEffect(lighting);
                clique = true;
            }else{
                image.setEffect(dropShadow);
                clique = false;
            }
        });
        //Ombre plateau
        dropShadow = new DropShadow(20, Color.BLACK);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        this.setEffect(dropShadow);
        this.getChildren().add(boutonToggle);
    }

    @FXML
    ImageView image;

    @FXML
    private Group villes;

    @FXML
    private Group routes;

    @FXML
    public void choixRouteOuVille(MouseEvent e) {
        IJeu jeu = ((VueDuJeu) getScene().getRoot()).getJeu();
        String source = String.valueOf(e.getSource());
        if (source.startsWith("Group")) {
            source = source.substring(9);
            source = source.replace(source.substring(source.length() - 1), "");
            jeu.uneVilleOuUneRouteAEteChoisie(source);
        } else {
            jeu.uneVilleOuUneRouteAEteChoisie(e.getPickResult().getIntersectedNode().getId());
        }
        if(!listener){
            for(int i=0; i<jeu.getRoutes().size(); i++){
                Route r = (Route) jeu.getRoutes().get(i);
                if(r.getProprietaire() == null){
                    r.proprietaireProperty().addListener(e1 -> {
                        for (Node n : routes.getChildren()) {
                            Group gRoute = (Group) n;
                            String stringRoute = String.valueOf(n);
                            if (stringRoute.startsWith("Group")) {
                                stringRoute = stringRoute.substring(9);
                                stringRoute = stringRoute.replace(stringRoute.substring(stringRoute.length() - 1), "");
                            }
                            if(stringRoute.toUpperCase().equals(r.getNom().toUpperCase())){
                                for (Node nR : gRoute.getChildren()) {
                                    Rectangle rect = (Rectangle) nR;
                                    Image imgWagon = new Image("images/wagons/image-wagon-" + r.getProprietaire().getCouleur().toString().toUpperCase() + ".png");
                                    rect.setFill(new ImagePattern(imgWagon));
                                }
                            }
                        }
                    });
                }
            }
            for(int i=0; i<jeu.getVilles().size(); i++){
                Ville v = (Ville) jeu.getVilles().get(i);
                if(v.getProprietaire() == null){
                    v.proprietaireProperty().addListener(e1 -> {
                        for (Node n : villes.getChildren()) {
                            if(n.getId().equals(v.getNom())){
                                Circle ville = (Circle) n;
                                switch (v.getProprietaire().getCouleur()) {
                                    case BLEU:
                                        ville.setFill(Paint.valueOf("34A9D5"));
                                        break;
                                    case JAUNE:
                                        ville.setFill(Paint.valueOf("FBBE00"));
                                        break;
                                    case ROSE:
                                        ville.setFill(Paint.valueOf("A04ABE"));
                                        break;
                                    case ROUGE:
                                        ville.setFill(Paint.valueOf("D1332C"));
                                        break;
                                    case VERT:
                                        ville.setFill(Paint.valueOf("3FA73D"));
                                        break;
                                }
                            }
                        }
                    });
                }
            }
            listener = true;
        }
    }

    public void creerBindings() {
        bindRedimensionPlateau();
    }

    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();
        //Les dimensions de l'image varient avec celle de la scène
        image.fitWidthProperty().bind(getScene().widthProperty().multiply(0.65));
        image.fitHeightProperty().bind(getScene().heightProperty().multiply(0.65));
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });
        rect.widthProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return DonneesPlateau.largeurRectangle * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.heightProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurRectangle * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
        rect.xProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return DonneesPlateau.xInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.yProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return DonneesPlateau.yInitial * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }

}
