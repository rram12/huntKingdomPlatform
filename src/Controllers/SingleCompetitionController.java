/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Competition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SingleCompetitionController implements Initializable {

       @FXML
    private Label categ;

    @FXML
    private Label particip;

    @FXML
    private JFXButton showBtn;

    @FXML
    private Label nam;
    private Competition Comp;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }   
    void getInfo(Competition c){
        nam.setText(c.getNom());
        categ.setText(c.getCategorie());
        particip.setText(Integer.toString(c.getNbParticipants()));
        Comp = c;
        
    }
    JFXButton getButton(){
        return showBtn;
    }
    int getCurrentId(){
        return this.Comp.getId();
    }
    
}

  
