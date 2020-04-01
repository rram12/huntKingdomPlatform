/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Entrainement;
import Services.AnimalService;
import Services.TrainingService;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class TrainingController implements Initializable {

    @FXML
    private Pane last;
    @FXML
    private Label catlast;
    @FXML
    private Label hnlast;
    @FXML
    private Label pricelast;
    @FXML
    private Label tdlast;
    @FXML
    private Label tplast;
    @FXML
    private ImageView acceptlast;
    @FXML
    private ImageView animallast;
    @FXML
    private ImageView produitlast;

    private Entrainement globalCurrent;

    private List<Entrainement> entr = new ArrayList<Entrainement>();

    @FXML
    private HBox tcontainer;
    @FXML
    private Pane examplePane;
    @FXML
    private Button Add;
    @FXML
    private AnchorPane main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Services.TrainingService SEntr = new TrainingService();
        Services.AnimalService SA = new AnimalService();

        globalCurrent = new Entrainement();
        examplePane.setVisible(false);

        try {

            Entrainement elast = SEntr.getById(SEntr.getLastTraining());

            catlast.setText("Training For " + elast.getCategorie());
            hnlast.setText("Hours Number : " + elast.getNbHeures());
            pricelast.setText("Price : " + elast.getPrix() + "DT");
            tdlast.setText("Training Date : " + elast.getDateEnt());
            tplast.setText("Training Place : " + elast.getLieu());
            if ("encours".equals(elast.getAccepter())) {
                acceptlast.setImage(new Image("/Uploads/loading.gif"));
            } else {
                acceptlast.setImage(new Image("/Uploads/checked.png"));
            }
            Image animalImage = new Image("file:C:/Users/tibh/Desktop/Pidev2020/copieJava/copie4/HuntKingdom/src/Uploads/" + SA.getById(elast.getAnimalId()).getImage_animal());
            animallast.setImage(animalImage);
            produitlast.setImage(new Image("/Uploads/image.jpg"));
            ImageView supp = new ImageView();
                 supp.setFitHeight(20);
                supp.setFitWidth(20);
                supp.setPreserveRatio(false);
                Image sup = new Image("Uploads/bin.png");
                supp.setImage(sup);                
                 Button supprimer = new Button("",supp);
                 supprimer.setStyle("-fx-background-color: transparent");
                
                supprimer.setLayoutX(440);
                supprimer.setLayoutY(0);


                supprimer.setOnMouseClicked((MouseEvent e) -> { 
                    Services.TrainingService ST = new TrainingService();
                    try {
                        ST.deleteTraining(elast.getId()); 
                        AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml")); 
                        main.getChildren().setAll(redirected);
                    } catch (SQLException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                last.getChildren().add(supprimer);
            List<Entrainement> e = SEntr.getTrainings();
            System.out.println(e);
            afficher(e);

        } catch (SQLException ex) {
            Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void afficher(List<Entrainement> entrs) throws SQLException {

        Services.TrainingService SEntr = new TrainingService();
        Services.AnimalService SA = new AnimalService();

        tcontainer.getChildren().clear();

        for (int i = 0; i < entrs.size(); i++) {
            Entrainement current = entrs.get(i);
            Pane entpane = new Pane();
            entpane.setPrefHeight(256);
            entpane.setPrefWidth(200);

            if (current.getId() == SEntr.getLastTraining()) {
                entpane.getChildren().clear();
            } else {

                Label categorie = new Label();
                categorie.setTextFill(Color.WHITE);
                categorie.setStyle("-fx-font-weight: bold");
                //categorie.setStyle("-fx-font-size: 20px");
                categorie.setText("Training For " + current.getCategorie());
                categorie.setLayoutX(1);
                categorie.setLayoutY(1);
                entpane.getChildren().add(categorie);

                
                final ImageView animalImage = new ImageView();

                animalImage.setLayoutX(13);
                animalImage.setLayoutY(19);
                animalImage.setFitHeight(86);
                animalImage.setFitWidth(86);

                Image animal = new Image("file:C:/Users/tibh/Desktop/Pidev2020/copieJava/copie4/HuntKingdom/src/Uploads/" + SA.getById(current.getAnimalId()).getImage_animal());
                animalImage.setImage(animal);

                entpane.getChildren().add(animalImage);

                final ImageView produitImage = new ImageView();

                produitImage.setLayoutX(124);
                produitImage.setLayoutY(19);
                produitImage.setFitHeight(86);
                produitImage.setFitWidth(86);

                produitImage.setImage(new Image("/Uploads/image.jpg"));

                entpane.getChildren().add(produitImage);

                Label hn = new Label();
                hn.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                hn.setText("Hours Number : " + current.getNbHeures());
                hn.setLayoutX(21);
                hn.setLayoutY(135);
                entpane.getChildren().add(hn);

                Label price = new Label();
                price.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                price.setText("Price : " + current.getPrix() + "DT");
                price.setLayoutX(21);
                price.setLayoutY(160);
                entpane.getChildren().add(price);

                Label dateT = new Label();
                dateT.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                dateT.setText("Training Date : " + current.getDateEnt());
                dateT.setLayoutX(21);
                dateT.setLayoutY(187);
                entpane.getChildren().add(dateT);

                Label place = new Label();
                place.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                place.setText("Training Place : " + current.getLieu());
                place.setLayoutX(21);
                place.setLayoutY(214);
                entpane.getChildren().add(place);

                final ImageView acceptImage = new ImageView();

                acceptImage.setLayoutX(183);
                acceptImage.setLayoutY(226);
                acceptImage.setFitHeight(45);
                acceptImage.setFitWidth(45);

                if ("encours".equals(current.getAccepter())) {
                    acceptImage.setImage(new Image("/Uploads/loading.gif"));
                } else {
                    acceptImage.setImage(new Image("/Uploads/checked.png"));
                }

                entpane.getChildren().add(acceptImage);
             
                ImageView supp = new ImageView();
                 supp.setFitHeight(20);
                supp.setFitWidth(20);
                supp.setPreserveRatio(false);
                Image sup = new Image("Uploads/bin.png");
                supp.setImage(sup);                
                 Button supprimer = new Button("",supp);
                 supprimer.setStyle("-fx-background-color: transparent");
                
                supprimer.setLayoutX(183);
                supprimer.setLayoutY(190);


                supprimer.setOnMouseClicked((MouseEvent e) -> { 
                    Services.TrainingService ST = new TrainingService();
                    try {
                        ST.deleteTraining(current.getId()); 
                        AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml")); 
                        main.getChildren().setAll(redirected);
                    } catch (SQLException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                entpane.getChildren().add(supprimer);

                
            }
           
            tcontainer.getChildren().add(entpane);

        }
    }

    @FXML
    private void btnAddAction(ActionEvent event) {
        try {
            AnchorPane addpane = FXMLLoader.load(getClass().getResource("/Gui/AddTraining.fxml"));
            main.getChildren().setAll(addpane);
        } catch (IOException ex) {
            Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
