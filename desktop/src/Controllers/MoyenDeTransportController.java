/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Hebergement;
import Entities.MoyenDeTransport;
import Services.MoyenDeTransportService;
import Utils.MyConnection;
import Utils.Session;
import com.sun.prism.impl.Disposer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.rgb;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ASUS1
 */
public class MoyenDeTransportController implements Initializable {
    
    @FXML
    private TextField search;//search field
    
    @FXML
    private AnchorPane mainpane;
    
    @FXML
    private TableColumn<MoyenDeTransport,String> Type;

    @FXML
    private Button addTransport;

    @FXML
    private TableColumn<MoyenDeTransport,String> Category;

    @FXML
    private TableColumn<MoyenDeTransport,Float> PricePerDay;

    @FXML
    private TableColumn<MoyenDeTransport,String> Mark;

    @FXML
    private TableColumn<MoyenDeTransport,Integer> ID;

    @FXML
    private TableView<MoyenDeTransport> table;
    @FXML
    private Button chooserFile;
    @FXML
    private ListView listV;
    @FXML
    private TextField listView;
    
    private int current_id;
    private String absolutePath;
    @FXML
    private Button update;

    @FXML
    private TextField image;

    @FXML
    private TextField prixParJour;

    @FXML
    private ComboBox<String> categorie;

    @FXML
    private ComboBox<String> type;
    
    @FXML
    TableColumn col_action;
    
    @FXML
    TableColumn col_action1;

    @FXML
    private TextField marque;
    ObservableList<String>list = FXCollections.observableArrayList("battery","gasoline","diesel");
    ObservableList<String>list1 = FXCollections.observableArrayList("Vehicule","motorCycle","Truck","Bike");
    @FXML
    private ImageView imageView;
    @FXML
    private Group ajout;
    
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
    MoyenDeTransportService ps = new MoyenDeTransportService();
    public ObservableList<MoyenDeTransport> obsl = FXCollections.observableArrayList(
            ps.afficher()
    );
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        MoyenDeTransportService ps= new MoyenDeTransportService();
//        ArrayList<MoyenDeTransport> a = new ArrayList<>();
//        a=(ArrayList<MoyenDeTransport>) ps.afficher();
//        ObservableList<MoyenDeTransport> obsl = FXCollections.observableArrayList(a);
       
