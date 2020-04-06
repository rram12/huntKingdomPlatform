/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Publicity;
import Services.PublicityService;
import Utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;



/**
 * FXML Controller class
 *
 * @author User
 */
public class AddPublicityController implements Initializable {
    
    @FXML
    private Button Add;

    @FXML
    private TextField compagnie;

    @FXML
    private Button chooserFile;

    @FXML
    private TextField prix;
    
     private String absolutePath;

    @FXML
    private DatePicker dpS;

    @FXML
    private TextField listView;

    @FXML
    private TextField titre;

    @FXML
    private TextField descriptionn;

    @FXML
    private DatePicker dpE;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }   
    
     private void copyFile(File file) {
        try {
            File dest = new File("C:\\Users\\User\\Desktop\\final project\\huntkingdom\\src\\Uploads\\"+file.getName()); //any location
            Files.copy(file.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    public void chooseFileAction(){
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select an image");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile!=null){
            //listView.getItems().add(selectedFile.getName());
            listView.setVisible(true);
            listView.setText(selectedFile.getName());
            copyFile(selectedFile);
            absolutePath = selectedFile.getAbsolutePath();
        }else
            System.out.println("file is not valid !");
    
    
    
}
        public void AddPublicity(ActionEvent event) throws IOException{
    MyConnection mc =  MyConnection.getInstance();
    PublicityService ps = new PublicityService();
    LocalDate dPub=dpS.getValue();
     LocalDate dPubli=dpE.getValue();
     float price=Float.parseFloat(prix.getText());
    Publicity p = new Publicity(java.sql.Date.valueOf(dpS.getValue().toString()),java.sql.Date.valueOf(dpE.getValue().toString()),compagnie.getText(),titre.getText(),price,descriptionn.getText(),absolutePath);
    ps.addPublicity(p);

    }
    
    
    
}
