/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Services.ProduitService;
import Services.PromotionService;
import Utils.MyConnection;
import Utils.ShowNotification;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static int test;

        User currentUser = LoginController.getInstance().getLoggedUser();

    ShowNotification notif = new ShowNotification();

    int secPassed = 0;
    final int MAX_SEC = 1000;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            secPassed++;
            System.out.println(secPassed);
            if (secPassed == MAX_SEC) {
                timer.cancel();
                try {
                    btnLogoutAction(new ActionEvent());
                } catch (Exception ex) {
                    Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }

    };
    
    
//    public AdminHomeController() {
//        cnx = MyConnection.getInstance().getCnx();
//        
//    }
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
    private AnchorPane mainPane;
    @FXML
    private ImageView img;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnRecruitments;
    @FXML
    private Button btnPub;
    
    private static AdminHomeController instance;
    @FXML
    private Button btnRec;
    @FXML
    private Button btnStat;

    public AdminHomeController() {
        instance = this;
    }
    
    public static AdminHomeController getInstance() {
        return instance;
    }

    public Timer getTimer() {
        return timer;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        timer.scheduleAtFixedRate(task, 1000, 1000);

        mainPane.setOnKeyTyped(e -> {
            System.out.println("timer reinitialised by key pressed");
            secPassed = 0;
        });

        mainPane.setOnMouseMoved(e -> {
            System.out.println("timer reinitialised by Drag");
            secPassed = 0;
        });

        mainPane.setOnMouseClicked(e -> {
            System.out.println("timer reinitialised by mouse click");
            secPassed = 0;
        });
        
        img.setImage(new Image("/Uploads/dash.jpg"));
        btnhome.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnservices.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

    }

    @FXML
    private void btnanimalsAction(ActionEvent event) throws IOException {
        btnanimals.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnservices.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnhome.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ListAnimalAdmin.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    void btneventsAction(ActionEvent event) throws IOException {
        btnevents.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnservices.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnhome.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Event.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnpublicityAction(ActionEvent event) throws IOException {
        btnPub.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnservices.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnhome.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Publicity.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnshopAction(ActionEvent event) throws IOException {
        btnshop.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnservices.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnhome.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Shop.fxml"));
        mainpane.getChildren().setAll(pane);

    }

    @FXML
    private void btnhomeAction(ActionEvent event) throws IOException {
        btnhome.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnservices.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
        mainPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnservicesAction(ActionEvent event) throws IOException {
        btnservices.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnhome.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Service.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btntrainingAction(ActionEvent event) throws IOException {
        btntraining.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnhome.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnservices.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Statistique.fxml"));
        mainpane.getChildren().setAll(pane);


    }

    @FXML
    void btnRecruitmentsAction(ActionEvent event) throws IOException {
        btnRecruitments.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");
        btnhome.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnservices.setStyle("-fx-background-color: none;");
        btntraining.setStyle("-fx-background-color: none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/AdminUsersList.fxml"));
        mainpane.getChildren().setAll(pane);
//FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/AdminUsersList.fxml"));
//
//        try {
//            Parent root = loader.load();
//            mainpane.getScene().setRoot(root);
//
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
    }

    @FXML
    private void btnLogoutAction(ActionEvent event) throws IOException, SQLException {

        timer.cancel();
        LoginController.getInstance().setLoggedUser(new User());


//        String query = "update fos_user set etat=0";
//        st = cnx.createStatement();
//        st.executeUpdate(query);
        AnchorPane pane;
        pane = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
        mainPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnRecAction(ActionEvent event) throws IOException {
        
        btntraining.setStyle("-fx-background-color: none;");
        btnhome.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnservices.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");


        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/AdminReclamationsList.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    private void btnStatAction(ActionEvent event) throws IOException {
        
        btntraining.setStyle("-fx-background-color: none;");
        btnhome.setStyle("-fx-background-color: none;");
        btnanimals.setStyle("-fx-background-color: none;");
        btnevents.setStyle("-fx-background-color: none;");
        btnshop.setStyle("-fx-background-color: none;");
        btnservices.setStyle("-fx-background-color: none;");
        btnRecruitments.setStyle("-fx-background-color:none;");
        btnPub.setStyle("-fx-background-color: none;");
        btnRec.setStyle("-fx-background-color: none;");
        btnStat.setStyle("-fx-border-color: none; -fx-background-color: #a55446; -fx-opacity: 0.9;");


        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Stats.fxml"));
        mainpane.getChildren().setAll(pane);
    }

}
