/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;



import static Controllers.AddTrainingController.showAlert;
import Entities.Entrainement;
import Entities.User;
import Services.AnimalService;
import Services.JavaMail;
import Services.TrainingService;
import Services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class TrainingListController implements Initializable {

    @FXML
    private ScrollPane scrollTraining;
    
    TrainingService ST = new TrainingService();
     Services.UserService SU = new UserService();
    
    @FXML
    private VBox vbox;
    @FXML
    private AnchorPane main;
    @FXML
    private ImageView calendar;
    @FXML
    private Pane paneReminder;
    @FXML
    private ImageView imgclose;
    @FXML
    private ImageView imgCertif;
    @FXML
    private ImageView like;
    @FXML
    private ImageView dislike;
    @FXML
    private Label nbLike;
    @FXML
    private Label nbdislike;
    
    User currentUser = LoginController.getInstance().getLoggedUser();

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //paneTest.setVisible(false);
      List<Entrainement> entrs = null;
       List<Entrainement> t = null;
        try {
            entrs = ST.getTrainings();
            t=ST.getTrainingsAccepted();
             afficher(entrs);
        } catch (SQLException ex) {
            Logger.getLogger(TrainingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         calendar.setImage(new Image("Uploads/calendar.png"));
         like.setImage(new Image("Uploads/hearted.png"));
         dislike.setImage(new Image("Uploads/broken-heart.png"));
        try {
            nbLike.setText(Integer.toString(ST.getNnLike(LoginController.getInstance().getLoggedUser().getId())));
            nbdislike.setText(Integer.toString(ST.getNnDisLike(LoginController.getInstance().getLoggedUser().getId())));
        } catch (SQLException ex) {
            Logger.getLogger(TrainingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
          
        Date now = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDateString = formatter.format(now);
        try {
            Date d =formatter.parse(mysqlDateString);
            
        } catch (ParseException ex) {
            Logger.getLogger(TrainingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ScrollBar sc = new ScrollBar();
        final VBox vb = new VBox();
        paneReminder.getChildren().addAll(vb,sc);
        vb.setLayoutX(0);
        vb.setSpacing(50);
       


        sc.setLayoutX(paneReminder.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(180);
        sc.setMax(180*2);
        UserService SU = new UserService();
        
        try {  
            Date date1=formatter.parse(mysqlDateString);
            
             for(Entrainement entr : t)
        {
            Label remind = new Label();
            remind.setTextFill(Color.WHITE);
            remind.setStyle("-fx-font-weight: bold");
            int diff = (int)getDaysBetweenDates(date1,entr.getDateEnt());
            if(diff==0)
            {
                  remind.setText("Training For " + SU.getUsername(entr.getUserId())+" Today");
            }
            else if(diff<0)
            {
                 
             remind.setText(" Finished Training For " + SU.getUsername(entr.getUserId()));
                
            }
            
            else 
              remind.setText("Training For " + SU.getUsername(entr.getUserId())+" After "+diff+" Days");
                remind.setLayoutX(1);
                remind.setLayoutY(1);
                 vb.getChildren().add(remind);
        }
        } catch (ParseException ex) {
            Logger.getLogger(TrainingListController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TrainingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sc.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            vb.setLayoutY(-new_val.doubleValue());
      });
        paneReminder.setVisible(false);
        try {
            if(ST.getNnLike(LoginController.getInstance().getLoggedUser().getId())>=3)
            {
            imgCertif.setImage(new Image("/Uploads/medal.png"));
            }
            else 
                imgCertif.setVisible(false);
            
                
                } catch (SQLException ex) {
            Logger.getLogger(TrainingListController.class.getName()).log(Level.SEVERE, null, ex);
           
                }

    } 
    public static double getDaysBetweenDates(Date theEarlierDate, Date theLaterDate) {
		double result = Double.POSITIVE_INFINITY;
		if (theEarlierDate != null && theLaterDate != null) {
			final long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
			Calendar aCal = Calendar.getInstance();
			aCal.setTime(theEarlierDate);
			long aFromOffset = aCal.get(Calendar.DST_OFFSET);
			aCal.setTime(theLaterDate);
			long aToOffset = aCal.get(Calendar.DST_OFFSET);
			long aDayDiffInMili = (theLaterDate.getTime() + aToOffset) - (theEarlierDate.getTime() + aFromOffset);
			result = ((double) aDayDiffInMili / MILLISECONDS_PER_DAY);
		}
		return result;
	}
    
     public void afficher(List<Entrainement> entr) throws SQLException{
        for (int i = 0; i < entr.size(); i++) {
             Entrainement current = entr.get(i);
            UserService SU = new UserService();
            AnimalService SA = new AnimalService();
           
            int idU=currentUser.getId();
            String nomA= SA.getById(current.getAnimalId()).getNom();
            String usernameUser = SU.getUsername(current.getUserId());
            Pane pane = new Pane();
            pane.setPrefWidth(694);
            pane.setPrefHeight(284);
            pane.setLayoutX(0);
            pane.setLayoutY(0);
            //pane.setStyle("-fx-background-color:#8A5548");
           
         
           Label categorie = new Label();
                categorie.setTextFill(Color.WHITE);
                categorie.setStyle("-fx-font-weight: bold;-fx-font-size: 11pt");
               
                categorie.setText("Training For " + current.getCategorie());
                categorie.setLayoutX(292);
                categorie.setLayoutY(20);
                pane.getChildren().add(categorie);
                
                
                Label username = new Label();
                username.setTextFill(Color.WHITE);
                username.setStyle("-fx-font-weight: bold;-fx-font-size: 11pt");
               
                username.setText("Username : " + usernameUser);
                username.setLayoutX(109);
                username.setLayoutY(72);
                pane.getChildren().add(username);
                
                Label datet = new Label();
                datet.setTextFill(Color.WHITE);
                datet.setStyle("-fx-font-weight: bold;-fx-font-size: 11pt");
               
                datet.setText("Training Date " + current.getDateEnt());
                datet.setLayoutX(109);
                datet.setLayoutY(100);
                pane.getChildren().add(datet);
                
                Label place = new Label();
                place.setTextFill(Color.WHITE);
                place.setStyle("-fx-font-weight: bold;-fx-font-size: 11pt");
               
                place.setText("Place " + current.getLieu());
                place.setLayoutX(109);
                place.setLayoutY(123);
                pane.getChildren().add(place);
                
                 Label lanimal = new Label();
                lanimal.setTextFill(Color.WHITE);
                lanimal.setStyle("-fx-font-weight: bold;-fx-font-size: 11pt");
               
                lanimal.setText("Animal " + nomA);
                lanimal.setLayoutX(484);
                lanimal.setLayoutY(72);
                pane.getChildren().add(lanimal);
                
                Label hn = new Label();
                hn.setTextFill(Color.WHITE);
                hn.setStyle("-fx-font-weight: bold;-fx-font-size: 11pt");
               
                hn.setText("Hours Number :  " + current.getNbHeures());
                hn.setLayoutX(484);
                hn.setLayoutY(123);
                pane.getChildren().add(hn);
                Button Accept = new Button("Accept");
                
                
                Accept.setLayoutX(292);
                Accept.setLayoutY(190);
                 pane.getChildren().add(Accept);
                
                 Button Refuse = new Button("Refuse");
                Refuse.setLayoutX(390);
                Refuse.setLayoutY(190);
                 pane.getChildren().add(Refuse);
                DropShadow shadow = new DropShadow();
                shadow.setColor(Color.WHITE);
                 //Refuse.setEffect(shadow);
                 
         Refuse.setStyle("-fx-background-color:transparent;-fx-text-fill:#AF3434; -fx-font-size: 13pt;-fx-font-family: Segoe UI Semibold;-fx-font-weight: bold");
               Refuse.addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            Refuse.setEffect(shadow);
            
          }
        });

    Refuse.addEventHandler(MouseEvent.MOUSE_EXITED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            Refuse.setEffect(null);
          }
        });
    
    
     Accept.setStyle("-fx-background-color:transparent;-fx-text-fill:#fea526; -fx-font-size: 13pt;-fx-font-family: Segoe UI Semibold;-fx-font-weight: bold");
               Accept.addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            Accept.setEffect(shadow);
            
          }
        });

    Accept.addEventHandler(MouseEvent.MOUSE_EXITED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            Accept.setEffect(null);
          }
        });


                vbox.getChildren().add(pane);
            vbox.setStyle("-fx-background-color: transparent;");
            scrollTraining.setStyle("-fx-background-color: transparent;");
                
                
                Accept.setOnMouseClicked((MouseEvent e) -> { 
                    Services.TrainingService ST = new TrainingService();
                    try {
                        showAlert(Alert.AlertType.INFORMATION, "", "", "Training Accepted !");
                        ST.updateTraining(idU,current.getId()); 
                        AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/TrainingList.fxml")); 
                        main.getChildren().setAll(redirected);
                    } catch (IOException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                Refuse.setOnMouseClicked((MouseEvent e) -> { 
                    Services.TrainingService ST = new TrainingService();
                    try {
                        showAlert(Alert.AlertType.INFORMATION, "", "", "Training Canceled !");
                        ST.deleteTraining(current.getId()); 
                        AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/TrainingList.fxml")); 
                        main.getChildren().setAll(redirected);
                    } catch (SQLException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
           
            
            
        }
          
    
    }

    @FXML
    private void btnReminderAction(ActionEvent event) {
        paneReminder.setVisible(true);
        imgclose.setImage(new Image("/Uploads/cancel.png"));
    }

    @FXML
    private void closeAction(ActionEvent event) {
        paneReminder.setVisible(false);
    }

 
}
