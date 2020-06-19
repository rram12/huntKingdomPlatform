/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus_pc
 */
public class WaitController implements Initializable {
    @FXML
    private Button logOutBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
    
    
     @FXML
    void logOutBtn(ActionEvent event) throws IOException {
        
         Stage stage = (Stage) logOutBtn.getScene().getWindow();
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
    
    
}
