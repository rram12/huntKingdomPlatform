/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Publicity;
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
 * @author User
 */
public class AffichageHomePublicityController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Label description;

    @FXML
    private Label title;
    private Publicity pub;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
  
    void getInfo(Publicity p) {
        title.setText(p.getTitre());
        description.setText(p.getDescription());
        pub = p;
         try {
                Image images = new Image(new FileInputStream(p.getImage()));
                image.setImage(images);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }

    }
    int getCurrentId() {
        return this.pub.getId();
    }
    

}
