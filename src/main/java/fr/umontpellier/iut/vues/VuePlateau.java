package fr.umontpellier.iut.vues;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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

    private Lighting lighting;

    private boolean clique;

    private DropShadow dropShadow;

    private Button boutonToggle;

    private ImageView toggle;

    private FileInputStream lienToggle;

    public boolean listener = false;

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
        lighting = new Lighting(new Light.Distant(30, 30, Color.WHITE));
        lighting.setDiffuseConstant(100);
        lighting.setContentInput(dropShadow);
        //Image
        try {
            lienToggle = new FileInputStream("ressources/images/images/toggle-button.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        toggle = new ImageView(new Image(lienToggle));
        toggle.setFitHeight(40);
        toggle.setFitWidth(40);
        //Bouton
        boutonToggle = new Button();
        boutonToggle.setGraphic(toggle);
        boutonToggle.setLayoutX(1);
        boutonToggle.setLayoutY(5);
        boutonToggle.setShape(new Circle(0));
        boutonToggle.setOnAction(e -> {
            if(!clique){
                this.setEffect(lighting);
                clique = true;
            }else{
                this.setEffect(dropShadow);
                clique = false;
            }
        });
        //Ombre plateau
        dropShadow = new DropShadow(20, Color.BLACK);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        this.setEffect(dropShadow);
        getChildren().add(boutonToggle);
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
                                    rect.setFill(new ImagePattern(new Image("images/wagons/image-wagon-" + r.getProprietaire().getCouleur().toString().toUpperCase() + ".png")));
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
                                ville.setFill(new ImagePattern(new Image("images/gares/gare-" + v.getProprietaire().getCouleur().toString().toUpperCase() + ".png")));
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
//        Les dimensions de l'image varient avec celle de la scène
        image.fitWidthProperty().bind(getScene().widthProperty());
        image.fitHeightProperty().bind(getScene().heightProperty());
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
