/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Hebergement;
import Services.HebergementService;
import Utils.MyConnection;
import com.sun.prism.impl.Disposer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.rgb;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class ServiceController implements Initializable {

    @FXML
    private TextField search;//search field

    //update variables
    @FXML
    private ImageView imageView;

    @FXML
    private Button update;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<String> type;
    ObservableList<String> list = FXCollections.observableArrayList("Caravane", "flat", "house");

    @FXML
    private Group ajout;

    @FXML
    private TextField nbChambre;

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

    private int current_id;

    private String absolutePath;
    //end update variables
    @FXML
    private AnchorPane mainpane; //container for child page

    @FXML
    private Button addHebergement;//go to adding accommodation page 

    //table columns
    @FXML
    private TableColumn<Hebergement, String> Category;

    @FXML
    private TableColumn<Hebergement, String> Address;

    @FXML
    private TableColumn<Hebergement, Integer> Capacity;

    @FXML
    private TableColumn<Hebergement, Float> PricePerDay;

    @FXML
    private TableColumn<Hebergement, Integer> ID;

    @FXML
    private TableColumn<Hebergement, Integer> NbRooms;

    @FXML
    private TableColumn<Hebergement, String> Description;

    @FXML
    private TableView<Hebergement> table;

    @FXML
    private TableColumn col_action;//delete column

    //end table columns
    public void chooseFileAction() {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select an image");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            listView.clear();
            listView.setText(selectedFile.getName());
            absolutePath = selectedFile.getAbsolutePath();
            /**
             * update the imageView*
             */
            try {
                Image images = new Image(new FileInputStream(absolutePath));
                imageView.setImage(images);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }

        } else {
            System.out.println("file is not valid !");
        }
    }

    MyConnection mc = MyConnection.getInstance();
    HebergementService ps = new HebergementService();
    public ObservableList<Hebergement> obsl = FXCollections.observableArrayList(ps.afficher());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        HebergementService ps= new HebergementService();
