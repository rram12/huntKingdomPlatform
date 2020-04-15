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
import Services.HebergementService;
import Services.LocationService;
import Services.MoyenDeTransportService;
import Services.ReservationService;
import Services.UserService;
import Utils.MyConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import static javafx.scene.paint.Color.rgb;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class ServiceFrontController implements Initializable {

    private String absolutePath;

    /*Hebergement*/
    @FXML
    private JFXTextField prixParJourHeb;

    @FXML
    private JFXTextField typeHeb;

    @FXML
    private JFXTextField nbChambreHeb;

    @FXML
    private ImageView imageHeb;

    @FXML
    private JFXTextField capaciteHeb;

    @FXML
    private JFXTextField adresseHeb;

    /*Hebergement end*/
 /*Moyen De Transport*/
    @FXML
    private ImageView images;

    @FXML
    private JFXTextField categorie;

    @FXML
    private JFXTextField type;

    @FXML
    private JFXTextField marque;

    @FXML
    private JFXTextField prixParJour;
    /*Moyen de Transport end*/

 /*Location Fields*/
    @FXML
    private JFXTextField prixTot1;

    @FXML
    private JFXTextField nbJours1;

    @FXML
    private DatePicker dateArrivee1;
    /*Location Fields end*/

 /*Reservation Fields*/
    @FXML
    private JFXTextField prixTot;

    @FXML
    private JFXTextField nbJours;

    @FXML
    private DatePicker dateArrivee;
    /*Reservation Fields end*/

    @FXML
    private FlowPane flow1;

    @FXML
    private Tab TransportPane;

    @FXML
    private Tab AccommodationsPane;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private FlowPane flow;

    @FXML
    private Tab ReservePane;

    @FXML
    private Tab RentPane;

    private int id;

    @FXML
    private JFXListView<Reservation> listReservations;

    @FXML
    private JFXListView<Location> listLocations;
    private int idU;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Services.UserService SU = new UserService();
        try {
            idU = SU.getConnectedUser();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        flow.getChildren().clear();
        displayAccommodations();
        flow1.getChildren().clear();
        displayTransports();
        // ListPane.onSelectionChangedProperty();
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (oldTab == ReservePane) {
                ReservePane.setDisable(true);
            }
            if (oldTab == RentPane) {
                RentPane.setDisable(true);
            }
        });
        nbJours1.setOnKeyReleased(e -> {
            nbJours1.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                //System.out.println(newValue);
                if (!Pattern.matches("^[1-9][0-9]*$", nbJours1.getText())) {
                    nbJours1.setText("");
                    prixTot1.setText("");
                } else if (nbJours1.getText().isEmpty()) {
                    prixTot1.setText("");
                } else {
                    prixTot1.setText(Float.toString(Float.parseFloat(newValue) * Float.parseFloat(prixParJour.getText())));
                }
            });
        });
        nbJours.setOnKeyReleased(e -> {

            nbJours.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                //System.out.println(newValue);
                if (!Pattern.matches("^[1-9][0-9]*$", nbJours.getText())) {
                    nbJours.setText("");
                    prixTot.setText("");
                } else if (nbJours.getText().isEmpty()) {
                    prixTot.setText("");
                } else {
                    prixTot.setText(Float.toString(Float.parseFloat(newValue) * Float.parseFloat(prixParJourHeb.getText())));
                }

            });
        });
    }

    private void displayAccommodations() {
        ArrayList<Hebergement> Hebs = new ArrayList<>();
        HebergementService ps = new HebergementService();
        Hebs = (ArrayList) ps.afficher();
        ObservableList<Hebergement> obsl = FXCollections.observableArrayList(Hebs);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("/Gui/SingleHebergement.fxml").openStream());
                SingleHebergementController single = (SingleHebergementController) loader.getController();
                single.getInfo(Hebs.get(i));
                int id1 = single.getCurrentId();
                Hebergement h = Hebs.get(i);
                JFXButton button = single.getButton();
                button.setText("Consult");
                button.setOnAction(e -> {
                    MyConnection mc = MyConnection.getInstance();
                    ReservationService rs = new ReservationService();
                    ObservableList<Reservation> obsal = FXCollections.observableArrayList(rs.afficherReservations(id1));
                    listReservations.setItems(obsal);
                    ReservePane.setDisable(false);
                    prixParJourHeb.setText(Float.toString(h.getPrixParJour()));
                    adresseHeb.setText(h.getAdresse());
                    typeHeb.setText(h.getType());
                    nbChambreHeb.setText(Integer.toString(h.getNbChambre()));
                    absolutePath = h.getImage();
                    capaciteHeb.setText(Integer.toString(h.getCapacite()));
                    try {
                        Image image = new Image(new FileInputStream(absolutePath));
                        imageHeb.setImage(image);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex);
                    }
                    prixParJourHeb.setEditable(false);
                    adresseHeb.setEditable(false);
                    nbChambreHeb.setEditable(false);
                    capaciteHeb.setEditable(false);
                    typeHeb.setEditable(false);
                    /**
                     * vider les champs*
                     */
                    nbJours.setText("1");
                    prixTot.setText(prixParJourHeb.getText());
                    dateArrivee.setValue(null);
                    /**
                     * ********
                     */
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(2); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    
//    @FXML
//    void listTransport(ActionEvent event) {
//        
//        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
//        if(oldTab==ReservePane){
//        ReservePane.setDisable(true);
//        }
//    });
//    }

    private void displayTransports() {
        ArrayList<MoyenDeTransport> trans = new ArrayList<>();
        MoyenDeTransportService ps = new MoyenDeTransportService();
        trans = (ArrayList) ps.afficher();
        ObservableList<MoyenDeTransport> obsl = FXCollections.observableArrayList(trans);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/SingleMoyenDeTransport.fxml").openStream());
                SingleMoyenDeTransportController single = (SingleMoyenDeTransportController) loader.getController();
                single.getInfo(trans.get(i));
                int id1 = single.getCurrentId();
                MoyenDeTransport m = trans.get(i);
                JFXButton button = single.getButton();
                button.setText("Consult");
                button.setOnAction(e -> {
                    MyConnection mc = MyConnection.getInstance();
                    LocationService ls = new LocationService();
                    ObservableList<Location> obsal = FXCollections.observableArrayList(ls.afficherLocations(id1));
                    listLocations.setItems(obsal);
                    RentPane.setDisable(false);
                    prixParJour.setText(Float.toString(m.getPrixParJour()));
                    marque.setText(m.getMarque());
                    categorie.setText(m.getCategorie());
                    absolutePath = m.getImage();
                    type.setText(m.getType());
                    try {
                        Image image = new Image(new FileInputStream(absolutePath));
                        images.setImage(image);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex);
                    }
                    prixParJour.setEditable(false);
                    marque.setEditable(false);
                    categorie.setEditable(false);
                    type.setEditable(false);

                    /**
                     * vider les champs*
                     */
                    nbJours1.setText("1");
                    prixTot1.setText(prixParJour.getText());
                    dateArrivee1.setValue(null);
                    /**
                     * ********
                     */
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(3); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow1.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onConfirmation(ActionEvent event) {
        if (controleDeSaisieRent()) {
            LocationService ps = new LocationService();
            int nbj = Integer.parseInt(nbJours1.getText());
            float price = Float.parseFloat(prixParJour.getText()) * nbj;
            Location l = new Location(nbj, price, java.sql.Date.valueOf(dateArrivee1.getValue().toString()), idU, id);
            if (ps.ajouterLocation(l)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rent");
                alert.setHeaderText(null);
                alert.setContentText("Your Rent has been succesfully added ");
                alert.showAndWait();
                flow.getChildren().clear();
                displayAccommodations();
                flow1.getChildren().clear();
                displayTransports();
                RentPane.setDisable(true);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(1); //select by index starting with 0
            }
        }

    }

    @FXML
    void onConfirmation1(ActionEvent event) {
        if (controleDeSaisieReservation()) {
            ReservationService ps = new ReservationService();
            int nbj = Integer.parseInt(nbJours.getText());
            float price = Float.parseFloat(prixParJourHeb.getText()) * nbj;
            Reservation r = new Reservation(nbj, price, java.sql.Date.valueOf(dateArrivee.getValue().toString()), idU, id);
            if (ps.ajouterReservation(r)) {
//                ps.afficherReservations(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resevation");
                alert.setHeaderText(null);
                alert.setContentText("Your Reservation has been succesfully added ");
                alert.showAndWait();
                flow.getChildren().clear();
                displayAccommodations();
                flow1.getChildren().clear();
                displayTransports();
                ReservePane.setDisable(true);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0); //select by index starting with 0   
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

    private boolean controleDeSaisieReservation() {

        if (prixTot.getText().isEmpty() || nbJours.getText().isEmpty()
                || dateArrivee.getValue() == null) {
            if (prixTot.getText().isEmpty()) {
                prixTot.setUnFocusColor(rgb(255, 0, 0));
                prixTot.setFocusColor(rgb(255, 0, 0));
            }
            if (nbJours.getText().isEmpty()) {
                nbJours.setUnFocusColor(rgb(255, 0, 0));
                nbJours.setFocusColor(rgb(255, 0, 0));
            }
            if (dateArrivee.getValue() == null) {
                dateArrivee.setStyle("-fx-border-color: red;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        } else {
            MyConnection mc = MyConnection.getInstance();
            ReservationService rs = new ReservationService();

            for (Reservation e : rs.afficherReservations(id)) {
                //Date parcours = null;
                for (int i = 0; i < Integer.parseInt(nbJours.getText()); i++) {

                    if (e.Arrivaldate().compareTo(datePlusOne(i, java.sql.Date.valueOf(dateArrivee.getValue().toString()))) * datePlusOne(i, java.sql.Date.valueOf(dateArrivee.getValue().toString())).compareTo(e.finaldate()) >= 0) {
                        showAlert(Alert.AlertType.ERROR, "Invalid date", "", "Please check the list of reservation for available date !");
                        return false;
                    }
                    //parcours = datePlusOne(java.sql.Date.valueOf(dateArrivee.getValue().toString()));
                }
            }
        }
        return true;
    }

    private boolean controleDeSaisieRent() {

        if (prixTot1.getText().isEmpty() || nbJours1.getText().isEmpty()
                || dateArrivee1.getValue() == null) {
            if (prixTot1.getText().isEmpty()) {
                prixTot1.setUnFocusColor(rgb(255, 0, 0));
                prixTot1.setFocusColor(rgb(255, 0, 0));
            }
            if (nbJours1.getText().isEmpty()) {
                nbJours1.setUnFocusColor(rgb(255, 0, 0));
                nbJours1.setFocusColor(rgb(255, 0, 0));
            }
            if (dateArrivee1.getValue() == null) {
                dateArrivee1.setStyle("-fx-border-color: red;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        } else {
            MyConnection mc = MyConnection.getInstance();
            LocationService ls = new LocationService();

            for (Location e : ls.afficherLocations(id)) {
                for (int i = 0; i < Integer.parseInt(nbJours1.getText()); i++) {

                    if (e.Arrivaldate().compareTo(datePlusOne(i, java.sql.Date.valueOf(dateArrivee1.getValue().toString()))) * datePlusOne(i, java.sql.Date.valueOf(dateArrivee1.getValue().toString())).compareTo(e.finaldate()) >= 0) {
                        showAlert(Alert.AlertType.ERROR, "Invalid date", "", "Please check the list of Rent for available date !");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Date datePlusOne(int i, Date d) {
//        String oldDate = "2017-01-29";  
//	System.out.println("Date before Addition: "+oldDate);
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        //Incrementing the date by 1 day
        c.add(Calendar.DAY_OF_MONTH, i);
        Date date = c.getTime();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String date1 = format1.format(date);

        Date inActiveDate = null;

        try {

            inActiveDate = format1.parse(date1);

        } catch (ParseException e1) {

            // TODO Auto-generated catch block
            e1.printStackTrace();

        }
        return inActiveDate;
    }
}
