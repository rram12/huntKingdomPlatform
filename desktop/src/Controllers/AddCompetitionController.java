/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Competition;
import Services.CompetitionService;
import Utils.GoogleMapsAPI;
import Utils.MyConnection;
import Utils.ReverseGeoCoding;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddCompetitionController implements Initializable {
        @FXML
    private ComboBox<String> Categorie;
    ObservableList<String>list = FXCollections.observableArrayList("Hunting","Fishing");

     @FXML
    private Button Add;

      @FXML
    private DatePicker dP;

    @FXML
    private TextField NbParticipants;

    @FXML
    private TextField Lieu;

     @FXML
    private DatePicker dT;

    @FXML
    private TextField Nom;
    
        @FXML
    private WebView mapView;
    private String gouvernorat;
    
      GoogleMapsAPI gma;
    ReverseGeoCoding rgc;
    @FXML
    private AnchorPane mainpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            gma = new GoogleMapsAPI(mapView);
        rgc = new ReverseGeoCoding();
        gma.initMap();
        Categorie.setItems(list);
    }    
    @FXML
    public void AddCompetition(ActionEvent event) throws IOException, ParseException{
        
    if (validateFields()){
        
    CompetitionService ps = new CompetitionService();
    int nbPartic=Integer.parseInt(NbParticipants.getText());
    Competition c = new Competition(Categorie.getValue(),Nom.getText(),gouvernorat,nbPartic,java.sql.Date.valueOf(dT.getValue().toString()),java.sql.Date.valueOf(dP.getValue().toString()));
     if(ps.addCompetition(c)) {
                showAlert(Alert.AlertType.INFORMATION, "Competition", null, "Competition succesfully added ");

            }
     //AdminHomeController.getInstance().btneventsAction(event);
     AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Event.fxml"));
        mainpane.getChildren().setAll(pane);


    }
    }
    
    @FXML
    private void coordonneesSelected(MouseEvent event) {
        System.out.println(String.valueOf(gma.getLatitude()));
        System.out.println(String.valueOf(gma.getLongitude()));
//        gouvernorat = rgc.getGouvernorat(String.valueOf(gma.getLatitude()), String.valueOf(gma.getLongitude()));
        try {
            gouvernorat = rgc.getGouvernorat(String.valueOf(gma.getLatitude()), String.valueOf(gma.getLongitude()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Lieu.setText(gouvernorat);
        Lieu.setEditable(false);
    }

        public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();

    }

    private boolean validateFields() throws ParseException {

        String format = "dd/MM/yyyy";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();

        java.util.Date d = formater.parse(formater.format(date));
        System.out.println(formater.format(d));
        java.util.Date datee;
        java.util.Date dateee;
        datee = java.sql.Date.valueOf(dT.getValue());
        
        if (d.compareTo(datee) == 1) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Date > Date courante !");
            dT.requestFocus();

            return false;
        } else {
            dateee = java.sql.Date.valueOf(dP.getValue());
            if (datee.compareTo(dateee) == 1) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Date Fin >Date Debut");
            dP.requestFocus();

            return false;
        }
        }
           if (Nom.getText().isEmpty() || Categorie.getValue().isEmpty() 
                || Lieu.getText().isEmpty() || NbParticipants.getText().isEmpty()) {
            if (Nom.getText().isEmpty()){
                Nom.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (Categorie.getValue().isEmpty()){
                Categorie.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (Lieu.getText().isEmpty()){
                Lieu.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (NbParticipants.getText().isEmpty()){
                NbParticipants.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        } 
            if (!Pattern.matches("^\\d{1,2}$", NbParticipants.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Verify The remaining places field!");
                NbParticipants.requestFocus();
                NbParticipants.selectEnd();
                NbParticipants.setStyle("-fx-border-color: red; -fx-background-color: white;");
                return false;
            }
              if (!Pattern.matches("^([a-zA-Z])[a-zA-Z_-]*[\\w_-]*[\\S]$|^([a-zA-Z])[0-9_-]*[\\S]$|^[a-zA-Z]*[\\S]$", Nom.getText())) {
               showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Verify the field Name ! ");
                Nom.requestFocus();
                Nom.selectEnd();
                Nom.setStyle("-fx-border-color: red; -fx-background-color: white;");
                return false;
            }
            
            
            

        return true;

    }
    
}
