/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Hebergement;
import Entities.MoyenDeTransport;
import Services.HebergementService;
import Services.MoyenDeTransportService;
import Utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class AddMoyenDeTransportController implements Initializable {
    
    @FXML
    private AnchorPane mainpane;
    @FXML
    private Button chooserFile;
    @FXML
    private TextField listView;
    
    
    private String absolutePath;
    @FXML
    private Button add;


    @FXML
    private TextField prixParJour;

    @FXML
    private TextField categorie;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField marque;
    ObservableList<String>list = FXCollections.observableArrayList("battery","gasoline","diesel");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type.setItems(list);
    }  
    
    private void copyFile(File file) {
        try {
            File dest = new File("C:\\Users\\ASUS1\\Desktop\\java projects\\Nerds Java\\HuntKingdom\\Uploads\\"+file.getName()); //any location
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
    @FXML
    public void AddTransport(ActionEvent event) throws IOException {
        MyConnection mc =  MyConnection.getInstance();
    MoyenDeTransportService ps = new MoyenDeTransportService();
    float price=Float.parseFloat(prixParJour.getText());
    MoyenDeTransport h = new MoyenDeTransport(type.getValue(),price,absolutePath,marque.getText(),categorie.getText());
    ps.addMoyenDeTransport(h);
//    prixParJour.clear();
//    categorie.clear();
//    type.setValue(null);
//    marque.clear();
//    listView.clear();
      AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addMoyenDeTransport.fxml"));
      mainpane.getChildren().setAll(pane);
    }
    
}
