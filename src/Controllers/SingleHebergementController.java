/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Hebergement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class SingleHebergementController implements Initializable {
    
    @FXML
    private Label labelPricePerDay;

    @FXML
    private Label labelCapacity;

    @FXML
    private ImageView ImageHeb;

    @FXML
    private JFXButton showBtn;

    @FXML
    private Label labelNbRooms;
    
    private Hebergement Heb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    void getInfo(Hebergement h){
        labelNbRooms.setText(h.getAdresse());
        labelCapacity.setText(Integer.toString(h.getCapacite()));
        labelPricePerDay.setText(Float.toString(h.getPrixParJour())+"dt");
        Heb = h;
        try {
            Image image = new Image(new FileInputStream(h.getImage()));
            ImageHeb.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
    }
    JFXButton getButton(){
        return showBtn;
    }
    int getCurrentId(){
        return this.Heb.getId();
    }
    
}
