/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Hebergement;
import Entities.Location;
import Entities.MoyenDeTransport;
import Entities.Reservation;
import Services.LocationService;
import Services.ReservationService;
import Utils.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXListView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class UnMoyenDeTransportController implements Initializable {

    @FXML
    private TextField prixParJour;

    @FXML
    private TextField categorie;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private TextField type;

    @FXML
    private Group ajout;

    @FXML
    private JFXListView<Location> listLocations;

    @FXML
    private TextField marque;

    String absolutePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MoyenDeTransport m = Session.current_moyenDeTransport;
        prixParJour.setText(Float.toString(m.getPrixParJour()));
        marque.setText(m.getMarque());
        categorie.setText(m.getCategorie());
        absolutePath = m.getImage();
        type.setText(m.getType());
        try {
            Image image = new Image(new FileInputStream(absolutePath));
            imageView.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        LocationService ls = new LocationService();
        ObservableList<Location> obsal = FXCollections.observableArrayList(ls.afficherLocations(m.getId()));
        listLocations.setItems(obsal);
    }

    public void goBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/MoyenDeTransport.fxml"));
        mainpane.getChildren().setAll(pane);
    }

}
