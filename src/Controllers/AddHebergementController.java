/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.CoordonneesGPS;
import Entities.Hebergement;
import Services.HebergementService;
import Utils.GoogleMapsAPI;
import Utils.MyConnection;
import Utils.ReverseGeoCoding;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class AddHebergementController implements Initializable {

    @FXML
    private AnchorPane mainpane;

    @FXML
    private TextField prixParJour;

    @FXML
    private TextField capacite;

    @FXML
    private Button chooserFile;

    @FXML
    private TextField listView;

    @FXML
    private TextField adresse;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField nbChambre;

    private String absolutePath;

    @FXML
    private Button add;

    ObservableList<String> list = FXCollections.observableArrayList("Caravane", "flat", "house");

    @FXML
    private WebView mapView;
    private String gouvernorat;

    GoogleMapsAPI gma;
    ReverseGeoCoding rgc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gma = new GoogleMapsAPI(mapView);
        rgc = new ReverseGeoCoding();
        gma.initMap();
        type.setItems(list);
    }

    private void copyFile(File file) {
        try {
            File dest = new File("C:\\Users\\ASUS1\\Desktop\\java projects\\Nerds Java\\HuntKingdom\\Uploads\\" + file.getName()); //any location
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

    public void AddAccommodation(ActionEvent event) throws IOException {
        if (controleDeSaisie()) {
            MyConnection mc = MyConnection.getInstance();
            HebergementService ps = new HebergementService();
            float price = Float.parseFloat(prixParJour.getText());
            int nbch = Integer.parseInt(nbChambre.getText());
            int nblits = Integer.parseInt(capacite.getText());
            //    CoordonneesGPS coogps = new CoordonneesGPS(gma.getLatitude(), gma.getLongitude());
            //    String lat=Double.toString(gma.getLatitude());
            //    String lon=Double.toString(gma.getLongitude());
            //    lon=lon.concat(" ");
            //    lon=lon.concat(lat);
            Hebergement h = new Hebergement(type.getValue(), price, absolutePath, gouvernorat, nbch, nblits, nblits, description.getText());
            if (ps.addHebergement(h)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Accomodation");
                alert.setHeaderText(null);
                alert.setContentText("Accommodation succesfully added ");
                alert.showAndWait();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addHebergement.fxml"));
                mainpane.getChildren().setAll(pane);
            }
        }
    }

    @FXML
    private void coordonneesSelected(MouseEvent event) {
        System.out.println(String.valueOf(gma.getLatitude()));
        System.out.println(String.valueOf(gma.getLongitude()));
//        gouvernorat = rgc.getGouvernorat(String.valueOf(gma.getLatitude()), String.valueOf(gma.getLongitude()));
        try {
            gouvernorat = rgc.getGouvernorat(String.valueOf(gma.getLatitude()), String.valueOf(gma.getLongitude()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        adresse.setText(gouvernorat);
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();

    }

    private boolean controleDeSaisie() {

        if (prixParJour.getText().isEmpty() || adresse.getText().isEmpty()
                || type.getValue().isEmpty() || nbChambre.getText().isEmpty() || capacite.getText().isEmpty() || absolutePath == null) {
            if (adresse.getText().isEmpty()) {
                adresse.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (prixParJour.getText().isEmpty()) {
                prixParJour.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (type.getValue() == null) {
                type.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (nbChambre.getText().isEmpty()) {
                nbChambre.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (capacite.getText().isEmpty()) {
                capacite.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (absolutePath == null) {
                chooserFile.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        }
        if (!Pattern.matches("^[0-9]*\\.?[0-9]+$", prixParJour.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Verify The price Per Day field!");
            prixParJour.requestFocus();
            prixParJour.selectEnd();
            prixParJour.setStyle("-fx-border-color: red; -fx-background-color: white;");
            return false;
        }
        if (!Pattern.matches("^[1-9][0-9]*$", capacite.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Verify The Capacity field!");
            capacite.requestFocus();
            capacite.selectEnd();
            capacite.setStyle("-fx-border-color: red; -fx-background-color: white;");
            return false;
        }
            if (!Pattern.matches("^[1-9][0-9]*$", nbChambre.getText())) {
                showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Verify The Number of Rooms field!");
                nbChambre.requestFocus();
                nbChambre.selectEnd();
                nbChambre.setStyle("-fx-border-color: red; -fx-background-color: white;");
                return false;
            }

        return true;
    }
}
