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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    MyConnection mc = MyConnection.getInstance();
    PublicityService ps = new PublicityService();
    List<Publicity> mylist = new ArrayList();
    public ObservableList<Publicity> pub = FXCollections.observableArrayList(
            ps.afficher()
    );

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void copyFile(File file) {
        try {
            File dest = new File("C:\\Users\\User\\Desktop\\integration 2\\huntkingdom\\src\\Uploads\\" + file.getName()); //any location
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    public void chooseFileAction() {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select an image");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            //listView.getItems().add(selectedFile.getName());
            listView.setVisible(true);
            listView.setText(selectedFile.getName());
            copyFile(selectedFile);
            absolutePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("file is not valid !");
        }

    }

    public void AddPublicity(ActionEvent event) throws IOException, ParseException {

        if (validateFields()) {
            MyConnection mc = MyConnection.getInstance();
            PublicityService ps = new PublicityService();
            LocalDate dPub = dpS.getValue();
            LocalDate dPubli = dpE.getValue();
            float price = Float.parseFloat(prix.getText());
            Publicity p = new Publicity(java.sql.Date.valueOf(dpS.getValue().toString()), java.sql.Date.valueOf(dpE.getValue().toString()), compagnie.getText(), titre.getText(), price, descriptionn.getText(), absolutePath);
          
               if(ps.addPublicity(p)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Publicity");
                alert.setHeaderText(null);
                alert.setContentText("Publicity succesfully added ");
                alert.showAndWait();
            }
            pub.clear();
            pub = FXCollections.observableArrayList(ps.afficher());
        }

    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();

    }

    private boolean validateFields() throws ParseException {

        String format = "dd/MM/yyyy";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        Date date = new Date();

        Date d = formater.parse(formater.format(date));
        System.out.println(formater.format(d));
        Date datee;
        Date dateee;
        datee = java.sql.Date.valueOf(dpS.getValue());
        
        if (d.compareTo(datee) == 1) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Date > Date courante !");
            dpS.requestFocus();

            return false;
        } else {
            dateee = java.sql.Date.valueOf(dpE.getValue());
            if (datee.compareTo(dateee) == 1) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Date Fin >Date Debut");
            dpS.requestFocus();

            return false;
        }
        }
           if (prix.getText().isEmpty() || titre.getText().isEmpty() 
                || compagnie.getText().isEmpty() || descriptionn.getText().isEmpty() || absolutePath==null) {
            if (titre.getText().isEmpty()){
                titre.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (prix.getText().isEmpty()){
                prix.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (compagnie.getText().isEmpty()){
                compagnie.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (descriptionn.getText().isEmpty()){
                descriptionn.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (absolutePath==null){
                chooserFile.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        } 

           if (!Pattern.matches("^[\\p{L} .'-]+$", titre.getText())) {
               showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Verify the field title ! ");
                titre.requestFocus();
                titre.selectEnd();
                titre.setStyle("-fx-border-color: red; -fx-background-color: white;");
                return false;
            }
            if (!Pattern.matches("^[0-9]*\\.?[0-9]+$", prix.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Verify The price field!");
                prix.requestFocus();
                prix.selectEnd();
                prix.setStyle("-fx-border-color: red; -fx-background-color: white;");
                return false;
            }
            

        return true;

    }

}
