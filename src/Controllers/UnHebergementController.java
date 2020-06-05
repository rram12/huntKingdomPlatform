/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Hebergement;
import Entities.Reservation;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class UnHebergementController implements Initializable {
    
    @FXML
    private TextField prixParJour;

    @FXML
    private JFXListView<Reservation> listReservations;

    @FXML
    private TextField capacite;

    @FXML
    private TextField adresse;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea description;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private TextField type;

    @FXML
    private Group ajout;

    @FXML
    private TextField nbChambre;
    
    String absolutePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Hebergement h=Session.current_hebergement;
        prixParJour.setText(Float.toString(h.getPrixParJour()));
                adresse.setText(h.getAdresse());
                description.setText(h.getDescription());
                nbChambre.setText(Integer.toString(h.getNbChambre()));
                capacite.setText(Integer.toString(h.getCapacite()));
                absolutePath = h.getImage();
                type.setText(h.getType());
                try {
                    Image image = new Image(new FileInputStream(absolutePath));
                    imageView.setImage(image);
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                }
                ReservationService rs = new ReservationService();
                    ObservableList<Reservation> obsal = FXCollections.observableArrayList(rs.afficherReservations(h.getId()));
                    listReservations.setItems(obsal);
    }  
    
    public void goBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Service.fxml"));
        mainpane.getChildren().setAll(pane);
    }
    
}