        /**
         * * filter *
         */
        FilteredList<MoyenDeTransport> filteredData = new FilteredList<>(obsl, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super MoyenDeTransport>) MoyenDeTransport -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (MoyenDeTransport.getType().toLowerCase().contains(lower)||MoyenDeTransport.getCategorie().toLowerCase().contains(lower)||String.valueOf(MoyenDeTransport.getId()).contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<MoyenDeTransport> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        table.setItems(obsl);
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        PricePerDay.setCellValueFactory(new PropertyValueFactory<>("prixParJour"));
        Category.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        Mark.setCellValueFactory(new PropertyValueFactory<>("marque"));
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
        col_action1.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        col_action1.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell2();
            }

        });
        type.setItems(list);
        categorie.setItems(list1);
        
        table.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {

                MoyenDeTransport rowData = table.getSelectionModel().getSelectedItem();
                /**
                 * fill the fields with the selected data *
                 */

                prixParJour.setText(Float.toString(rowData.getPrixParJour()));
                marque.setText(rowData.getMarque());
                categorie.setValue(rowData.getCategorie());
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
            TableRow<MoyenDeTransport> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    MoyenDeTransport rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    prixParJour.setText(Float.toString(rowData.getPrixParJour()));
                    marque.setText(rowData.getMarque());
                    categorie.setValue(rowData.getCategorie());
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
                    //confirmation alert 
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("delete Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure ?");

                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        // get Selected Item
                        MoyenDeTransport selectedMoyenDeTransport = (MoyenDeTransport) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        MoyenDeTransportService ps = new MoyenDeTransportService();
                        //Piecesdefectueuses p = new Piecesdefectueuses(nom.getText(), combobox.getValue(), description.getText(), image.getText(), 1);
                        if(ps.deleteMoyenDeTransport(selectedMoyenDeTransport.getId())){
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Mean of Transport");
                            alert1.setHeaderText(null);
                            alert1.setContentText("Mean of transport succesfully deleted");
                            alert1.showAndWait();
                            //remove it from the tableView
                            obsl.remove(selectedMoyenDeTransport);
                            
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
                cellButton.setStyle("-fx-background-color: crimson;"
                    + "    -fx-text-fill: WHITE;");
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
    
    private class ButtonCell2 extends TableCell<Disposer.Record, Boolean> {

        final Button cellButton2 = new Button("Show");

        ButtonCell2() {

            //Action when the button is pressed
            cellButton2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    
                        // get Selected Item
                        MoyenDeTransport selectedH = (MoyenDeTransport) ButtonCell2.this.getTableView().getItems().get(ButtonCell2.this.getIndex());
                        Session.current_moyenDeTransport=selectedH;
                        AnchorPane pane;
                    try {
                        pane = FXMLLoader.load(getClass().getResource("/Gui/UnMoyenDeTransport.fxml"));
                        mainpane.getChildren().setAll(pane);
                    } catch (IOException ex) {
                        Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                }
            });
        }
        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setStyle("-fx-background-color: lightblue;"
                    + "    -fx-text-fill: WHITE;");
                setGraphic(cellButton2);
            } else {
                setGraphic(null);
            }
        }
        }
    
    @FXML
    public void goToAdd(ActionEvent event) throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addMoyenDeTransport.fxml"));
          mainpane.getChildren().setAll(pane);
    }
    
    @FXML
    public void goToAccommodations(ActionEvent event) throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Service.fxml"));
          mainpane.getChildren().setAll(pane);
    }
    
    public void updateTransport(ActionEvent event) throws IOException {
        if(controleDeSaisie()){
        MyConnection mc = MyConnection.getInstance();
        MoyenDeTransportService ps = new MoyenDeTransportService();
        MoyenDeTransport mt = new MoyenDeTransport(current_id, type.getValue(),Float.parseFloat(prixParJour.getText()) , absolutePath, marque.getText(), categorie.getValue());
        if(ps.updateMoyenDeTransport(mt)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mean of Transport");
            alert.setHeaderText(null);
            alert.setContentText("Mean of transport succesfully updated");
            alert.showAndWait();
            obsl.clear();
            obsl = FXCollections.observableArrayList(ps.afficher());
            table.setItems(obsl);
            }
        
            /**
             * refreshing the table view *
             */
        
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/MoyenDeTransport.fxml"));
//        mainpane.getChildren().setAll(pane);
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
                || type.getValue().isEmpty() || categorie.getValue().isEmpty() || absolutePath==null) {
            if (marque.getText().isEmpty()){
                marque.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (prixParJour.getText().isEmpty()){
                prixParJour.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (type.getValue()==null){
                type.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (categorie.getValue()==null){
                categorie.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            if (absolutePath==null){
                chooserFile.setStyle("-fx-border-color: red; -fx-background-color: white;");
            }
            showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Please Fill all the fields !");
            return false;
        } 

//           if (!Pattern.matches("^[\\p{L} .'-]+$", marque.getText()) ) {
//               showAlert(Alert.AlertType.ERROR,"Invalid data", "Verify your fields", "Verify the field Mark ! ");
//                marque.requestFocus();
//                marque.selectEnd();
//                marque.setStyle("-fx-border-color: red; -fx-background-color: white;");
////                marque.setFocusColor(color);
//                return false;
//            }
            if (!Pattern.matches("^[0-9]*\\.?[0-9]+$", prixParJour.getText())) {
                showAlert(Alert.AlertType.ERROR, "Invalid data", "Verify your fields", "Vérifiez The price Per Day field!");
                prixParJour.requestFocus();
                prixParJour.selectEnd();
                prixParJour.setStyle("-fx-border-color: red; -fx-background-color: white;");
                return false;
            }
        
        return true;
    }
}
