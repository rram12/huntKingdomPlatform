/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class AdminHomeController implements Initializable {

    @FXML
    private AnchorPane mainpane;
    @FXML
    private Button btnanimals;
    @FXML
    private Button btnevents;
    @FXML
    private Button btnshop;
    @FXML
    private Button btnhome;
    @FXML
    private Button btnservices;
    @FXML
    private Button btntraining;
    @FXML
    private Button btnreparation;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
    }    

    @FXML
    private void btnanimalsAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ListAnimalAdmin.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btneventsAction(ActionEvent event)throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Event.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnshopAction(ActionEvent event) throws IOException {
      AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Shop.fxml"));
        mainpane.getChildren().setAll(pane);
        
    }

    @FXML
    private void btnhomeAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
        mainPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnservicesAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/MoyenDeTransport.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btntrainingAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Statistique.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnreparationAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Reparation.fxml"));
        mainpane.getChildren().setAll(pane);
    }
    
}
