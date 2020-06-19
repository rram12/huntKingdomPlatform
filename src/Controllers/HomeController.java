/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Publicity;
import Entities.User;
import Services.PieceService;
import Services.ProduitService;
import Services.PromotionService;
import Services.PublicityService;
import Services.UserService;
import Utils.Marquee;
import Utils.MyConnection;
import Utils.ShowNotification;
import Utils.topNews;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import huntkingdom.HuntKingdom;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

    topNews tn = new topNews();
    UserService us = new UserService();

    ArrayList topnews = new ArrayList();
    @FXML
    private MenuButton menu;
    @FXML
    private MenuItem profile;
    @FXML
    private MenuItem reclamation;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem desactivate;
    @FXML
    private ImageView avatarUser;

    public AnchorPane getMainPane() {
        return mainpane;
    }

    private static HomeController instance;

    public HomeController() {
        instance = this;
    }

    public static HomeController getInstance() {
        return instance;
    }

    public void setUpdatedUser(Image img, String username) {
        avatarUser.setImage(img);
        menu.setText(username);
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
    private Button next;
    @FXML
    private Button previous;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView img;
    @FXML
    private Button btnLogout;
    @FXML
    private HBox hbox;

    @FXML
    private Label lTopNews;
    @FXML
    private Pane pMarquee;

    @FXML
    private Pane pane;
    @FXML
    private Button btnreparateur;
    private int GlobJ;
    MyConnection mc = MyConnection.getInstance();
    PublicityService ps = new PublicityService();
    ArrayList<Publicity> trans = (ArrayList<Publicity>) ps.afficher();
    public ObservableList<Publicity> obsl = FXCollections.observableArrayList(trans);
    Node[] nodes = new Node[obsl.size()];
    int i = 0;

    User currentUser = LoginController.getInstance().getLoggedUser();
    String email = currentUser.getEmail();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//         String role =  UserSession.getInstace("",0, "", "", "", 0).getRoles();

        if (!currentUser.getRoles().contains("REPAIRER")) {
            btnreparateur.setVisible(false);
            btnreparateur.setDisable(true);
        }

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
        GlobJ = j;
        String nb = Integer.toString(GlobJ);
        labelNotif.setText(nb);

        Button b1 = new Button("", notif);
        b1.setStyle("-fx-background-color:transparent");
        hbox.getChildren().add(b1);
        b1.setOnAction(e -> {
            if (GlobJ > 0) {

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

        Image imageAvatar;
        try {
            imageAvatar = new Image(new FileInputStream("res\\uploadedImages\\" + currentUser.getPicture()));
            avatarUser.setImage(imageAvatar);
            avatarUser.setPreserveRatio(false);

            //System.out.println("uploadedImages/"+loggedUser.getPicture());
            avatarUser.setFitHeight(60.0);
            avatarUser.setFitWidth(80.0);
            avatarUser.setY(12);
            menu.setLayoutX(avatarUser.getLayoutX());
            menu.setLayoutY(avatarUser.getLayoutY() + avatarUser.getFitHeight() + avatarUser.getY());

            //
            menu.setText(currentUser.getUsername());
//        menu.setText("z");

//        MenuItem viewProfile = new MenuItem("View profile");
//        MenuItem reclamations = new MenuItem("Reclamations");
//        MenuItem logout = new MenuItem("Log out");
            menu.resize(menu.getWidth(), 500);

//        menu.getItems().add(viewProfile);
//        menu.getItems().add(reclamations);
//        menu.getItems().add(logout);
//        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String news = "";
        try {
            topnews = tn.getTopNews();
            for (Object i : topnews) {
                news += i.toString();
                news += "  ***  ";

            }
            news = news.substring(0, news.length() - 6);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        FadeTransition ft = new FadeTransition(Duration.millis(3000), lTopNews);
        ft.setAutoReverse(false);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setByValue(1);
        ft.play();

        Marquee marquee = new Marquee(news);
        marquee.setColor("black");
        marquee.setStyle("-fx-font: bold 16 arial;");
        marquee.setBoundsFrom(mainPane);
        marquee.moveDownBy(7);
        marquee.setScrollDuration(60);

        pMarquee.getChildren().add(marquee);
        marquee.run();

    }

    private void displayPub(int i) {
        try {

            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/Gui/AffichageHomePublicity.fxml").openStream());
            AffichageHomePublicityController single = (AffichageHomePublicityController) loader.getController();
            single.getInfo(trans.get(i));

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
        this.next.setVisible(false);
        this.previous.setVisible(false);
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
        this.next.setVisible(false);
        this.previous.setVisible(false);
    }

    @FXML
    private void btnshopAction(ActionEvent event) throws IOException {
        /**
         * * delete ended reductions *
         */
        ProduitService ps = new ProduitService();
        ps.deletePromotionFini();
        PromotionService promos = new PromotionService();
        promos.supprimerPromotionFini();
        /**
         * **
         */
        btnanimals.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent;-fx-text-fill:#E38450");
        btnevents.setStyle("-fx-background-color:transparent");
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ProductsFront.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
        this.next.setVisible(false);
        this.previous.setVisible(false);
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
        this.pane.setVisible(true);
        this.next.setVisible(true);
        this.previous.setVisible(true);
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
        this.next.setVisible(false);
        this.previous.setVisible(false);
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
//        String role =  UserSession.getInstace("",0, "", "", "", 0).getRoles();
//        System.out.println("role : "+role);
        if ((currentUser.getRoles().contains("CLIENT") == true)) {
            System.out.println("CLIENT");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
            mainpane.getChildren().setAll(pane);
            this.pane.setVisible(false);
            this.next.setVisible(false);
            this.previous.setVisible(false);
        } else if ((currentUser.getRoles().contains("TRAINER") == true)) {

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/TrainingList.fxml"));
            mainpane.getChildren().setAll(pane);
            this.pane.setVisible(false);
            this.next.setVisible(false);
            this.previous.setVisible(false);
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
        this.next.setVisible(false);
        this.previous.setVisible(false);
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
        this.pane.setVisible(false);
        this.next.setVisible(false);
        this.previous.setVisible(false);
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
        ShowNotification notif = new ShowNotification();
        notif.show("Good bye", "See You Soon ");
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        // do what you have to do
        LoginController.getInstance().setLoggedUser(new User());

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Style/bootstrap3.css").toExternalForm());
        primaryStage.setTitle("HuntKingdom");
        Image ico = new Image("Uploads/logo2.png");
        primaryStage.getIcons().add(ico);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage.close();
    }

    void decrementReady() {
        GlobJ--;
        labelNotif.setText(Integer.toString(GlobJ));
        System.out.println("GlobJ : " + GlobJ);
    }

    @FXML
    private void updateProfile(ActionEvent event) throws IOException {
        btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/UpdateProfile.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
        this.next.setVisible(false);
        this.previous.setVisible(false);
    }

    @FXML
    private void reclamation(ActionEvent event) throws IOException {
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/ReclamationsList.fxml"));
//
//            try {
//                Parent root = loader.load();
//                //ReclamationsListController rc = loader.getController();
//                menu.getScene().setRoot(root);
//
//            } catch (IOException ex) {
//                System.err.println(ex.getMessage());
//            }
btnhome.setStyle("-fx-background-color:transparent");
        btnreparateur.setStyle("-fx-background-color:transparent");
        btnevents.setStyle("-fx-background-color:transparent");
        btnanimals.setStyle("-fx-background-color:transparent");
        btnreparation.setStyle("-fx-background-color:transparent");
        btnshop.setStyle("-fx-background-color:transparent");
        btntraining.setStyle("-fx-background-color:transparent");
        btnservices.setStyle("-fx-background-color:transparent");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ReclamationsList.fxml"));
        mainpane.getChildren().setAll(pane);
        this.pane.setVisible(false);
        this.next.setVisible(false);
        this.previous.setVisible(false);
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            ShowNotification notif = new ShowNotification();
            notif.show("Good bye", "See You Soon ");           
            LoginController.getInstance().setLoggedUser(new User());

            Stage stage = (Stage) btnLogout.getScene().getWindow();
            // do what you have to do

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Style/bootstrap3.css").toExternalForm());
            primaryStage.setTitle("HuntKingdom");
            Image ico = new Image("Uploads/logo2.png");
            primaryStage.getIcons().add(ico);
            primaryStage.setScene(scene);
            primaryStage.show();
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void desactivate(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account desactivation");
        alert.setContentText("Are you sure to desactivate your account ?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            try {
                us.deleteUser(currentUser.getId());
                LoginController.getInstance().setLoggedUser(new User());
                
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                // do what you have to do
                
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/Style/bootstrap3.css").toExternalForm());
                primaryStage.setTitle("HuntKingdom");
                Image ico = new Image("Uploads/logo2.png");
                primaryStage.getIcons().add(ico);
                primaryStage.setScene(scene);
                primaryStage.show();
                stage.close();
                ShowNotification notif = new ShowNotification();
                notif.showInformation("Good bye", "Sorry to see you leave our family !");
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {

        }

    }
}
