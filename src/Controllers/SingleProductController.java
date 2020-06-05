/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PiecesDefectueuses;
import Entities.Produits;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author asus_pc
 */
public class SingleProductController implements Initializable {

    @FXML
    private JFXButton addTocardBtn;

    @FXML
    private ImageView img_produit;

    @FXML
    private Label labelCategory;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrice;
    private Produits produit;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     void getInfo(Produits p){
        labelCategory.setText(p.getType());
        labelNom.setText(p.getLib_prod());
        labelPrice.setText(Double.toString(p.getPrix()));
        produit = p;
        try {
            Image image = new Image(new FileInputStream(p.getImage()));
            img_produit.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
    }
    JFXButton getButton(){
        return addTocardBtn;
    }
    
}
