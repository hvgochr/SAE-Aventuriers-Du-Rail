package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de
 * l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private Text titre;

    private DropShadow dropShadow;

    private Font fontTitre;
    private Font fontPasser;

    private IJeu jeu;

    private VuePlateau vuePlateau;

    private BorderPane choix;

    private VBox boxJoueurs;
    private VBox carteWagonVisibles;

    private ImageView piocheWagon;
    private ImageView piocheDestination;

    private Label instructions;

    private HBox bot;
    private HBox boxChoix;
    private HBox top;
    private VBox left;

    private Button passer;

    private VueJoueurCourant vueJoueurCourant;

    private VueAutresJoueurs vueAutreJoueur1;
    private VueAutresJoueurs vueAutreJoueur2;
    private VueAutresJoueurs vueAutreJoueur3;
    private VueAutresJoueurs vueAutreJoueur4;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        this.setPrefSize(1280, 720);
        // Font
        fontTitre = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 30);
        fontPasser = Font.loadFont("file:ressources/images/fonts/Trade_Winds/TradeWinds-Regular.ttf", 9);
        // BoxCarteWagon
        carteWagonVisibles = new VBox();
        carteWagonVisibles.setSpacing(6);
        // BoxJoueurs
        boxJoueurs = new VBox();
        boxJoueurs.setPrefSize(250, 512);
        boxJoueurs.setMaxSize(250, 512);
        boxJoueurs.setSpacing(9);
        // Choix
        choix = new BorderPane();
        // Instructions
        instructions = new Label(this.getJeu().instructionProperty().get());
        instructions.setFont(fontPasser);
        choix.setTop(instructions);
        BorderPane.setMargin(instructions, new Insets(0, 0, 10, 0));
        // BoxChoix
        boxChoix = new HBox();
        boxChoix.setSpacing(10);
        boxChoix.setPrefSize(798, 33);
        choix.setLeft(boxChoix);
        // DropShadow
        dropShadow = new DropShadow(10, Color.BLACK);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        // BoxTop
        top = new HBox();
        top.setSpacing(100);
        top.setPrefHeight(80);
        // Pioches
        piocheWagon = new ImageView(new Image("images/wagons.png"));
        piocheDestination = new ImageView(new Image("images/destinations.png"));
        piocheDestination.setFitHeight(61);
        piocheDestination.setFitWidth(99);
        piocheDestination.setEffect(dropShadow);
        piocheDestination.setOnMouseClicked(e -> {
            this.getJeu().uneDestinationAEtePiochee();
        });
        piocheWagon.setFitHeight(61);
        piocheWagon.setFitWidth(99);
        piocheWagon.setEffect(dropShadow);
        piocheWagon.setOnMouseClicked(e -> {
            this.getJeu().uneCarteWagonAEtePiochee();
        });
        // BoxLeft
        left = new VBox();
        left.setSpacing(20);
        left.getChildren().addAll(carteWagonVisibles, piocheWagon, piocheDestination);
        // Titre
        titre = new Text("Les Aventuriers Du Rail - Version Europe");
        titre.setFont(fontTitre);
        top.getChildren().add(titre);
        top.setAlignment(Pos.CENTER);
        // Plateau
        vuePlateau = new VuePlateau();
        // Passer
        passer = new Button("Passer");
        passer.setPrefSize(108, 33);
        passer.setStyle("-fx-background-color: #FBF8DC;");
        passer.setFont(fontPasser);
        passer.setEffect(dropShadow);
        choix.setRight(passer);
        // Bot
        bot = new HBox();
        bot.getChildren().addAll(choix);
        // This
        this.setTop(top);
        this.setLeft(left);
        this.setCenter(vuePlateau);
        this.setRight(boxJoueurs);
        this.setBottom(bot);
        VueDuJeu.setMargin(bot, new Insets(0, 0, 50, 100));
        VueDuJeu.setMargin(boxJoueurs, new Insets(0, 58, 0, 0));
        VueDuJeu.setMargin(left, new Insets(0, 0, 0, 58));
        VueDuJeu.setMargin(vuePlateau, new Insets(-65, 0, 0, 0));
        this.setStyle("-fx-background-color: #F2EDBF");
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        instructions.textProperty().bind(this.getJeu().instructionProperty());
        this.boxJoueurs.prefWidthProperty().bind(this.getScene().widthProperty().multiply(0.2));
        this.boxJoueurs.prefHeightProperty().bind(this.getScene().heightProperty().multiply(0.2));
        vuePlateau.creerBindings();
        this.getJeu().destinationsInitialesProperty().addListener(destinationsSontPiocheesListener);
        this.getJeu().cartesWagonVisiblesProperty().addListener(listeCarte);
        this.getJeu().joueurCourantProperty().addListener(joueurChangeListener);
        passer.setOnAction(e -> {
            jeu.passerAEteChoisi();
        });
    }

    private final ListChangeListener<IDestination> destinationsSontPiocheesListener = change -> {
        Platform.runLater(() -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (IDestination d : change.getAddedSubList()) {
                        Button b = new Button(d.getNom());
                        b.setStyle("-fx-background-color: #FBF8DC");
                        b.setPrefHeight(25);
                        b.setFont(fontPasser);
                        b.setEffect(dropShadow);
                        boxChoix.getChildren().add(b);
                        b.setOnAction(e -> {
                            if (boxChoix.getChildren().size() > 2) {
                                boxChoix.getChildren().remove(b);
                            }
                            jeu.uneDestinationAEteChoisie(d.getNom());
                        });
                    }
                } else if (change.wasRemoved()) {
                    for (IDestination d : change.getRemoved()) {
                        boxChoix.getChildren().remove(trouveButtonDestination(d));
                    }
                }
            }
        });
    };

    private final ListChangeListener<ICouleurWagon> listeCarte = action -> Platform.runLater(() -> {
        while (action.next()) {
            if (action.wasAdded()) {
                for (ICouleurWagon couleurWagon : action.getAddedSubList()) {
                    VueCarteWagon vueCarteWagon = new VueCarteWagon(couleurWagon);
                    vueCarteWagon.setEffect(dropShadow);
                    vueCarteWagon.setOnMouseClicked(e -> {
                        // carteWagonVisibles.getChildren().remove(carte);
                        jeu.uneCarteWagonAEteChoisie(couleurWagon);
                    });
                    carteWagonVisibles.getChildren().add(vueCarteWagon);
                }
            } else if (action.wasRemoved()) {
                for (ICouleurWagon couleurWagon : action.getRemoved()) {
                    carteWagonVisibles.getChildren().remove(trouveCarte(couleurWagon));
                }
            }
            if (carteWagonVisibles.getChildren().size() > 5) {
                carteWagonVisibles.getChildren().clear();
                System.out.println("CLEAR");
                for (ICouleurWagon c : jeu.cartesWagonVisiblesProperty()) {
                    VueCarteWagon carte = new VueCarteWagon(c);
                    carte.setOnMouseClicked(a -> jeu.uneCarteWagonAEteChoisie(c));
                    carteWagonVisibles.getChildren().add(carte);
                }
            }
        }
    });

    private final ChangeListener<IJoueur> joueurChangeListener = (observableValue, ancienJoueur, nouveauJoueur) -> {
        Platform.runLater(() -> {
            this.vueJoueurCourant = new VueJoueurCourant(nouveauJoueur, jeu);
            boxJoueurs.getChildren().clear();
            changementOrdreDesJoueurs(nouveauJoueur.getOrdreJoueur());
        });
    };

    private Button trouveButtonDestination(IDestination d) {
        for (Node n : boxChoix.getChildren()) {
            Button buttonDestination = (Button) n;
            if (buttonDestination.getText().equals(d.getNom())) {
                return buttonDestination;
            }
        }
        return null;
    }

    private Label trouveCarte(ICouleurWagon c) {
        Label res = null;
        for (Node n : carteWagonVisibles.getChildren()) {
            VueCarteWagon labelCouleurWagon = (VueCarteWagon) n;
            if (labelCouleurWagon.getCouleurWagon().toString().equals(c.toString())) {
                res = labelCouleurWagon;
            }
        }
        return res;
    }

    public void changementOrdreDesJoueurs(int numJoueurCourant) {
        // NbJoueurs = 2
        if (jeu.getJoueurs().size() == 2) {
            if (numJoueurCourant == 0) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1);
            } else if (numJoueurCourant == 1) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant);
            }
            hoverProperties(2);
        }
        // NbJoueurs = 3
        else if (jeu.getJoueurs().size() == 3) {
            if (numJoueurCourant == 0) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2);
            } else if (numJoueurCourant == 1) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant, vueAutreJoueur2);
            } else if (numJoueurCourant == 2) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueJoueurCourant);
            }
            hoverProperties(3);
        }
        // NbJoueurs = 4
        else if (jeu.getJoueurs().size() == 4) {
            if (numJoueurCourant == 0) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3);
            } else if (numJoueurCourant == 1) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant, vueAutreJoueur2, vueAutreJoueur3);
            } else if (numJoueurCourant == 2) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueJoueurCourant, vueAutreJoueur3);
            } else if (numJoueurCourant == 3) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueJoueurCourant);
            }
            hoverProperties(4);
        }
        // NbJoueurs = 5
        else if (jeu.getJoueurs().size() == 5) {
            if (numJoueurCourant == 0) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueJoueurCourant, vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3,
                        vueAutreJoueur4);
            } else if (numJoueurCourant == 1) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueJoueurCourant, vueAutreJoueur2, vueAutreJoueur3,
                        vueAutreJoueur4);
            } else if (numJoueurCourant == 2) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueJoueurCourant, vueAutreJoueur3,
                        vueAutreJoueur4);
            } else if (numJoueurCourant == 3) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(4));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueJoueurCourant,
                        vueAutreJoueur4);
            } else if (numJoueurCourant == 4) {
                vueAutreJoueur1 = new VueAutresJoueurs(jeu.getJoueurs().get(0));
                vueAutreJoueur2 = new VueAutresJoueurs(jeu.getJoueurs().get(1));
                vueAutreJoueur3 = new VueAutresJoueurs(jeu.getJoueurs().get(2));
                vueAutreJoueur4 = new VueAutresJoueurs(jeu.getJoueurs().get(3));
                boxJoueurs.getChildren().addAll(vueAutreJoueur1, vueAutreJoueur2, vueAutreJoueur3, vueAutreJoueur4,
                        vueJoueurCourant);
            }
            hoverProperties(5);
        }
    }

    public int getNbLocomotives() {
        int res = 0;
        for (int i = 0; i < this.getJeu().cartesWagonVisiblesProperty().size(); i++) {
            if (this.getJeu().cartesWagonVisiblesProperty().get(i).equals(CouleurWagon.LOCOMOTIVE)) {
                res++;
            }
        }
        return res;
    }

    /**
     * <p>
     * Méthode permettant d'initialiser les hover properties de toutes les vues des
     * joueurs autres que le joueur courant.
     * </p>
     * <p>
     * Préciser le nombre de joueurs en paramètre.
     * </p>
     * 
     * @param nbJoueurs
     * @return void
     */
    public void hoverProperties(int nbJoueurs) {
        if (nbJoueurs == 2) {
            vueAutreJoueur1.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur1.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur1.shrink();
                }
            });
        } else if (nbJoueurs == 3) {
            vueAutreJoueur1.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur1.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur1.shrink();
                }
            });
            vueAutreJoueur2.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur2.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur2.shrink();
                }
            });
        } else if (nbJoueurs == 4) {
            vueAutreJoueur1.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur1.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur1.shrink();
                }
            });
            vueAutreJoueur2.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur2.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur2.shrink();
                }
            });
            vueAutreJoueur3.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur3.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur3.shrink();
                }
            });
        } else if (nbJoueurs == 5) {
            vueAutreJoueur1.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur1.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur1.shrink();
                }
            });
            vueAutreJoueur2.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur2.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur2.shrink();
                }
            });
            vueAutreJoueur3.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur3.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur3.shrink();
                }
            });
            vueAutreJoueur4.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    vueAutreJoueur4.expand();
                    vueJoueurCourant.shrink();
                } else {
                    vueJoueurCourant.expand();
                    vueAutreJoueur4.shrink();
                }
            });
        }
    }

}
