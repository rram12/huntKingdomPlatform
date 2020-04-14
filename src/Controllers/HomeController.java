/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import Entities.User;
import Services.UserService;
import Utils.MyConnection;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class HomeController implements Initializable {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pst;
    private PreparedStatement pst1;
    private ResultSet rs;
    public static int test ;
    

    public HomeController() {
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
         if(!HuntKingdom.isSplasheded)
        {
                    loadSplashScreen();
        }
        img.setImage(new Image("/Uploads/2.jpg"));
        
    }    

    @FXML
    private void btnanimalsAction(ActionEvent event) throws IOException {
         btntraining.setStyle("-fx-background-color:transparent");
         btnanimals.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Animals.fxml"));
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
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ServiceFront.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btntrainingAction(ActionEvent event) throws IOException, SQLException {
        btnanimals.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
         Services.UserService SU = new UserService();
        int idU=SU.getConnectedUser();
        String username = SU.getUserByIdFos(idU);
        
        if("toutou".equals(username)==true)
        {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/TrainingList.fxml"));
        mainpane.getChildren().setAll(pane);
    }
        else if("khaled".equals(username)==true)
        {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
        mainpane.getChildren().setAll(pane);
    }
    }

    @FXML
    private void btnreparationAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Reparation.fxml"));
        mainpane.getChildren().setAll(pane);
    }
      private void loadSplashScreen() {
    try {
        HuntKingdom.isSplasheded=true;
        //Load splash screen view FXML
        StackPane panee = FXMLLoader.load(getClass().getResource(("/Gui/welcome.fxml")));
        
        //Add it to root container (Can be StackPane, AnchorPane etc)
        mainPane.getChildren().setAll(panee);
 
        //Load splash screen with fade in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), panee);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
 
        //Finish splash with fade out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), panee);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
 
        fadeIn.play();
 
        //After fade in, start fade out
        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });
 
        //After fade out, load actual content
        fadeOut.setOnFinished((e) -> {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
                 mainPane.getChildren().setAll(pane);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
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
