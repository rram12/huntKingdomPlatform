/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Services.UserService;
import Utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class LoginController implements Initializable {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pst;
    private PreparedStatement pst1;
    private ResultSet rs;
    public static int test ;
    @FXML
    private ImageView logoimg;
    @FXML
    private Button btnLogin;
    @FXML
    private ImageView imglogin;
    

    public LoginController() {
        cnx = MyConnection.getInstance().getCnx();
        

    }
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logoimg.setImage(new Image("/Uploads/logo2.png"));
        imglogin.setImage(new Image("/Uploads/backgrounLogin.jpg"));
    }    

    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException, SQLException {
         System.out.println("aaa");
        
        User p = new User();
        p.setUsername(txtusername.getText());
       
        
        
        UserService sp = new UserService();
        test=sp.authentification(p);
        
        
          System.out.println(test);
          
          String query = "update fos_user set etat=1 where id="+test;
       
                st = cnx.createStatement();

                st.executeUpdate(query);
                if(test!=0){
                    String role = sp.getUserByRole(txtusername.getText());
        if(txtusername.getText().equals("salsa")==true)
        {
        
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/AdminHome.fxml"));
         
        mainPane.getChildren().setAll(pane);
        }
        else   if(txtusername.getText().equals("khaled")==true)
        {
        
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/AdminHome.fxml"));
        
        mainPane.getChildren().setAll(pane);
        }
        else if(txtusername.getText().equals("toutou")==true)
        {
        
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
        mainPane.getChildren().setAll(pane);
        }
    }
    }
    
    
}