//        ArrayList<Hebergement> a = new ArrayList<>();
//        a=(ArrayList<Hebergement>) ps.afficher();
//        ObservableList<Hebergement> obsl = FXCollections.observableArrayList(a);

        /**
         * * filter *
         */
        FilteredList<Hebergement> filteredData = new FilteredList<>(obsl, e -> true);
        search.setOnKeyReleased(e -> {
            adresse.setStyle("-fx-background-color: white;");
            prixParJour.setStyle("-fx-background-color: white;");
            type.setStyle("-fx-background-color: white;");
            nbChambre.setStyle("-fx-background-color: white;");
            capacite.setStyle("-fx-background-color: white;");
            chooserFile.setStyle("-fx-background-color: white;");
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Hebergement>) Hebergement -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (Hebergement.getType().toLowerCase().contains(lower) || Hebergement.getAdresse().toLowerCase().contains(lower) || String.valueOf(Hebergement.getNbChambre()).contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Hebergement> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        table.setItems(obsl);
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Category.setCellValueFactory(new PropertyValueFactory<>("type"));
        PricePerDay.setCellValueFactory(new PropertyValueFactory<>("prixParJour"));
        Address.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        NbRooms.setCellValueFactory(new PropertyValueFactory<>("nbChambre"));
        Capacity.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        col_action.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        type.setItems(list);

        table.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                adresse.setStyle("-fx-background-color: white;");
                prixParJour.setStyle("-fx-background-color: white;");
                type.setStyle("-fx-background-color: white;");
                nbChambre.setStyle("-fx-background-color: white;");
                capacite.setStyle("-fx-background-color: white;");
                chooserFile.setStyle("-fx-background-color: white;");
                Hebergement rowData = table.getSelectionModel().getSelectedItem();
                /**
                 * fill the fields with the selected data *
                 */

                prixParJour.setText(Float.toString(rowData.getPrixParJour()));
                adresse.setText(rowData.getAdresse());
                description.setText(rowData.getDescription());
                nbChambre.setText(Integer.toString(rowData.getNbChambre()));
                capacite.setText(Integer.toString(rowData.getCapacite()));
                absolutePath = rowData.getImage();
                listView.clear();
                listView.setText(absolutePath.substring(absolutePath.lastIndexOf(("\\")) + 1));
                type.setValue(rowData.getType());
                current_id = rowData.getId();
                try {
                    Image image = new Image(new FileInputStream(absolutePath));
                    imageView.setImage(image);
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                }
                ajout.setVisible(true);

            }
        });
        /**
         * ** double click event **
         */
        table.setRowFactory(tv -> {
            TableRow<Hebergement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    adresse.setStyle("-fx-background-color: white;");
                    prixParJour.setStyle("-fx-background-color: white;");
                    type.setStyle("-fx-background-color: white;");
                    nbChambre.setStyle("-fx-background-color: white;");
                    capacite.setStyle("-fx-background-color: white;");
                    chooserFile.setStyle("-fx-background-color: white;");
                    Hebergement rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    prixParJour.setText(Float.toString(rowData.getPrixParJour()));
                    adresse.setText(rowData.getAdresse());
                    description.setText(rowData.getDescription());
                    nbChambre.setText(Integer.toString(rowData.getNbChambre()));
                    capacite.setText(Integer.toString(rowData.getCapacite()));
                    absolutePath = rowData.getImage();
                    listView.clear();
                    listView.setText(absolutePath.substring(absolutePath.lastIndexOf(("\\")) + 1));
                    type.setValue(rowData.getType());
                    current_id = rowData.getId();
                    try {
                        Image image = new Image(new FileInputStream(absolutePath));
                        imageView.setImage(image);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex);
                    }
                    ajout.setVisible(true);
                }
            });
            return row;
        });
    }

    private class ButtonCell extends TableCell<Disposer.Record, Boolean> {

        final Button cellButton = new Button("Delete");

        ButtonCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    adresse.setStyle("-fx-background-color: white;");
                    prixParJour.setStyle("-fx-background-color: white;");
                    type.setStyle("-fx-background-color: white;");
                    nbChambre.setStyle("-fx-background-color: white;");
                    capacite.setStyle("-fx-background-color: white;");
                    chooserFile.setStyle("-fx-background-color: white;");
                    //confirmation alert 
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("delete Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure ?");

                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        // get Selected Item
                        Hebergement selectedH = (Hebergement) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        HebergementService ps = new HebergementService();
                        //Piecesdefectueuses p = new Piecesdefectueuses(nom.getText(), combobox.getValue(), description.getText(), image.getText(), 1);
                        if (ps.deleteHebergement(selectedH.getId())) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Accommodation");
                            alert1.setHeaderText(null);
                            alert1.setContentText("Accommodation succesfully updated");
                            alert1.showAndWait();
                            //remove it from the tableView
                            obsl.remove(selectedH);
                        }
                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    public void goToAdd(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addMoyenDeTransport.fxml"));
        mainpane.getChildren().setAll(pane);
    }

    @FXML
    public void updateHebergement(ActionEvent event) {
        if (controleDeSaisie()) {
            MyConnection mc = MyConnection.getInstance();
            HebergementService ps = new HebergementService();
            float price = Float.parseFloat(prixParJour.getText());
            int nbch = Integer.parseInt(nbChambre.getText());
            int nblits = Integer.parseInt(capacite.getText());
            Hebergement mt = new Hebergement(current_id, type.getValue(), Float.parseFloat(prixParJour.getText()), absolutePath, adresse.getText(), nbch, nblits, nblits, description.getText());
            if (ps.updateHebergement(mt)) {
                adresse.setStyle("-fx-background-color: white;");
                prixParJour.setStyle("-fx-background-color: white;");
                type.setStyle("-fx-background-color: white;");
                nbChambre.setStyle("-fx-background-color: white;");
                capacite.setStyle("-fx-background-color: white;");
                chooserFile.setStyle("-fx-background-color: white;");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Accommodation");
                alert.setHeaderText(null);
                alert.setContentText("Accommodation succesfully updated");
                alert.showAndWait();
                obsl.clear();
                obsl = FXCollections.observableArrayList(ps.afficher());
                table.setItems(obsl);
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
