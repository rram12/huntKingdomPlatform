/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Services.UserService;
import Utils.MyConnection;
import Utils.Session;
import Utils.UserSession;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
       
    }    

   
    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException, SQLException {
        User p = new User();
        p.setUsername(txtusername.getText());
        UserService sp = new UserService();
        User trieduser=sp.verifyLogin(txtusername.getText());
        System.out.println("trieduser : "+trieduser);    
                if(trieduser.getId()!=0){
                    if(trieduser.isConfirmed()){
                    UserSession.getInstace(trieduser.getUsername(),trieduser.getId(), trieduser.getEmail(), trieduser.getRoles(), trieduser.getAddress(), trieduser.getPhoneNumber());
                     System.out.println(UserSession.getInstace("",0, "", "", "", 0)); 
                     String query = "update fos_user set etat=1 where id="+trieduser.getId();
                st = cnx.createStatement();
                st.executeUpdate(query);
                    Session.current_user=trieduser;
//                    String role = sp.getUserByRole(txtusername.getText());
//                    System.out.println(role);
        //if(txtusername.getText().equals("salsa")==true)
        if(trieduser.getRoles().equals("ADMIN")==true)
        {
        
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/AdminHome.fxml"));
        mainPane.getChildren().setAll(pane);
        }
       
        else 
        {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
        mainPane.getChildren().setAll(pane);
        }
        }else{
       /* AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Wait.fxml"));
        mainPane.getChildren().setAll(pane);    */
       Stage stage = (Stage) btnLogin.getScene().getWindow();
    // do what you have to do
         
            Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Gui/Wait.fxml"));
        Scene scene = new Scene(root);
         scene.getStylesheets().add(getClass().getResource("/Style/bootstrap3.css").toExternalForm());
         primaryStage.setTitle("not Confirmed Account !");
                 primaryStage.setTitle("HuntKingdom");
        Image ico = new Image("Uploads/logo2.png");
        primaryStage.getIcons().add(ico);
        primaryStage.setScene(scene);
        primaryStage.show();
       stage.close();
       
       
       
                    }
    }else{
Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Error");
alert.setHeaderText("Login failed");
alert.setContentText("username does not exist !");

alert.showAndWait();    
                
                
                }
    }
    
    
}
