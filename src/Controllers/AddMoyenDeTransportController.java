/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.MoyenDeTransport;
import Services.MoyenDeTransportService;
import Utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.rgb;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class AddMoyenDeTransportController implements Initializable {

    //FX start
    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField marque;

    @FXML
    private ComboBox<String> categorie;

    @FXML
    private TextField prixParJour;

    @FXML
    private Button chooserFile;

    //FX end
    @FXML
    private AnchorPane mainpane;

    @FXML
    private TextField listView;

    private String absolutePath;
    @FXML
    private Button add;
    ObservableList<String> list = FXCollections.observableArrayList("Battery", "Gasoline", "Diesel");
    ObservableList<String> list1 = FXCollections.observableArrayList("Vehicule", "motorCycle", "Truck", "Bike");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type.setItems(list);
        categorie.setItems(list1);
    }

    private void copyFile(File file) {
        try {
            File dest = new File("E:\\roadToInfini\\java\\projet\\final\\git\\HuntKingdom\\src\\Uploads\\" + file.getName()); //any location
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
            listView.setVisible(true);
            listView.setText(selectedFile.getName());
            copyFile(selectedFile);
            absolutePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("file is not valid !");
        }

    }

    @FXML
    public void AddTransport(ActionEvent event) throws IOException {
        if (controleDeSaisie()) {
            MyConnection mc = MyConnection.getInstance();
            MoyenDeTransportService ps = new MoyenDeTransportService();
            float price = Float.parseFloat(prixParJour.getText());
            MoyenDeTransport h = new MoyenDeTransport(type.getValue(), price, absolutePath, marque.getText(), categorie.getValue());
            if (ps.addMoyenDeTransport(h)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mean of Transport");
                alert.setHeaderText(null);
                alert.setContentText("Mean of transport succesfully added ");
                alert.showAndWait();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addMoyenDeTransport.fxml"));
                mainpane.getChildren().setAll(pane);
            }

        }

    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();

    }

    private boolean controleDeSaisie() {

        if (prixParJour.getText().isEmpty() || marque.getText().isEmpty()
                || type.getValue().isEmpty() || categorie.getValue().isEmpty() || absolutePath == null) {
            if (marque.getText().isEmpty()) {
                marque.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (prixParJour.getText().isEmpty()) {
                prixParJour.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (type.getValue() == null) {
                type.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (categorie.getValue() == null) {
                categorie.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (absolutePath == null) {
                chooserFile.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        }

        if (!Pattern.matches("^[\\p{L} .'-]+$", marque.getText()) || prixParJour.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Verify the field Mark ! ");
            marque.requestFocus();
            marque.selectEnd();
            marque.setStyle("-fx-border-color: red; -fx-background-color: white;");
            return false;
        }
        if (!Pattern.matches("^[0-9]*\\.?[0-9]+$", prixParJour.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Verify The price Per Day field!");
            prixParJour.requestFocus();
            prixParJour.selectEnd();
            prixParJour.setStyle("-fx-border-color: red; -fx-background-color: white;");
            return false;
        }

        return true;
    }

}
