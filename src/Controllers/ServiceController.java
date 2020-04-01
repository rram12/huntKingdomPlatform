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
    ObservableList<String>list = FXCollections.observableArrayList("Caravane","flat","house");
    
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
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Hebergement>) Hebergement -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (Hebergement.getType().toLowerCase().contains(lower)||Hebergement.getAdresse().toLowerCase().contains(lower)||String.valueOf(Hebergement.getNbChambre()).contains(lower)) {
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
                        ps.deleteHebergement(selectedH.getId());
                        //remove it from the tableView
                        obsl.remove(selectedH);

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
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/Gui/addHebergement.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Add Accommodation");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        Stage stage = (Stage) table.getScene().getWindow();
//        stage.close();
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addHebergement.fxml"));
          mainpane.getChildren().setAll(pane);
    }
    @FXML
    public void updateHebergement(ActionEvent event) {
        MyConnection mc = MyConnection.getInstance();
        HebergementService ps = new HebergementService();
        float price=Float.parseFloat(prixParJour.getText());
        int nbch=Integer.parseInt(nbChambre.getText());
        int nblits=Integer.parseInt(capacite.getText());
        Hebergement mt = new Hebergement(current_id, type.getValue(),Float.parseFloat(prixParJour.getText()) , absolutePath,adresse.getText(),nbch,nblits,nblits,description.getText());
        ps.updateHebergement(mt);
        
        
        obsl.clear();
        obsl = FXCollections.observableArrayList(ps.afficher());
        table.setItems(obsl);
    }
}
