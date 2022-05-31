package fr.umontpellier.iut.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    public VuePlateau() {
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
    public void choixRouteOuVille() {
    }

    
}
