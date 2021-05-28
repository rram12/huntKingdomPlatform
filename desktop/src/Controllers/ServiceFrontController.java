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
import Entities.User;
import Services.HebergementService;
import Services.LocationService;
import Services.MoyenDeTransportService;
import Services.ReservationService;
import Utils.MyConnection;
import Utils.Session;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.rgb;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class ServiceFrontController implements Initializable {
    
    private String absolutePath;
    @FXML
    private AnchorPane pane;

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

    @FXML
    private JFXButton confirmBtn1;
    
    @FXML
    private JFXButton cancelBtn1;

   

    @FXML
    private JFXButton updateBtn1;

    @FXML
    private Label sorry1;

    @FXML
    private Label sorry2;
    /*Location Fields end*/

 /*Reservation Fields*/
    @FXML
    private JFXTextField prixTot;
    
    @FXML
    private JFXTextField nbJours;
    
    @FXML
    private DatePicker dateArrivee;
    
    @FXML
    private JFXButton confirmBtn;
    /*Reservation Fields end*/



    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private Label sorry3;

    @FXML
    private Label sorry4;
    /*Reservation Fields end*/
    @FXML
    private FlowPane flow1;
    
    @FXML
    private AnchorPane flowRent;

    @FXML
    private Tab TransportPane;
    
    @FXML
    private Tab AccommodationsPane;
    
    @FXML
    private Tab MyPane;
    
    @FXML
    private FlowPane flow2;
    
   

   

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
    private int id2;
    Location loc;
    Reservation res;
    
    User currentUser = LoginController.getInstance().getLoggedUser();


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Services.UserService SU = new UserService();
//        try {
//            idU = SU.getConnectedUser();
//        } catch (SQLException ex) {
//            Logger.getLogger(ServiceFrontController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        idU =currentUser.getId();
        System.out.println(idU);
        flow.getChildren().clear();
        displayAccommodations();
        flow1.getChildren().clear();
        displayTransports();
        flow2.getChildren().clear();
        displayMy();
        displayMyReservations();
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
                    cancelBtn.setVisible(false);
                    updateBtn.setVisible(false);
                    sorry3.setVisible(false);
                    sorry4.setVisible(false);
                    confirmBtn.setVisible(true);
                    nbJours.setEditable(true);
                    dateArrivee.setEditable(true);
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
                    selectionModel.select(3); //select by index starting with 0
                    this.id = id1;
                    
                });
                
                nodes[i] = root;
                flow.getChildren().add(nodes[i]);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                    cancelBtn1.setVisible(false);
                    confirmBtn1.setVisible(true);
                    updateBtn1.setVisible(false);
                    sorry1.setVisible(false);
                    sorry2.setVisible(false);
                    confirmBtn1.setVisible(true);
                    nbJours1.setEditable(true);
                    dateArrivee1.setEditable(true);
                    MyConnection mc = MyConnection.getInstance();
                    LocationService ls = new LocationService();
                    ObservableList<Location> obsal = FXCollections.observableArrayList(ls.afficherLocations(id1));
                    listLocations.setItems(obsal);
                    RentPane.setDisable(false);
                    System.out.println(m);
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
                    selectionModel.select(4); //select by index starting with 0
                    this.id = id1;
                    
                });
                
                nodes[i] = root;
                flow1.getChildren().add(nodes[i]);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void displayMy() {
        MoyenDeTransportService ps = new MoyenDeTransportService();
        ArrayList<MoyenDeTransport> trans = (ArrayList) ps.afficher();
//        ArrayList<Location> MyLoc = new ArrayList<>();
        LocationService myLs = new LocationService();
        ArrayList<Location> MyLoc = (ArrayList) myLs.afficherMesLocations(idU);
        System.out.println("aaaaaaaaa" + MyLoc);
        MyLoc = validListLocation(MyLoc, trans);
        System.out.println("bbbbbbbbbb" + MyLoc);
        ObservableList<Location> obsl = FXCollections.observableArrayList(MyLoc);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/SingleLocation.fxml").openStream());
                SingleLocationController single = (SingleLocationController) loader.getController();
                Location l = MyLoc.get(i);
                MoyenDeTransport m = getTransport(l, trans);
                single.getInfo(m, l);
                int id1 = single.getCurrentId();
                

                JFXButton button = single.getButton();
                button.setText("Consult");
                button.setOnAction(e -> {
                    id2 = single.getCurrentLocationId();
                    loc = l;
                    Date now1 = new Date();
                    long diff = l.Arrivaldate().getTime() - now1.getTime();
                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    if (diffDays > 7) {
                        cancelBtn1.setVisible(true);
                        updateBtn1.setVisible(true);
                        sorry1.setVisible(false);
                        sorry2.setVisible(false);
                        nbJours1.setEditable(true);
                        dateArrivee1.setEditable(true);
                    } else {
                        cancelBtn1.setVisible(false);
                        updateBtn1.setVisible(false);
                        sorry1.setVisible(true);
                        sorry2.setVisible(true);
                        nbJours1.setEditable(false);
                        dateArrivee1.setEditable(false);
                    }
                    confirmBtn1.setVisible(false);
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
                    nbJours1.setText(Integer.toString(l.getNbJours()));
                    prixTot1.setText(Float.toString(l.getPrixTot()));
                    dateArrivee1.setValue(l.Arrivaldate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                    /**
                     * ********
                     */
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(4); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow2.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayMyReservations() {
        HebergementService ps = new HebergementService();
        ArrayList<Hebergement> trans = (ArrayList) ps.afficher();
//        ArrayList<Location> MyLoc = new ArrayList<>();
        ReservationService myLs = new ReservationService();
        ArrayList<Reservation> MyRes = (ArrayList) myLs.afficherMesReservations(idU);
        System.out.println("aaaaaaaaa" + MyRes);
        MyRes = validListReservation(MyRes, trans);
        System.out.println("bbbbbbbbbb" + MyRes);
        ObservableList<Reservation> obsl = FXCollections.observableArrayList(MyRes);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/SingleReservation.fxml").openStream());
                SingleReservationController single = (SingleReservationController) loader.getController();
                Reservation l = MyRes.get(i);
                Hebergement m = getHebergement(l, trans);
                single.getInfo(m, l);
                int id1 = single.getCurrentId();
                

                JFXButton button = single.getButton();
                button.setText("Consult");
                button.setOnAction(e -> {
                    id2 = single.getCurrentReservationId();
                    res = l;
                    Date now1 = new Date();
                    long diff = l.Arrivaldate().getTime() - now1.getTime();
                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    if (diffDays > 7) {
                        cancelBtn.setVisible(true);
                        updateBtn.setVisible(true);
                        sorry3.setVisible(false);
                        sorry4.setVisible(false);
                        nbJours.setEditable(true);
                        dateArrivee.setEditable(true);
                    } else {
                        cancelBtn.setVisible(false);
                        updateBtn.setVisible(false);
                        sorry3.setVisible(true);
                        sorry4.setVisible(true);
                        nbJours.setEditable(false);
                        dateArrivee.setEditable(false);
                    }
                    confirmBtn.setVisible(false);
                    ReservationService ls = new ReservationService();
                    ObservableList<Reservation> obsal = FXCollections.observableArrayList(ls.afficherReservations(id1));
                    listReservations.setItems(obsal);
                    ReservePane.setDisable(false);
                    prixParJourHeb.setText(Float.toString(m.getPrixParJour()));
                    adresseHeb.setText(m.getAdresse());
                    typeHeb.setText(m.getType());
                    nbChambreHeb.setText(Integer.toString(m.getNbChambre()));
                    absolutePath = m.getImage();
                    capaciteHeb.setText(Integer.toString(m.getCapacite()));
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
                    nbJours.setText(Integer.toString(l.getNbJours()));
                    prixTot.setText(Float.toString(l.getPrixTot()));
                    dateArrivee.setValue(l.Arrivaldate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                    /**
                     * ********
                     */
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(3); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow2.getChildren().add(nodes[i]);

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
                createpdf(null,l);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rent");
                alert.setHeaderText(null);
                alert.setContentText("Your Rent has been succesfully added and a pdf file with details is generated");
                alert.showAndWait();
                flow.getChildren().clear();
                displayAccommodations();
                flow1.getChildren().clear();
                displayTransports();
                flow2.getChildren().clear();
                displayMy();
                displayMyReservations();
                dateArrivee1.setStyle("-fx-border-color: none;");
                RentPane.setDisable(true);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(1); //select by index starting with 0
            }
        }

    }

    @FXML
    void onUpdating(ActionEvent event) {
        if (controleDeSaisieRent1()) {
            LocationService ps = new LocationService();
            int nbj = Integer.parseInt(nbJours1.getText());
            float price = Float.parseFloat(prixParJour.getText()) * nbj;
            Location lo = new Location(loc.getId(), nbj, price, java.sql.Date.valueOf(dateArrivee1.getValue().toString()));
            if (ps.updateLocation(lo)) {
                createpdf(null,lo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rent");
                alert.setHeaderText(null);
                alert.setContentText("Your Rent has been succesfully updated and a pdf file with new details is generated ");
                alert.showAndWait();
                flow.getChildren().clear();
                displayAccommodations();
                flow1.getChildren().clear();
                displayTransports();
                flow2.getChildren().clear();
                displayMy();
                displayMyReservations();
                dateArrivee1.setStyle("-fx-border-color: none;");
                RentPane.setDisable(true);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(1); //select by index starting with 0
            }
        }
        
    }
    @FXML
    void onCancelling(ActionEvent event) {
            LocationService ps = new LocationService();
            if (ps.supprimerLocation(id2)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rent Cancelling");
                alert.setHeaderText(null);
                alert.setContentText("Your Rent has been succesfully cancelled ");
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
    
 

    @FXML
    void onConfirmation1(ActionEvent event) {
        if (controleDeSaisieReservation()) {
            ReservationService ps = new ReservationService();
            int nbj = Integer.parseInt(nbJours.getText());
            float price = Float.parseFloat(prixParJourHeb.getText()) * nbj;
            Reservation r = new Reservation(nbj, price, java.sql.Date.valueOf(dateArrivee.getValue().toString()), idU, id);
            if (ps.ajouterReservation(r)) {
                createpdf(r,null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resevation");
                alert.setHeaderText(null);
                alert.setContentText("Your Reservation has been succesfully added and a pdf file with details is generated");
                alert.showAndWait();
                flow.getChildren().clear();
                displayAccommodations();
                flow1.getChildren().clear();
                displayTransports();
                flow2.getChildren().clear();
                displayMy();
                displayMyReservations();
                dateArrivee.setStyle("-fx-border-color: none;");
                ReservePane.setDisable(true);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0); //select by index starting with 0   
            }
        }
    }

    @FXML
    void onUpdating1(ActionEvent event) {
        if (controleDeSaisieReservation1()) {
            ReservationService ps = new ReservationService();
            int nbj = Integer.parseInt(nbJours.getText());
            float price = Float.parseFloat(prixParJourHeb.getText()) * nbj;
            Reservation lo = new Reservation(res.getId(), nbj, price, java.sql.Date.valueOf(dateArrivee.getValue().toString()));
            if (ps.updateReservation(lo)) {
                createpdf(lo,null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reservation");
                alert.setHeaderText(null);
                alert.setContentText("Your Reservation has been succesfully updated and a pdf file with new details is generated ");
                alert.showAndWait();
                flow.getChildren().clear();
                displayAccommodations();
                flow1.getChildren().clear();
                displayTransports();
                flow2.getChildren().clear();
                displayMy();
                displayMyReservations();
                dateArrivee.setStyle("-fx-border-color: none;");
                ReservePane.setDisable(true);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0); //select by index starting with 0
            }
        }

    }

    @FXML
    void onCancelling1(ActionEvent event) {
        ReservationService ps = new ReservationService();
        if (ps.supprimerReservation(id2)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reservation Cancelling");
            alert.setHeaderText(null);
            alert.setContentText("Your Reservation has been succesfully cancelled ");
            alert.showAndWait();
            flow.getChildren().clear();
            displayAccommodations();
            flow1.getChildren().clear();
            displayTransports();
            flow2.getChildren().clear();
            displayMy();
            displayMyReservations();
            dateArrivee.setStyle("-fx-border-color: none;");
            ReservePane.setDisable(true);
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(0); //select by index starting with 0
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

    private boolean controleDeSaisieReservation1() {

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
//            MyConnection mc = MyConnection.getInstance();
            ReservationService ls = new ReservationService();
            List<Reservation> li = ls.afficherReservations(id);
            for (int j = 0; j < li.size(); j++) {
                if (li.get(j).getId() == res.getId()) {
                    li.remove(j);
                }
            }
            System.out.println(res);
            li.remove(res);
            System.out.println(li);
            for (Reservation e : li) {
                for (int i = 0; i < Integer.parseInt(nbJours.getText()); i++) {

                    if (e.Arrivaldate().compareTo(datePlusOne(i, java.sql.Date.valueOf(dateArrivee.getValue().toString()))) * datePlusOne(i, java.sql.Date.valueOf(dateArrivee.getValue().toString())).compareTo(e.finaldate()) >= 0) {
                        showAlert(Alert.AlertType.ERROR, "Invalid date", "", "Please check the list of Reservation for available date !");
                        return false;
                    }
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

    private boolean controleDeSaisieRent1() {

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
//            MyConnection mc = MyConnection.getInstance();
            LocationService ls = new LocationService();
            List<Location> li = ls.afficherLocations(id);
            for (int j = 0; j < li.size(); j++) {
                if (li.get(j).getId() == loc.getId()) {
                    li.remove(j);
                }
            }
            System.out.println(loc);
            li.remove(loc);
            System.out.println(li);
            for (Location e : li) {
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
    public ArrayList<Location> validListLocation(ArrayList<Location> MyLoc, ArrayList<MoyenDeTransport> trans) {
        ArrayList<Location> MyLoc1 = new ArrayList<>();;
        boolean exist = false;
        if (MyLoc != null) {
            for (int j = 0; j < MyLoc.size(); j++) {
                Iterator<MoyenDeTransport> it = trans.iterator();
                while (it.hasNext() && exist == false) {
                    if (it.next().getId() == MyLoc.get(j).getMoyenDeTransportId()) {
                        exist = true;
                    }
                }
                if (exist == true) {
                    MyLoc1.add(MyLoc.get(j));
                    exist = false;
                }
            }
        }
        return MyLoc1;
    }
    public MoyenDeTransport getTransport(Location l, ArrayList<MoyenDeTransport> trans) {
        MoyenDeTransport m = new MoyenDeTransport();
        for (int i = 0; i < trans.size(); i++) {
            if (trans.get(i).getId() == l.getMoyenDeTransportId()) {
                return trans.get(i);
            }
        }
        return m;
    }

    public ArrayList<Reservation> validListReservation(ArrayList<Reservation> MyLoc, ArrayList<Hebergement> trans) {
        ArrayList<Reservation> MyLoc1 = new ArrayList<>();;
        boolean exist = false;
        if (MyLoc != null) {
            for (int j = 0; j < MyLoc.size(); j++) {
                Iterator<Hebergement> it = trans.iterator();
                while (it.hasNext() && exist == false) {
                    if (it.next().getId() == MyLoc.get(j).getHebergementId()) {
                        exist = true;
                    }
                }
                if (exist == true) {
                    MyLoc1.add(MyLoc.get(j));
                    exist = false;
                }
            }
        }
        return MyLoc1;
    }

    public Hebergement getHebergement(Reservation l, ArrayList<Hebergement> trans) {
        Hebergement m = new Hebergement();
        for (int i = 0; i < trans.size(); i++) {
            if (trans.get(i).getId() == l.getHebergementId()) {
                return trans.get(i);
            }
        }
        return m;
    }
    
    private void createpdf(Reservation r,Location l) {
        int idprod,idbooking;
        float prix;
        String dateDebut;
        String DynamicPath =System.getProperty("user.dir");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
        LocalDateTime today = LocalDateTime.now();
        String dateS = today.toString();
        String now = today.toString();
        dateS = dateS.replace(':','A');
        try {
            String out = DynamicPath+"\\src\\Uploads\\bill"+dateS+".pdf";
            Document document = new Document();
            StringBuilder boxText = new StringBuilder();
            PdfWriter.getInstance(document, new FileOutputStream(out));
            document.open();
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(DynamicPath+"\\src\\Uploads\\logo2.png");
            logo.scaleToFit(200, 70);
            document.add(logo);
            document.add(new Paragraph("\n\n\n"));
            document.add(new Paragraph("Booking bill :\n\n\nDate : "+now));
            PdfPTable table = new PdfPTable(5); // 5 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            PdfPCell cell1 = new PdfPCell(new Paragraph("Image"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PdfPCell cell2 = new PdfPCell(new Paragraph("PRODUCT ID"));
            cell2.setBorderColor(BaseColor.BLUE);
            cell2.setPaddingLeft(5);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Arrival Date"));
            cell3.setBorderColor(BaseColor.BLUE);
            cell3.setPaddingLeft(5);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            
            PdfPCell cell5 = new PdfPCell(new Paragraph("DAYS"));
            cell5.setBorderColor(BaseColor.BLUE);
            cell5.setPaddingLeft(5);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell4 = new PdfPCell(new Paragraph("TOTAL PRICE"));
            cell3.setBorderColor(BaseColor.BLUE);
            cell3.setPaddingLeft(5);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell5);
            table.addCell(cell4);
            document.add(table);
            PdfPTable table1 = new PdfPTable(5); // 5 columns.
            table1.setWidthPercentage(100); //Width 100%
            table1.setSpacingBefore(10f); //Space before table
            table1.setSpacingAfter(10f); //Space after table
            if(r==null){
                idprod=l.getMoyenDeTransportId();
                idbooking=l.getNbJours();
                prix=l.getPrixTot();
                dateDebut=sdf.format( l.getDateArrivee());
            }
            else{
                idprod=r.getHebergementId();
                idbooking=r.getNbJours();
                prix=r.getPrixTot();
                dateDebut=sdf.format(r.getDateArrivee());
            }
                
            com.itextpdf.text.Image x = com.itextpdf.text.Image.getInstance(DynamicPath+"\\src\\Uploads\\logo2.png");
               x.scaleToFit(100, 100);
            PdfPCell image = new PdfPCell(x);
            image.setBorderColor(BaseColor.BLUE);
            image.setPaddingLeft(10);
            image.setHorizontalAlignment(Element.ALIGN_CENTER);
            image.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell txt_titre = new PdfPCell(new Paragraph(Integer.toString(idprod)));
            txt_titre.setBorderColor(BaseColor.BLUE);
            txt_titre.setPaddingLeft(5);
            txt_titre.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_titre.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell txt_desc = new PdfPCell(new Paragraph(Integer.toString(idbooking)));
            txt_desc.setBorderColor(BaseColor.BLUE);
            txt_desc.setPaddingLeft(5);
            txt_desc.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_desc.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell txt_debut = new PdfPCell(new Paragraph(dateDebut));
            txt_debut.setBorderColor(BaseColor.BLUE);
            txt_debut.setPaddingLeft(5);
            txt_debut.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_debut.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell txt_prix = new PdfPCell(new Paragraph(Float.toString(prix)));
            txt_prix.setBorderColor(BaseColor.BLUE);
            txt_prix.setPaddingLeft(5);
            txt_prix.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_prix.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(image);
            table1.addCell(txt_titre);
            table1.addCell(txt_debut);
            table1.addCell(txt_desc);
            table1.addCell(txt_prix);
            document.add(table1);
            document.close();
           
            System.out.println("Document '" + out + "' generated");
//             try {
//                        Desktop.getDesktop().open(new java.io.File(out));
//                    } catch (IOException ex) {
//                        System.out.println(ex.getMessage());
//                    }
        } catch (FileNotFoundException ex) {
        } catch (DocumentException ex) {
        } catch (IOException ex) {
        }

       
    }
}
