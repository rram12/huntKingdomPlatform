/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Location;
import Entities.MoyenDeTransport;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class SingleLocationController implements Initializable {

    @FXML
    private ImageView ImageTran;
    @FXML
    private Label labelPricePerDay;
    @FXML
    private Label labelMark;
    @FXML
    private Label labelCategory;
    @FXML
    private JFXButton showBtn;
    private MoyenDeTransport Tran;
    private Location Loc;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void getInfo(MoyenDeTransport Mt,Location l){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String dDate = dateFormat.format(l.Arrivaldate());
        String fDate = dateFormat.format(l.finaldate());
        labelMark.setText(dDate);
        labelCategory.setText(fDate);
        labelPricePerDay.setText(Float.toString(l.getPrixTot()));
        labelPricePerDay.setText(Float.toString(l.getPrixTot())+"dt");
        Tran = Mt;
        Loc=l;
        try {
            Image image = new Image(new FileInputStream(Mt.getImage()));
            ImageTran.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
    }
    JFXButton getButton(){
        return showBtn;
    }
    int getCurrentId(){
        return this.Tran.getId();
    }
    int getCurrentLocationId(){
        return this.Loc.getId();
    }
}
