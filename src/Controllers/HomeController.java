/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Publicity;
import Entities.User;
import Services.PieceService;
import Services.PublicityService;
import Services.UserService;
import Utils.MyConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.Notifications;

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
    public static int test;
    @FXML
    private ImageView logoimg;

    public HomeController() {
        cnx = MyConnection.getInstance().getCnx();

    }
    @FXML
    private FontAwesomeIcon notif;
    @FXML
    private Label labelNotif;
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
    @FXML
    private HBox hbox;

    @FXML
    private Pane pane;
    @FXML
    private Button btnreparateur;

    MyConnection mc = MyConnection.getInstance();
    PublicityService ps = new PublicityService();
    ArrayList<Publicity> trans = (ArrayList<Publicity>) ps.afficher();
    public ObservableList<Publicity> obsl = FXCollections.observableArrayList(trans);
    Node[] nodes = new Node[obsl.size()];
    int i = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!HuntKingdom.isSplasheded) {
            loadSplashScreen();
        }
        img.setImage(new Image("/Uploads/2.jpg"));
        logoimg.setImage(new Image("/Uploads/logo.png"));
        pane.getChildren().clear();
        i = 0;
        displayPub(i);

        PieceService ps = new PieceService();
        ps.updateEtat();
        int j = ps.countPieceReady();

        String nb = Integer.toString(j);
        labelNotif.setText(nb);

        Button b1 = new Button("", notif);
        b1.setStyle("-fx-background-color:transparent");
        hbox.getChildren().add(b1);
        b1.setOnAction(e -> {
            if (j > 0) {

                Image img = new Image("/Uploads/accept.png");
                ImageView imgV = new ImageView(img);
                imgV.setFitHeight(100);
                imgV.setFitWidth(100);

                Notifications notif = Notifications.create()
                        .title("pieces")
                        .text(nb + " pieces are ready, you can consult all pieces in reparation")
                        .graphic(imgV)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .onAction(s -> {
                            // System.out.println("notif clicked");
                        });
                notif.show();

            }
        });
    }

    private void displayPub(int i) {
        try {

            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/Gui/AffichageHomePublicity.fxml").openStream());
            AffichageHomePublicityController single = (AffichageHomePublicityController) loader.getController();
            single.getInfo(trans.get(i));
            int id1 = single.getCurrentId();
            Publicity m = trans.get(i);

            nodes[i] = root;
            pane.getChildren().add(nodes[i]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void nextPub(ActionEvent event) {
        if (i == obsl.size() - 1) {
            i = 0;
        } else {
            i++;
        }
        pane.getChildren().clear();
        displayPub(i);

    }

    @FXML
    void previousPub(ActionEvent event) {
        if (i == 0) {
            i = obsl.size() - 1;
        } else {
            i--;
        }
        pane.getChildren().clear();
        displayPub(i);

    }

    @FXML
    private void btnanimalsAction(ActionEvent event) throws IOException {
        btnshop.setStyle("-fx-background-color:transparent");
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Animals.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
    }

    @FXML
    private void btneventsAction(ActionEvent event) throws IOException {
        btnanimals.setStyle("-fx-background-color:transparent");
        btnevents.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnshop.setStyle("-fx-background-color:transparent");
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/consultCompetition.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
    }

    @FXML
    private void btnshopAction(ActionEvent event) throws IOException {
        btnanimals.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Shop.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
    }

    @FXML
    private void btnhomeAction(ActionEvent event) throws IOException {
        btnshop.setStyle("-fx-background-color:transparent");
        btnhome.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
        mainPane.getChildren().setAll(pane);
        this.pane.setVisible(false);
    }

    @FXML
    private void btnservicesAction(ActionEvent event) throws IOException {
        btnhome.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ServiceFront.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
    }

    @FXML
    private void btntrainingAction(ActionEvent event) throws IOException, SQLException {
        btnhome.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        Services.UserService SU = new UserService();
        int idU = SU.getConnectedUser();
        String role = SU.getUserByIdFos(idU);
        if ((role.equals("CLIENT") == true)) {

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
            mainpane.getChildren().setAll(pane);
            this.pane.setVisible(false);
        } else if ((role.equals("TRAINER") == true)) {

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/TrainingList.fxml"));
            mainpane.getChildren().setAll(pane);
            this.pane.setVisible(false);
        }
    }

    @FXML
    private void btnreparationAction(ActionEvent event) throws IOException {
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Reparation.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
    }

    @FXML
    void btnreparateurAction(ActionEvent event) throws IOException {
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Reparateur.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    private void loadSplashScreen() {
        try {
            HuntKingdom.isSplasheded = true;
            //Load splash screen view FXML
            StackPane panee = FXMLLoader.load(getClass().getResource(("/Gui/welcome.fxml")));

            //Add it to root container (Can be StackPane, AnchorPane etc)
            mainPane.getChildren().setAll(panee);

            //Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), panee);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), panee);
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
