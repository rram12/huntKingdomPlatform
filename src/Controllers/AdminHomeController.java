/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class AdminHomeController implements Initializable {
     private Connection cnx;
    private Statement st;
    private PreparedStatement pst;
    private PreparedStatement pst1;
    private ResultSet rs;
    public static int test ;
    

    public AdminHomeController() {
        cnx = MyConnection.getInstance().getCnx();
        

    }
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
    @FXML
    private Button btnLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        img.setImage(new Image("/Uploads/dash.jpg"));
    }    

    @FXML
    private void btnanimalsAction(ActionEvent event) throws IOException {
         btntraining.setStyle("-fx-background-color:transparent");
         btnanimals.setStyle("-fx-background-color:transparent;-fx-text-fill:#f55e57");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ListAnimalAdmin.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btneventsAction(ActionEvent event)throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Event.fxml"));
        mainpane.getChildren().setAll(pane);
    }
    @FXML
    private void btnpublicityAction(ActionEvent event)throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Publicity.fxml"));
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
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Service.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btntrainingAction(ActionEvent event) throws IOException {
         btnanimals.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent;-fx-text-fill:#f55e57");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Statistique.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnreparationAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Reparation.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnLogoutAction(ActionEvent event) throws IOException, SQLException {
          String query = "update fos_user set etat=0";
        
                st = cnx.createStatement();
                

                st.executeUpdate(query);
                
        
      AnchorPane pane;
         pane = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
        mainPane.getChildren().setAll(pane);
    }
    
}
