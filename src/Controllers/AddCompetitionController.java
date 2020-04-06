/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Competition;
import Services.CompetitionService;
import Utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Categorie.setItems(list);
    }    
    public void AddCompetition(ActionEvent event) throws IOException{
    MyConnection mc =  MyConnection.getInstance();
    CompetitionService ps = new CompetitionService();
    LocalDate dCompetition=dT.getValue();
     LocalDate dComp=dP.getValue();
    int nbPartic=Integer.parseInt(NbParticipants.getText());
    Competition c = new Competition(Categorie.getValue(),Nom.getText(),Lieu.getText(),nbPartic,java.sql.Date.valueOf(dT.getValue().toString()),java.sql.Date.valueOf(dP.getValue().toString()));
    ps.addCompetition(c);

    }
    
}
